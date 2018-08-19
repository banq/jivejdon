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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

public class ThreadDirector {
	private final static Logger logger = LogManager.getLogger(ThreadDirector.class);

	private final ThreadBuilder forumThreadBuilder;
	
	private final Map nullthreads ;

	public ThreadDirector(ThreadBuilder forumThreadBuilder) {
		this.forumThreadBuilder = forumThreadBuilder;
		this.nullthreads = lruCache(100);
	}

	public ForumThread getThread(Long threadId) throws Exception {
		try {
			return getThread(threadId, null, null);
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
	public ForumThread getThread(final Long threadId, final ForumMessage rootForumMessage, final Forum forum) throws Exception {
		logger.debug("TH----> enter getThread, threadId=" + threadId);
		if (nullthreads.containsKey(threadId)){
			logger.error("repeat no threadId=" + threadId);			
			throw new Exception("repeat no this forumThread");			
		}
		ForumThread forumThread = (ForumThread) forumThreadBuilder.create(threadId);
		if (forumThread == null) {
			nullthreads.put(threadId, "null");
			logger.error("no threadId=" + threadId);			
			throw new Exception("no this forumThread");
		}

		if (forumThread.isSolid())
			return forumThread;

		construct(forumThread, rootForumMessage, forum);
		forumThread.setSolid(true);
		return forumThread;
	}

	public void construct(ForumThread forumThread, ForumMessage rootForumMessage, Forum forum) throws Exception {
		try {
			logger.debug("ForumThread construct :<Embed ForumThread---->  start, threadId=" + forumThread.getThreadId());
			// buildTreeModel at first called
			forumThreadBuilder.buildTreeModel(forumThread);

			// in buildPart will create rootForumMessage that need
			// buildInitState's TreeModel
			forumThreadBuilder.buildRootMessage(forumThread, rootForumMessage, forum);
			forumThreadBuilder.buildForum(forumThread, rootForumMessage, forum);
			forumThreadBuilder.buildProperties(forumThread);
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

}
