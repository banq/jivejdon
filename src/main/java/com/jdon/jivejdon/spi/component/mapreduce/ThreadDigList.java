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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;

/**
 * Like or Give the thumbs-up
 */
public class ThreadDigList implements Startable{

	public final static int TIME_WINDOWS = 100;

	private final SortedSet<Long> sortedWindows;

	public ThreadDigList(ForumMessageQueryService forumMessageQueryService) {
		this.sortedWindows = new ConcurrentSkipListSet<>(new ThreadDigComparator(forumMessageQueryService));
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getRootMessage().getModifiedDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		if (daysBetween < TIME_WINDOWS) {
			sortedWindows.add(forumThread.getThreadId());
		}
	}


	public Collection<Long> getDigThreadIds(int DigsListMAXSize) {
		List<Long> threads = new CopyOnWriteArrayList<Long>(sortedWindows);
		return threads.stream().limit(DigsListMAXSize).collect(Collectors.toList());
	}

	public void clear() {
		sortedWindows.clear();
	}

	@Override
	public void start() {
	 
	}

	@Override
	public void stop() {
		 clear();
	}

}
