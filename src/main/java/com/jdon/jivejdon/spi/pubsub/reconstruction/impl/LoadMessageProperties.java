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
package com.jdon.jivejdon.spi.pubsub.reconstruction.impl;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.spi.pubsub.subscriber.MessagePropertiesListener;
import com.jdon.jivejdon.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

@Consumer("loadMessageProperties")
public class LoadMessageProperties implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessagePropertiesListener.class);

	private final PropertyDao propertyDao;

	public LoadMessageProperties(PropertyDao propertyDao) {
		super();
		this.propertyDao = propertyDao;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		Long messageId = (Long) event.getDomainMessage().getEventSource();
		try {
			Collection props2 = propertyDao.getProperties(Constants.MESSAGE, messageId);
			Collection props = new ArrayList(props2);
			event.getDomainMessage().setEventResult(props);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
