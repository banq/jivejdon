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
package com.jdon.jivejdon.domain.model.subscription.subscribed;

import com.jdon.jivejdon.domain.model.Forum;

public class ForumSubscribed implements Subscribed {

	public final static int TYPE = 0;

	private Forum forum;

	private final long subscribedId;

	public ForumSubscribed(long subscribedId) {
		super();
		this.subscribedId = subscribedId;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@Override
	public Long getSubscribeId() {
		return subscribedId;
	}

	@Override
	public int getSubscribeType() {
		return ForumSubscribed.TYPE;
	}

	@Override
	public void updateSubscriptionCount(int count) {
		forum.getForumState().updateSubscriptionCount(count);

	}

	@Override
	public String getName() {
		if (forum != null)
			return forum.getName();
		else
			return "";
	}

}
