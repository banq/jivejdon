/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.repository.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.MessageVO;

public class MessageDirector {
	private final static Logger logger = LogManager.getLogger(MessageDirector.class);

	private final MessageBuilder messageBuilder;
	
	private final Map nullmessages ;


	public MessageDirector(MessageBuilder messageBuilder) {
		super();
		this.messageBuilder = messageBuilder;
		this.nullmessages = lruCache(100);
	}

	public ForumMessage getMessageWithPropterty(Long messageId) {
		return getMessage(messageId);
	}

	public ForumMessage getMessage(Long messageId) {
		if (messageId == null || messageId == 0)
			return null;
		try {
			return getMessage(messageId, null, null);
		} catch (Exception e) {
			return null;
		}
	}

	public MessageVO getMessageVO(Long messageId) {
		if (messageId == null || messageId == 0)
			return null;
		MessageVO mVO = null;
		try {
			mVO = messageBuilder.createMessageVO(messageId);
			// if construts mVo put code here
		} catch (Exception e) {
			return null;
		}
		return mVO;
	}

	/*
	 * return a full ForumMessage need solve the relations with Forum
	 * ForumThread parentMessage
	 */
	public ForumMessage getMessage(Long messageId, final ForumThread forumThread, final Forum forum) throws Exception {
		logger.debug(" enter get a full Message for id=" + messageId);
		if (messageId == null || messageId == 0) {
			return null;
		}
		if(nullmessages.containsKey(messageId)){
			logger.error("repeat no this message in database id=" + messageId);
			return null;
		}
		final ForumMessage forumMessage = (ForumMessage) messageBuilder.create(messageId);
		if (forumMessage == null) {
			nullmessages.put(messageId, "NULL");
			logger.error("no this message in database id=" + messageId);
			return null;
		}
		if (forumMessage.isSolid())
			return forumMessage;

		// http://www.javalobby.org/forums/thread.jspa?messageID=91836328
		construct(forumMessage, forumThread, forum);
		forumMessage.applyFilters();
		forumMessage.setSolid(true);
		return forumMessage;
	}

	private void construct(ForumMessage forumMessage, ForumThread forumThread, Forum forum) throws Exception {
		if (forumMessage.getMessageId() == null)
			return;
		try {
			messageBuilder.asyncGetAccount(forumMessage);
			messageBuilder.buildFilters(forumMessage);
			messageBuilder.buildUploadFiles(forumMessage);
			messageBuilder.buildPart(forumMessage, forumThread, forum);
			messageBuilder.buildMessageProperties(forumMessage);
		} catch (Exception e) {
			String error = e + " construct forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}
	
	public static <K,V> Map<K,V> lruCache(final int maxSize) {
	    return new LinkedHashMap<K,V>(maxSize*4/3, 0.75f, true) {
	        @Override
	        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
	            return size() > maxSize;
	        }
	    };
	}


}
