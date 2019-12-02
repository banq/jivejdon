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
import com.jdon.jivejdon.domain.event.MessageRevisedEvent;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.search.MessageSearchRepository;

@Consumer("messageRevised")
public class MessageSaveSearch implements DomainEventHandler {

	private MessageSearchRepository messageSearchRepository;

	public MessageSaveSearch(MessageSearchRepository messageSearchRepository) {
		super();
		this.messageSearchRepository = messageSearchRepository;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageRevisedEvent messageRevisedEvent = (MessageRevisedEvent) event.getDomainMessage().getEventSource();
		messageSearchRepository.updateMessage(AnemicMessageDTO.commandToDTO(messageRevisedEvent.getReviseForumMessageCommand()));

	}

}
