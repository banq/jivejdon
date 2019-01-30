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

import com.jdon.domain.model.cache.ModelKey;
import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
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

	public ForumMessage create(Long id) {
		ModelKey modelKey = new ModelKey(id, ForumMessageReply.class);
		Object result = messageDao.getMessageCore(modelKey);
		if (result != null && result instanceof ForumMessageReply)
			return (ForumMessageReply) result;

		// if no reply message for this id, it should be rootmessage
		modelKey = new ModelKey(id, ForumMessage.class);
		return messageDao.getMessageCore(modelKey);

	}

	public ForumMessage createCore(Long id) {
		ModelKey modelKey = new ModelKey(id, ForumMessageReply.class);
		Object result = messageDao.getMessageCore(modelKey);
		if (result != null && result instanceof ForumMessageReply)
			return (ForumMessageReply) result;

		// if no reply message for this id, it should be rootmessage
		modelKey = new ModelKey(id, ForumMessage.class);
		return messageDao.getMessageCore(modelKey);

	}

	public MessageVO createMessageVO(ForumMessage forumMessage) {
		return messageDao.getMessageVOCore(forumMessage);
	}
//
//	public void buildPart(ForumMessage forumMessage, ForumThread forumThread, Forum forum) throws
// Exception {
//		try {
//			logger.debug("Embed  embed thread start");
//			if ((forum == null) || (forum.getForumId().longValue() != forumMessage.getForum()
// .getForumId().longValue()))
//				forum = forumAbstractFactory.forumDirector.getForum(forumMessage.getForum()
// .getForumId(), forumThread, forumMessage);
//			forumMessage.setForum(forum);
//
//			Long threadId = forumMessage.getForumThread().getThreadId();
//			if ((forumThread == null) || (threadId.longValue() != forumThread.getThreadId()
// .longValue())) {
//				forumThread = forumAbstractFactory.threadDirector.getThread(threadId,
// forumMessage, forum);
//			}
//			forumMessage.setForumThread(forumThread);
//
//		} catch (Exception e) {
//			String error = e + " buildPart forumMessageId=" + forumMessage.getMessageId();
//			logger.error(error);
//			throw new Exception(error);
//		}
//
//	}
//
//	public void buildFilters(ForumMessage forumMessage) throws Exception {
//		try {
//			FilterPipleSpec filterPipleSpec = new FilterPipleSpec(outFilterManager.getOutFilters
//					());
//			forumMessage.setFilterPipleSpec(filterPipleSpec);
//		} catch (Exception e) {
//			String error = e + " buildFilters forumMessageId=" + forumMessage.getMessageId();
//			logger.error(error);
//			throw new Exception(error);
//		}
//
//	}
//
//	public void buildUploadFiles(ForumMessage forumMessage) throws Exception {
//		try {
//			if (forumMessage.getMessageId() == null)
//				return;
//			// Collection uploadFiles =
//			// uploadRepository.getUploadFiles(forumMessage.getMessageId().toString());
//			// forumMessage.getAttachment().importUploadFiles(uploadFiles);
//
//			// forumMessage.getAttachment().preloadUploadFileDatas();
//		} catch (Exception e) {
//			String error = e + " buildProperties forumMessageId=" + forumMessage.getMessageId();
//			logger.error(error);
//			throw new Exception(error);
//		}
//
//	}
//
//	public void buildMessageProperties(ForumMessage forumMessage) throws Exception {
//		try {
//			if (forumMessage.getMessageId() == null)
//				return;
//			// getMessageWithPropterty not use, mask property must be load with
//			// message, load it when display it
//			//
//			forumMessage.getMessagePropertysVO().preLoadPropertys();
//		} catch (Exception e) {
//			String error = e + " buildmessageVO forumMessageId=" + forumMessage.getMessageId();
//			logger.error(error);
//			throw new Exception(error);
//		}
//
//	}
//
//	public void asyncGetAccount(final ForumMessage forumMessage) throws Exception {
//		try {
//			logger.debug(" embed getAccount ");
//			Account account = accountFactory.getFullAccount(forumMessage.getAccount());
//			if (account == null || account.getUserId().isEmpty())
//				throw new Exception("set null account or userId is null " + forumMessage
// .getMessageId());
//			forumMessage.setAccount(account);
//		} catch (Exception e) {
//			String error = e + " embedAccount forumMessageId=" + forumMessage.getMessageId();
//			logger.error(error);
//			throw new Exception(error);
//		}
//	}

	public OutFilterManager getOutFilterManager() {
		return outFilterManager;
	}


	public Optional<Account> createAccount(final ForumMessage forumMessage) {
		Account account = null;
		try {
			logger.debug(" embed getAccount ");
			account = accountFactory.getFullAccount(forumMessage.getAccount());
			if (account == null || account.getUserId().isEmpty())
				throw new Exception("set null account or userId is null " + forumMessage
						.getMessageId());
			forumMessage.setAccount(account);
		} catch (Exception e) {
			String error = e + " embedAccount forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
		}
		return Optional.ofNullable(account);
	}

}
