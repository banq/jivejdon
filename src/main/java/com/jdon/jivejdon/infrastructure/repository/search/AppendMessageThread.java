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
package com.jdon.jivejdon.infrastructure.repository.search;

import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;

public class AppendMessageThread extends Thread {

	private AnemicMessageDTO forumMessage;

	private MessageSearchProxy messageSearchProxy;

	public AppendMessageThread(AnemicMessageDTO forumMessage) {
		super();
		this.forumMessage = forumMessage;
	}

	public void run() {
		if (messageSearchProxy != null) {
//			if (forumMessage instanceof ForumMessageReply)
//				messageSearchProxy.createMessageReply((ForumMessageReply) forumMessage);
//			else
				messageSearchProxy.createMessage(forumMessage);
		}

	}

	public MessageSearchProxy getMessageSearchProxy() {
		return messageSearchProxy;
	}

	public void setMessageSearchProxy(MessageSearchProxy messageSearchProxy) {
		this.messageSearchProxy = messageSearchProxy;
	}

}
