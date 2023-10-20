/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.infrastructure.repository.dao;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.RootMessage;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;

import java.util.Collection;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 */
public interface MessageDao {

	AnemicMessageDTO getAnemicMessage(Long messageId);

	MessageVO getMessageVOCore(ForumMessage forumMessage);

	void createMessage(AnemicMessageDTO forumMessagePostDTO) throws Exception;

	void createMessageReply(AnemicMessageDTO forumMessageReply) throws Exception;

	void updateMessage(AnemicMessageDTO forumMessage) throws Exception;

	void deleteMessage(Long forumMessageId) throws Exception;

	ForumThread getThreadCore(Long threadId, RootMessage rootMessage);

	Long getThreadRootMessageId(Long threadId);

	void createThread(AnemicMessageDTO forumMessagePostDTO) throws Exception;

	void updateThread(ForumThread forumThread) throws Exception;

	void updateThreadName(String name, ForumThread forumThread);

	void updateMovingForum(Long messageId, Long threadId, Long forumId) throws Exception;

	void deleteThread(Long forumThreadId) throws Exception;

	void saveReBlog(OneOneDTO oneOneDTO) throws Exception;

	void delReBlog(Long msgId) throws Exception;

	Collection<Long> getReBlogByFrom(Long messageId) throws Exception;

	Collection<Long> getReBlogByTo(Long threadId) throws Exception;

}
