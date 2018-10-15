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

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageQueryService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ThreadDigList {

	public final static int DigsListMAXSize = 30;

	private final static int TIME_WINDOWS = 100;

	private final static int TAGSLIST_SIZE = 10;
	private final TreeSet<Long> sortedAll;
	private final TreeSet<Long> sortedWindows;
	private final ForumMessageQueryService forumMessageQueryService;

	public ThreadDigList(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
		this.sortedAll = createTreeList();
		this.sortedWindows = createTreeList();
		;
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getState().getModifiedDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		if (daysBetween < TIME_WINDOWS) {
			sortedWindows.add(forumThread.getThreadId());
		}
		sortedAll.add(forumThread.getThreadId());
	}

	public PageIterator getPageIterator(int start, int count) {
		List<Long> threads = sortedAll.stream().skip(start).limit(count).collect(Collectors.toList
				());
		return new PageIterator(sortedAll.size(), threads.toArray());
	}

	public Collection<ForumThread> getDigs() {
		return sortedWindows.stream().limit(DigsListMAXSize).map
				(forumMessageQueryService::getThread)
				.collect(Collectors.toList());

	}


	public void clear() {
		sortedAll.clear();
		sortedWindows.clear();
	}

	private TreeSet<Long> createTreeList() {
		return new TreeSet<Long>(new Comparator<Long>() {
			public int compare(Long threadId1, Long threadId2) {
				if (threadId1.longValue() == threadId2.longValue())
					return 0;
				ForumThread thread1 = forumMessageQueryService.getThread(threadId1);
				ForumThread thread2 = forumMessageQueryService.getThread(threadId2);
				int thread1Count = thread1.getRootMessage().getDigCount();
				int thread2Count = thread2.getRootMessage().getDigCount();

				if (thread1Count > thread2Count)
					return -1; // returning the first object
				else if (thread1Count < thread2Count)
					return 1;
				else {
					if (threadId1.longValue() > threadId2.longValue())
						return -1;
					else
						return 1;
				}
			}

		});
	}


}
