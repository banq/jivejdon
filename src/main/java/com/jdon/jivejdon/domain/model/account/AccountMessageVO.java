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
package com.jdon.jivejdon.domain.model.account;

import java.util.concurrent.atomic.AtomicInteger;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.util.LazyLoader;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

public class AccountMessageVO extends LazyLoader {

	private final AtomicInteger messageCount = new AtomicInteger(-1);

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
		int count = messageCount.get();
		if (count == -1) {
			// 使用循环以确保在并发情况下正确设置
			while (!messageCount.compareAndSet(-1, loadMessageCount())) {
				// 如果更新失败，说明其他线程已经修改了值，重新读取当前值
				count = messageCount.get();
				if (count != -1) {
					return count;
				}
			}
			// 更新成功后读取最新的值
			count = messageCount.get();
		}
		return count;
	}

	public int loadMessageCount(){
		return super.loadResult().map(value -> (Integer) value).orElse(0);
	}

	public DomainMessage getDomainMessage() {
		return lazyLoaderRole.loadAccountMessageCount(accountId);
	}


	public void update(int count) {
		if (messageCount.get() != -1) {
			messageCount.addAndGet(count);
		}

	}
}
