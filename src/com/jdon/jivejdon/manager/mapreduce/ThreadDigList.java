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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageQueryService;

public class ThreadDigList {

	public final static int DigsListMAXSize = 15;

	private final ConcurrentHashMap<Long, Integer> digsCount;

	private Collection<ForumThread> sortedDigThreads = Collections
			.unmodifiableList(Collections.EMPTY_LIST);

	private final ForumMessageQueryService forumMessageQueryService;

	public ThreadDigList(ForumMessageQueryService forumMessageQueryService) {
		this.digsCount = new ConcurrentHashMap();
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public void addForumThread(ForumThread forumThread) {
		digsCount.put(forumThread.getThreadId(), forumThread.getRootMessage()
				.getDigCount());
	}

	public void populate() {
		TreeMap<Long, Integer> sorted_map = createTreeMap();
		sorted_map.putAll(digsCount);

		List newThreads = new ArrayList();
		int i = 0;
		for (Long threadId : sorted_map.keySet()) {
			newThreads.add(forumMessageQueryService.getThread(threadId));
			if (i > DigsListMAXSize) {
				break;
			}
			i++;

		}
		sortedDigThreads = Collections.unmodifiableList(newThreads);

	}

	public Collection<ForumThread> getDigs() {
		return sortedDigThreads;
	}

	private TreeMap<Long, Integer> createTreeMap() {
		return new TreeMap(new Comparator() {
			public int compare(Object num1, Object num2) {
				if (num1 == num2)
					return 0;
				Long thread1 = (Long) num1;
				Long thread2 = (Long) num2;

				int thread1Count = digsCount.get(thread1);
				int thread2Count = digsCount.get(thread2);
				if (thread1Count > thread2Count)
					return -1; // returning the first object
				else if (thread1Count < thread2Count)
					return 1;
				else if (thread1Count == thread2Count) {
					if (thread1 > thread2)
						return -1;
					else if (thread1 < thread2)
						return 1;
				}
				return 0;
			}

		});
	}
}
