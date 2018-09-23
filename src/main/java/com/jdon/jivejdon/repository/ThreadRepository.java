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
package com.jdon.jivejdon.repository;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

import java.util.List;

public interface ThreadRepository {

	/**
	 * create a new Thread, this is for topic message
	 *
	 * @param rootForumMessage
	 * @return
	 * @throws Exception
	 */
	public abstract ForumThread createThread(ForumMessage rootForumMessage) throws Exception;

	public abstract void updateThread(ForumThread thread) throws Exception;

	public abstract void deleteThread(ForumThread thread) throws Exception;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.jivejdon.service.ForumMessageService#getThreadsPrevNext(java
	 *      .lang.String, int)
	 */
	public abstract List getThreadsPrevNext(Long forumId, Long currentThreadId);

	void updateThreadName(String name, ForumThread forumThread);

}