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
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
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

	public final static int maxSize = 500;
	public final static String NAME = "threadApprovedNewList";
	private final static Logger logger = LogManager
			.getLogger(ThreadApprovedNewList.class);
	// 只缓存完整的 threadId 列表
	private volatile List<Long> approvedThreadIdList = new CopyOnWriteArrayList<>();
	private final ThreadTagList threadTagList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final AccountService accountService;
	private final ApprovedListSpec approvedListSpec = new ApprovedListSpec();
	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadApprovedNewList(
			ForumMessageQueryService forumMessageQueryService,
			AccountService accountService, TagService tagService,
			ScheduledExecutorUtil scheduledExecutorUtil) {
		this.accountService = accountService;
		this.threadTagList = new ThreadTagList(tagService, forumMessageQueryService);
		this.forumMessageQueryService = forumMessageQueryService;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				init();
				refreshApprovedThreadIdList();
			}
		};
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, 0, 30 * 60, TimeUnit.SECONDS); 
	}

	public void init() {
		approvedThreadIdList.clear();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		approvedListSpec.setResultSort(resultSort);
	}

	public ThreadTagList getThreadTagList() {
		return threadTagList;
	}

	/**
	 * 获取分页后的 threadId 列表
	 */
	public List<Long> getApprovedThreads(int start, int count) {
		if (start < 0 || start >= approvedThreadIdList.size()) {
			return new ArrayList<>();
		}
		int end = Math.min(start + count, approvedThreadIdList.size());
		return approvedThreadIdList.subList(start, end);
	}

	/**
	 * 刷新完整的 threadId 列表缓存
	 */
	public void refreshApprovedThreadIdList() {
		List<Long> newList = loadApprovedThreads(approvedListSpec);
		approvedThreadIdList = new CopyOnWriteArrayList<>(newList);
	}

	/**
	 * 加载所有 approved threadId
	 */
	public List<Long> loadApprovedThreads(ApprovedListSpec approvedListSpec) {
		List<ForumThread> resultSorteds = new ArrayList<>();

		try {
			int start = 0;
			int count = 100;
			while (resultSorteds.size() < maxSize) {
				PageIterator pi = forumMessageQueryService.getThreads(start, count, approvedListSpec);
				if (!pi.hasNext())
					break;

				while (pi.hasNext()) {

					Long threadId = (Long) pi.next();
					ForumThread thread = forumMessageQueryService.getThread(threadId);
					if (thread == null || thread.getRootMessage() == null)
						continue;

					if (approvedListSpec.isApprovedToBest(thread)) {
						resultSorteds.add(thread);
						threadTagList.addForumThread(thread);
						if (resultSorteds.size() >= maxSize)
							break;
					}
				}
				start += count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSorteds.stream().map(ForumThread::getThreadId).collect(Collectors.toList());
	}

	public int getMaxSize() {
		return approvedThreadIdList.size();
	}

	@Override
	public void stop() {
		init();
	}
}
