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

import java.util.Collection;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;

public interface MessageSearchRepository {

	public abstract void createMessage(ForumMessage forumMessage);

	public abstract void createMessageReply(ForumMessageReply forumMessageReply);

	public void createMessageTimer(ForumMessage forumMessage);

	public void createMessageReplyTimer(ForumMessageReply forumMessageReply);

	public abstract void updateMessage(ForumMessage forumMessage);

	public abstract void deleteMessage(Long forumMessageId);

	int findThreadsAllCount(String query);

	Collection findThread(String query, int start, int count);

	Collection find(String query, int start, int count);
}