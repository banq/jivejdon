package com.jdon.jivejdon.infrastructure.repository.builder;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.ThreadRepository;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDaoFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	 * @see com.jdon.jivejdon.infrastructure.repository.ThreadRepository#updateThread(com.jdon.jivejdon.domain.model
	 * .ForumThread)
	 */
	public void updateThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().updateThread(thread);
		// thread.setModified(true);
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.infrastructure.repository.ThreadRepository#deleteThread(com.jdon.jivejdon.domain.model
	 * .ForumThread)
	 */
	public void deleteThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().deleteThread(thread.getThreadId());

	}

	public void updateThreadName(String name, ForumThread forumThread) {
		messageDaoFacade.getMessageDao().updateThreadName(name, forumThread);
	}

	

}
