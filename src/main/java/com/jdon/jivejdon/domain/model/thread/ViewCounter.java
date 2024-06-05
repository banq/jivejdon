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
import java.util.concurrent.atomic.AtomicInteger;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.util.LazyLoader;

public class ViewCounter extends LazyLoader implements Comparable<ViewCounter> {

	private final ForumThread thread;
	private AtomicInteger viewCount = new AtomicInteger(0);
	private int lastSavedCount;
	private String lastIP = "";
	private boolean load = false;
	

	public ViewCounter(ForumThread thread) {
		this.thread = thread;
		this.lastSavedCount = -1;
		this.lastIP = "";
	
	}

	public void loadinitCount() {
		if (!load) {
			Integer count = super.loadResult().map(value -> (Integer) value).orElse(null);
			if (count != null) {
				viewCount.addAndGet(count);
				this.lastSavedCount = count;
			}
			load = true;
		}
	}

	public int getViewCount() {
		loadinitCount();
		return this.viewCount.get();
	}

	public void addViewCount(String ip) {
		loadinitCount();
		if (!lastIP.equals(ip)) {
			viewCount.incrementAndGet();
			lastIP = ip;
		}

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

	@Override
	public DomainMessage getDomainMessage() {
		return this.thread.lazyLoaderRole.loadViewCount(thread.getThreadId());
	}

}
