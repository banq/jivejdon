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
package com.jdon.jivejdon.service;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.auth.CUDInputInterceptor;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;

/**
 * Message operations interface. if modify this interface, remmeber modify
 * com.jdon.jivejdon.model.jivejdon_permission.xml
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * @see CUDInputInterceptor
 * 
 */
public interface ForumMessageService {

    AnemicMessageDTO initMessage(EventModel em);

    AnemicMessageDTO initReplyMessage(EventModel em);

	/**
	 * has Authorization ; no cache Intercepts it, it is Called by message's
	 * modify or deletion first time accessing this method must be checked. it
	 * is configured in jdonframework.xml
	 * 
	 * <getMethod name="findMessage"/>
	 */
	AnemicMessageDTO findMessage(Long messageId);

	/**
	 * no Authorization ; cache Intercepts it, Called by message List, not for
	 * modify or deletion .
	 * 
	 * now MessageListAction or MessageRecursiveListAction call this method
	 */
	ForumMessage getMessage(Long messageId);

	/**
	 * no Authorization ; no cache Intercept equals getMessage, has full
	 * propperties
	 */
	ForumMessage findMessageWithPropterty(Long messageId);

	/**
	 * create a topic message, it is a root message
	 */
	Long createTopicMessage(EventModel em) throws Exception;

	/**
	 * create a reply message.
	 */
	Long createReplyMessage(EventModel em) throws Exception;

	void updateMessage(EventModel em) throws Exception;

	void deleteMessage(EventModel em) throws Exception;

	void deleteUserMessages(String username) throws Exception;

	/**
	 * for batch inquiry
	 */
	ForumThread getThread(Long id);

	RenderingFilterManager getFilterManager();

	/**
	 * check if forumMessage is Authenticated by current login user.
	 */
	boolean checkIsAuthenticated(ForumMessage forumMessage);

	// /message/messageMaskAction.shtml?method=maskMessage
	void maskMessage(EventModel em) throws Exception;

	public void reBlog(Long replyMessageId, Long topicMessageId) throws Exception;

	public void updateThreadName(EventModel em) throws Exception;

}
