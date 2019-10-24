/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.repository.dao.filter;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.repository.builder.MessageInitFactory;
import com.jdon.jivejdon.repository.dao.sql.ForumDaoSql;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * Cache Decorator
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Introduce("modelCache")
public class ForumDaoCache extends ForumDaoSql {

	public ForumDaoCache(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, Constants constants, MessageInitFactory messageFactory) {
		super(jdbcTempSource, containerUtil, messageFactory, constants);
	}

	@Around()
	public Forum getForum(Long forumId) {
		return super.getForum(forumId);
	}

	/**
	 * public ForumState getForumState(Long forumId){ ForumState forumState =
	 * (ForumState) containerUtil.getModelFromCache(forumId, ForumState.class);
	 * if ((forumState == null) || (forumState.isModified())){ forumState =
	 * super.getForumState(forumId); containerUtil.addModeltoCache(forumId,
	 * forumState); } return forumState; }
	 */

	public void createForum(Forum forum) {
		super.createForum(forum);
		clearCache();
	}

	public void updateForum(Forum forum) {
		super.updateForum(forum);
		clearCache();

	}

	public void deleteForum(Forum forum) {
		super.deleteForum(forum);
		clearCache();
	}

}
