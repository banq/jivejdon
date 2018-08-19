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
package com.jdon.jivejdon.model.account;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.model.util.LazyLoader;

public class AccountMessageVO extends LazyLoader {

	private int messageCount = -1;

	private long accountId;

	private LazyLoaderRole lazyLoaderRole;

	public AccountMessageVO(long accountId, LazyLoaderRole lazyLoaderRole) {
		super();
		this.accountId = accountId;
		this.lazyLoaderRole = lazyLoaderRole;
	}

	/**
	 * 1.first times preload
	 * 
	 * 2.second times get the result;
	 * 
	 * @param domainEvents
	 * @return
	 */
	public int getMessageCount() {
		if (messageCount == -1) {
			if (super.domainMessage != null) {
				messageCount = (Integer) super.loadResult();
			} else {
				super.preload();
			}
		}
		return messageCount;
	}

	public DomainMessage getDomainMessage() {
		return lazyLoaderRole.loadAccountMessageCount(accountId);
	}

	public int getMessageCountNow() {
		if (messageCount == -1) {
			messageCount = (Integer) super.loadResult();
		}
		return messageCount;
	}

	public void update(int count) {
		if (messageCount != -1) {
			messageCount = messageCount + count;
		}

	}
}
