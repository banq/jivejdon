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
package com.jdon.jivejdon.domain.model.thread;

import java.util.Objects;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import java.util.Queue;
import com.google.common.collect.EvictingQueue;

public class ViewCounter implements Comparable<ViewCounter> {

	private final ForumThread thread;
	private int viewCount = -1;
	private int lastSavedCount;
	Queue<String> fifo;
	Queue<String> fifo2;

	public ViewCounter(ForumThread thread) {
		this.thread = thread;
		this.lastSavedCount = -1;
		this.fifo = EvictingQueue.create(2);
		this.fifo2 = EvictingQueue.create(2);
	}

	public void loadinitCount() {
		if (this.viewCount != -1)
			return;
		DomainMessage dm = this.thread.lazyLoaderRole.loadViewCount(thread.getThreadId());
		try {
			// this.viewCount = 0;//flag it
			Integer count = (Integer) dm.getEventResult();
			if (count != null) {
				this.viewCount = count;
				this.lastSavedCount = count;
				dm.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getViewCount() {
		if (this.viewCount == -1) {
			loadinitCount();
		}
		return this.viewCount;
	}

	public void addViewCount(String ip) {
		if (getViewCount() != -1) 
			if (!fifo.contains(ip)){
                viewCount++;
				fifo.add(ip);
			}
				
		
	}

	public void removeViewCount(String ip) {
			fifo2.add(ip);
	}

	public boolean isIdempotent(String ip) {
		// if (!fifo.contains(ip))
		// 	return false;
		if (fifo2.contains(ip))
			return false;
		return true;
	}

	public int getLastSavedCount() {
		return lastSavedCount;
	}

	public void setLastSavedCount(int lastSavedCount) {
		this.lastSavedCount = lastSavedCount;
	}

	// public ForumThread getThread() {
	// return thread;
	// }

	public Long getThreadId() {
		return thread.getThreadId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ViewCounter viewCounter = (ViewCounter) o;
		if (this.thread.getThreadId() == null || viewCounter.getThreadId() == null)
			return false;
		return this.thread.getThreadId().longValue() == viewCounter.getThreadId().longValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.thread.getThreadId());
	}

	@Override
	public int compareTo(ViewCounter o) {
		int diff1 = getViewCount() - getLastSavedCount();
		int diff2 = o.getViewCount() - o.getLastSavedCount();
		if (diff1 == diff2) {
			if (thread.getThreadId() > o.getThreadId())
				return -1;
			else if (thread.getThreadId() < o.getThreadId())
				return 1;
		} else if (diff1 > diff2)
			return -1;
		else
			return 1;
		return 0;
	}

}
