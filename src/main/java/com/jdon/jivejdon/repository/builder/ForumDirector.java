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
import com.jdon.jivejdon.repository.HotKeysRepository;
import com.jdon.jivejdon.repository.dao.ForumDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForumDirector {
	private final static Logger logger = LogManager.getLogger(ForumDirector.class);

	private final HotKeysRepository hotKeysFactory;

	private final ForumDao forumDao;

	public ForumDirector(ForumDao forumDao,  HotKeysRepository hotKeysFactory) {
		this.hotKeysFactory = hotKeysFactory;
		this.forumDao = forumDao;
	}
	public Forum getForum(Long forumId) {
		logger.debug(" enter getForum for forumId=" + forumId);
		if (forumId == null)
			return null;
		try {
			final Forum forum = (Forum) create(forumId);
			if (forum == null) {
				logger.error("no this forum in database id=" + forumId);
				return null;
			}
			if (forum.isSolid())
				return forum;

			buildProperties(forum);
			forum.setSolid(true);
			return forum;
		} catch (Exception e) {
			return null;
		}

	}

	public Forum create(Long forumId) {
		return forumDao.getForum(forumId);
	}

	public void buildProperties(Forum forum) {
		forum.setHotKeys(hotKeysFactory.getHotKeys());
	}


	public void construct(Forum forum, ForumThread forumThread, ForumMessage forumMessage) throws Exception {
		buildProperties(forum);
//		forumBuilder.buildState(forum, forumThread, forumMessage, forumAbstractFactory.messageDirector);

	}

}
