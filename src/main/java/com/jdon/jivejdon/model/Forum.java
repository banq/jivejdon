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
package com.jdon.jivejdon.model;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.annotation.model.OnCommand;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.event.domain.producer.write.ThreadEventSourcingRole;
import com.jdon.jivejdon.model.event.PostTopicMessageCommand;
import com.jdon.jivejdon.model.event.TopicMessagePostedEvent;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.model.property.HotKeys;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.event.ForumSubscribedNotifyEvent;
import com.jdon.jivejdon.model.util.ForumModel;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Searchable
@Model
public class Forum extends ForumModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@SearchableId
	private Long forumId;

	@SearchableProperty
	private String name;
	private String description;
	private String creationDate;

	/**
	 * the forum modified date, not the message modified date in the forum,
	 */
	private String modifiedDate;
	private Collection propertys;

	/**
	 * @link aggregation
	 */

	private volatile AtomicReference<ForumState> forumState;

	private HotKeys hotKeys;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public SubPublisherRoleIF publisherRole;
//
//	@Inject
//	private ForumStateFactory forumStateManager;

	@Inject
	public ThreadEventSourcingRole eventSourcingRole;

	@OnCommand("postMessageCommand")
	public void postMessage(AnemicMessageDTO anemicMessageDTO) {
		//fill the business rule for post a topic message
		if (isRepeatedMessage(anemicMessageDTO)){
			System.err.println("repeat message error: " + anemicMessageDTO.getMessageVO().getSubject());
			return;
		}
		DomainMessage domainMessage = eventSourcingRole.postTopicMessage(new
				PostTopicMessageCommand(anemicMessageDTO));
		ForumMessage rootForumMessage = (ForumMessage)domainMessage.getBlockEventResult();
		if (rootForumMessage != null) {//make sure repostiory finish save new message
			threadPosted(rootForumMessage);
			eventSourcingRole.topicMessagePosted(new TopicMessagePostedEvent(rootForumMessage));
		}
	}

	private boolean isRepeatedMessage(AnemicMessageDTO anemicMessageDTO){
		if (this.forumState.get().getLastPost() == null) return false;
		return this.forumState.get().getLastPost().isSubjectRepeated(anemicMessageDTO.getMessageVO().getSubject())?true:false;

	}

	public void threadPosted(ForumMessage rootForumMessage) {
		forumState.get().addThreadCount();
		forumState.get().setLastPost(rootForumMessage);
		this.publisherRole.subscriptionNotify(new ForumSubscribedNotifyEvent(this.forumId, rootForumMessage));
	}

	public Forum() {
		// init state
  	    forumState = new AtomicReference(new ForumState(this));
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
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
	 * @param description
	 *            The description to set.
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
	 * @param forumId
	 *            The forumId to set.
	 */
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		if (getForumState().getLastPost() != null)
			return getForumState().getModifiedDate();
		else
			return this.creationDate;
	}

	public long getModifiedDate2() {
		if (modifiedDate == null)
			return 0;
		Date mdate = Constants.parseDateTime(modifiedDate);
		return mdate.getTime();
	}


	public void setModifiedDate(String modifiedDate) {
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
		try {
			return forumState.get();
		} finally {
		}
	}
//
//	public void setForumState(ForumState forumState) {
//		if (forumState != null)
//			this.forumState.lazySet(forumState);
//	}

	public HotKeys getHotKeys() {
		return hotKeys;
	}

	public void setHotKeys(HotKeys hotKeys) {
		this.hotKeys = hotKeys;
	}

	public void addNewMessage(ForumMessageReply forumMessageReply) {
		forumState.get().addMessageCount();
		forumState.get().setLastPost(forumMessageReply);

		// Date olddate = Constants.parseDateTime(oldmessage.getCreationDate());
		// if (Constants.timeAfter(1, olddate)) {// a event per one hour
		// this.publisherRole.subscriptionNotify(new
		// ForumSubscribedNotifyEvent(this.getForumId(), forumMessageReply));
		// }
	}

	public void updateNewMessage(ForumMessage forumMessage) {
		forumState.get().setLastPost(forumMessage);
	}



}
