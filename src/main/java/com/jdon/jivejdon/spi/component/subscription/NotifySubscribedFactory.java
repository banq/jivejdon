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
package com.jdon.jivejdon.spi.component.subscription;

import java.util.Optional;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
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
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;

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
            return createForumNotifySubscribed((ForumSubscribedNotifyEvent) subscribedNotifyEvent);
        } else if (subscribedNotifyEvent instanceof ThreadSubscribedNotifyEvent) {
            return createThreadNotifySubscribed((ThreadSubscribedNotifyEvent) subscribedNotifyEvent);
        } else if (subscribedNotifyEvent instanceof TagSubscribedNotifyEvent) {
            return createTagNotifySubscribed((TagSubscribedNotifyEvent) subscribedNotifyEvent);
        } else if (subscribedNotifyEvent instanceof AccountSubscribedNotifyEvent) {
            return createAccountNotifySubscribed((AccountSubscribedNotifyEvent) subscribedNotifyEvent);
        } else {
            return null;
        }
    }

    private ForumNotifySubscribed createForumNotifySubscribed(ForumSubscribedNotifyEvent event) throws Exception {
        Forum forum = forumAbstractFactory.getForum(event.getForumId());
        return new ForumNotifySubscribed(forum, forumNotifyMessageTemp, event.getForumMessage());
    }

    private ThreadNotifySubscribed createThreadNotifySubscribed(ThreadSubscribedNotifyEvent event) throws Exception {
        Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread(event.getThreadId());
        return forumThreadOptional.map(thread -> new ThreadNotifySubscribed(thread, threadNotifyMessageTemp)).orElse(null);
    }

    private TagNotifySubscribed createTagNotifySubscribed(TagSubscribedNotifyEvent event) throws Exception {
        Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread(event.getThreadId());
        ThreadTag tag = tagRepository.getThreadTag(event.getTagId());
        if (tag != null && forumThreadOptional.isPresent()) {
            return new TagNotifySubscribed(tag, forumThreadOptional.get(), tagNotifyMessageTemp);
        }
        return null;
    }

    private AccountNotifySubscribed createAccountNotifySubscribed(AccountSubscribedNotifyEvent event) throws Exception {
        Account account = accountFactory.getFullAccount(event.getUserId());
        ForumMessage message = forumAbstractFactory.getMessage(event.getMessageId());
        if (account != null && message != null) {
            return new AccountNotifySubscribed(account, message, accountNotifyMessageTemp);
        }
        return null;
    }
}
