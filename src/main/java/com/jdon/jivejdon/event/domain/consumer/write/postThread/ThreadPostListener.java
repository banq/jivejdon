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
package com.jdon.jivejdon.event.domain.consumer.write.postThread;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.dci.RoleAssigner;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.bus.cqrs.query.EventBusHandler;
import com.jdon.jivejdon.event.bus.cqrs.query.PostThreadEventHandler;
import com.jdon.jivejdon.event.domain.producer.write.LobbyPublisherRole;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.model.subscription.SubPublisherRole;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.event.AccountSubscribedNotifyEvent;
import com.jdon.jivejdon.model.subscription.event.TagSubscribedNotifyEvent;
import com.jdon.jivejdon.model.subscription.event.ThreadSubscribedCreateEvent;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.MessagePageIteratorSolver;

@Consumer("postThread")
public class ThreadPostListener implements DomainEventHandler {

	private final ForumFactory forumFactory;
	private final RoleAssigner roleAssigner;
	private final EventBusHandler eventHandler;

	public ThreadPostListener(RoleAssigner roleAssigner, ForumFactory forumFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		super();
		this.forumFactory = forumFactory;
		this.roleAssigner = roleAssigner;
		eventHandler = new PostThreadEventHandler(forumFactory, messagePageIteratorSolver);

	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		DomainMessage lastStepMessage = (DomainMessage) event.getDomainMessage().getEventSource();
		Object lastStepOk = lastStepMessage.getBlockEventResult();
		if (lastStepOk != null) {
			// the forumMessage is input DTO
			ForumMessage forumMessageInputDTO = (ForumMessage) lastStepOk;
			boolean isReplyNotifyForAuthor = forumMessageInputDTO.isReplyNotify();

			// get the true forumMessage.
			ForumMessage forumMessage = forumFactory.getMessage(forumMessageInputDTO.getMessageId());
			forumMessage.getForum().postThread(forumMessage);

			messageNotifyAction(isReplyNotifyForAuthor, forumMessage);

			// if there is a event bus server, rewrite this code:
			eventHandler.refresh(forumMessageInputDTO.getMessageId());
		}

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
