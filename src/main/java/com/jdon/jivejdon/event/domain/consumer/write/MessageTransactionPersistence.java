package com.jdon.jivejdon.event.domain.consumer.write;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.event.MessageRemovedEvent;
import com.jdon.jivejdon.model.event.TopicMessageCreateCommand;
import com.jdon.jivejdon.model.util.OneOneDTO;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.builder.MessageRepositoryDao;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class MessageTransactionPersistence {
	private final static Logger logger = LogManager.getLogger(MessageTransactionPersistence.class);

	private final JtaTransactionUtil jtaTransactionUtil;
	private final MessageRepositoryDao messageRepository;
	private final TagRepository tagRepository;
	private final ForumFactory forumAbstractFactory;

	public MessageTransactionPersistence(JtaTransactionUtil jtaTransactionUtil, MessageRepositoryDao messageRepository, TagRepository tagRepository,
										 ForumFactory forumAbstractFactory) {
		super();
		this.jtaTransactionUtil = jtaTransactionUtil;
		this.messageRepository = messageRepository;
		this.tagRepository = tagRepository;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	@OnEvent("postTopicMessageCommand")
	public ForumMessage insertTopicMessage(TopicMessageCreateCommand command) {
		logger.debug("enter createTopicMessage");
		ForumMessage forumMessagePostDTO = command.getForumMessageDTO();
		try {
//			Thread.sleep(5000);
			jtaTransactionUtil.beginTransaction();
			messageRepository.createTopicMessage(forumMessagePostDTO);
			logger.debug("createTopicMessage ok!");
			jtaTransactionUtil.commitTransaction();
			return forumMessagePostDTO;
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " createTopicMessage forumMessageId=" + forumMessagePostDTO.getMessageId();
			logger.error(error);
			return null;
		}

	}

	public void insertReplyMessage(ForumMessageReply forumMessageReplyPostDTO) throws Exception {
		logger.debug("enter createReplyMessage");
		if ((forumMessageReplyPostDTO.getParentMessage() == null) || (forumMessageReplyPostDTO.getParentMessage().getMessageId() == null)) {
			logger.error("parentMessage is null, this is not reply message!");
			return;
		}
		try {
			jtaTransactionUtil.beginTransaction();
			messageRepository.createReplyMessage(forumMessageReplyPostDTO);
			logger.debug("createReplyMessage ok!");
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " createTopicMessage forumMessageId=" + forumMessageReplyPostDTO.getParentMessage().getMessageId();
			logger.error(error);
			throw new Exception(error);
		}

	}

	public void updateMessage(ForumMessage newForumMessageInputparamter) throws Exception {

		logger.debug("enter updateMessage");
		try {
			// merge
			jtaTransactionUtil.beginTransaction();
			messageRepository.updateMessage(newForumMessageInputparamter);

			// update the forumThread's updatetime
			messageRepository.updateThread(newForumMessageInputparamter.getForumThread());

			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " updateMessage forumMessageId=" + newForumMessageInputparamter.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}

	}

	public void moveMessage(Long messageId, Long forumId) throws Exception {
		Forum newForum = this.forumAbstractFactory.getForum(forumId);
		if (newForum == null) {
			logger.error("forum is null forumId=" + forumId);
			return;
		}
		ForumMessage forumMessage = forumAbstractFactory.getMessage(messageId);
		if (forumMessage == null)
			return;
		Long threadId = forumMessage.getForumThread().getThreadId();
		messageRepository.getMessageDaoFacade().getMessageDao().updateMovingForum(messageId, threadId, forumId);
	}

	@OnEvent("postReBlog")
	public void postReBlog(OneOneDTO oneOneDTO) throws Exception {
		logger.debug("enter postReBlog");
		try {
			jtaTransactionUtil.beginTransaction();
			messageRepository.saveReBlog(oneOneDTO);
			logger.debug("postReBlog ok!");
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " postReBlog oneManyDTO=" + oneOneDTO.getParent();
			logger.error(error);
			throw new Exception(error);
		}
	}

	@OnEvent("deleteMessage")
	public Long deleteMessage(MessageRemovedEvent event) throws Exception {
		logger.debug("enter deleteMessage");
		Long messageId = event.getMessageId();
		ForumMessage delforumMessage = forumAbstractFactory.getMessage(messageId);
		try {

			jtaTransactionUtil.beginTransaction();
			this.messageRepository.deleteMessageComposite(delforumMessage);

			// if the root message was deleted, the thread that it be in
			// will all be deleted
			if (delforumMessage.getMessageId().longValue() == delforumMessage.getForumThread().getRootMessage().getMessageId().longValue()) {
				logger.debug("1. it is a root message, delete the forumThread");
				tagRepository.deleteTagTitle(delforumMessage.getForumThread().getThreadId());
				messageRepository.deleteThread(delforumMessage.getForumThread());
			}

			jtaTransactionUtil.commitTransaction();

		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " deleteMessage forumMessageId=" + delforumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
		return messageId;
	}

}
