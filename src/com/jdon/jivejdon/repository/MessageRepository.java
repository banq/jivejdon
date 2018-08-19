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

import java.util.Collection;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.util.OneOneDTO;
import com.jdon.jivejdon.repository.dao.MessageDaoFacade;

public interface MessageRepository {

	/*
	 * create the topic message
	 */
	public abstract void createTopicMessage(ForumMessage forumMessage) throws Exception;

	/**
	 * the relation about creating reply forumMessage only need a parameter :
	 * parent message. we can get the Forum or ForumThread from the parent
	 * message. the hypelink parameter in jsp must be a paremeter: the Id of
	 * parent message.
	 * 
	 */
	public abstract void createReplyMessage(ForumMessageReply forumMessageReply) throws Exception;

	/*
	 * update the message, update the message's subject and body we must mark
	 * the message that has been updated. there are two kinds of parameters: the
	 * primary key /new entity data in DTO ForumMessage of the method patameter
	 */
	public abstract void updateMessage(ForumMessage forumMessage) throws Exception;

	/**
	 * 
	 * Composite pattern
	 * 
	 * delete a node or its all childern and refresh the cache.
	 * 
	 * @param forumMessage
	 */
	public abstract void deleteMessageComposite(ForumMessage delforumMessage) throws Exception;

	public abstract void deleteMessage(Long messageId) throws Exception;

	public abstract Long getNextId(int idType) throws Exception;

	/**
	 * @return Returns the forumBuilder.
	 */
	public abstract ForumFactory getForumBuilder();

	/**
	 * @return Returns the messageDaoFacade.
	 */
	public abstract MessageDaoFacade getMessageDaoFacade();

	public void saveReBlog(OneOneDTO oneOneDTO) throws Exception;

	public Collection<ForumMessage> getReBlogByFrom(Long messageId) throws Exception;

	public ForumMessage getReBlogByTo(Long threadId) throws Exception;
}