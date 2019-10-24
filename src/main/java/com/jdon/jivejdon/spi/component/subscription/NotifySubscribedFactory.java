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
package com.jdon.jivejdon.infrastructure.component.subscription;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.subscription.event.AccountSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.ForumSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.SubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.TagSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.ThreadSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.messsage.AccountNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.messsage.ForumNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.messsage.TagNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.AccountNotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.ForumNotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.NotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.TagNotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.ThreadNotifySubscribed;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;

import java.util.Optional;

@Component
public class NotifySubscribedFactory {
	private final ForumFactory forumAbstractFactory;
	private final AccountFactory accountFactory;
	private final TagRepository tagRepository;

	private final ForumNotifyMessage forumNotifyMessageTemp;
	private final ThreadNotifyMessage threadNotifyMessageTemp;
	private final TagNotifyMessage tagNotifyMessageTemp;
	private final AccountNotifyMessage accountNotifyMessageTemp;

	public NotifySubscribedFactory(AccountFactory accountFactory, ForumFactory forumAbstractFactory, ForumNotifyMessage forumNotifyMessageTemp,
			ThreadNotifyMessage threadNotifyMessageTemp, TagNotifyMessage tagNotifyMessageTemp, AccountNotifyMessage accountNotifyMessageTemp,
			TagRepository tagRepository) {
		this.accountFactory = accountFactory;
		this.forumAbstractFactory = forumAbstractFactory;
		this.forumNotifyMessageTemp = forumNotifyMessageTemp;
		this.threadNotifyMessageTemp = threadNotifyMessageTemp;
		this.tagNotifyMessageTemp = tagNotifyMessageTemp;
		this.accountNotifyMessageTemp = accountNotifyMessageTemp;
		this.tagRepository = tagRepository;

	}

	public NotifySubscribed createFullSubscribed(SubscribedNotifyEvent subscribedNotifyEvent) throws Exception {
		if (subscribedNotifyEvent instanceof ForumSubscribedNotifyEvent) {
			Forum forum = forumAbstractFactory.getForum(((ForumSubscribedNotifyEvent) subscribedNotifyEvent).getForumId());
			return new ForumNotifySubscribed(forum, forumNotifyMessageTemp, ((ForumSubscribedNotifyEvent) subscribedNotifyEvent).getForumMessage());
		} else if (subscribedNotifyEvent instanceof ThreadSubscribedNotifyEvent) {
			Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread((
					(ThreadSubscribedNotifyEvent) subscribedNotifyEvent).getThreadId());
			return new ThreadNotifySubscribed(forumThreadOptional.get(), threadNotifyMessageTemp);
		} else if (subscribedNotifyEvent instanceof TagSubscribedNotifyEvent) {
			Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread((
					(TagSubscribedNotifyEvent) subscribedNotifyEvent).getThreadId());
			ThreadTag tag = tagRepository.getThreadTag(((TagSubscribedNotifyEvent) subscribedNotifyEvent).getTagId());
			return new TagNotifySubscribed(tag, forumThreadOptional.get(), tagNotifyMessageTemp);
		} else if (subscribedNotifyEvent instanceof AccountSubscribedNotifyEvent) {
			Account account = accountFactory.getFullAccount(((AccountSubscribedNotifyEvent) subscribedNotifyEvent).getUserId());
			ForumMessage message = forumAbstractFactory.getMessage(((AccountSubscribedNotifyEvent) subscribedNotifyEvent).getMessageId());
			return new AccountNotifySubscribed(account, message, accountNotifyMessageTemp);
		} else
			return null;

	}
}
