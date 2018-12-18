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
package com.jdon.jivejdon.model.dci;

import com.jdon.annotation.Component;
import com.jdon.cache.UtilCache;
import com.jdon.container.pico.Startable;
import com.jdon.domain.dci.RoleAssigner;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.write.ThreadEventSourcingRoleImpl;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.event.MessageRemovedEvent;
import com.jdon.jivejdon.model.event.TopicMessageCreateCommand;
import com.jdon.jivejdon.model.util.OneOneDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class ThreadManagerContext implements Startable {
	private final static Logger logger = LogManager.getLogger(ThreadManagerContext.class);

	private final RoleAssigner roleAssinger;

	private final UtilCache transactions;

	public ThreadManagerContext(RoleAssigner roleAssinger) {
		super();
		this.roleAssinger = roleAssinger;
		this.transactions = new UtilCache(100, 30 * 60 * 1000, false);
	}

	/**
	 * until creating ok, Read can be execute.
	 * 
	 * @param messageId
	 * @return
	 */
	public boolean isTransactionOk(Long messageId) {
		if (transactions.size() == 0)
			return true;
		if (!transactions.containsKey(messageId)) {
			return true;
		}

		DomainMessage message = (DomainMessage) transactions.get(messageId);
		if (message.getBlockEventResult() != null) {
			transactions.remove(messageId);
			message.clear();
			return true;
		}
		return false;
	}

	public void post(ForumMessage forumMessageInputDTO) {
		// 1. create a root message of a new thread, topic message
		ThreadEventSourcingRole eventSourcingRole = (ThreadEventSourcingRole) roleAssinger.assign(forumMessageInputDTO,
				new ThreadEventSourcingRoleImpl());
		DomainMessage domainMessage = eventSourcingRole.postTopicMessage(new
				TopicMessageCreateCommand(forumMessageInputDTO));
		transactions.put(forumMessageInputDTO.getMessageId(), domainMessage);

		if (!isTransactionOk(forumMessageInputDTO.getMessageId())) {
			logger.error(" create message error: " + forumMessageInputDTO.getMessageId());
			logger.error(" create message error subject:" + forumMessageInputDTO.getMessageVO()
					.getSubject());
		} else {
			//2.post thread in memmory
			ThreadEventSourcingRole threadRole = (ThreadEventSourcingRole) roleAssinger.assign
					(forumMessageInputDTO, new ThreadEventSourcingRoleImpl());
			threadRole.postThread(domainMessage);
		}

	}

	public void postReBlog(Long messageFromId, Long messageToId) {
		ThreadEventSourcingRole eventSourcingRole = (ThreadEventSourcingRole) roleAssinger.assignRoleEvents(new ThreadEventSourcingRoleImpl());
		OneOneDTO oneOneDTO = new OneOneDTO(messageFromId, messageToId);
		eventSourcingRole.postReBlog(oneOneDTO);
	}

	public void delete(ForumMessage delforumMessage) {
		ThreadEventSourcingRole eventSourcingRole = (ThreadEventSourcingRole) roleAssinger.assign(delforumMessage, new ThreadEventSourcingRoleImpl());
		DomainMessage domainMessage = eventSourcingRole.deleteMessage(new MessageRemovedEvent(delforumMessage.getMessageId()));
		transactions.put(delforumMessage.getMessageId(), domainMessage);

		ThreadEventSourcingRole threadRole = (ThreadEventSourcingRole) roleAssinger.assign(delforumMessage, new ThreadEventSourcingRoleImpl());
		threadRole.delThread(domainMessage);

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		transactions.stop();

	}

}
