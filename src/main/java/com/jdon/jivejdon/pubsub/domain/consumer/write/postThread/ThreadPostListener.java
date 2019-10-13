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
package com.jdon.jivejdon.pubsub.domain.consumer.write.postThread;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.dci.RoleAssigner;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.pubsub.bus.cqrs.query.CacheQueryRefresher;
import com.jdon.jivejdon.pubsub.domain.producer.write.LobbyPublisherRole;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.property.ThreadTag;
import com.jdon.jivejdon.model.event.TopicMessagePostedEvent;
import com.jdon.jivejdon.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.model.subscription.SubPublisherRole;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.event.AccountSubscribedNotifyEvent;
import com.jdon.jivejdon.model.subscription.event.TagSubscribedNotifyEvent;
import com.jdon.jivejdon.model.subscription.event.ThreadSubscribedCreateEvent;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.query.MessagePageIteratorSolver;

@Consumer("topicMessagePostedEvent")
public class ThreadPostListener implements DomainEventHandler {

	private final ForumFactory forumFactory;
	private final RoleAssigner roleAssigner;
	private final CacheQueryRefresher eventHandler;

	public ThreadPostListener(RoleAssigner roleAssigner, ForumFactory forumFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		super();
		this.forumFactory = forumFactory;
		this.roleAssigner = roleAssigner;
		eventHandler = new CacheQueryRefresher(forumFactory, messagePageIteratorSolver);

	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		TopicMessagePostedEvent topicMessagePostedEvent = (TopicMessagePostedEvent) event.getDomainMessage().getEventSource();
		ForumMessage forumMessage = topicMessagePostedEvent.getForumMessage();
			// if there is a pubsub bus server, rewrite this code:
		eventHandler.refresh(forumMessage);

	}

	/**
	 * subscription notify is about post thread context, it is not about
	 * aggregation function. not need be written in forum.
	 * 
	 * 
	 * @param isReplyNotifyForAuthor
	 * @param forumMessage
	 */
	public void messageNotifyAction(boolean isReplyNotifyForAuthor, ForumMessage forumMessage) {
		ForumMessageDTO forumMessageDTO = new ForumMessageDTO(forumMessage);
		LobbyPublisherRoleIF lobbyPublisherRole = (LobbyPublisherRoleIF) roleAssigner.assign(forumMessageDTO, new LobbyPublisherRole());
		Notification notification = new Notification();
		notification.setSource(forumMessageDTO);
		lobbyPublisherRole.notifyLobby(notification);

		SubPublisherRoleIF subPublisherRole = (SubPublisherRoleIF) roleAssigner.assign(forumMessage, new SubPublisherRole());
		AccountSubscribedNotifyEvent accountSubscribedNotifyEvent = new AccountSubscribedNotifyEvent(forumMessage.getAccount().getUserId(),
				forumMessage.getMessageId());
		// notify the author's fans
		subPublisherRole.subscriptionNotify(accountSubscribedNotifyEvent);

		// notify the tag's fans
		ForumThread forumThread = forumMessage.getForumThread();
		for (Object o : forumThread.getTags()) {
			ThreadTag tag = (ThreadTag) o;
			// changeTags will notify subscription
			subPublisherRole.subscriptionNotify(new TagSubscribedNotifyEvent(tag.getTagID(), forumThread.getThreadId()));
		}

		// if enable reply notify, so the author is the thread's fans
		if (isReplyNotifyForAuthor) {
			subPublisherRole.createSubscription(new ThreadSubscribedCreateEvent(forumMessage.getAccount().getUserId(), forumThread.getThreadId()));
		}
	}

}
