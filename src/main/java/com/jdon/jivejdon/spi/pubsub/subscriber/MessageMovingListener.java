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
package com.jdon.jivejdon.spi.pubsub.subscriber;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.domain.event.MessageOwnershipChangedEvent;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.infrastructure.MessageCRUDService;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;

@Consumer("moveMessage")
public class MessageMovingListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessageMovingListener.class);

	protected final MessageCRUDService messageCRUDService;
	protected final MessageDao messageDao;
	protected final ForumFactory forumFactory;
	protected final MessageQueryDao messageQueryDao;

	public MessageMovingListener(MessageCRUDService messageCRUDService, MessageDao messageDao, ForumFactory forumFactory, MessageQueryDao messageQueryDao) {
		super();
		this.messageCRUDService = messageCRUDService;
		this.messageDao = messageDao;
		this.forumFactory = forumFactory;
		this.messageQueryDao = messageQueryDao;
	}

	@Override
	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageOwnershipChangedEvent es = (MessageOwnershipChangedEvent) event.getDomainMessage().getEventSource();
		try {
			messageCRUDService.moveMessage(es.getOldMessageId(), es.getNewForumId());

			ForumMessage forumMessage = forumFactory.getMessage(es.getOldMessageId());
			if (forumMessage == null) {
				logger.warn("ForumMessage not found: " + es.getOldMessageId());
				return;
			}
			
			forumMessage.setForum(forumFactory.getForum(es.getNewForumId()));
			forumMessage.getForumThread().setForum(forumFactory.getForum(es.getNewForumId()));

			Long threadId = forumMessage.getForumThread().getThreadId();
			
			// 更新旧forum中的相邻线程
			updateAdjacentThreads(es.getOldForumId(), threadId);
			
			// 更新新forum中的相邻线程
			updateAdjacentThreads(es.getNewForumId(), threadId);
			
			logger.info("Message moved successfully: " + es.getOldMessageId());

		} catch (Exception e) {
			logger.error("Error moving message: " + es.getOldMessageId(), e);
		}
	}

	private void updateAdjacentThreads(Long forumId, Long threadId) throws Exception {
		List<Long> latestThreadIds = messageQueryDao.getThreadsPrevNext(forumId, threadId);
		for (Long id : latestThreadIds) {
			forumFactory.getThread(id).ifPresent(forumThread -> {
				try {
					messageDao.updateThread(forumThread);
					messageDao.updateForum(forumThread.getForum());
				} catch (Exception e) {
					logger.error("Error updating thread: " + id, e);
				}
			});
		}
	}
}
