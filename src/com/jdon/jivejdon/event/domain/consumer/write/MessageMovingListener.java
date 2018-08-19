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
package com.jdon.jivejdon.event.domain.consumer.write;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.event.MessageMovedEvent;

@Consumer("moveMessage")
public class MessageMovingListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessageMovingListener.class);

	protected MessageTransactionPersistence messageTransactionPersistence;

	public MessageMovingListener(MessageTransactionPersistence messageTransactionPersistence) {
		super();
		this.messageTransactionPersistence = messageTransactionPersistence;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageMovedEvent es = (MessageMovedEvent) event.getDomainMessage().getEventSource();
		try {
			messageTransactionPersistence.moveMessage(es.getOldMessageId(), es.getNewMessageId());
		} catch (Exception e) {
			logger.error(e);
		}

	}
}
