/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * prepare all approved thread list
 *
 * @author banq
 */
@Component("threadApprovedNewList")
public class ThreadApprovedNewList implements Startable {

	public final static int maxSize = 15000;
	public final static String NAME = "threadApprovedNewList";
	private final static Logger logger = LogManager
			.getLogger(ThreadApprovedNewList.class);
	//key is start of one page,
	public final Map<Integer, Collection<Long>> approvedThreadList;
	// author sort map
	private final AuthorList authorList;
	// dig sort map, start is more greater, the dig collection is more greater.
	private final ThreadDigList threadDigList;
	private final ThreadTagList threadTagList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final AccountService accountService;
	// private Cache approvedThreadList = new LRUCache("approvedCache.xml");
	private ApprovedListSpec approvedListSpec;
	private boolean refresh;
	private int maxStart = -1;

	public ThreadApprovedNewList(
			ForumMessageQueryService forumMessageQueryService,
			AccountService accountService, TagService tagService) {
		approvedThreadList = new HashMap();
		approvedListSpec = new ApprovedListSpec();
		this.accountService = accountService;
		this.authorList = new AuthorList(accountService);
		this.threadDigList = new ThreadDigList(forumMessageQueryService);
		this.threadTagList = new ThreadTagList(tagService);
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public void start() {

		Runnable task = new Runnable() {
			public void run() {
				init();
				getApprovedThreads(maxSize);
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 60 * 60 * 6,
				60 * 60 * 3, TimeUnit.SECONDS);

	}

	public void stop() {
		try {
			ScheduledExecutorUtil.scheduExecStatic.shutdownNow();
		} catch (Exception e) {
		}
	}

	public void init() {
		approvedThreadList.clear();
		approvedListSpec = new ApprovedListSpec();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		approvedListSpec.setResultSort(resultSort);
		refresh = true;
		maxStart = -1;
		threadDigList.clear();
		authorList.clear();
		threadTagList.clear();
	}

	public ThreadTagList getThreadTagList() {
		return threadTagList;
	}

	public Collection<Long> getApprovedThreads(int start) {
		if (maxStart != -1 && start > maxStart) {
			return new ArrayList<>();
		}
		if (start % approvedListSpec.getNeedCount() != 0)
			return new ArrayList<>();
		if (approvedThreadList.containsKey(start)) {
			return approvedThreadList.get(start);
		}
		if (start < approvedListSpec.getCurrentStartPage()) {
			logger.error("start=" + start
					+ " < approvedListSpec.getCurrentStartPage()"
					+ approvedListSpec.getCurrentStartPage());
			return null;
		}
		return appendList(start, approvedListSpec);

	}

	public ThreadDigList getThreadDigList() {
		if (!approvedThreadList.containsKey(0))
			appendList(0, approvedListSpec);
		return this.threadDigList;

	}

	public AuthorList getAuthorList() {
		if (!approvedThreadList.containsKey(0))
			appendList(0, approvedListSpec);
		return this.authorList;

	}

	protected synchronized Collection<Long> appendList(int start,
													   ApprovedListSpec approvedListSpec) {
		if (approvedThreadList.containsKey(start)) {
			return approvedThreadList.get(start);
		}

		this.refresh = false;
		Collection<Long> resultSorteds = null;
		logger.debug("not found it in cache, create it");
		int count = approvedListSpec.getNeedCount();
		int i = approvedListSpec.getCurrentStartPage();
		while (i < start + count) {
			resultSorteds = loadApprovedThreads(approvedListSpec);
			approvedThreadList.put(i, resultSorteds);
			if (resultSorteds.size() < approvedListSpec.getNeedCount()) {
				if (maxStart == -1) {
					maxStart = i;
					break;
				}
			}
			i = i + count;
		}
		if (i > approvedListSpec.getCurrentStartPage())
			approvedListSpec.setCurrentStartPage(i);
		return resultSorteds;

	}

	public synchronized List<Long> loadApprovedThreads(
			ApprovedListSpec approvedListSpec) {
		List<Long> resultSorteds = new ArrayList(
				approvedListSpec.getNeedCount());
		final Date nowD = new Date();
		try {
			int i = 0;
			int start = approvedListSpec.getCurrentStartBlock();
			int count = 100;
			while (i < approvedListSpec.getNeedCount()) {
				PageIterator pi = forumMessageQueryService.getThreads(start,
						count, approvedListSpec);
				if (!pi.hasNext())
					break;

				ForumThread threadPrev = null;	
				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();					
					if (approvedListSpec.getCurrentIndicator() > threadId
							|| approvedListSpec.getCurrentIndicator() == 0) {
						final ForumThread thread = forumMessageQueryService
								.getThread(threadId);																						
						if (thread == null) continue;
						Long userId = thread.getRootMessage().getAccount()
								.getUserIdLong();
						final Account account = accountService
								.getAccount(userId);

						if (approvedListSpec.isApprovedToBest(thread, account, i, threadPrev)){
							resultSorteds.add(thread.getThreadId());
							// map to sort account
							authorList.addAuthor(account);
							threadDigList.addForumThread(thread);
							i++;
						}
						threadTagList.addForumThread(thread);
						threadPrev = thread;

						if (i >= approvedListSpec.getNeedCount()) {
							approvedListSpec.setCurrentIndicator(threadId);
							approvedListSpec.setCurrentStartBlock(start);
							break;
						}
					}
				}
				start = start + count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSorteds;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public int getMaxSize() {
		if (maxStart != -1)
			return maxStart + approvedListSpec.getNeedCount();
		else
			return maxStart;
	}
}
