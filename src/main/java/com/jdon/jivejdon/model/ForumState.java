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
package com.jdon.jivejdon.model;

import java.util.concurrent.atomic.AtomicLong;

import com.jdon.jivejdon.model.subscription.SubscribedState;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;

/**
 * Forum State ValueObject this is a embeded class in Forum.
 * 
 * state is a value Object of DDD, and is immutable
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumState {
	private final AtomicLong threadCount;

	/**
	 * the number of messages in the thread. This includes the root message. So,
	 * to find the number of replies to the root message, subtract one from the
	 * answer of this method.
	 */
	private final AtomicLong messageCount;

	private final ForumMessage lastPost;

	private final Forum forum;

	private SubscribedState subscribedState;

	public ForumState(Forum forum, ForumMessage lastPost, long messageCount, long threadCount) {
		super();
		this.forum = forum;
		this.lastPost = lastPost;
		this.messageCount = new AtomicLong(messageCount);
		this.threadCount = new AtomicLong(threadCount);

	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		return messageCount.intValue();
	}

	public long addMessageCount() {
		return this.messageCount.incrementAndGet();
	}

	/**
	 * @return Returns the threadCount.
	 */
	public int getThreadCount() {
		return threadCount.intValue();
	}

	public long addThreadCount() {
		return this.threadCount.incrementAndGet();
	}

	public ForumMessage getLastPost() {
		return lastPost;
	}

	public Forum getForum() {
		return forum;
	}

	public void updateSubscriptionCount(int count) {
		getSubscribedState().update(count);
	}

	public SubscribedState getSubscribedState() {
		if (subscribedState == null)
			subscribedState = new SubscribedState(new ForumSubscribed(forum.getForumId()));
		return subscribedState;
	}

	public int getSubscriptionCount() {
		return getSubscribedState().getSubscribedCount(this.forum.lazyLoaderRole);
	}

	public String getModifiedDate() {
		if (getLastPost() != null)
			return getLastPost().getModifiedDate();
		else
			return "";
	}
}
