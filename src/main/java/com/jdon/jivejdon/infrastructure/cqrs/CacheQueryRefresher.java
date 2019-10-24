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
package com.jdon.jivejdon.infrastructure.cqrs;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.query.MessagePageIteratorSolver;

public class CacheQueryRefresher {

	protected final ForumFactory forumAbstractFactory;
	private final MessagePageIteratorSolver messagePageIteratorSolver;

	public CacheQueryRefresher(ForumFactory forumAbstractFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		this.forumAbstractFactory = forumAbstractFactory;
		this.messagePageIteratorSolver = messagePageIteratorSolver;
	}

	public void refresh(ForumMessage forumMessage) {
		// send to JMS or MQ
		// refresh the batch inquiry cache
		messagePageIteratorSolver.clearPageIteratorSolver("getMessages");
		messagePageIteratorSolver.clearPageIteratorSolver("getThreads");
		messagePageIteratorSolver.clearPageIteratorSolver(forumMessage.getForum().getForumId().toString());
		messagePageIteratorSolver.clearPageIteratorSolver(forumMessage.getForumThread().getThreadId().toString());
		messagePageIteratorSolver.clearPageIteratorSolver(forumMessage.getAccount().getUserId());

	}
}
