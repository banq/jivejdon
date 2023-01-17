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

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Like or Give the thumbs-up
 */
public class ThreadDigList {

	public final static int TIME_WINDOWS = 10000;

	private final SortedSet<Long> sortedAll;
	private final SortedSet<Long> sortedWindows;
	private final ForumMessageQueryService forumMessageQueryService;

	public ThreadDigList(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
		this.sortedAll = Collections.synchronizedSortedSet(new TreeSet<>(new ThreadDigComparator(forumMessageQueryService)));
		this.sortedWindows = Collections.synchronizedSortedSet(new TreeSet<>(new ThreadDigComparator(forumMessageQueryService)));
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getCreationDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		if (daysBetween < TIME_WINDOWS) {
			sortedWindows.add(forumThread.getThreadId());
		}
		sortedAll.add(forumThread.getThreadId());
	}

	public PageIterator getPageIterator(int start, int count) {
		List<Long> threads = sortedAll.stream().skip(start).limit(count).collect(Collectors.toList());
		return new PageIterator(sortedAll.size(), threads.toArray());
	}

	public PageIterator getRandomPageIterator(int count) {
		List<Long> threads = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			int randstart = ThreadLocalRandom.current().nextInt(sortedAll.size());
			threads.addAll(sortedAll.stream().skip(randstart).limit(1).collect(Collectors.toList()));
		}
		return new PageIterator(sortedAll.size(), threads.toArray());
	}

	public Collection<ForumThread> getDigs(int DigsListMAXSize) {
		return sortedWindows.parallelStream().limit(DigsListMAXSize).map(forumMessageQueryService::getThread)
				.collect(Collectors.toList());

	}

	public Collection<Long> getDigThreadIds(int DigsListMAXSize) {
		return sortedWindows.stream().limit(DigsListMAXSize).collect(Collectors.toList());

	}

	public void clear() {
		sortedAll.clear();
		sortedWindows.clear();
	}

}
