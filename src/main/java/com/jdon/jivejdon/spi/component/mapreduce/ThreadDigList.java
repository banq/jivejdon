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

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;

/**
 * Like or Give the thumbs-up
 */
public class ThreadDigList {

	public final static int TIME_WINDOWS = 10000;

	private final SortedSet<Long> sortedAll;
	private final SortedSet<Long> sortedWindows;
	private final ForumMessageQueryService forumMessageQueryService;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public ThreadDigList(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
		this.sortedAll = new TreeSet<>(new ThreadDigComparator(forumMessageQueryService));
		this.sortedWindows = new TreeSet<>(new ThreadDigComparator(forumMessageQueryService));
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getCreationDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		readWriteLock.writeLock().lock(); // 加写锁
		try {
			if (daysBetween < TIME_WINDOWS) {
				sortedWindows.add(forumThread.getThreadId());
			}
			sortedAll.add(forumThread.getThreadId());
		} finally {
			readWriteLock.writeLock().unlock(); // 释放写锁
		}

	}

	public PageIterator getPageIterator(int start, int count) {
		List<Long> threads = new ArrayList<>();
		readWriteLock.readLock().lock();
		try {
			threads = sortedAll.stream().skip(start).limit(count).collect(Collectors.toList());
		} finally {
			readWriteLock.readLock().unlock();
		}
		return new PageIterator(sortedAll.size(), threads.toArray());
	}

	public PageIterator getRandomPageIterator(int count) {
		List<Long> threads = new ArrayList<>();
		readWriteLock.readLock().lock();
		for (int i = 0; i < count; i++) {
			int randstart = ThreadLocalRandom.current().nextInt(sortedAll.size());
			threads.addAll(sortedAll.stream().skip(randstart).limit(1).collect(Collectors.toList()));
		}
		readWriteLock.readLock().unlock();
		return new PageIterator(sortedAll.size(), threads.toArray());
	}

	public Collection<ForumThread> getDigs(int DigsListMAXSize) {
		if (sortedWindows.size() < DigsListMAXSize)
			DigsListMAXSize = sortedWindows.size();
		List<ForumThread> threads = new ArrayList<>();
		readWriteLock.readLock().lock();
		try {
			threads = sortedWindows.stream().limit(DigsListMAXSize).map(forumMessageQueryService::getThread)
					.collect(Collectors.toList());
		} finally {
			readWriteLock.readLock().unlock();
		}
		return threads;

	}

	public Collection<Long> getDigThreadIds(int DigsListMAXSize) {
		List<Long> threads = new ArrayList<>();
		readWriteLock.readLock().lock();
		try {
			threads = sortedWindows.stream().limit(DigsListMAXSize).collect(Collectors.toList());
		} finally {
			readWriteLock.readLock().unlock();
		}
		return threads;
	}

	public void clear() {
		sortedAll.clear();
		sortedWindows.clear();
	}

}
