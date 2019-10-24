/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.domain.model;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.subscription.SubscribedState;
import com.jdon.jivejdon.domain.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;

import java.util.concurrent.atomic.AtomicLong;

/**
 * State is a Value Object, it is immutable. need a pattern to keep immutable.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumThreadState {
	private final ForumThread forumThread;
	/**
	 * the number of messages in the thread. This includes the root message. So,
	 * to find the number of replies to the root message, subtract one from the
	 * answer of this method.
	 */
	private AtomicLong messageCount;
	private ForumMessage latestPost;
	private SubscribedState subscribedState;

	public ForumThreadState(ForumThread forumThread, ForumMessage latestPost, long messageCount) {
		super();
		this.forumThread = forumThread;
		this.latestPost = latestPost;
		this.messageCount = new AtomicLong(messageCount);

	}

	public ForumThreadState(ForumThread forumThread) {
		super();
		this.forumThread = forumThread;
		this.latestPost = null;
		this.messageCount = null;
	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		if (this.messageCount == null)
			projectStateFromEventSource();
		if (messageCount != null)
			return messageCount.intValue();
		return -1;
	}

	public long addMessageCount() {
		if (this.messageCount == null)
			projectStateFromEventSource();
		if (getMessageCount() != -1)
			return messageCount.incrementAndGet();
		else
			return 0;
	}

	public ForumMessage getLatestPost() {
		if (this.latestPost == null) {
			projectStateFromEventSource();
		}
		return latestPost;
	}

	public void setLatestPost(ForumMessage latestPost) {
		if (this.latestPost == null) {
			projectStateFromEventSource();
		}
		this.latestPost = latestPost;
	}

	public void projectStateFromEventSource() {
		DomainMessage dm = this.forumThread.lazyLoaderRole.projectStateFromEventSource(forumThread.getThreadId());
		OneOneDTO oneOneDTO = null;
		try {
			oneOneDTO = (OneOneDTO) dm.getEventResult();
			if (oneOneDTO != null) {
				latestPost = (ForumMessage) oneOneDTO.getParent();
				messageCount = new AtomicLong((Long) oneOneDTO.getChild());
				dm.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean latestPostIsNull() {
		return latestPost == null ? true : false;
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public String getModifiedDate() {
		if (getLatestPost() != null)
			return getLatestPost().getModifiedDate();
		else
			return "";
	}

	public long getModifiedDate2() {
		if (getLatestPost() != null)
			return getLatestPost().getModifiedDate2();
		else
			return 0;
	}

	public SubscribedState getSubscribedState() {
		if (this.subscribedState == null)
			this.subscribedState = new SubscribedState(new ThreadSubscribed(forumThread.getThreadId()));
		return this.subscribedState;
	}

	public int getSubscriptionCount() {
		return getSubscribedState().getSubscribedCount(this.forumThread.lazyLoaderRole);
	}

	public void updateSubscriptionCount(int count) {
		getSubscribedState().update(count);
	}

}
