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
package com.jdon.jivejdon.spi.pubsub.subscriber.postThread;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.dci.RoleAssigner;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.domain.event.TopicMessagePostedEvent;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.domain.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.domain.model.realtime.Notification;
import com.jdon.jivejdon.domain.model.subscription.SubPublisherRole;
import com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.domain.model.subscription.event.AccountSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.TagSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.ThreadSubscribedCreateEvent;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.spi.pubsub.publish.LobbyPublisherRole;

@Consumer("topicMessagePostedEvent")
public class ThreadPostListener implements DomainEventHandler {

	private final static Logger logger = LogManager.getLogger(ThreadPostListener.class);

	private final RoleAssigner roleAssigner;
	private final ForumFactory forumFactory;
	private final MessageQueryDao messageQueryDao;
	private final MessageDao messageDao;

	public ThreadPostListener(RoleAssigner roleAssigner, ForumFactory forumFactory, 
			MessageQueryDao messageQueryDao, MessageDao messageDao) {
		super();
		this.roleAssigner = roleAssigner;
		this.forumFactory = forumFactory;
		this.messageQueryDao = messageQueryDao;
		this.messageDao = messageDao;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		TopicMessagePostedEvent topicMessagePostedEvent = (TopicMessagePostedEvent) event.getDomainMessage()
				.getEventSource();
		ForumMessage forumMessage = topicMessagePostedEvent.getForumMessage();

		// 获取论坛ID
		Long forumId = forumMessage.getForum().getForumId();

		// 获取最新的3个线程ID
		Collection<Long> latestThreadIds = messageQueryDao.getForumLatestThreadIds(forumId);

		// 更新每个线程
		for (Long threadId : latestThreadIds) {
			forumFactory.getThread(threadId).ifPresent(forumThread -> {
				try {
					messageDao.updateThread(forumThread);
				} catch (Exception e) {
					logger.error("Error updating thread: " + threadId, e);
				}
			});
		}

		// baiduSearchClient(forumMessage);
		// messageLobbyNotifyAction(forumMessage);
		// messageNotifyAction(false,forumMessage);
		// if there is a pubsub bus server, rewrite this code:

	}

	/**
	 * subscription notify is about post thread context, it is not about
	 * aggregation function. not need be written in forum.
	 * 
	 * 
	 * @param isReplyNotifyForAuthor
	 * @param forumMessage
	 */
	public void messageLobbyNotifyAction(ForumMessage forumMessage) {
		ForumMessageDTO forumMessageDTO = new ForumMessageDTO(forumMessage);
		LobbyPublisherRoleIF lobbyPublisherRole = (LobbyPublisherRoleIF) roleAssigner.assign(forumMessageDTO,
				new LobbyPublisherRole());
		Notification notification = new Notification();
		notification.setSource(forumMessageDTO);
		lobbyPublisherRole.notifyLobby(notification);
	}

	public void messageNotifyAction(boolean isReplyNotifyForAuthor, ForumMessage forumMessage) {

		SubPublisherRoleIF subPublisherRole = (SubPublisherRoleIF) roleAssigner.assign(forumMessage,
				new SubPublisherRole());
		AccountSubscribedNotifyEvent accountSubscribedNotifyEvent = new AccountSubscribedNotifyEvent(
				forumMessage.getAccount().getUserId(),
				forumMessage.getMessageId());
		// notify the author's fans
		subPublisherRole.subscriptionNotify(accountSubscribedNotifyEvent);

		// notify the tag's fans
		ForumThread forumThread = forumMessage.getForumThread();
		for (Object o : forumThread.getTags()) {
			ThreadTag tag = (ThreadTag) o;
			// changeTags will notify subscription
			subPublisherRole
					.subscriptionNotify(new TagSubscribedNotifyEvent(tag.getTagID(), forumThread.getThreadId()));
		}

		// if enable reply notify, so the author is the thread's fans
		if (isReplyNotifyForAuthor) {
			subPublisherRole.createSubscription(
					new ThreadSubscribedCreateEvent(forumMessage.getAccount().getUserId(), forumThread.getThreadId()));
		}
	}

}
