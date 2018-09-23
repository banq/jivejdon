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

public class ForumDirector {
	private final static Logger logger = LogManager.getLogger(ForumDirector.class);

	private ForumBuilder forumBuilder;

	private ForumAbstractFactory forumAbstractFactory;

	public ForumDirector(ForumAbstractFactory forumAbstractFactory, ForumBuilder forumBuilder) {
		this.forumBuilder = forumBuilder;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public Forum getForum(Long forumId) {
		try {
			return getForum(forumId,null, null);
		} catch (Exception e) {
			return null;
		}

	}

	public Forum getForum(Long forumId, ForumThread forumThread,  final ForumMessage forumMessage) throws Exception {
		logger.debug(" enter getForum for forumId=" + forumId);
		if (forumId == null)
			return null;
		final Forum forum = (Forum) forumBuilder.create(forumId);
		if (forum == null) {
			logger.error("no this forum in database id=" + forumId);
			return null;
		}
		if (forum.isSolid())
			return forum;
		
		construct(forum, forumThread, forumMessage);
		forum.setSolid(true);

		return forum;
	}

	public void construct(Forum forum, ForumThread forumThread, ForumMessage forumMessage) throws Exception {
		forumBuilder.buildProperties(forum);
		forumBuilder.buildState(forum, forumThread, forumMessage, forumAbstractFactory.messageDirector);

	}

	public void setForumBuilder(ForumBuilder forumBuilder) {
		this.forumBuilder = forumBuilder;
	}

}
