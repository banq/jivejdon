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
package com.jdon.jivejdon.spi.pubsub.subscriber.updatemessage;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.infrastructure.MessageCRUDService;
import com.jdon.jivejdon.domain.event.MessageRevisedEvent;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Consumer("messageRevised")
public class MessageSaveListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessageSaveListener.class);

	protected MessageCRUDService messageCRUDService;
	protected ForumFactory forumAbstractFactory;

	public MessageSaveListener(MessageCRUDService messageCRUDService, ForumFactory forumAbstractFactory) {
		super();
		this.messageCRUDService = messageCRUDService;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageRevisedEvent es = (MessageRevisedEvent) event.getDomainMessage().getEventSource();
		AnemicMessageDTO newForumMessageInputparamter = es.getNewForumMessageInputparamter();
		if (newForumMessageInputparamter == null)
			return;
		try {
			messageCRUDService.updateMessage(newForumMessageInputparamter);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
