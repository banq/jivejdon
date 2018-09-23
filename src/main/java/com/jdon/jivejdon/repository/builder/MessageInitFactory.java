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

import java.util.Map;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.model.message.MessageVO;

public class MessageInitFactory {

	private Constants constants;

	public MessageInitFactory(Constants constants) {
		super();
		this.constants = constants;
	}

	public ForumMessage createMessageCore(Long messageId, Map map) {
		ForumMessage forumMessage = null;
		try {
			Long pID = (Long) map.get("parentMessageID");
			if (pID == null)
				forumMessage = new ForumMessage(messageId);
			else {
				forumMessage = new ForumMessageReply(messageId, new ForumMessage(pID));
			}
			Object o = map.get("userID");
			if (o != null) {
				com.jdon.jivejdon.model.Account account = new com.jdon.jivejdon.model.Account();
				account.setUserIdLong((Long) o);
				forumMessage.setAccount(account);
			} else {
				System.err.print("messageId=" + messageId + " no userID in DB");
				forumMessage.setAccount(createAnonymous());
			}

			MessageVO messageVO = createMessageVOCore(messageId, map);
			forumMessage.setMessageVO(messageVO);

			String saveDateTime = ((String) map.get("modifiedDate")).trim();
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			forumMessage.setModifiedDate(Long.parseLong(saveDateTime));

			saveDateTime = ((String) map.get("creationDate")).trim();
			displayDateTime = constants.getDateTimeDisp(saveDateTime);
			forumMessage.setCreationDate(displayDateTime);
			// get the formatter so later can transfer String to Date

			ForumThread forumThread = new ForumThread();
			forumThread.setThreadId((Long) map.get("threadID"));
			forumMessage.setForumThread(forumThread);

			Forum forum = new Forum();
			forum.setForumId((Long) map.get("forumID"));
			forumMessage.setForum(forum);

			// lazy load
			// forumMessage.setPropertys(propertyDaoSql.getAllPropertys(Constants.MESSAGE,
			// messageId));
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		}
		return forumMessage;
	}

	public MessageVO createMessageVOCore(Long messageId, Map map) {
		MessageVO messageVO = new MessageVO();
		try {
			messageVO.setSubject((String) map.get("subject"));
			messageVO.setBody((String) map.get("body"));
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		}
		return messageVO;
	}

	public ForumThread createThreadCore(Long threadId, Map map) {

		ForumMessage rootforumMessage = new ForumMessage((Long) map.get("rootMessageID"));
		ForumThread forumThread = new ForumThread(rootforumMessage);

		try {
			forumThread.setThreadId(threadId);

			Forum forum = new Forum();
			forum.setForumId((Long) map.get("forumID"));
			forumThread.setForum(forum);

			// String saveDateTime = ((String) map.get("modifiedDate")).trim();
			// String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			// forumThread.setModifiedDate(displayDateTime);

			String saveDateTime = ((String) map.get("creationDate")).trim();
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			forumThread.setCreationDate(displayDateTime);

			// forumThread.setPropertys(propertyDaoSql.getAllPropertys(Constants.THREAD,
			// threadId));
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		}
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
			System.err.println(e);
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
