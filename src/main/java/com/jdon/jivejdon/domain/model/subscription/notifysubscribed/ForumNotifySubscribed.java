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
package com.jdon.jivejdon.domain.model.subscription.notifysubscribed;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.ForumNotifyMessage;
import com.jdon.util.StringUtil;

public class ForumNotifySubscribed implements NotifySubscribed {

	private final Forum forum;

	private final ForumNotifyMessage forumNotifyMessage;

	private final ForumMessage message;

	public ForumNotifySubscribed(Forum forum, ForumNotifyMessage forumNotifyMessage, ForumMessage message) {
		super();
		this.forum = forum;
		this.forumNotifyMessage = forumNotifyMessage;
		this.message = message;
	}

	public ForumNotifyMessage getForumNotifyMessage() {
		return forumNotifyMessage;
	}

	public ShortMessage createShortMessage(Subscription subscription) {
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(forumNotifyMessage.getNotifyTitle());
		shortMessage.setMessageFrom(forumNotifyMessage.getNotifier());

		shortMessage.setAccount(subscription.getAccount());
		shortMessage.setMessageTo(subscription.getAccount().getUsername());

		// http://www.jdon.com/jivejdon/threadId#messageId
		String newSubscribedUrl = StringUtil.replace(forumNotifyMessage.getNotifyUrlTemp(), "threadId", message.getForumThread().getThreadId().toString());		
		String[] tagTitles = message.getForumThread().getTagTitles();
		String body = message.getForumThread().getName() + " " + ((tagTitles != null && tagTitles.length != 0)?" #"+tagTitles[0]+"# ":" #");
		shortMessage.setMessageBody(newSubscribedUrl + " " + body.substring(0, body.length() > 120 ? 120 : body.length()) );
		shortMessage.setMessageTitle(message.getForumThread().getName() + "-" + shortMessage.getMessageTitle());

		return shortMessage;
	}

	@Override
	public Long getSubscribeId() {
		return forum.getForumId();
	}

}
