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
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.state.ForumThreadStateFactory;
import com.jdon.jivejdon.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.dao.MessageDao;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class ThreadBuilder {
	private final static Logger logger = LogManager.getLogger(ThreadBuilder.class);

	private final MessageDao messageDao;

	private final TagRepository tagRepository;

	private final MessageQueryDao messageQueryDao;

	private final ForumThreadStateFactory forumThreadStateFactory;

	private final ForumAbstractFactory forumAbstractFactory;

	private final PropertyDao propertyDao;

	public ThreadBuilder(MessageDao messageDao, TagRepository tagRepository, MessageQueryDao messageQueryDao, PropertyDao propertyDao,
						 ForumThreadStateFactory forumThreadStateFactory, ForumAbstractFactory
								 forumAbstractFactory) {
		this.messageDao = messageDao;
		this.tagRepository = tagRepository;
		this.messageQueryDao = messageQueryDao;
		this.propertyDao = propertyDao;
		this.forumThreadStateFactory = forumThreadStateFactory;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public ForumThread create(Long threadId) {
		ForumThread forumThread =  messageDao.getThreadCore(threadId);
		forumThread.setSolid(false);
		return forumThread;
	}

	public void buildRootMessage(ForumThread forumThread, ForumMessage rootForumMessage, Forum forum) throws Exception {
		try {
			Long rootmessageId = forumThread.getRootMessage().getMessageId();
			if ((rootForumMessage == null) || rootForumMessage instanceof ForumMessageReply
					|| rootForumMessage.getMessageId().longValue() != rootmessageId.longValue()) {
				rootForumMessage = forumAbstractFactory.messageDirector.getMessage(rootmessageId, forumThread, forum);
			}
			forumThread.setRootMessage(rootForumMessage);
			rootForumMessage.setForumThread(forumThread);

			// only have rootMessage, so have thread
			buildProperties(forumThread);
		} catch (Exception e) {
			String error = e + " buildRootMessage forumThreadId=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	public void buildProperties(ForumThread forumThread) {
		try {
			//init viewcount
			forumThread.getViewCounter().loadinitCount();

			forumThread.setName(forumThread.getRootMessage().getMessageVO().getSubject());
			Collection tags = tagRepository.getThreadTags(forumThread);
			ThreadTagsVO threadTagsVO = new ThreadTagsVO(forumThread, tags);
			forumThread.setThreadTagsVO(threadTagsVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buildForum(ForumThread forumThread, ForumMessage rootForumMessage, Forum forum) throws Exception {
		try {
			if ((forum == null) || (forum.getForumId().longValue() != forumThread.getForum().getForumId().longValue())) {
				forum = forumAbstractFactory.forumDirector.getForum(forumThread.getForum().getForumId(), forumThread, rootForumMessage);
			}
			forumThread.setForum(forum);
		} catch (Exception e) {
			String error = e + " buildRootMessage forumThreadId=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/**
	 * get a state of a thread forumThreadState.setTreeModel(treeModel);
	 * 
	 * @param forumThread
	 */
	public void buildTreeModel(final ForumThread forumThread) throws Exception {
		try {
			// NO NEED PRELOADE tREE, ONLY LOAD IT WHEN NEED.
			// forumThread.preloadTreeMode();
			// forumThreadTreeModelFactory.create(forumThread);
		} catch (Exception e) {
			String error = e + " buildInitState forumThreadId=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	public void buildState(ForumThread forumThread, ForumMessage rootMessage, MessageDirector messageDirector) throws Exception {
		try {
			logger.debug(" buildPartyState for forumThread=" + forumThread.getThreadId());
			Long lastMessageId = messageQueryDao.getLastPostMessageId(forumThread.getThreadId());
			if (lastMessageId == null) {
				logger.warn("maybe first running, not found lastMessageId for forumthreadId: " + forumThread.getThreadId());
				return;
			}
			ForumMessage lastMessage = rootMessage;
			if ((rootMessage == null) || (rootMessage.getMessageId().longValue() != lastMessageId.longValue()))
				lastMessage = messageDirector.getMessage(lastMessageId, forumThread, forumThread.getForum());
			lastMessage.setForumThread(forumThread);

			forumThreadStateFactory.init(forumThread, lastMessage);

			logger.debug(" buildPartyState for forumThread=" + forumThread.getThreadId());

		} catch (Exception e) {
			String error = e + " buildComponentState forumThreadId=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}

	}

}
