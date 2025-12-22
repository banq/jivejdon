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
package com.jdon.jivejdon.domain.model;

import java.util.Collection;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.annotation.model.OnCommand;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.event.TopicMessagePostedEvent;
import com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.domain.model.subscription.event.ForumSubscribedNotifyEvent;
import com.jdon.jivejdon.spi.pubsub.publish.ThreadEventSourcingRole;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class Forum {
	private final static Logger logger = LogManager.getLogger(Forum.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long forumId;

	private String name;
	private String description;
	private String creationDate;

	/**
	 * the forum modified date, not the message modified date in the forum,
	 */
	private long modifiedDate;
	private Collection propertys;

	/**
	 * @link aggregation
	 */

	private final ForumState forumState;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public SubPublisherRoleIF publisherRole;
	//
	// @Inject
	// private ForumStateFactory forumStateManager;

	@Inject
	public ThreadEventSourcingRole eventSourcingRole;

	@OnCommand("postTopicMessageCommand")
	public void postMessage(PostTopicMessageCommand postTopicMessageCommand) {
		// fill the business rule for post a topic message
		if (isRepeatedMessage(postTopicMessageCommand)) {
			logger.error("repeat message error: " + postTopicMessageCommand.getMessageVO().getSubject());
			return;
		}
		DomainMessage domainMessage = eventSourcingRole.saveTopicMessage(postTopicMessageCommand);
		ForumMessage rootForumMessage = (ForumMessage) domainMessage.getBlockEventResult();
		if (rootForumMessage != null) {// make sure repostiory finish save new message
			threadPosted(rootForumMessage);
			eventSourcingRole.topicMessagePosted(new TopicMessagePostedEvent(rootForumMessage));
		}
	}

	private boolean isRepeatedMessage(PostTopicMessageCommand postTopicMessageCommand) {
		if (this.forumState.getLatestPost() == null)
			return false;
		return this.forumState.getLatestPost()
				.isSubjectRepeated(postTopicMessageCommand.getMessageVO().getSubject()) ? true : false;

	}

	public void threadPosted(ForumMessage rootForumMessage) {
		forumState.addThreadCount();
		forumState.setLatestPost(rootForumMessage);
		this.publisherRole.subscriptionNotify(new ForumSubscribedNotifyEvent(this.forumId, rootForumMessage));
	}

	public Forum() {
		// init state
		forumState = new ForumState(this);
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the forumId.
	 */
	public Long getForumId() {
		return forumId;
	}

	/**
	 * @param forumId The forumId to set.
	 */
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public long getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection getPropertys() {
		return propertys;
	}

	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public ForumState getForumState() {
			return forumState;
	}
	//
	// public void setForumState(ForumState forumState) {
	// if (forumState != null)
	// this.forumState.lazySet(forumState);
	// }

	public void addNewMessage(ForumMessageReply forumMessageReply) {
		forumState.addMessageCount();
		forumState.setLatestPost(forumMessageReply);

		// Date olddate = Constants.parseDateTime(oldmessage.getCreationDate());
		// if (Constants.timeAfter(1, olddate)) {// a pubsub per one hour
		// this.publisherRole.subscriptionNotify(new
		// ForumSubscribedNotifyEvent(this.getForumId(), forumMessageReply));
		// }
	}

	public void updateNewMessage(ForumMessage forumMessage) {
		forumState.setLatestPost(forumMessage);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Forum forum = (Forum) o;
		if (forum.getForumId() == null || this.forumId == null)
			return false;
		return this.forumId.longValue() == forum.getForumId().longValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.forumId);
	}

}
