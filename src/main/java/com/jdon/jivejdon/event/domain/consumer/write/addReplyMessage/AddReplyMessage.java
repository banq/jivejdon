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
package com.jdon.jivejdon.event.domain.consumer.write.addReplyMessage;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.event.domain.consumer.write.MessageTransactionPersistence;
import com.jdon.jivejdon.event.domain.consumer.write.updatemessage.MessageSaveListener;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.event.ReplyMessageCreatedEvent;
import com.jdon.jivejdon.repository.ForumFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * topic addReplyMessage has three DomainEventHandlers: AddReplyMessage;
 * AddReplyMessageRefresher AddReplyMessageSearchFile
 * 
 * these DomainEventHandlers run by the alphabetical(字母排列先后运行) of their class
 * name
 * 
 * 
 * @author banq
 * 
 */
@Consumer("addReplyMessage")
public class AddReplyMessage implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(MessageSaveListener.class);

	protected final MessageTransactionPersistence messageTransactionPersistence;
	protected final ForumFactory forumAbstractFactory;

	public AddReplyMessage(MessageTransactionPersistence messageTransactionPersistence, ForumFactory forumAbstractFactory) {
		super();
		this.messageTransactionPersistence = messageTransactionPersistence;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		ReplyMessageCreatedEvent es = (ReplyMessageCreatedEvent) event.getDomainMessage().getEventSource();
		ForumMessageReply forumMessageReplyPostDTO = es.getForumMessageReplyDTO();

		try {
			messageTransactionPersistence.insertReplyMessage(forumMessageReplyPostDTO);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
