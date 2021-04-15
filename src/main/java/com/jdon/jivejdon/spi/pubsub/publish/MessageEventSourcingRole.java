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
package com.jdon.jivejdon.spi.pubsub.publish;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.MessageRemoveCommand;
import com.jdon.jivejdon.domain.event.*;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;

@Introduce("message")
public class MessageEventSourcingRole {

	@Send("addReplyMessage")
	public DomainMessage addReplyMessage(RepliesMessagePostedEvent event) {
		return new DomainMessage(event);
	}

	@Send("messageRevised")
	public DomainMessage saveMessage(MessageRevisedEvent event) {
		return new DomainMessage(event);
	}

	@Send("moveMessage")
	public DomainMessage moveMessage(MessageOwnershipChangedEvent event) {
		return new DomainMessage(event);
	}

	@Send("deleteMessage")
	public DomainMessage deleteMessage(MessageRemoveCommand event) {
		return new DomainMessage(event);
	}

	@Send("delThread")
	public DomainMessage delThread(MessageRemovedEvent event) {
		return new DomainMessage(event);
	}

	@Send("updateMessageProperties")
	public DomainMessage saveMessageProperties(MessagePropertiesRevisedEvent event) {
		return new DomainMessage(event);
	}

	@Send("saveUploadFiles")
	public DomainMessage saveUploadFiles(UploadFilesAttachedEvent event) {
		return new DomainMessage(event);
	}

	@Send("saveName")
	public DomainMessage saveName(ThreadNameRevisedEvent event) {
		return new DomainMessage(event);
	}

}