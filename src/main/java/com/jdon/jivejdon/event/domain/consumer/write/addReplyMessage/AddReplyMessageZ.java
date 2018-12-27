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
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.event.ReplyMessageCreatedEvent;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * 
 * 
 * the last evenHandler
 * 
 * @author banq
 * 
 */
@Consumer("addReplyMessage")
public class AddReplyMessageZ implements DomainEventHandler {

	protected final ContainerUtil containerUtil;

	public AddReplyMessageZ(ContainerUtil containerUtil) {
		super();
		this.containerUtil = containerUtil;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		ReplyMessageCreatedEvent es = (ReplyMessageCreatedEvent) event.getDomainMessage().getEventSource();
		ForumMessageReply forumMessageReply = es.getForumMessageReplyDTO();
		// change the forumMessageReply parameter DTO from commannd to like from
		// repository
		forumMessageReply.finishDTO();
		event.getDomainMessage().clear();
		containerUtil.addModeltoCache(forumMessageReply.getMessageId(), forumMessageReply);
		//update state for Eventually consistent so MessageListNav2Action can find its state has
		// updated
		forumMessageReply.getForumThread().changeState(forumMessageReply);
	}
}
