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
package com.jdon.jivejdon.spi.pubsub.subscriber.delmessage;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.domain.event.MessageRemovedEvent;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.cqrs.CacheQueryRefresher;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.query.MessagePageIteratorSolver;

@Consumer("delThread")
public class RefreshThreadAction implements DomainEventHandler {

	protected final ForumFactory forumAbstractFactory;

	private CacheQueryRefresher eventHandler;

	public RefreshThreadAction(ForumFactory forumAbstractFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		super();
		this.forumAbstractFactory = forumAbstractFactory;
		eventHandler = new CacheQueryRefresher(forumAbstractFactory, messagePageIteratorSolver);

	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageRemovedEvent messageRemovedEvent = (MessageRemovedEvent) event.getDomainMessage().getEventSource();
		// reload(messageRemovedEvent.getForumMessage());
		// send to pubsub bus to notify refresh view model
		eventHandler.refresh(messageRemovedEvent.getForumMessage());

	}

	public void reload(ForumMessage delforumMessage) {
		try {
			// update memory
//			if (!delforumMessage.getForumThread().isRoot(delforumMessage)) {// this
				// thread is be deleted only
				ForumThread forumThread = delforumMessage.getForumThread();
				this.forumAbstractFactory.reloadThreadState(forumThread);// refresh
//			} else
			    delforumMessage.getForum().getForumState().loadinitState();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
