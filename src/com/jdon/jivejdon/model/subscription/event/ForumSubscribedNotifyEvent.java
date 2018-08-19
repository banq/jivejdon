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
package com.jdon.jivejdon.model.subscription.event;

import com.jdon.jivejdon.model.ForumMessage;

public class ForumSubscribedNotifyEvent implements SubscribedNotifyEvent {

	private final Long forumId;

	private final ForumMessage forumMessage;

	public ForumSubscribedNotifyEvent(Long forumId, ForumMessage forumMessage) {
		super();
		this.forumId = forumId;
		this.forumMessage = forumMessage;
	}

	public ForumMessage getForumMessage() {
		return forumMessage;
	}

	public Long getForumId() {
		return forumId;
	}

}
