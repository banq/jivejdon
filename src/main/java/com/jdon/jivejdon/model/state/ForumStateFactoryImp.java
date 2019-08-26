/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.model.state;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumState;
import com.jdon.jivejdon.repository.dao.ForumDao;

/**
 * state is a value Object of DDD, and is immutable
 * 
 * @author banq
 * 
 */
@Component
public class ForumStateFactoryImp implements ForumStateFactory {

	private ForumDao forumDao;

	public ForumStateFactoryImp(ForumDao forumDao) {
		super();
		this.forumDao = forumDao;
	}

	public void init(Forum forum, ForumMessage lastPost) {
		try {
			long newMessageCount = forumDao.getMessageCount(forum.getForumId());
			long newThreadCount = forumDao.getThreadCount(forum.getForumId());
			ForumState forumState = new ForumState(forum, lastPost, newMessageCount, newThreadCount);
			forum.setForumState(forumState);
//			lastPost.setForum(forum);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addNewMessage(Forum forum, ForumMessage newLastPost) {
		try {
			long newMessageCount = forum.getForumState().addMessageCount();
			ForumState forumState = new ForumState(forum, newLastPost, newMessageCount, forum.getForumState().getThreadCount());
			forum.setForumState(forumState);
//			newLastPost.setForum(forum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNewThread(Forum forum, ForumMessage newLastPost) {
		try {
			long newMessageCount = forum.getForumState().addMessageCount();
			long newThreadCount = forum.getForumState().addThreadCount();
			ForumState forumState = new ForumState(forum, newLastPost, newMessageCount, newThreadCount);
			forum.setForumState(forumState);
//			newLastPost.setForum(forum);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateMessage(Forum forum, ForumMessage forumMessage) {
		try {
			ForumState forumState = new ForumState(forum, forumMessage, forum.getForumState().getMessageCount(), forum.getForumState()
					.getThreadCount());
			forum.setForumState(forumState);
//			forumMessage.setForum(forum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
