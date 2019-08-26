/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.repository.builder;

import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.dao.MessageDao;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Builder pattern for ForumMessage
 */
public class MessageBuilder {
	private final static Logger logger = LogManager.getLogger(MessageBuilder.class);

	public final MessageDao messageDao;

	public final MessageQueryDao messageQueryDao;

	private final AccountFactory accountFactory;

	private ForumAbstractFactory forumAbstractFactory;

	private OutFilterManager outFilterManager;

	public MessageBuilder(MessageDao messageDao, MessageQueryDao messageQueryDao, AccountFactory accountFactory, OutFilterManager outFilterManager) {
		super();
		this.messageDao = messageDao;
		this.messageQueryDao = messageQueryDao;
		this.accountFactory = accountFactory;
		this.outFilterManager = outFilterManager;
	}

	public ForumAbstractFactory getForumAbstractFactory() {
		return forumAbstractFactory;
	}

	public void setForumAbstractFactory(ForumAbstractFactory forumAbstractFactory) {
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public AnemicMessageDTO createAnemicMessage(Long id) {
		return messageDao.getAnemicMessage(id);

	}

//	public ForumMessage createCore(Long id) {
//		ModelKey modelKey = new ModelKey(id, ForumMessageReply.class);
//		Object result = messageDao.getMessageCore(modelKey);
//		if (result != null && result instanceof ForumMessageReply)
//			return (ForumMessageReply) result;
//
//		// if no reply message for this id, it should be rootmessage
//		modelKey = new ModelKey(id, ForumMessage.class);
//		return messageDao.getMessageCore(modelKey);
//
//	}

	public MessageVO createMessageVO(ForumMessage forumMessage) {
		return messageDao.getMessageVOCore(forumMessage);
	}


	public OutFilterManager getOutFilterManager() {
		return outFilterManager;
	}


	public Optional<Account> createAccount(Account accountIn) {
		Account account = null;
		try {
			logger.debug(" embed getAccount ");
			account = accountFactory.getFullAccount(accountIn);
			if (account == null || account.getUserId().isEmpty())
				throw new Exception("set null account or userId is null " );
//			forumMessage.setAccount(account);
		} catch (Exception e) {
			String error = e + " embedAccount accountIn=null";
			logger.error(error);
		}
		return Optional.ofNullable(account);
	}

}
