/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.repository.builder;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.MessageRepository;
import com.jdon.jivejdon.repository.property.TagRepository;
import com.jdon.jivejdon.repository.property.UploadRepository;
import com.jdon.jivejdon.repository.dao.MessageDaoFacade;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.treepatterns.TreeVisitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Kernel of Message business operations
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class MessageRepositoryDao extends ThreadRepositoryDao implements MessageRepository {
	private final static Logger logger = LogManager.getLogger(MessageRepositoryDao.class);

	protected ForumFactory forumBuilder;
	protected UploadRepository uploadRepository;
	protected PropertyDao propertyDao;
	private TagRepository tagRepository;

	public MessageRepositoryDao(MessageDaoFacade messageDaoFacade, ForumFactory forumBuilder, ContainerUtil containerUtil,
			TagRepository tagRepository, UploadRepository uploadRepository, PropertyDao propertyDao) {
		super(messageDaoFacade);
		this.messageDaoFacade = messageDaoFacade;
		this.forumBuilder = forumBuilder;
		this.tagRepository = tagRepository;
		this.uploadRepository = uploadRepository;
		this.propertyDao = propertyDao;
	}

	/*
	 * create the topic message
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.MessageRepository#createTopicMessage(com
	 * .jdon.jivejdon.model.ForumMessage)
	 */
	public void createTopicMessage(AnemicMessageDTO forumMessagePostDTO) throws Exception {
		try {
			logger.debug(" enter service: createMessage ");
			Forum forum = forumBuilder.getForum(forumMessagePostDTO.getForum().getForumId());
			if (forum == null) {
				logger.error(" no this forum, forumId = " + forumMessagePostDTO.getForum().getForumId());
				return;
			}
			Long tIDInt = messageDaoFacade.getSequenceDao().getNextId(Constants.THREAD);
			forumMessagePostDTO.setForum(forum);
			forumMessagePostDTO.setForumThread(new ForumThread(null, tIDInt,
					forumMessagePostDTO.getForum()));
			messageDaoFacade.getMessageDao().createMessage(forumMessagePostDTO);
			super.createThread(forumMessagePostDTO);
			uploadRepository.saveAllUploadFiles(forumMessagePostDTO.getMessageId().toString(), forumMessagePostDTO.getAttachment().getUploadFiles());

			propertyDao.saveProperties(Constants.MESSAGE, forumMessagePostDTO.getMessageId(), forumMessagePostDTO.getMessagePropertysVO()
					.getPropertys());

			// tag title can be updated between in thread with repository
			// so it can be used in model ForumThread's changetags method
			tagRepository.saveTagTitle(forumMessagePostDTO.getForumThread().getThreadId(), forumMessagePostDTO.getTagTitle());

		} catch (Exception e) {
			try {
				messageDaoFacade.getMessageDao().deleteThread(forumMessagePostDTO.getForumThread().getThreadId());
			} finally {
			}
			try {
				messageDaoFacade.getMessageDao().deleteMessage(forumMessagePostDTO.getMessageId());
			} finally {
			}
			String error = e + " createTopicMessage forumMessageId=" + forumMessagePostDTO
					.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.MessageRepository#createReplyMessage(com
	 * .jdon.jivejdon.model.ForumMessageReply)
	 */
	public void createReplyMessage(AnemicMessageDTO forumMessageReplyPostDTO) throws Exception {
		try {
			logger.debug(" enter service: createReplyMessage ....");

			// create
			messageDaoFacade.getMessageDao().createMessageReply(forumMessageReplyPostDTO);

			uploadRepository.saveAllUploadFiles(forumMessageReplyPostDTO.getMessageId().toString(), forumMessageReplyPostDTO.getAttachment()
					.getUploadFiles());

			propertyDao.saveProperties(Constants.MESSAGE, forumMessageReplyPostDTO.getMessageId(), forumMessageReplyPostDTO.getMessagePropertysVO()
					.getPropertys());

			super.updateThread(forumMessageReplyPostDTO.getForumThread());
		} catch (Exception e) {
			String error = e + " createReplyMessage forumMessageId=" + forumMessageReplyPostDTO.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/*
	 * update the message, update the message's subject and body we must mark
	 * the message that has been updated. there are two kinds of parameters: the
	 * primary key /new entity data in DTO ForumMessage of the method patameter
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.MessageRepository#updateMessage(com.jdon
	 * .jivejdon.model.ForumMessage)
	 */
	public void updateMessage(AnemicMessageDTO newForumMessageInputparamter) throws Exception {
		logger.debug(" enter updateMessage id =" + newForumMessageInputparamter.getMessageId());
		try {

			messageDaoFacade.getMessageDao().updateMessage(newForumMessageInputparamter);

			// updateMessageProperties(newForumMessageInputparamter);
		} catch (Exception e) {
			String error = e + " updateMessage forumMessageId=" + newForumMessageInputparamter.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.MessageRepository#updateMessageProperties
	 * (com.jdon.jivejdon.domain.model.ForumMessage)
	 */
	public void updateMessageProperties(ForumMessage forumMessage) throws Exception {
		try {
			propertyDao.deleteProperties(Constants.MESSAGE, forumMessage.getMessageId());
			propertyDao.saveProperties(Constants.MESSAGE, forumMessage.getMessageId(), forumMessage.getMessagePropertysVO().getPropertys());
		} catch (Exception e) {
			String error = e + " updateMessageProperties forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.MessageRepository#deleteMessageComposite
	 * (com.jdon.jivejdon.domain.model.ForumMessage)
	 */
	public void deleteMessageComposite(ForumMessage delforumMessage) throws Exception {
		Long key = delforumMessage.getMessageId();
		logger.debug("deleteNode messageId =" + key);
		try {
			Optional<ForumThread> forumThreadOptional = forumBuilder.getThread(delforumMessage
					.getForumThread().getThreadId());
			TreeVisitor messageDeletor = new MessageDeletor(this);
			logger.debug(" begin to walk into tree, and delete them");
			forumThreadOptional.get().acceptTreeModelVisitor(delforumMessage.getMessageId(),
					messageDeletor);
		} catch (Exception e) {
			String error = e + " deleteMessageComposite forumMessageId=" + delforumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.MessageRepository#deleteMessage(java.lang
	 * .Long)
	 */
	public void deleteMessage(Long messageId) throws Exception {
		try {
			messageDaoFacade.getMessageDao().deleteMessage(messageId);
			uploadRepository.deleteAllUploadFiles(messageId.toString());
			propertyDao.deleteProperties(Constants.MESSAGE, messageId);
			messageDaoFacade.getMessageDao().delReBlog(messageId);
		} catch (Exception e) {
			String error = e + " deleteMessage forumMessageId=" + messageId;
			logger.error(error);
			throw new Exception(error);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.MessageRepository#getNextId(int)
	 */
	public synchronized Long getNextId(int idType) throws Exception {
		try {
			Long mIDInt = messageDaoFacade.getSequenceDao().getNextId(Constants.MESSAGE);
			return mIDInt;
		} catch (SQLException e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	public void saveReBlog(OneOneDTO oneOneDTO) throws Exception {
		this.messageDaoFacade.getMessageDao().saveReBlog(oneOneDTO);
	}

	public Collection<ForumMessage> getReBlogByFrom(Long messageId) throws Exception {
		ForumMessage message = this.forumBuilder.getMessage(messageId);
		if (message == null)
			return null;

		Collection<Long> reblogIdTos = this.messageDaoFacade.getMessageDao().getReBlogByFrom(messageId);
		if (reblogIdTos == null || reblogIdTos.size() == 0)
			return null;
		Collection<ForumMessage> forumMessageTos = new ArrayList();
		for (Long reblogIdTo : reblogIdTos) {
			ForumMessage messageTo = forumBuilder.getMessage(reblogIdTo);
			if (messageTo != null)
				forumMessageTos.add(messageTo);
		}

		return forumMessageTos;

	}

	public ForumMessage getReBlogByTo(Long messageId) throws Exception {
		ForumMessage messageTo = forumBuilder.getMessage(messageId);
		if (messageTo == null)
			return null;

		Long reblogFromId = this.messageDaoFacade.getMessageDao().getReBlogByTo(messageId);
		return forumBuilder.getMessage(reblogFromId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.MessageRepository#getForumBuilder()
	 */
	public ForumFactory getForumBuilder() {
		return forumBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.MessageRepository#getMessageDaoFacade()
	 */
	public MessageDaoFacade getMessageDaoFacade() {
		return messageDaoFacade;
	}

}
