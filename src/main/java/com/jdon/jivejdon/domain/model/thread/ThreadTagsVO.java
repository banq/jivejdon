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
package com.jdon.jivejdon.domain.model.thread;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.subscription.event.TagSubscribedNotifyEvent;

import java.util.Collection;

public class ThreadTagsVO {

	// same as rooMessage 's tags, but this is Collection
	private final Collection<ThreadTag> tags;
	private ForumThread forumThread;

	public ThreadTagsVO(ForumThread forumThread, Collection tags) {
		super();
		this.forumThread = forumThread;
		this.tags = tags;
	}

	public Collection<ThreadTag> getTags() {
		return tags;
	}

	public void subscriptionNotify(Collection lasttags) {
		for (Object o : this.tags) {
			ThreadTag tag = (ThreadTag) o;
			if (!lasttags.contains(tag))
				// changeTags will notify subscription
				this.forumThread.subPublisherRole.subscriptionNotify(new TagSubscribedNotifyEvent
						(tag.getTagID(), forumThread.getThreadId()));
		}
	}

}
