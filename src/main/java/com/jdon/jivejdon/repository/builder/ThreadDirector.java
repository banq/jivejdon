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

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.dao.MessageDao;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

@Introduce("modelCache")
public class ThreadDirector implements ThreadDirectorIF {
	private final static Logger logger = LogManager.getLogger(ThreadDirector.class);

	private final MessageDao messageDao;

	private final TagRepository tagRepository;

	private final PropertyDao propertyDao;

	private final MessageDirectorIF messageDirectorIF;

	private final ForumDirector forumDirector;

	public ThreadDirector(ForumDirector forumDirector, MessageDao messageDao, TagRepository tagRepository,  PropertyDao propertyDao,MessageDirectorIF
			messageDirectorIF) {
		this.forumDirector = forumDirector;
		this.messageDao = messageDao;
		this.tagRepository = tagRepository;
		this.propertyDao = propertyDao;
		this.messageDirectorIF = messageDirectorIF;
	}

	@Override
	@Around
	public ForumThread getThread(Long threadId) throws Exception {
		if (threadId == null || threadId == 0)
			return null;
		try {
			return build(threadId, null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}


	/**
	 * return a full ForumThread one ForumThread has one rootMessage need solve
	 * the realtion with Forum rootForumMessage lastPost
	 *
	 * @param threadId
	 * @return
	 */
	@Override
	@Around
	public ForumThread getThread(final Long threadId, ForumMessage rootMessage) throws Exception {
		logger.debug("TH----> enter getThread, threadId=" + threadId);
		if (threadId == null || threadId == 0)
			return null;

		try {
			return build(threadId, rootMessage);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private ForumThread build(final Long threadId, ForumMessage rootMessage) throws Exception {
		ForumThread forumThread = messageDao.getThreadCore(threadId , rootMessage);
		if (forumThread == null) {
			logger.error("no threadId=" + threadId);
			throw new Exception("no this forumThread");
		}
		Forum forum = forumDirector.getForum(forumThread.getForum().getForumId());
		if (rootMessage == null) {
			Long rootmessageId = this.messageDao.getThreadRootMessageId(forumThread.getThreadId());
			rootMessage = messageDirectorIF.getMessage(rootmessageId, forumThread);
		}
			//init viewcount
		forumThread.getViewCounter().loadinitCount();
		Collection tags = tagRepository.getThreadTags(forumThread);
		ThreadTagsVO threadTagsVO = new ThreadTagsVO(forumThread, tags);
		forumThread.build(forum, rootMessage, threadTagsVO);
		return forumThread;
	}

}
