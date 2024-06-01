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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

/**
 * Home Mixer
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
	public final ConcurrentHashMap<Integer, Collection<Long>> approvedThreadList;
	// author sort map
	private final AuthorList authorList;
	// dig sort map, start is more greater, the dig collection is more greater.
	private final ThreadDigList threadDigList;
	private final ThreadTagList threadTagList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final AccountService accountService;
	// private Cache approvedThreadList = new LRUCache("approvedCache.xml");
	private final ApprovedListSpec approvedListSpec = new ApprovedListSpec();
	private int maxStart = -1;
	private	long currentIndicator = 0;
	private	int currentStartBlock = 0;
	private int currentStartPage = 0;

	public ThreadApprovedNewList(
			ForumMessageQueryService forumMessageQueryService,
			AccountService accountService, TagService tagService) {
		approvedThreadList = new ConcurrentHashMap<>();
		this.accountService = accountService;
		this.authorList = new AuthorList(accountService);
		this.threadDigList = new ThreadDigList(forumMessageQueryService);
		this.threadTagList = new ThreadTagList(tagService, forumMessageQueryService);
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public void start() {

		Runnable task = new Runnable() {
			public void run() {
				init();
				getApprovedThreads(maxSize);
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 60 * 60 * 5,
				60 * 60 * 1, TimeUnit.SECONDS);

	}

	public void stop() {
		try {
			ScheduledExecutorUtil.scheduExecStatic.shutdownNow();
		} catch (Exception e) {
		}
	}

	public void init() {
		approvedThreadList.clear();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		approvedListSpec.setResultSort(resultSort);
		maxStart = -1;
		currentIndicator = 0;
	    currentStartBlock = 0;
	    currentStartPage = 0;
		threadDigList.clear();
		authorList.clear();
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
		if (start < currentStartPage) {
			logger.error("start=" + start
					+ " < approvedListSpec.getCurrentStartPage()"
					+ currentStartPage);
			return new ArrayList<>();
		}
		return initApprovedList( start, approvedListSpec);
	}

	public ThreadDigList getThreadDigList() {
		if (!approvedThreadList.containsKey(0))
			initApprovedList(0, approvedListSpec);
		return this.threadDigList;

	}

	public AuthorList getAuthorList() {
		if (!approvedThreadList.containsKey(0))
			initApprovedList(0, approvedListSpec);
		return this.authorList;

	}

	protected Collection<Long> initApprovedList(int start,
			ApprovedListSpec approvedListSpec) {
		Collection<Long> resultSorteds = new ArrayList<>();
		logger.debug("not found it in cache, create it");
		int count = approvedListSpec.getNeedCount();
		int i = currentStartPage;
		while (i < start + count) {
			resultSorteds = approvedThreadList.computeIfAbsent(i, k->loadApprovedThreads(approvedListSpec));
			if (resultSorteds.size() < approvedListSpec.getNeedCount()) {
				if (maxStart == -1) {
					maxStart = i;
					break;
				}
			}
			i = i + count;
		}
		if (i > currentStartPage)
			currentStartPage = i;
		return resultSorteds;
	}

	/**
	 * CompletableFuture.supplyAsync
	 * @param approvedListSpec
	 * @return
	 */
	public List<Long> loadApprovedThreads(ApprovedListSpec approvedListSpec) {
		List<Long> resultSorteds = new CopyOnWriteArrayList<>();
		try {
			AtomicInteger i = new AtomicInteger(0);
			int start = currentStartBlock;
			int count = 100;
			int allCount =  forumMessageQueryService.getThreads(0, 1, approvedListSpec).getAllCount();

			while (i.get() < approvedListSpec.getNeedCount() && start < allCount) {
				PageIterator pi = forumMessageQueryService.getThreads(start,
						count, approvedListSpec);
				if (!pi.hasNext())
					break;

				ForumThread threadPrev = null;
				ForumThread threadPrev2 = null;
				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();
					if (currentIndicator > threadId
							|| currentIndicator == 0) {
						final ForumThread thread = forumMessageQueryService
								.getThread(threadId);
						if (thread == null || thread.getRootMessage() == null)
							continue;
						Account account = thread.getRootMessage().getAccount();

						final ForumThread threadPrevP = threadPrev;
						final ForumThread threadPrev2P = threadPrev2;
						CompletableFuture.supplyAsync(
								() -> approvedListSpec.isApprovedToBest(thread, i.get(), threadPrevP, threadPrev2P))
								.thenAccept(isApproved -> {
									if (isApproved) {
										resultSorteds.add(thread.getThreadId());
										// map to sort account
										authorList.addAuthor(account);
										threadDigList.addForumThread(thread);
										i.incrementAndGet();
									}
								});

						threadTagList.addForumThread(thread);
						threadPrev2 = threadPrev;
						threadPrev = thread;

						if (i.get() >= approvedListSpec.getNeedCount()) {
							currentIndicator = threadId;
							currentStartBlock = start;
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



	public int getMaxSize() {
		if (maxStart != -1)
			return maxStart + approvedListSpec.getNeedCount();
		else
			return maxStart;
	}
}
