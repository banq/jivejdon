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
package com.jdon.jivejdon.infrastructure.repository.builder;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.*;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.MessageDaoSql;
import com.jdon.jivejdon.domain.model.message.MessageVO;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageInitFactory {
	private final static Logger logger = LogManager.getLogger(MessageInitFactory.class);

	private Constants constants;

	public MessageInitFactory(Constants constants) {
		super();
		this.constants = constants;
	}


	public AnemicMessageDTO createAnemicMessage(Long messageId, Map map) {
		AnemicMessageDTO messageCore = new AnemicMessageDTO(messageId);
		try {
			Long pID = (Long) map.get("parentMessageID");
			messageCore.setParentMessage(new AnemicMessageDTO(pID));

			Object o = map.get("userID");
			if (o != null) {
				Account account = new Account();
				account.setUserIdLong((Long) o);
				messageCore.setAccount(account);
			} else {
				logger.warn("messageId=" + messageId + " no userID in DB ");
				messageCore.setAccount(createAnonymous());				
			}

			MessageVO messageVO = new MessageVO();
			messageVO = messageVO.builder().subject((String) map.get("subject")).body((String) map.get("body")).build();
			messageCore.setMessageVO(messageVO);

			String saveDateTime = ((String) map.get("modifiedDate")).trim();
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			messageCore.setModifiedDate(Long.parseLong(saveDateTime));

			saveDateTime = ((String) map.get("creationDate")).trim();
			displayDateTime = constants.getDateTimeDisp(saveDateTime);
			messageCore.setCreationDate(displayDateTime);
			// get the formatter so later can transfer String to Date

			Forum forum = new Forum();
			forum.setForumId((Long) map.get("forumID"));
			messageCore.setForum(forum);

			Long threadId = (Long) map.get("threadID");
			ForumThread forumThread = new ForumThread(null, threadId);
			forumThread.setForum(forum);
			messageCore.setForumThread(forumThread);
			// lazy load
			// forumMessage.setPropertys(propertyDaoSql.getAllPropertys(Constants.MESSAGE,
			// messageId));
		} catch (Exception e) {
			logger.error(e);
		} finally {
		}
		return messageCore;
	}

	public MessageVO createMessageVOCore(Long messageId, Map map, ForumMessage forumMessage) {
		String subject = "";
		String body = "";
		try {
			subject = (String) map.get("subject");
			body = (String) map.get("body");
		} catch (Exception e) {
			logger.error("createMessageVOCore " + subject + " " + body + " error: " + e);
		} finally {
		}
		return forumMessage.messageVOBuilder().subject(subject).body(body).build();
	}

	public ForumThread createThreadCore(Long threadId, Map map, RootMessage rootMessage) {

			Forum forum = new Forum();
			forum.setForumId((Long) map.get("forumID"));
			ForumThread forumThread = rootMessage.getForumThread();
			forumThread.setForum(forum);

//			forumThread.setForum(forum);

			// String saveDateTime = ((String) map.get("modifiedDate")).trim();
			// String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			// forumThread.setModifiedDate(displayDateTime);

			String saveDateTime = ((String) map.get("creationDate")).trim();
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			forumThread.setCreationDate(displayDateTime);
			forumThread.setCreationDate2(Long.parseLong(saveDateTime));

			// forumThread.setPropertys(propertyDaoSql.getAllPropertys(Constants.THREAD,
			// threadId));

		return forumThread;
	}

	public Forum createForumCore(Map map) {
		Forum ret = new Forum();
		try {
			ret.setForumId((Long) map.get("forumID"));
			ret.setName((String) map.get("name"));
			ret.setDescription((String) map.get("description"));

			String saveDateTime = ((String) map.get("modifiedDate")).trim();
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			ret.setModifiedDate(displayDateTime);

			saveDateTime = ((String) map.get("creationDate")).trim();
			displayDateTime = constants.getDateTimeDisp(saveDateTime);
			ret.setCreationDate(displayDateTime);
		} catch (Exception e) {
			logger.error(e);
		} finally {
		}
		return ret;
	}

	private Account createAnonymous() {
		Account account = new Account();
		account.setUsername("anonymous");
		account.setUserIdLong(new Long(0));
		account.setEmail("anonymous@anonymous.com");
		account.setRoleName(Role.ANONYMOUS);
		account.setModifiedDate("");
		account.setCreationDate("");
		account.setAnonymous(true);
		return account;
	}
}
