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
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.model.event.MessagePropertiesUpdatedEvent;
import com.jdon.jivejdon.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.repository.dao.PropertyDao;

@Consumer("updateMessageProperties")
public class MessagePropertiesListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessagePropertiesListener.class);

	private final PropertyDao propertyDao;
	private final ForumAbstractFactory forumAbstractFactory;

	public MessagePropertiesListener(PropertyDao propertyDao, ForumAbstractFactory forumAbstractFactory) {
		super();
		this.propertyDao = propertyDao;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessagePropertiesUpdatedEvent es = (MessagePropertiesUpdatedEvent) event.getDomainMessage().getEventSource();
		try {
			long messageId = es.getMessageId();
			propertyDao.deleteProperties(Constants.MESSAGE, messageId);
			propertyDao.saveProperties(Constants.MESSAGE, messageId, es.getProperties());
			event.getDomainMessage().setEventResult(true);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
