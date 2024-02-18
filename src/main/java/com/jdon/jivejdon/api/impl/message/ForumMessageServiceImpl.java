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
package com.jdon.jivejdon.api.impl.message;

import com.jdon.annotation.Service;
import com.jdon.annotation.intercept.Poolable;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.property.UploadService;
import com.jdon.jivejdon.api.util.SessionContextUtil;
import com.jdon.jivejdon.auth.NoPermissionException;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.domain.command.PostRepliesMessageCommand;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.command.ReviseForumMessageCommand;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.output.RenderingFilterManager;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.service.MessageDomainService;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.spi.component.filter.InFilterManager;
import com.jdon.jivejdon.util.Constants;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * ForumMessageServiceImpl is the shell of ForumMessage core implementions.
 * 
 * invoking order: ForumMessageServiceImpl --> MessageContentFilter --->
 * ForumMessageServiceImp
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Poolable
@Service("forumMessageService")
public class ForumMessageServiceImpl implements ForumMessageService {
	private final static Logger logger = LogManager.getLogger(ForumMessageServiceImpl.class);
	protected final InFilterManager inFilterManager;
	protected final SessionContextUtil sessionContextUtil;
	protected final ResourceAuthorization resourceAuthorization;
	protected final MessageDomainService messageKernel;
	protected final UploadService uploadService;
	protected final ForumFactory forumBuilder;
	private final RenderingFilterManager renderingFilterManager;
	protected SessionContext sessionContext;

	public ForumMessageServiceImpl(SessionContextUtil sessionContextUtil, ResourceAuthorization messageServiceAuth,
			InFilterManager inFilterManager, MessageDomainService messageKernel, UploadService uploadService,
			ForumFactory forumBuilder, RenderingFilterManager renderingFilterManager) {
		this.sessionContextUtil = sessionContextUtil;
		this.resourceAuthorization = messageServiceAuth;
		this.inFilterManager = inFilterManager;
		this.renderingFilterManager = renderingFilterManager;
		this.messageKernel = messageKernel;
		this.uploadService = uploadService;
		this.forumBuilder = forumBuilder;
	}

	/**
	 * must be often checked, every request, if use login.
	 * 
	 * @param forumMessage
	 */
	public boolean checkIsAuthenticated(ForumMessage forumMessage) {
		boolean allowEdit = false;
		if (forumMessage == null)
			return allowEdit;
		if (!forumMessage.isCreated())
			return allowEdit;// forumMessage is null or only has mesageId
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		allowEdit = resourceAuthorization.isAuthenticated(forumMessage, account);
		return allowEdit;
	}

	/**
	 * create Topic Message
	 */
	public Long createTopicMessage(EventModel em) throws Exception {
		AnemicMessageDTO forumMessagePostDTO = (AnemicMessageDTO) em.getModelIF();
		if (!prepareCreate(forumMessagePostDTO))
			return null;
		Forum forum = forumBuilder.getForum(forumMessagePostDTO.getForum().getForumId());
		if (forum == null)
			return null;
		Long mIDInt = forumBuilder.getNextId(Constants.MESSAGE);
		try {
			List<UploadFile> uploads = uploadService.loadAllUploadFilesOfMessage(mIDInt, sessionContext);
			AttachmentsVO attachmentsVO = new AttachmentsVO(mIDInt, uploads);
			Account operator = sessionContextUtil.getLoginAccount(sessionContext);
			Collection<Property> properties = new ArrayList<>();
			properties.add(new Property(MessagePropertysVO.PROPERTY_IP, operator.getPostIP()));
			MessagePropertysVO messagePropertysVO = new MessagePropertysVO(properties);

			if (!UtilValidate.isEmpty(forumMessagePostDTO.getMessageVO().getBody())
					|| !UtilValidate.isEmpty(forumMessagePostDTO.getMessageVO().getSubject())) {
				PostTopicMessageCommand postTopicMessageCommand = new PostTopicMessageCommand(mIDInt, forum, operator,
						inFilterManager.applyFilters(forumMessagePostDTO.getMessageVO()), attachmentsVO,
						messagePropertysVO, forumMessagePostDTO.getTagTitle());
				messageKernel.post(forum.getForumId(), forum, postTopicMessageCommand);
			}

		} catch (Exception e) {
			logger.error(e);
			em.setErrors(Constants.ERRORS);
		} finally {
			uploadService.clearSession(sessionContext);
		}
		// for MessageListNavAction
		forumMessagePostDTO.setMessageId(mIDInt);
		return mIDInt;
	}

	/**
	 * set the login account into the domain model
	 */
	public Long createReplyMessage(EventModel em) throws Exception {
		AnemicMessageDTO forumMessageReplyPostDTO = (AnemicMessageDTO) em.getModelIF();
		if (UtilValidate.isEmpty(forumMessageReplyPostDTO.getMessageVO().getBody()))
			return null;
		if ((forumMessageReplyPostDTO.getParentMessage() == null
				|| forumMessageReplyPostDTO.getParentMessage().getMessageId() == null)) {
			return null;
		}
		ForumMessage parentMessage = getMessage(forumMessageReplyPostDTO.getParentMessage().getMessageId());
		if (parentMessage == null) {
			logger.error("not this parent Message: " + forumMessageReplyPostDTO.getParentMessage().getMessageId());
			return null;
		}
		if (!prepareCreate(forumMessageReplyPostDTO))
			return null;
		Long mIDInt = this.forumBuilder.getNextId(Constants.MESSAGE);
		try {
			List<UploadFile> uploads = uploadService.loadAllUploadFilesOfMessage(mIDInt, sessionContext);
			AttachmentsVO attachmentsVO = new AttachmentsVO(mIDInt, uploads);
			forumMessageReplyPostDTO.setAttachment(attachmentsVO);

			Account operator = sessionContextUtil.getLoginAccount(sessionContext);
			forumMessageReplyPostDTO.setOperator(operator);
			forumMessageReplyPostDTO.setAccount(operator);

			Collection<Property> properties = new ArrayList<>();
			properties.add(new Property(MessagePropertysVO.PROPERTY_IP, operator.getPostIP()));
			MessagePropertysVO messagePropertysVO = new MessagePropertysVO(properties);
			PostRepliesMessageCommand postRepliesMessageCommand = new PostRepliesMessageCommand(parentMessage, mIDInt,
					operator, inFilterManager.applyFilters(forumMessageReplyPostDTO.getMessageVO()), attachmentsVO,
					messagePropertysVO, forumMessageReplyPostDTO.getTagTitle());
			messageKernel.addreply(parentMessage.getForumThread().getThreadId(), parentMessage,
					postRepliesMessageCommand);
		} catch (Exception e) {
			logger.error(e);
			em.setErrors(Constants.ERRORS);
		} finally {
			uploadService.clearSession(sessionContext);
		}
		// for MessageListNav2Action
		forumMessageReplyPostDTO.setMessageId(mIDInt);
		return mIDInt;
	}

	private boolean prepareCreate(AnemicMessageDTO forumMessage) throws Exception {
		// the poster
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		if (account == null)
			return false;
		forumMessage.setAccount(account);
		return true;
	}

	private boolean isAuthenticated(ForumMessage forumMessage) {
		try {
			Account account = sessionContextUtil.getLoginAccount(sessionContext);
			resourceAuthorization.verifyAuthenticated(forumMessage, account);
			return true;
		} catch (NoPermissionException e) {
			logger.error("No Permission to operate it mesageId=" + forumMessage.getMessageId());
		}
		return false;
	}

	/**
	 * 1. auth check: amdin and the owner can modify this nessage. 2. if the message
	 * has childern, only admin can update it. before business logic, we must get a
	 * true message from persistence layer, now the ForumMessage packed in
	 * EventModel object is not full, it is a DTO from prensentation layer.
	 * 
	 * 
	 */
	public void updateMessage(EventModel em) throws Exception {
		AnemicMessageDTO newForumMessageInputparamter = (AnemicMessageDTO) em.getModelIF();
		ForumMessage oldforumMessage = messageKernel.getMessage(newForumMessageInputparamter.getMessageId());
		if (oldforumMessage == null)
			return;
		if (!isAuthenticated(oldforumMessage)) {
			em.setErrors(Constants.NOPERMISSIONS);
			return;
		}
		try {
			Account operator = sessionContextUtil.getLoginAccount(sessionContext);
			List<UploadFile> uploads = uploadService.loadAllUploadFilesOfMessage(oldforumMessage.getMessageId(),
					this.sessionContext);
			AttachmentsVO attachmentsVO = new AttachmentsVO(newForumMessageInputparamter.getMessageId(), uploads);
			Collection<Property> properties = new ArrayList<>();
			properties.add(new Property(MessagePropertysVO.PROPERTY_IP, operator.getPostIP()));
			MessagePropertysVO messagePropertysVO = new MessagePropertysVO(properties);
			ReviseForumMessageCommand reviseForumMessageCommand = new ReviseForumMessageCommand(oldforumMessage,
					inFilterManager.applyFilters(newForumMessageInputparamter.getMessageVO()), attachmentsVO,
					messagePropertysVO);
			messageKernel.revise(oldforumMessage.getForumThread().getThreadId(), oldforumMessage,
					reviseForumMessageCommand);
		} catch (Exception e) {
			logger.error(e);
			em.setErrors(Constants.ERRORS);
		} finally {
			uploadService.clearSession(sessionContext);
		}
	}

	public void updateThreadName(Long threadId, String name) throws Exception {
		Optional<ForumThread> forumThreadOptional = messageKernel.getThread(threadId);
		if (!forumThreadOptional.isPresent())
			return;
		if (!isAuthenticated(forumThreadOptional.get().getRootMessage())) {
			return;
		}
		forumThreadOptional.get().updateName(name);
	}

	/**
	 * delete a message the auth is same as to the updateMessage
	 * 
	 * 
	 */
	public void deleteMessage(EventModel em) throws Exception {
		AnemicMessageDTO anemicMessageDTO = (AnemicMessageDTO) em.getModelIF();
		ForumMessage forumMessage = messageKernel.getMessage(anemicMessageDTO.getMessageId());
		if (forumMessage == null)
			return;
		if (!isAuthenticated(forumMessage)) {
			em.setErrors(Constants.NOPERMISSIONS);
			return;
		}
		// em.setModelIF(forumMessage);
		try {
			messageKernel.deleteMessage(anemicMessageDTO);

		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
			logger.error(e);
		}
	}

	public void deleteUserMessages(String username) throws Exception {
		try {
			Account account = sessionContextUtil.getLoginAccount(sessionContext);
			if (resourceAuthorization.isAdmin(account))
				messageKernel.deleteUserMessages(username);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);

		}
	}

	/**
	 * only admin or moderator can mask a message
	 */
	public void maskMessage(EventModel em) throws Exception {
		logger.debug("enter maskMessage");
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		if (!resourceAuthorization.isAdmin(account))
			return;

		ForumMessage forumMessageParamter = (ForumMessage) em.getModelIF();
		// masked value is from messageForm and from
		// /message/messageMaskAction.shtml?method=maskMessage&masked=false
		boolean masked;
		try {
			masked = forumMessageParamter.isMasked();
			ForumMessage forumMessage = messageKernel.getMessage(forumMessageParamter.getMessageId());
			if (forumMessage == null) {
				logger.error("the message don't existed!");
				return;
			}
			forumMessage.updateMasked(masked);
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/**
	 * get the full forum in forumMessage, and return it.
	 */
	public AnemicMessageDTO initMessage(EventModel em) {
		return messageKernel.initMessage(em);
	}

	public AnemicMessageDTO initReplyMessage(EventModel em) {
		return messageKernel.initReplyMessage(em);
	}

	/*
	 * return message with body filter to client; return a full ForumMessage need
	 * solve the relations with Forum ForumThread
	 */
	public ForumMessage getMessage(Long messageId) {
		if (messageId == null)
			return null;
		return messageKernel.getMessage(messageId);
	}

	/**
	 * find a Message for modify
	 */
	public AnemicMessageDTO findMessage(Long messageId) {
		if (messageId == null)
			return null;

		logger.debug("enter ForumMessageServiceImpl's findMessage");
		ForumMessage message = messageKernel.getMessage(messageId);
		if (message == null)
			return null;
		try {
			ForumMessage newMessage = (ForumMessage) message.clone();
			newMessage.reloadMessageVOOrignal();
			AnemicMessageDTO anemicMessageDTO = new AnemicMessageDTO();
			anemicMessageDTO.setMessageId(newMessage.getMessageId());
			anemicMessageDTO.setForum(newMessage.getForum());
			anemicMessageDTO.setAccount(newMessage.getAccount());
			anemicMessageDTO.setMessageVO(newMessage.getMessageVO());
			anemicMessageDTO.setForumThread(newMessage.getForumThread());

			// after update must enable it see updateMessage;
			return anemicMessageDTO;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * return a full ForumThread one ForumThread has one rootMessage need solve the
	 * realtion with Forum rootForumMessage latestPost
	 * 
	 * @param threadId
	 * @return
	 */
	public ForumThread getThread(Long threadId) {
		return messageKernel.getThread(threadId).orElse(null);
	}

	public RenderingFilterManager getFilterManager() {
		return this.renderingFilterManager;
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
