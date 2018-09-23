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

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.state.ForumStateFactory;
import com.jdon.jivejdon.repository.HotKeysRepository;
import com.jdon.jivejdon.repository.dao.ForumDao;

public class ForumBuilder {
	private final static Logger logger = LogManager.getLogger(ForumBuilder.class);

	private HotKeysRepository hotKeysFactory;

	private ForumDao forumDao;

	private ForumStateFactory forumStateFactory;

	public ForumBuilder(ForumDao forumDao, ForumStateFactory forumStateFactory, HotKeysRepository hotKeysFactory) {
		this.hotKeysFactory = hotKeysFactory;
		this.forumDao = forumDao;
		this.forumStateFactory = forumStateFactory;
	}

	public Forum create(Long forumId) {
		return forumDao.getForum(forumId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.Builder#buildPart(com.jdon.jivejdon
	 * .model.Forum)
	 */
	public void buildPart(Forum forum) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.Builder#buildProperties(com.jdon
	 * .jivejdon.model.Forum)
	 */
	public void buildProperties(Forum forum) {
		forum.setHotKeys(hotKeysFactory.getHotKeys());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.Builder#buildState(com.jdon.jivejdon
	 * .model.Forum, com.jdon.jivejdon.model.ForumMessage)
	 */
	public void buildState(Forum forum, ForumThread forumThread, ForumMessage message, MessageDirector messageDirector) throws Exception {
		try {
			logger.debug(" loadForumState for forumId=" + forum.getForumId());
			Long lastMessageId = forumDao.getLastPostMessageId(forum.getForumId());
			if (lastMessageId == null) {
				logger.warn("maybe first running, not found lastMessageId for forumId: " + forum.getForumId());
				return;
			}
			if ((message == null) || (message.getMessageId().longValue() != lastMessageId.longValue()))
				message = messageDirector.getMessage(lastMessageId, forumThread, forum);
			this.forumStateFactory.init(forum, message);
		} catch (Exception e) {
			String error = e + " buildState forumMessageId=" + forum.getForumId();
			logger.error(error);
			throw new Exception(error);
		}
	}

}
