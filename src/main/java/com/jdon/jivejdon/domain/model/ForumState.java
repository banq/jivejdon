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

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.subscription.SubscribedState;
import com.jdon.jivejdon.domain.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;

/**
 * Forum State ValueObject this is a embeded class in Forum.
 * 
 * state is a value Object of DDD, and is immutable
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumState  {
	private  AtomicLong threadCount ;

	/**
	 * the number of messages in the thread. This includes the root message. So,
	 * to find the number of replies to the root message, subtract one from the
	 * answer of this method.
	 */
	private  AtomicLong messageCount;

	private AtomicReference<ForumMessage> latestPost;

	private final Forum forum;

	private SubscribedState subscribedState;

	public ForumState(Forum forum) {
		super();
		this.forum = forum;
		this.messageCount = new AtomicLong(Integer.MIN_VALUE);
		this.threadCount = new AtomicLong(Integer.MIN_VALUE);
		this.latestPost = new AtomicReference<>();
		
	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		if (messageCount.get() == Integer.MIN_VALUE)
			synchronized (messageCount) {
				if (messageCount.get() == Integer.MIN_VALUE)
					loadinitState();
			}
		return (int) messageCount.get();
	}

	public long addMessageCount() {
        getMessageCount();
		return this.messageCount.incrementAndGet();
	}

	/**
	 * @return Returns the threadCount.
	 */
	public int getThreadCount() {
		if (threadCount.longValue() == Integer.MIN_VALUE)
			synchronized (threadCount) {
				if (threadCount.longValue() == Integer.MIN_VALUE)
					loadinitState();
			}
		return threadCount.intValue();
	}

	public long addThreadCount() {
		getThreadCount();
		return this.threadCount.incrementAndGet();
	}

	public ForumMessage getLatestPost() {
		if (this.latestPost.get() == null)
			synchronized (latestPost) {
				if (this.latestPost.get() == null)
					loadinitState();
			}
		return latestPost.get();
	}

	public void setLatestPost(ForumMessage forumMessage) {
		getLatestPost();
		this.latestPost.set(forumMessage);
	}

	public Forum getForum() {
		return forum;
	}

	public void updateSubscriptionCount(int count) {
		getSubscribedState().update(count);
	}

	public SubscribedState getSubscribedState() {
		if (subscribedState == null && forum.getForumId() != null )
			subscribedState = new SubscribedState(new ForumSubscribed(forum.getForumId()));
		return subscribedState;
	}

	public String getModifiedDate() {
		if (getLatestPost() != null)
			return getLatestPost().getModifiedDate();
		else
			return "";
	}

	public void loadinitState() {
		DomainMessage dm = this.forum.lazyLoaderRole.loadForumState(forum.getForumId());
		OneOneDTO oneOneDTO = null;
		try {
			// synchronized (this) {
			 if (messageCount.get() != Integer.MIN_VALUE)
                return;
			oneOneDTO = (OneOneDTO) dm.getEventResult();
			if (oneOneDTO != null) {
				OneOneDTO oneOneDTO2 = (OneOneDTO) oneOneDTO.getParent();
				if(oneOneDTO2 != null){
				   threadCount.set((Long)oneOneDTO.getChild());
				   latestPost.set((ForumMessage) oneOneDTO2.getParent());
				   messageCount.set((Long) oneOneDTO2.getChild());
				}
				dm.clear();
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
