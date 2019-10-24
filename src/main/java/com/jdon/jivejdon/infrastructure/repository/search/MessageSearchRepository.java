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
package com.jdon.jivejdon.repository.search;

import com.jdon.jivejdon.domain.model.message.AnemicMessageDTO;

import java.util.Collection;

public interface MessageSearchRepository {

	public abstract void createMessage(AnemicMessageDTO forumMessage);

//
//	public abstract void createMessageReply(AnemicMessageDTO forumMessageReply);

	public void createMessageTimer(AnemicMessageDTO forumMessage);

//	public void createMessageReplyTimer(AnemicMessageDTO forumMessageReply);

	public abstract void updateMessage(AnemicMessageDTO forumMessage);

	public abstract void deleteMessage(Long forumMessageId);

	int findThreadsAllCount(String query);

	Collection findThread(String query, int start, int count);

	Collection find(String query, int start, int count);
}