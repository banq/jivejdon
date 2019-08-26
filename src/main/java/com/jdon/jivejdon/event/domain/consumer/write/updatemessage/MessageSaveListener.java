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
package com.jdon.jivejdon.event.domain.consumer.write.updatemessage;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.event.domain.consumer.write.MessageTransactionPersistence;
import com.jdon.jivejdon.model.event.MessageUpdatedEvent;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.repository.ForumFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Consumer("saveMessage")
public class MessageSaveListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessageSaveListener.class);

	protected MessageTransactionPersistence messageTransactionPersistence;
	protected ForumFactory forumAbstractFactory;

	public MessageSaveListener(MessageTransactionPersistence messageTransactionPersistence, ForumFactory forumAbstractFactory) {
		super();
		this.messageTransactionPersistence = messageTransactionPersistence;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageUpdatedEvent es = (MessageUpdatedEvent) event.getDomainMessage().getEventSource();
		AnemicMessageDTO newForumMessageInputparamter = es.getNewForumMessageInputparamter();
		if (newForumMessageInputparamter == null)
			return;
		try {
			messageTransactionPersistence.updateMessage(newForumMessageInputparamter);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
