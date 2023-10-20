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
package com.jdon.jivejdon.infrastructure.repository.builder;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.spi.component.filter.OutFilterManager;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.RootMessage;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.domain.model.message.FilterPipleSpec;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.property.HotKeysRepository;
import com.jdon.jivejdon.infrastructure.repository.property.UploadRepository;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
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

	private final HotKeysRepository hotKeysFactory;

	private ThreadDirectorIF threadDirectorIF;

	public MessageDirector(ForumDirector forumDirector, MessageDao messageDao, AccountFactory accountFactory,
			OutFilterManager outFilterManager, PropertyDao propertyDao, UploadRepository uploadRepository,
			HotKeysRepository hotKeysFactory) {
		super();
		this.forumDirector = forumDirector;
		this.messageDao = messageDao;
		this.accountFactory = accountFactory;
		this.outFilterManager = outFilterManager;
		this.propertyDao = propertyDao;
		this.uploadRepository = uploadRepository;
		this.hotKeysFactory = hotKeysFactory;

	}

	public void setThreadDirector(ThreadDirectorIF threadDirectorIF) {
		this.threadDirectorIF = threadDirectorIF;
	}

	@Around()
	public ForumMessage getMessage(Long messageId) {
		if (messageId == null || messageId == 0)
			return null;
		try {
			return buildMessage(messageId);
		} catch (Exception e) {
			return null;
		}
	}

	@Around()
	public RootMessage getRootMessage(Long messageId, Long threadId) {
		if (messageId == null || messageId == 0)
			return null;
		try {
			return buildRootMessage(messageId, threadId);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * builder pattern with lambdas return a full ForumMessage need solve the
	 * relations with Forum ForumThread parentMessage
	 */
	private RootMessage buildRootMessage(Long messageId, Long threadId) throws Exception {
		logger.debug(" enter createMessage for id=" + messageId);
		try {
			final AnemicMessageDTO anemicMessageDTO = (AnemicMessageDTO) messageDao.getAnemicMessage(messageId);
			if (anemicMessageDTO == null) {
				logger.error("no this message in database id=" + messageId);
				return null;
			}
			if (anemicMessageDTO.getParentMessage() != null
					&& anemicMessageDTO.getParentMessage().getMessageId() != null) {
				logger.error("this message is not root message  id=" + messageId);
				return null;
			}
			return createRootMessage(anemicMessageDTO, threadId);

		} catch (Exception e) {
			logger.error("getMessage exception " + e.getMessage() + " messageId=" + messageId);
			return null;
		}
	}

	private RootMessage createRootMessage(AnemicMessageDTO anemicMessageDTO, Long threadId) {
		Optional<Account> accountOptional = createAccount(anemicMessageDTO.getAccount());
		FilterPipleSpec filterPipleSpec = new FilterPipleSpec(outFilterManager.getOutFilters());
		Forum forum = forumDirector.getForum(anemicMessageDTO.getForum().getForumId());
		Collection props = propertyDao.getProperties(Constants.MESSAGE, anemicMessageDTO.getMessageId());
		Collection uploads = uploadRepository.getUploadFiles(anemicMessageDTO.getMessageId().toString());
		HotKeys hotKeys = hotKeysFactory.getHotKeys();
		ForumMessage forumMessage = RootMessage.messageBuilder().messageId(anemicMessageDTO.getMessageId())
		        .messageVO(anemicMessageDTO.getMessageVO()).forum(forum)
				.acount(accountOptional.orElse(new Account())).creationDate(anemicMessageDTO.getCreationDate())
				.modifiedDate(anemicMessageDTO.getModifiedDate()).filterPipleSpec(filterPipleSpec).uploads(uploads)
				.props(props).hotKeys(hotKeys).build(threadId);
		return forumMessage;
	}

	/*
	 * builder pattern with lambdas return a full ForumMessage need solve the
	 * relations with Forum
	 *
	 */
	private ForumMessage buildMessage(Long messageId) throws Exception {
		logger.debug(" enter createMessage for id=" + messageId);
		try {
			final AnemicMessageDTO anemicMessageDTO = (AnemicMessageDTO) messageDao.getAnemicMessage(messageId);
			if (anemicMessageDTO == null) {
				logger.error("no this message in database id=" + messageId);
				return null;
			}
			ForumThread forumThread = threadDirectorIF.getThread(anemicMessageDTO.getForumThread().getThreadId());
			ForumMessage parentforumMessage = null;
			if (anemicMessageDTO.getParentMessage() != null
					&& anemicMessageDTO.getParentMessage().getMessageId() != null) {
				if (anemicMessageDTO.getParentMessage().getMessageId().longValue() == forumThread.getRootMessage()
						.getMessageId().longValue())
					parentforumMessage = forumThread.getRootMessage();
				else
					parentforumMessage = buildMessage(anemicMessageDTO.getParentMessage().getMessageId());
				return createRepliesMessage(anemicMessageDTO, parentforumMessage);
			} else {
				return forumThread.getRootMessage();
			}

		} catch (Exception e) {
			logger.error("getMessage exception " + e.getMessage() + " messageId=" + messageId);
			return null;
		}
	}

	private ForumMessage createRepliesMessage(AnemicMessageDTO anemicMessageDTO, ForumMessage parentforumMessage) {
		Optional<Account> accountOptional = createAccount(anemicMessageDTO.getAccount());
		FilterPipleSpec filterPipleSpec = new FilterPipleSpec(outFilterManager.getOutFilters());
		Forum forum = forumDirector.getForum(anemicMessageDTO.getForum().getForumId());
		Collection props = propertyDao.getProperties(Constants.MESSAGE, anemicMessageDTO.getMessageId());
		Collection uploads = uploadRepository.getUploadFiles(anemicMessageDTO.getMessageId().toString());
		HotKeys hotKeys = hotKeysFactory.getHotKeys();
		ForumMessage forumMessage = RootMessage.messageBuilder().messageId(anemicMessageDTO.getMessageId())
				.messageVO(anemicMessageDTO.getMessageVO()).forum(forum)
				.acount(accountOptional.orElse(new Account()))
				.creationDate(anemicMessageDTO.getCreationDate()).modifiedDate(anemicMessageDTO.getModifiedDate())
				.filterPipleSpec(filterPipleSpec).uploads(uploads).props(props).hotKeys(hotKeys).build(parentforumMessage);
		return forumMessage;
	}

	private Optional<Account> createAccount(Account accountIn) {
		Account account = null;
		try {
			logger.debug(" embed getAccount ");
			account = accountFactory.getFullAccount(accountIn);
			if (account == null || account.getUserId().isEmpty())
				throw new Exception("set null account or userId is null ");
			// forumMessage.setAccount(account);
		} catch (Exception e) {
			String error = e + " embedAccount accountIn=null";
			logger.error(error);
		}
		return Optional.ofNullable(account);
	}

}
