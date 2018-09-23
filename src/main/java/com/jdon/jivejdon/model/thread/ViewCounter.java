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
package com.jdon.jivejdon.model.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumThread;

public class ViewCounter {

	private AtomicInteger viewCount;
	private final ForumThread thread;
	private int lastSavedCount;
	private final int fixedSize = 5;
	private final List lastViewIPDeque;

	public ViewCounter(ForumThread thread) {
		this.thread = thread;
		this.viewCount = null;
		this.lastSavedCount = -1;
		this.lastViewIPDeque = new ArrayList(fixedSize);
	}

	private void loadinitCount() {
		DomainMessage dm = this.thread.lazyLoaderRole.loadViewCount(thread.getThreadId());
		Integer count;
		try {
			// synchronized (this) {
			if (this.viewCount != null)
				return;
			count = (Integer) dm.getEventResult();
			if (count != null) {
				this.viewCount = new AtomicInteger(count);
				this.lastSavedCount = count;
				dm.clear();
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getViewCount() {
		if (this.viewCount == null) {
			loadinitCount();
		}
		if (this.viewCount != null)
			return viewCount.intValue();
		else
			return -1;
	}

	public void addViewCount() {
		if (getViewCount() != -1)
			viewCount.incrementAndGet();
	}

	public boolean isContains(String ip) {
		return lastViewIPDeque.contains(ip);
	}

	public void addViewCount(String ip) {
		if (!lastViewIPDeque.contains(ip)) {
			addViewCount();
			addLastViewIPDeque(ip);
		}
	}

	private void addLastViewIPDeque(Object o) {
		if (lastViewIPDeque.size() >= fixedSize) {
			lastViewIPDeque.remove(fixedSize-1);
		}
		lastViewIPDeque.add(o);
	}

	public long getLastSavedCount() {
		return lastSavedCount;
	}

	public void setLastSavedCount(int lastSavedCount) {
		this.lastSavedCount = lastSavedCount;
	}

	public ForumThread getThread() {
		return thread;
	}

}
