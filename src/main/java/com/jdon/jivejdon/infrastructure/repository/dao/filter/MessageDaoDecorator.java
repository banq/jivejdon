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
package com.jdon.jivejdon.infrastructure.repository.dao.filter;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.builder.MessageInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.AccountDao;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.infrastructure.repository.dao.UploadFileDao;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.MessageDaoSql;
import com.jdon.jivejdon.infrastructure.repository.search.MessageSearchRepository;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * Cache decorator
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Introduce("modelCache")
public class MessageDaoDecorator extends MessageDaoSql {

	protected MessageSearchRepository messageSearchProxy;

	public MessageDaoDecorator(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, AccountDao accountDao, PropertyDao propertyDao,
			UploadFileDao uploadFileDao, MessageSearchRepository messageSearchProxy, MessageInitFactory messageFactory, Constants constants) {
		super(jdbcTempSource, messageFactory, constants);
		this.messageSearchProxy = messageSearchProxy;
	}



	public AnemicMessageDTO getAnemicMessage(Long messageId) {
		return  super.getAnemicMessage(messageId);
	}

	@Around()
	public ForumThread getThreadCore(Long threadId, ForumMessage rootMessage) {
		return super.getThreadCore(threadId, rootMessage);
	}

	public void createMessage(AnemicMessageDTO forumMessagePostDTO) throws Exception {
		super.createMessage(forumMessagePostDTO);// db
		messageSearchProxy.createMessageTimer(forumMessagePostDTO);//
	}

	/**
	 * if this deleted message is the last message, we must refresh the forum
	 * state.
	 */
	public void deleteMessage(Long forumMessageId) throws Exception {
		// second delete the message from database
		super.deleteMessage(forumMessageId);
		// todo refactor to domaine events
		messageSearchProxy.deleteMessage(forumMessageId);

	}

	/**
	 * deleteThread always combined with deleteMessage.
	 */
	public void deleteThread(Long forumThreadId) throws Exception {
		super.deleteThread(forumThreadId);
	}

}
