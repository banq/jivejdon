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
package com.jdon.jivejdon.model.subscription;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.model.util.LazyLoader;
import com.jdon.jivejdon.model.util.OneOneDTO;

/**
 * // the field is not the core field of ThreadTag, it's value is fetched by //
 * query, not by aggregation
 * 
 * @author banq
 * 
 */
public class SubscribedState extends LazyLoader {

	// 订阅 following
	private int subscriptionCount = -1;

	// 被订阅 follower
	private int subscribedCount = -1;

	private Subscribed subscribed;

	private LazyLoaderRole lazyLoaderRole;

	public SubscribedState(Subscribed subscribed) {
		super();
		this.subscribed = subscribed;
	}

	/**
	 * ajax async invoke. call this method for two times.
	 * 
	 * 
	 * @return
	 */
	public int getSubscriptionCount(LazyLoaderRole lazyLoaderRole) {
		if (subscriptionCount == -1 && this.lazyLoaderRole == null) {
			this.lazyLoaderRole = lazyLoaderRole;
			super.preload();
		} else if (subscriptionCount == -1 && this.lazyLoaderRole != null) {
			OneOneDTO oneOneDTO = (OneOneDTO) super.loadResult();
			subscriptionCount = (Integer) oneOneDTO.getParent();
			subscribedCount = (Integer) oneOneDTO.getChild();
		}
		return subscriptionCount;
	}

	public int getSubscribedCount(LazyLoaderRole lazyLoaderRole) {
		if (subscribedCount == -1 && this.lazyLoaderRole == null) {
			this.lazyLoaderRole = lazyLoaderRole;
			super.preload();
		} else if (subscribedCount == -1 && this.lazyLoaderRole != null) {
			OneOneDTO oneOneDTO = (OneOneDTO) super.loadResult();
			subscriptionCount = (Integer) oneOneDTO.getParent();
			subscribedCount = (Integer) oneOneDTO.getChild();
		}
		return subscribedCount;
	}

	public void update(int count) {
		if (subscriptionCount != -1) {
			subscriptionCount = subscriptionCount + count;
		}

	}

	public DomainMessage getDomainMessage() {
		OneOneDTO oneOneDTO = new OneOneDTO(subscribed.getSubscribeId(), subscribed.getSubscribeType());
		return lazyLoaderRole.loadSubscriptionNumbers(oneOneDTO);
	}

}
