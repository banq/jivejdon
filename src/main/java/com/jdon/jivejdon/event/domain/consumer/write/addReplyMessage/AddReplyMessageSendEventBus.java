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
import com.jdon.jivejdon.event.bus.cqrs.query.CacheQueryRefresher;
import com.jdon.jivejdon.model.event.ReplyMessageCreatedEvent;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.query.MessagePageIteratorSolver;

/**
 * 
 * Event Sourcing send this event to a message Bus to refresh Query system.
 * 
 * 
 * @author banq
 * 
 */
@Consumer("addReplyMessage")
public class AddReplyMessageSendEventBus implements DomainEventHandler {
	private final CacheQueryRefresher cacheQueryRefresher;
	private final ForumFactory forumFactory;

	public AddReplyMessageSendEventBus(ForumFactory forumFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		this.forumFactory = forumFactory;
		cacheQueryRefresher = new CacheQueryRefresher(forumFactory, messagePageIteratorSolver);
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		ReplyMessageCreatedEvent es = (ReplyMessageCreatedEvent) event.getDomainMessage().getEventSource();
		Long messageId = es.getForumMessageReplyDTO().getMessageId();
		cacheQueryRefresher.refresh(this.forumFactory.getMessage(messageId));
	}
}
