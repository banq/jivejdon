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
package com.jdon.jivejdon.manager.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * CQRS complex query using map reduce;
 *
 * @author banq
 */
@Component("threadApprovedNewList")
public class ThreadApprovedNewList implements Startable {
	public final static String NAME = "threadApprovedNewList";
	private final static Logger logger = LogManager
			.getLogger(ThreadApprovedNewList.class);
	public final Map<Integer, Collection<Long>> approvedThreadList;
	// author sort map
	private final AuthorList authorList;
	// dig sort map
	private final ThreadDigList threadDigList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final AccountService accountService;
	// private Cache approvedThreadList = new LRUCache("approvedCache.xml");
	private ApprovedListSpec approvedListSpec;
	private boolean refresh;

	public ThreadApprovedNewList(
			ForumMessageQueryService forumMessageQueryService,
			AccountService accountService) {
		approvedThreadList = new HashMap();
		approvedListSpec = new ApprovedListSpec();
		this.forumMessageQueryService = forumMessageQueryService;
		this.accountService = accountService;
		this.authorList = new AuthorList(accountService);
		this.threadDigList = new ThreadDigList(forumMessageQueryService);
	}

	public void start() {

		Runnable task = new Runnable() {
			public void run() {
				init();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 0,
				60 * 60 * 24 * 30, TimeUnit.SECONDS);

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
	}

	public Collection<Long> getApprovedThreads(int start) {
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

				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();
					if (approvedListSpec.getCurrentIndicator() > threadId
							|| approvedListSpec.getCurrentIndicator() == 0) {
						final ForumThread thread = forumMessageQueryService
								.getThread(threadId);
						Long userId = thread.getRootMessage().getAccount()
								.getUserIdLong();
						final Account account = accountService
								.getAccount(userId);
						if (approvedListSpec.isApproved(thread, account)
								&& i < approvedListSpec.getNeedCount()) {
							resultSorteds.add(thread.getThreadId());
							// map to sort account
							authorList.addAuthor(account);
							i++;
						}

						// map to sort dignumber near 120x10 day
						Date threadDate = new Date(thread.getRootMessage()
								.getModifiedDate2());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(threadDate);
						calendar.add(Calendar.HOUR, 28800);
						if (thread.getRootMessage().getDigCount() > 1
								&& calendar.getTime().after(nowD)) {
							threadDigList.addForumThread(thread);
						}

						if (i >= approvedListSpec.getNeedCount()) {
							approvedListSpec.setCurrentIndicator(threadId);
							approvedListSpec.setCurrentStartBlock(start);
							break;
						}
					}
				}
				start = start + count;
			}
			threadDigList.populate();
			authorList.populate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSorteds;
	}

	public boolean isRefresh() {
		return refresh;
	}

}
