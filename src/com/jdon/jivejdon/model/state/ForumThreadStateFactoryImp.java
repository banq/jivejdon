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
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ForumThreadState;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;

/**
 * state is a value Object of DDD, and is immutable
 * 
 * @author banq
 * 
 */
@Component
public class ForumThreadStateFactoryImp implements ForumThreadStateFactory {

	private final MessageQueryDao messageQueryDao;

	public ForumThreadStateFactoryImp(MessageQueryDao messageQueryDao) {
		super();
		this.messageQueryDao = messageQueryDao;
	}

	public void init(ForumThread forumThread, ForumMessage lastPost) {
		try {
			long messagereplyCount;
			long messageCount = messageQueryDao.getMessageCount(forumThread.getThreadId());
			if (messageCount >= 1)
				messagereplyCount = messageCount - 1;
			else
				messagereplyCount = messageCount;
			ForumThreadState forumThreadState = new ForumThreadState(forumThread, lastPost, messagereplyCount);
			forumThread.setState(forumThreadState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNewMessage(ForumThread forumThread, ForumMessage newLastPost) {
		try {
			long newMessageCount = forumThread.getState().addMessageCount();
			ForumThreadState forumThreadState = new ForumThreadState(forumThread, newLastPost, newMessageCount);
			forumThread.setState(forumThreadState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMessage(ForumThread forumThread, ForumMessage forumMessage) {
		try {
			ForumThreadState forumThreadState = new ForumThreadState(forumThread, forumMessage, forumThread.getState().getMessageCount());
			forumThread.setState(forumThreadState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
