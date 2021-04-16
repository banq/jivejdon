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
package com.jdon.jivejdon.infrastructure.repository;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDaoFacade;

import java.util.Collection;

public interface MessageRepository {

	void createTopicMessage(AnemicMessageDTO forumMessage) throws Exception;

	void createReplyMessage(AnemicMessageDTO forumMessageReply) throws Exception;

	void updateMessage(AnemicMessageDTO forumMessage) throws Exception;

	void deleteMessageComposite(ForumMessage delforumMessage) throws Exception;

	void deleteMessage(Long messageId) throws Exception;

	Long getNextId(int idType) throws Exception;

	ForumFactory getForumBuilder();

	MessageDaoFacade getMessageDaoFacade();

	void saveReBlog(OneOneDTO oneOneDTO) throws Exception;

	void delReBlog(Long msgId) throws Exception;

	Collection<Long> getReBlogByFrom(Long messageId) throws Exception;

	Collection<Long> getReBlogByTo(Long messageId) throws Exception;
}