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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class ThreadDigList {

	public final static int DigsListMAXSize = 30;

	private final TreeSet<Long> sorted_set;
	private final ForumMessageQueryService forumMessageQueryService;

	public ThreadDigList(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
		this.sorted_set = createTreeList();
	}

	public void addForumThread(ForumThread forumThread) {
		sorted_set.add(forumThread.getThreadId());
	}


	public PageIterator getPageIterator(int start, int count) {
		List<Long> threads = new ArrayList<>(sorted_set);
		List pageIds = new ArrayList(threads.size());
		for (int i = start; i < start + count; i++) {
			if (i < threads.size()) {
				pageIds.add(threads.get(i));
			} else
				break;
		}
		return new PageIterator(sorted_set.size(), pageIds.toArray());
	}

	public Collection<ForumThread> getDigs() {

		List newThreads = new ArrayList();
		int i = 0;
		for (Long threadId : sorted_set) {
			ForumThread thread = forumMessageQueryService.getThread(threadId);
			newThreads.add(thread);
			if (i > DigsListMAXSize) {
				break;
			}
			i++;

		}
		return Collections.unmodifiableList(newThreads);
	}

	private TreeSet<Long> createTreeList() {
		return new TreeSet(new Comparator<Long>() {
			public int compare(Long threadId1, Long threadId2) {
				if (threadId1 == threadId2)
					return 0;
				ForumThread thread1 = forumMessageQueryService.getThread(threadId1);
				ForumThread thread2 = forumMessageQueryService.getThread(threadId2);
				Integer thread1Count = thread1.getRootMessage().getDigCount();
				Integer thread2Count = thread2.getRootMessage().getDigCount();

				if (thread1Count > thread2Count)
					return -1; // returning the first object
				else if (thread1Count < thread2Count)
					return 1;
				else if (thread1Count == thread2Count) {
					if (threadId1 > threadId2)
						return -1;
					else
						return 1;
				}
				return 0;
			}

		});
	}
}
