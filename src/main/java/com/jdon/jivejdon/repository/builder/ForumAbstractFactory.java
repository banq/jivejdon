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

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.dao.MessageDao;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.util.ContainerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ForumAbstractFactory implements ForumFactory {
	private final static Logger logger = LogManager.getLogger(ForumAbstractFactory.class);

	public final ForumDirector forumDirector;
	public final MessageDirector messageDirector;
	public final ThreadDirector threadDirector;

	protected final MessageBuilder messageBuilder;
	protected final ThreadBuilder threadBuilder;
	protected final ForumBuilder forumBuilder;
	protected final ContainerUtil containerUtil;

	private final SequenceDao sequenceDao;

	// define in manager.xml

	public ForumAbstractFactory(MessageBuilder messageBuilder, ForumBuilder forumBuilder,
								ContainerUtil containerUtil, SequenceDao sequenceDao, MessageDao
										messageDao, TagRepository tagRepository, MessageQueryDao
										messageQueryDao, PropertyDao propertyDao) {
		this.containerUtil = containerUtil;
		this.messageBuilder = messageBuilder;
		this.messageBuilder.setForumAbstractFactory(this);

		this.threadBuilder = new ThreadBuilder(messageDao, tagRepository, messageQueryDao,
				propertyDao,  this);

		this.forumBuilder = forumBuilder;

		this.sequenceDao = sequenceDao;

		this.forumDirector = new ForumDirector(this, forumBuilder);
		this.messageDirector = new MessageDirector(messageBuilder);
		this.threadDirector = new ThreadDirector(threadBuilder);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getForum(java.lang.
	 * Long)
	 */
	public Forum getForum(Long forumId) {
		return forumDirector.getForum(forumId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getMessageWithPropterty
	 * (java.lang.Long)
	 */
	public ForumMessage getMessageWithPropterty(Long messageId) {
		return messageDirector.getMessageWithPropterty(messageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getMessage(java.lang
	 * .Long)
	 */
	public ForumMessage getMessage(Long messageId) {
		return messageDirector.buildMessage(messageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getThread(java.lang
	 * .Long)
	 */
	public Optional<ForumThread> getThread(Long threadId) {
		ForumThread forumThread = null;
		try {
			forumThread = threadDirector.getThread(threadId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(forumThread);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#reloadThreadState(com
	 * .jdon.jivejdon.model.ForumThread)
	 */
	public void reloadThreadState(ForumThread forumThread) throws Exception {
		try {
			threadBuilder.buildTreeModel(forumThread);
			forumThread.getState().loadinitState();
//			threadBuilder.buildState(forumThread, forumThread.getRootMessage(), messageDirector);
//
//			Forum forum = getForum(forumThread.getForum().getForumId());
//			forumBuilder.buildState(forum);

		} catch (Exception e) {
			String error = e + " refreshAllState forumThread=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}


	public Long getNextId(final int idType) throws Exception {
		try {
			return sequenceDao.getNextId(idType);
		} catch (Exception e) {
			String error = e + " getNextId ";
			logger.error(error);
			throw new Exception(error);
		}

	}

}
