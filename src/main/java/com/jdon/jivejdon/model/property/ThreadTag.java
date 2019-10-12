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
package com.jdon.jivejdon.model.property;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.model.subscription.SubscribedState;
import com.jdon.jivejdon.model.subscription.subscribed.TagSubscribed;

@Model
public class ThreadTag {

	private Long tagID;
	private String title;
	private int assonum;

	private SubscribedState subscribedState;

	@Inject
	private LazyLoaderRole domainEvents;

	public ThreadTag() {
		this.title = "";

	}

	public SubscribedState getSubscribedState() {
		if (this.subscribedState == null)
			this.subscribedState = new SubscribedState(new TagSubscribed(tagID, new Long(0)));
		return subscribedState;
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAssonum() {
		return assonum;
	}

	public void setAssonum(int assonum) {
		this.assonum = assonum;
	}

	/**
	 * ajax async invoke.
	 * 
	 * getLazyCount at first is called. and later getSubscriptionCount will be
	 * called, and then messageSubCount will have value.
	 * 
	 * @return
	 */
	public int getSubscriptionCount() {
		return getSubscribedState().getSubscribedCount(domainEvents);
	}

	public LazyLoaderRole getDomainEvents() {
		return domainEvents;
	}

	public void setDomainEvents(LazyLoaderRole domainEvents) {
		this.domainEvents = domainEvents;
	}

	public void updateSubscriptionCount(int count) {
		getSubscribedState().update(count);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || (this.getClass() != obj.getClass())) {
			return false;
		}
		ThreadTag t = (ThreadTag) obj;
		if (this.title.equalsIgnoreCase(t.getTitle())) {
			return true;
		}
		return false;

	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}

}
