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
import com.jdon.jivejdon.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.dao.MessageDao;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThreadDirector {
	private final static Logger logger = LogManager.getLogger(ThreadDirector.class);

	private final MessageDao messageDao;

	private final TagRepository tagRepository;

	private final PropertyDao propertyDao;

	private final MessageDirectorIF messageDirectorIF;

	private final ForumDirector forumDirector;

	private final Map nullthreads ;

	public ThreadDirector(ForumDirector forumDirector, MessageDao messageDao, TagRepository tagRepository,  PropertyDao propertyDao,MessageDirectorIF
			messageDirectorIF) {
		this.forumDirector = forumDirector;
		this.messageDao = messageDao;
		this.tagRepository = tagRepository;
		this.propertyDao = propertyDao;
		this.messageDirectorIF = messageDirectorIF;
		this.nullthreads = lruCache(100);
	}

	public ForumThread getThread(Long threadId) throws Exception {
		try {
			return getThread(threadId,null,null);
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
	public ForumThread getThread(final Long threadId, ForumMessage forumMessage, final Forum forum) throws Exception {
		logger.debug("TH----> enter getThread, threadId=" + threadId);
		if (nullthreads.containsKey(threadId)){
			logger.error("repeat no threadId=" + threadId);
			throw new Exception("repeat no this forumThread");
		}
		ForumThread forumThread = (ForumThread) create(threadId);
		if (forumThread == null) {
			nullthreads.put(threadId, "null");
			logger.error("no threadId=" + threadId);
			throw new Exception("no this forumThread");
		}

		if (forumThread.isSolid())
			return forumThread;

		construct(forumThread, forum);
		forumThread.setSolid(true);
		return forumThread;
	}

	public ForumThread create(Long threadId) {
		return  messageDao.getThreadCore(threadId);

	}

	public void construct(ForumThread forumThread, Forum forum) throws Exception {
		try {
			logger.debug("ForumThread construct :<Embed ForumThread---->  start, threadId=" + forumThread.getThreadId());
			// buildTreeModel at first called
			buildTreeModel(forumThread);

			// in buildPart will create rootForumMessage that need
			// buildInitState's TreeModel
			buildForum(forumThread, forum);
			buildRootMessage(forumThread,forum);

			buildProperties(forumThread);
			logger.debug("ForumThread construct: ok threadId=" + forumThread.getThreadId());

		} catch (Exception e) {
			String error = e + " construct forumThreadId=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	public static <K,V> Map<K,V> lruCache(final int maxSize) {
	    return new LinkedHashMap<K,V>(maxSize*4/3, 0.75f, true) {
	        @Override
	        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
	            return size() > maxSize;
	        }
	    };
	}

	public void buildRootMessage(ForumThread forumThread, Forum forum) throws Exception {
		try {
			Long rootmessageId = this.messageDao.getThreadRootMessageId(forumThread.getThreadId());
			ForumMessage rootForumMessage  = messageDirectorIF.getMessage(rootmessageId, forumThread);
			forumThread.setRootMessage(rootForumMessage);
//			rootForumMessage.setForumThread(forumThread);

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

			Collection tags = tagRepository.getThreadTags(forumThread);
			ThreadTagsVO threadTagsVO = new ThreadTagsVO(forumThread, tags);
			forumThread.setThreadTagsVO(threadTagsVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buildForum(ForumThread forumThread, Forum forum) throws Exception {
		try {
			if ((forum == null) || (forum.getForumId().longValue() != forumThread.getForum().getForumId().longValue())) {
				forum = forumDirector.getForum(forumThread.getForum().getForumId());
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

}
