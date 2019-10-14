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
package com.jdon.jivejdon.pubsub.domain.consumer.read;

import org.apache.logging.log4j.*;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.util.OneOneDTO;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;

@Consumer("projectStateFromEventSource")
public class ThreadStateLoader implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(ThreadStateLoader.class);

	private final MessageQueryDao messageQueryDao;

	protected final ForumFactory forumAbstractFactory;

	public ThreadStateLoader(MessageQueryDao messageQueryDao, ForumFactory forumAbstractFactory) {
		super();
		this.messageQueryDao = messageQueryDao;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		try {
			long threadId = (Long) event.getDomainMessage().getEventSource();

			Long lastMessageId = messageQueryDao.getLatestPostMessageId(threadId);
			if (lastMessageId == null) {
				logger.warn("maybe first running, not found lastMessageId for forumthreadId: " + threadId);
				return;
			}
			ForumMessage latestPost = forumAbstractFactory.getMessage(lastMessageId);

			long messagereplyCount;
			long messageCount = messageQueryDao.getMessageCount(threadId);
			if (messageCount >= 1)
				messagereplyCount = messageCount - 1;
			else
				messagereplyCount = messageCount;

			OneOneDTO oneOneDTO = new OneOneDTO(latestPost, messagereplyCount);
			event.getDomainMessage().setEventResult(oneOneDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
