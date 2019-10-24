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

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.ForumThread;

public class ViewCounter {

	private final ForumThread thread;
	private int viewCount = -1;
	private int lastSavedCount;

	public ViewCounter(ForumThread thread) {
		this.thread = thread;
		this.lastSavedCount = -1;
	}

	public void loadinitCount() {
		if (this.viewCount != -1) return;
		DomainMessage dm = this.thread.lazyLoaderRole.loadViewCount(thread.getThreadId());
		try {
//			this.viewCount = 0;//flag it
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

	public void addViewCount() {
		if (getViewCount() != -1)
			viewCount++;
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
