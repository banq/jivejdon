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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.util.LazyLoader;

public class ViewCounter extends LazyLoader implements Comparable<ViewCounter> {

	private final ForumThread thread;
	private final AtomicInteger viewCount = new AtomicInteger(0);
	private final AtomicBoolean dirty = new AtomicBoolean(false);
	private final AtomicReference<String> lastIP = new AtomicReference<>("");
	// 懒加载优化：用 volatile boolean loaded 替代 AtomicBoolean load
	private volatile boolean loaded = false;
	

	public ViewCounter(ForumThread thread) {
		this.thread = thread;
	}

	public void loadinitCount() {
		if (!loaded) {
			synchronized (this) {
				if (!loaded) {
					Integer count = super.loadResult().map(value -> (Integer) value).orElse(null);
					if (count != null) {
						viewCount.addAndGet(count);
					}
					loaded = true;
				}
			}
		}
	}

	public int getViewCount() {
		loadinitCount();
		return this.viewCount.get();
	}

	public void addViewCount(String ip) {
		try {
			loadinitCount();
			if (!Objects.equals(this.lastIP.get(), ip)) {
				viewCount.incrementAndGet();
				this.lastIP.set(ip);
				dirty.set(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isDirty() {
		return dirty.get();
	}

	public void clearDirty() {
		dirty.set(false);
	}
	// public ForumThread getThread() {
	// return thread;
	// }

	public Long getThreadId() {
		return thread.getThreadId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ViewCounter viewCounter = (ViewCounter) o;
		return Objects.equals(this.thread.getThreadId(), viewCounter.getThreadId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.thread.getThreadId());
	}

	@Override
	public int compareTo(ViewCounter o) {
		int diff = Integer.compare(o.getViewCount(), this.getViewCount());
		if (diff != 0)
			return diff;
		// 浏览量相同，按 threadId 降序
		return Long.compare(o.getThreadId(), this.getThreadId());
	}

	@Override
	public DomainMessage getDomainMessage() {
		return this.thread.lazyLoaderRole.loadViewCount(thread.getThreadId());
	}

}
