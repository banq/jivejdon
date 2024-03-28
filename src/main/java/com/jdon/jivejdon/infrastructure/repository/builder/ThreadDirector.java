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
package com.jdon.jivejdon.infrastructure.repository.builder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.RootMessage;
import com.jdon.jivejdon.domain.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;

@Introduce("modelCache")
public class ThreadDirector implements ThreadDirectorIF{
	private final static Logger logger = LogManager.getLogger(ThreadDirector.class);

	private final MessageDao messageDao;

	private final TagRepository tagRepository;

	private final MessageDirectorIF messageDirectorIF;

	private final ForumDirector forumDirector;

	private final ConcurrentMap<Long, ForumThread> atomicFactorys = new ConcurrentHashMap<>();

	public ThreadDirector(ForumDirector forumDirector, MessageDao messageDao, TagRepository tagRepository,
			MessageDirectorIF messageDirectorIF) {
		this.forumDirector = forumDirector;
		this.messageDao = messageDao;
		this.tagRepository = tagRepository;
		this.messageDirectorIF = messageDirectorIF;
	}

	@Override
	@Around
	public ForumThread getThread(Long threadId) throws Exception {
		if (threadId == null || threadId == 0)
			return null;
		try {
			ForumThread  forumThread = atomicFactorys.computeIfAbsent(threadId, this::build);
			if(forumThread != null)
				atomicFactorys.remove(threadId);
			return forumThread;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	

	private ForumThread build(Long threadId) {
		Long rootmessageId = this.messageDao.getThreadRootMessageId(threadId);
		RootMessage rootMessage = messageDirectorIF.getRootMessage(rootmessageId);

	
		ForumThread forumThread = messageDao.getThreadCore(threadId, rootMessage);

		Forum forum = forumDirector.getForum(forumThread.getForum().getForumId());
		ThreadTagsVO threadTagsVO= new ThreadTagsVO(forumThread, tagRepository.getThreadTags(threadId));

		forumThread.build(forum, threadTagsVO);

		forumThread.getViewCounter().loadinitCount();
		return forumThread;
	}

}
