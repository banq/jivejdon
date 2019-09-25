package com.jdon.jivejdon.repository.builder;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.repository.ThreadRepository;
import com.jdon.jivejdon.repository.dao.MessageDaoFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ThreadRepositoryDao implements ThreadRepository {
	private final static Logger logger = LogManager.getLogger(ThreadRepositoryDao.class);

	protected MessageDaoFacade messageDaoFacade;

	public ThreadRepositoryDao(MessageDaoFacade messageDaoFacade) {
		this.messageDaoFacade = messageDaoFacade;
	}

	
	public void createThread(AnemicMessageDTO forumMessagePostDTO) throws Exception {
		messageDaoFacade.getMessageDao().createThread(forumMessagePostDTO);
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#updateThread(com.jdon.jivejdon.model
	 * .ForumThread)
	 */
	public void updateThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().updateThread(thread);
		// thread.setModified(true);
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#deleteThread(com.jdon.jivejdon.model
	 * .ForumThread)
	 */
	public void deleteThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().deleteThread(thread.getThreadId());

	}

	public void updateThreadName(String name, ForumThread forumThread) {
		messageDaoFacade.getMessageDao().updateThreadName(name, forumThread);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.jivejdon.service.ForumMessageService#getThreadsPrevNext(java
	 *      .lang.String, int)
	 */
	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#getThreadsPrevNext(java.lang.Long, java
	 * .lang.Long)
	 */
	public List getThreadsPrevNext(Long forumId, Long currentThreadId) {
		return messageDaoFacade.getMessageQueryDao().getThreadsPrevNext(forumId, currentThreadId);
	}

}
