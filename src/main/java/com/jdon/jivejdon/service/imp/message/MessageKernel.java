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
package com.jdon.jivejdon.service.imp.message;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Owner;
import com.jdon.annotation.model.Receiver;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Component
@Introduce("componentmessage")
public class MessageKernel implements MessageKernelIF {
	private final static Logger logger = LogManager.getLogger(MessageKernel.class);
	private final static Pattern htmlEscape = Pattern.compile("\\<.*?\\>|<[^>]+");
	protected ForumMessageQueryService forumMessageQueryService;
	protected ForumFactory forumAbstractFactory;

	public MessageKernel(ForumMessageQueryService forumMessageQueryService, ForumFactory forumAbstractFactory) {
		this.forumMessageQueryService = forumMessageQueryService;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	/*
	 * /message/message.jsp
	 * before create a new post
	 * in models.xml
	 * AnemicMessageDTO will map to messageForm
	 * 
	 * @see
	 * com.jdon.jivejdon.service.imp.message.MessageKernelIF#initMessage(com
	 * .jdon.controller.events.EventModel)
	 */
	@Override
	public AnemicMessageDTO initMessage(EventModel em) {
		logger.debug(" enter service: initMessage ");
        AnemicMessageDTO forumMessage = (AnemicMessageDTO) em.getModelIF();
		try {
			if (forumMessage.getForum() == null) {
				logger.error(" no Forum in this ForumMessage");
				return forumMessage;
			}
			Long forumId = forumMessage.getForum().getForumId();
			logger.debug(" paremter forumId =" + forumId);
			Forum forum = forumAbstractFactory.getForum(forumId);
			forumMessage.setForum(forum);
		} catch (Exception e) {
			logger.error(e);
		}
		return forumMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.imp.message.MessageKernelIF#initReplyMessage
	 * (com.jdon.controller.events.EventModel)
	 */
	@Override
	public AnemicMessageDTO initReplyMessage(EventModel em) {
		logger.debug(" enter service: initReplyMessage ");
        AnemicMessageDTO forumMessageReply = (AnemicMessageDTO) initMessage(em);
		try {
			Long pmessageId = forumMessageReply.getParentMessage().getMessageId();
			if (pmessageId == null) {
				logger.error(" no the parentMessage.messageId parameter");
				return null;
			}
			ForumMessage pMessage = forumAbstractFactory.getMessage(pmessageId);
            forumMessageReply.setParentMessage(new AnemicMessageDTO(pmessageId));
			forumMessageReply.setForum(pMessage.getForum());
			forumMessageReply.setForumThread(pMessage.getForumThread());
			String rsubject = htmlEscape.matcher(pMessage.getMessageVO().getSubject()).replaceAll("");
			MessageVO messageVo = pMessage.messageVOBuilder().subject(rsubject).body
					(forumMessageReply.getMessageVO().getBody()).build();
			forumMessageReply.setMessageVO(messageVo);
		} catch (Exception e) {
			logger.error(e);
		}
		return forumMessageReply;
	}

	/*
	 * return a full ForumMessage need solve the relations with Forum
	 * ForumThread parentMessage
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.imp.message.MessageKernelIF#getMessage(java
	 * .lang.Long)
	 */
	@Override
	public ForumMessage getMessage(Long messageId) {
		logger.debug("enter MessageServiceImp's getMessage");
//		if (threadManagerContext.isTransactionOk(messageId))
			return forumAbstractFactory.getMessage(messageId);
//		else
//			return null;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.imp.message.MessageKernelIF#getThread(java.
	 * lang.Long)
	 */
	@Override
	public Optional<ForumThread> getThread(Long threadId) {
		logger.debug("enter getThread");
		return forumAbstractFactory.getThread(threadId);

	}
	public DomainMessage post(@Owner long forumId, @Receiver Forum forum, AnemicMessageDTO forumMessageInputDTO) {
		return new DomainMessage(forumMessageInputDTO);
	}

	public DomainMessage addreply(long threadId, ForumMessage paranetforumMessage, AnemicMessageDTO newForumMessageInputparamter) {
		return new DomainMessage(newForumMessageInputparamter);
	}

	public DomainMessage update(long threadId, ForumMessage oldforumMessage, AnemicMessageDTO newForumMessageInputparamter) {
		return new DomainMessage(newForumMessageInputparamter);
	}

	/*
	 * delete a message and not inlcude its childern
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.imp.message.MessageKernelIF#deleteMessage(com
	 * .jdon.jivejdon.model.ForumMessage)
	 */
	@Override
	public void deleteMessage(AnemicMessageDTO beingdelforumMessage) throws Exception {
        ForumMessage delforumMessage = this.getMessage(beingdelforumMessage.getMessageId());
		if (delforumMessage != null)
			delforumMessage.getForumThread().delete(delforumMessage);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.imp.message.MessageKernelIF#deleteUserMessages
	 * (java.lang.String)
	 */
	@Override
	public void deleteUserMessages(String username) throws Exception {
		logger.debug("enter userMessageListDelete username=" + username);
		MultiCriteria mqc = new MultiCriteria("1970/01/01");
		mqc.setUsername(username);

		// iterate all messages
		int oneMaxSize = 100;
		PageIterator pi = forumMessageQueryService.getMessages(mqc, 0, oneMaxSize);
		int allCount = pi.getAllCount();

		int wheelCount = allCount / oneMaxSize;
		int start = 0;
		int end = 0;
		for (int i = 0; i <= wheelCount; i++) {
			end = oneMaxSize + oneMaxSize * i;
			logger.debug("start = " + start + " end = " + end);
			if (pi == null)
				pi = forumMessageQueryService.getMessages(mqc, start, end);
			messagesDelete(pi, username);
			pi = null;
			start = end;
		}
	}

	private void messagesDelete(PageIterator pi, String username) throws Exception {
		Object[] keys = pi.getKeys();
		for (int i = 0; i < keys.length; i++) {
			Long messageId = (Long) keys[i];
			logger.debug("delete messageId =" + messageId);
			ForumMessage message = getMessage(messageId);
			if (message!= null
					&& message.getAccount().getUsername().equals(username)) {
				   message.getForumThread().delete(message);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.service.imp.message.MessageKernelIF#
	 * getForumMessageQueryService()
	 */
	@Override
	public ForumMessageQueryService getForumMessageQueryService() {
		return forumMessageQueryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.service.imp.message.MessageKernelIF#
	 * setForumMessageQueryService
	 * (com.jdon.jivejdon.service.query.ForumMessageQueryService)
	 */
	@Override
	public void setForumMessageQueryService(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
	}

}
