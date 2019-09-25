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

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.model.message.FilterPipleSpec;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.jivejdon.repository.dao.MessageDao;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Optional;

/**
 * Builder pattern for ForumMessage
 */

@Introduce("modelCache")
public class MessageDirector implements MessageDirectorIF {
	private final static Logger logger = LogManager.getLogger(MessageDirector.class);

	public final MessageDao messageDao;

	private final AccountFactory accountFactory;

	private final OutFilterManager outFilterManager;

	private final ForumDirector forumDirector;

	private final PropertyDao propertyDao;

	private final UploadRepository uploadRepository;

	private ThreadDirectorIF threadDirectorIF;

	public MessageDirector(ForumDirector forumDirector, MessageDao messageDao,  AccountFactory accountFactory,
						   OutFilterManager outFilterManager, PropertyDao propertyDao, UploadRepository uploadRepository) {
		super();
		this.forumDirector = forumDirector;
		this.messageDao = messageDao;
		this.accountFactory = accountFactory;
		this.outFilterManager = outFilterManager;
		this.propertyDao = propertyDao;
		this.uploadRepository = uploadRepository;

	}

	public void setThreadDirector(ThreadDirectorIF threadDirectorIF) {
		this.threadDirectorIF = threadDirectorIF;
	}

	@Override
	@Around()
	public ForumMessage getMessage(Long messageId) {
		if (messageId == null || messageId == 0)
			return null;
		try {
			return buildMessage(messageId, null);
		} catch (Exception e) {
			return null;
		}
	}

	@Around()
	public ForumMessage getMessage(Long messageId, ForumThread forumThread){
		if (messageId == null || messageId == 0)
			return null;
		try {
			return buildMessage(messageId, forumThread);
		} catch (Exception e) {
			return null;
		}
	}



	/*
	 * builder pattern with lambdas
	 * return a full ForumMessage need solve the relations with Forum
	 * ForumThread parentMessage
	 */
	private ForumMessage buildMessage(Long messageId, ForumThread forumThread) throws Exception {
		logger.debug(" enter createMessage for id=" + messageId);
		try {
			final AnemicMessageDTO anemicMessageDTO = (AnemicMessageDTO) messageDao.getAnemicMessage(messageId);
			if (anemicMessageDTO == null) {
				logger.error("no this message in database id=" + messageId);
				return null;
			}
			ForumMessage parentforumMessage = null;
			if (anemicMessageDTO.getParentMessage() != null && anemicMessageDTO.getParentMessage().getMessageId() != null) {
				parentforumMessage = buildMessage(anemicMessageDTO.getParentMessage().getMessageId(),forumThread);
			}

			Optional<Account> accountOptional = createAccount(anemicMessageDTO.getAccount());
			FilterPipleSpec filterPipleSpec = new FilterPipleSpec(outFilterManager.getOutFilters());
			Forum forum = forumDirector.getForum(anemicMessageDTO.getForum().getForumId());
			if (forumThread == null )
				forumThread = threadDirectorIF.getThread(anemicMessageDTO.getForumThread().getThreadId());
			Collection props = propertyDao.getProperties(Constants.MESSAGE, messageId);
			Collection uploads = uploadRepository.getUploadFiles(anemicMessageDTO.getMessageId().toString());
			ForumMessage forumMessage = ForumMessage.messageBuilder().messageId(anemicMessageDTO.getMessageId())
					.parentMessage(parentforumMessage)
					.messageVO(anemicMessageDTO.getMessageVO())
					.forum(forum).forumThread(forumThread)
					.acount(accountOptional.orElse(new Account())).creationDate(anemicMessageDTO.getCreationDate()).modifiedDate(anemicMessageDTO.getModifiedDate()).filterPipleSpec(filterPipleSpec)
					.uploads(uploads).props(props).build();
			return forumMessage;
		}catch (Exception e){
			logger.error("getMessage exception "+ e.getMessage() + " messageId=" + messageId);
			return null;
		}


	}


	private Optional<Account> createAccount(Account accountIn) {
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
