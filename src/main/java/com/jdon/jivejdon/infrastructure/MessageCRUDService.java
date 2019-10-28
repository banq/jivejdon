package com.jdon.jivejdon.infrastructure;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.jivejdon.api.util.JtaTransactionUtil;
import com.jdon.jivejdon.domain.command.MessageRemoveCommand;
import com.jdon.jivejdon.domain.command.PostRepliesMessageCommand;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.command.ReviseForumMessageCommand;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.builder.MessageRepositoryDao;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class MessageCRUDService {
	private final static Logger logger = LogManager.getLogger(MessageCRUDService.class);

	private final JtaTransactionUtil jtaTransactionUtil;
	private final MessageRepositoryDao messageRepository;
	private final TagRepository tagRepository;
	private final ForumFactory forumAbstractFactory;

	public MessageCRUDService(JtaTransactionUtil jtaTransactionUtil, MessageRepositoryDao messageRepository, TagRepository tagRepository,
							  ForumFactory forumAbstractFactory) {
		super();
		this.jtaTransactionUtil = jtaTransactionUtil;
		this.messageRepository = messageRepository;
		this.tagRepository = tagRepository;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	@OnEvent("saveTopicMessageCommand")
	public ForumMessage insertTopicMessage(PostTopicMessageCommand postTopicMessageCommand) {
		logger.debug("enter createTopicMessage");
		try {
			messageRepository.createTopicMessage(AnemicMessageDTO.commandToDTO(postTopicMessageCommand));
			logger.debug("createTopicMessage ok!");
			return forumAbstractFactory.getMessage(postTopicMessageCommand.getMessageId());
		} catch (Exception e) {
			String error = e + " createTopicMessage forumMessageId=" + postTopicMessageCommand.getMessageId();
			logger.error(error);
			return null;
		}
	}



	/**
	 * called by @Consumer("addReplyMessage") in AddReplyMessage that is listerner.
	 */
	public void insertReplyMessage(PostRepliesMessageCommand postRepliesMessageCommand) throws Exception {
		logger.debug("enter createReplyMessage");
		try {
            AnemicMessageDTO anemicMessageDTO = AnemicMessageDTO.commandToDTO(postRepliesMessageCommand);
            anemicMessageDTO.setParentMessage(new AnemicMessageDTO(postRepliesMessageCommand.getParentMessage().getMessageId()));
			anemicMessageDTO.setForumThread(postRepliesMessageCommand.getParentMessage().getForumThread());
            messageRepository.createReplyMessage(anemicMessageDTO);
			logger.debug("createReplyMessage ok!");
		} catch (Exception e) {
			String error = e + " createTopicMessage forumMessageId=" + postRepliesMessageCommand.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}


	public void updateMessage(ReviseForumMessageCommand reviseForumMessageCommand) throws Exception {
		logger.debug("enter updateMessage");
        AnemicMessageDTO anemicMessageDTO = AnemicMessageDTO.commandToDTO(reviseForumMessageCommand);
        try {
			messageRepository.updateMessage(anemicMessageDTO);

			// update the forumThread's updatetime
			messageRepository.updateThread(anemicMessageDTO.getForumThread());

		} catch (Exception e) {
			String error = e + " updateMessage forumMessageId=" + anemicMessageDTO.getMessageId();
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
	public Long deleteMessage(MessageRemoveCommand event) throws Exception {
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
			forumAbstractFactory.reloadThreadState(delforumMessage.getForumThread());
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
