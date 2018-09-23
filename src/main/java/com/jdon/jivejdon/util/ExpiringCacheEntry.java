/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.util;

import java.io.Serializable;

/**
 * A cache entry that expires.
 * 
 * We use this class to wrap objects being cached and associate a timestamp and
 * timeout period with them so we can know when they expire.
 */
public class ExpiringCacheEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;
	private long timeCached = -1;
	private long timeout = 0;

	public ExpiringCacheEntry(Object value, long timeout) {
		this.value = value;

		// make sure that we don't support negative values
		if (timeout > 0) {
			this.timeout = timeout;
		}

		this.timeCached = System.currentTimeMillis();
	}

	public long getTimeCached() {
		return this.timeCached;
	}

	public long getTimeout() {
		return this.timeout;
	}

	/**
	 * Retrieve the value of this cache entry.
	 * 
	 * If the value has expired then we return null.
	 */
	public Object getValue() {
		if (this.hasExpired()) {
			return null;
		} else {
			return this.value;
		}
	}

	public Object getOldValue() {
		return this.value;
	}

	/**
	 * Determine if this cache entry has expired.
	 */
	public boolean hasExpired() {

		long now = System.currentTimeMillis();

		return ((this.timeCached + this.timeout) < now);
	}

}