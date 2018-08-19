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
package com.jdon.jivejdon.event.bus.cqrs.query;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.MessagePageIteratorSolver;

public class PostThreadEventHandler implements EventBusHandler {
	protected final ForumFactory forumAbstractFactory;
	private final MessagePageIteratorSolver messagePageIteratorSolver;

	public PostThreadEventHandler(ForumFactory forumAbstractFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		this.forumAbstractFactory = forumAbstractFactory;
		this.messagePageIteratorSolver = messagePageIteratorSolver;
	}

	public void refresh(Long forumMessageId) {
		ForumMessage forumMessage = forumAbstractFactory.getMessage(forumMessageId);
		messagePageIteratorSolver.clearPageIteratorSolver("getThreads");
		messagePageIteratorSolver.clearPageIteratorSolver(forumMessage.getForum().getForumId().toString());
		messagePageIteratorSolver.clearPageIteratorSolver(forumMessage.getAccount().getUserId());

	}
}
