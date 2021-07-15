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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.infrastructure.repository.builder.MessageInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;
import com.jdon.jivejdon.util.ToolsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 */
public abstract class MessageDaoSql implements MessageDao {
	private final static Logger logger = LogManager.getLogger(MessageDaoSql.class);

	protected JdbcTempSource jdbcTempSource;

	private Constants constants;

	private MessageInitFactory messageFactory;

	public MessageDaoSql(JdbcTempSource jdbcTempSource, MessageInitFactory messageFactory, Constants constants) {
		this.jdbcTempSource = jdbcTempSource;
		this.constants = constants;
		this.messageFactory = messageFactory;
	}

	public AnemicMessageDTO getAnemicMessage(Long messageId) {
		logger.debug("enter getMessage  for id:" + messageId);
		String LOAD_MESSAGE = "SELECT threadID, forumID, userID, subject, body, modValue, " + "rewardPoints, "
				+ "creationDate, modifiedDate, parentMessageID FROM jiveMessage WHERE messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(messageId);

		AnemicMessageDTO forumMessage = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_MESSAGE);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				forumMessage = messageFactory.createAnemicMessage(messageId, map);
			}
		} catch (Exception e) {
			logger.error("AnemicMessageDTO messageId=" + messageId + " happend  " + e);
		}
		logger.debug("getAnemicMessage end");
		return forumMessage;
	}

	public Long getParentMessageId(Long messageId) {
		logger.debug("enter isRoot  for id:" + messageId);
		String LOAD_MESSAGE = "SELECT parentMessageID,threadID FROM jiveMessage WHERE messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(messageId);

		Long pmessageId = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_MESSAGE);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				pmessageId = (Long) map.get("parentMessageID");
			}
		} catch (Exception e) {
			logger.error("isRoot messageId=" + messageId + " happend  " + e);
		}
		return pmessageId;
	}

	public MessageVO getMessageVOCore(ForumMessage forumMessage) {
		logger.debug("enter MessageVO  for id:" + forumMessage.getMessageId());
		String LOAD_MESSAGE = "SELECT threadID, forumID, userID, subject, body, modValue, " + "rewardPoints, "
				+ "creationDate, modifiedDate, parentMessageID FROM jiveMessage WHERE messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(forumMessage.getMessageId());

		MessageVO messageVO = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_MESSAGE);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				messageVO = messageFactory.createMessageVOCore(forumMessage.getMessageId(), map, forumMessage);
			}
		} catch (Exception e) {
			logger.error("messageId=" + forumMessage.getMessageId() + " happend messageVO " + e);
		}
		return messageVO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.jivejdon.dao.MessageDao#getThread(java.lang.String)
	 */
	public ForumThread getThreadCore(Long threadId, ForumMessage rootMessage) {
		logger.debug("enter getThread for id:" + threadId);
		String LOAD_THREAD = "SELECT forumID, rootMessageID, modValue, rewardPoints, " + "creationDate, "
				+ "modifiedDate FROM jiveThread WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadId);

		ForumThread forumThread = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_THREAD);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				forumThread = messageFactory.createThreadCore(threadId, map, rootMessage);
			}
		} catch (Exception e) {
			logger.error("threadId=" + threadId + " happend  " + e);
		}
		return forumThread;
	}

	public Long getThreadRootMessageId(Long threadId) {
		logger.debug("enter getThreadRootMessageId for id:" + threadId);
		String LOAD_THREAD = "SELECT forumID, rootMessageID FROM jiveThread WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadId);

		Long rootMessageId = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_THREAD);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				rootMessageId = (Long) map.get("rootMessageID");

			}
		} catch (Exception e) {
			logger.error("threadId=" + threadId + " happend  " + e);
		}
		return rootMessageId;
	}

	/*
	 * topic message insert, no parentMessageID value,so the table's field default
	 * value must be null
	 */
	public void createMessage(AnemicMessageDTO forumMessagePostDTO) throws Exception {
		logger.debug("enter createTopicMessage for id:" + forumMessagePostDTO.getMessageId());
		// differnce with createRpleyMessage: parentMessageID,
		MessageVO messageVO = forumMessagePostDTO.getMessageVO();
		if (messageVO.getSubject().length() == 0 || messageVO.getBody().length() == 0)
			return;
		String INSERT_MESSAGE = "INSERT INTO jiveMessage(messageID, threadID, forumID, "
				+ "userID, subject, body, modValue, rewardPoints, creationDate, modifiedDate) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		List queryParams = new ArrayList();
		queryParams.add(forumMessagePostDTO.getMessageId());
		queryParams.add(forumMessagePostDTO.getForumThread().getThreadId());
		queryParams.add(forumMessagePostDTO.getForum().getForumId());
		queryParams.add(forumMessagePostDTO.getAccount().getUserId());

		queryParams.add(messageVO.getSubject());
		queryParams.add(messageVO.getBody());
		queryParams.add(new Integer(0));
		// getRewardPoints
		queryParams.add(new Integer(0));

		long now = System.currentTimeMillis();
		String saveDateTime = ToolsUtil.dateToMillis(now);
		String displayDateTime = constants.getDateTimeDisp(saveDateTime);
		queryParams.add(saveDateTime);
		queryParams.add(saveDateTime);

		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_MESSAGE);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("messageId=" + forumMessagePostDTO.getMessageId() + " happend " + e);
		}
	}

	public void createMessageReply(AnemicMessageDTO forumMessage) throws Exception {
		logger.debug("enter createMessageReply for id:" + forumMessage.getMessageId());
		try {
			// differnce with createTopicMessage: parentMessageID,
			if (this.getAnemicMessage(forumMessage.getParentMessage().getMessageId()) == null)
				throw new Exception(" this message=" + forumMessage.getMessageId() + "'s parent= "
						+ forumMessage.getParentMessage().getMessageId() + " has deleted");

			String INSERT_MESSAGE = "INSERT INTO jiveMessage(messageID, parentMessageID, " + "threadID," + " forumID, "
					+ "userID, subject, body, modValue, rewardPoints, creationDate, " + "modifiedDate)" + " " + ""
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(forumMessage.getMessageId());
			queryParams.add(forumMessage.getParentMessage().getMessageId());
			queryParams.add(forumMessage.getForumThread().getThreadId());
			queryParams.add(forumMessage.getForum().getForumId());
			queryParams.add(forumMessage.getAccount().getUserId());
			MessageVO messageVO = forumMessage.getMessageVO();
			queryParams.add(messageVO.getSubject());
			queryParams.add(messageVO.getBody());
			queryParams.add(new Integer(0));
			// getRewardPoints
			queryParams.add(new Integer(0));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			queryParams.add(saveDateTime);
			queryParams.add(saveDateTime);

			jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_MESSAGE);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("messageId=" + forumMessage.getMessageId() + " happend "
					+ forumMessage.getForum().getForumId() + e);

		}
	}

	public void createThread(AnemicMessageDTO forumMessagePostDTO) throws Exception {
		String INSERT_THREAD = "INSERT INTO jiveThread(threadID,forumID,rootMessageID,modValue, "
				+ "rewardPoints,creationDate,modifiedDate) VALUES(?,?,?,?,?,?,?)";
		List queryParams = new ArrayList();
		queryParams.add(forumMessagePostDTO.getForumThread().getThreadId());
		queryParams.add(forumMessagePostDTO.getForumThread().getForum().getForumId());
		queryParams.add(forumMessagePostDTO.getMessageId());
		queryParams.add(new Integer(0));
		// MessageVO messageVO = forumThread.getRootMessage().getMessageVO();
		// getRewardPoints
		queryParams.add(new Integer(0));
		// queryParams.add(new Integer(messageVO.getRewardPoints()));

		long now = System.currentTimeMillis();
		String saveDateTime = ToolsUtil.dateToMillis(now);
		String displayDateTime = constants.getDateTimeDisp(saveDateTime);
		queryParams.add(saveDateTime);

		queryParams.add(saveDateTime);
		// forumThread.setModifiedDate(displayDateTime);

		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_THREAD);
		} catch (Exception e) {
			logger.error("forumThread=" + forumMessagePostDTO.getForumThread().getThreadId() + " happend " + e);
			throw new Exception(e);
		}
	}

	/**
	 * when update the root message, call this method
	 *
	 * @param forumThread
	 */
	public void updateThread(ForumThread forumThread) throws Exception {
		String SAVE_THREAD = "UPDATE jiveThread SET  modifiedDate=? WHERE threadID=?";

		List queryParams = new ArrayList();

		long now = System.currentTimeMillis();
		String saveDateTime = ToolsUtil.dateToMillis(now);
		queryParams.add(saveDateTime);
		// forumThread.setModifiedDate(displayDateTime);
		queryParams.add(forumThread.getThreadId());
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_THREAD);

		} catch (Exception e) {
			logger.error("forumThread=" + forumThread.getThreadId() + " happend " + e);
			throw new Exception(e);
		}
	}

	public void updateThreadName(String name, ForumThread forumThread) {
		String SQL = "UPDATE jiveMessage SET  subject=? WHERE messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(name);
		queryParams.add(forumThread.getRootMessage().getMessageId());
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
		} catch (Exception e) {
			logger.error("forumThread=" + forumThread.getThreadId() + " happend " + e);

		}
	}

	public void saveReBlog(OneOneDTO oneOneDTO) throws Exception {
		String INSERT_S = "INSERT INTO reblog(reblogFromID, reblogToID) VALUES(?,?)";
		List queryParams = new ArrayList();
		try {
			queryParams.add((Long) oneOneDTO.getParent());
			queryParams.add((Long) oneOneDTO.getChild());
			jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_S);
		} catch (Exception e) {
			logger.error("reBlog=" + oneOneDTO.getParent() + " happend " + e);
			throw new Exception(e);
		}
	}

	public void delReBlog(Long msgId) throws Exception {
		String S1 = "Delete from reblog where reblogFromID =?";
		List<Long> queryParams = new ArrayList<>();
		try {
			queryParams.add(msgId);
			jdbcTempSource.getJdbcTemp().operate(queryParams, S1);
		} catch (Exception e) {
			logger.error("delete reBlog error: " + msgId + e);
			throw new Exception(e);
		}
	}

	public Collection<Long> getReBlogByFrom(Long reblogFromID) throws Exception {
		String LOAD_S = "SELECT reblogToID FROM reblog WHERE reblogFromID=?";
		List queryParams = new ArrayList();
		queryParams.add(reblogFromID);
		Collection<Long> reblogToIDs = new ArrayList();
		;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_S);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				reblogToIDs.add((Long) map.get("reblogToID"));
			}
		} catch (Exception e) {
			logger.error("getReBlogByMessage reblogFromID=" + reblogFromID + " happend " + e);
			throw new Exception(e);
		}
		return reblogToIDs;
	}

	public Collection<Long> getReBlogByTo(Long reblogToID) throws Exception {
		String LOAD_S = "SELECT reblogFromID FROM reblog WHERE reblogToID=?";
		List queryParams = new ArrayList();
		queryParams.add(reblogToID);
		Collection<Long> reblogFromIDs = new ArrayList();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_S);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				reblogFromIDs.add((Long) map.get("reblogFromID"));
			}
		} catch (Exception e) {
			logger.error("getReBlogByThreadId threadId=" + reblogToID + " happend " + e);
			throw new Exception(e);
		}
		return reblogFromIDs;
	}

	public void updateMovingForum(Long messageId, Long threadId, Long forumId) throws Exception {
		String SQL = "UPDATE jiveMessage SET  forumID=? WHERE messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(forumId);
		queryParams.add(messageId);

		String SQL2 = "UPDATE jiveThread SET  forumID=? WHERE threadID=?";
		List queryParams2 = new ArrayList();
		queryParams2.add(forumId);
		queryParams2.add(threadId);
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
			jdbcTempSource.getJdbcTemp().operate(queryParams2, SQL2);
		} catch (Exception e) {
			logger.error(" updateMovingForum forumThread=" + threadId + " happend " + e);
			throw new Exception(e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.dao.MessageDao#updateMessage(com.jdon.jivejdon.domain.model
	 * .ForumMessage)
	 */
	public void updateMessage(AnemicMessageDTO forumMessage) throws Exception {
		String SAVE_MESSAGE = "";
		SAVE_MESSAGE = "UPDATE jiveMessage SET  subject=?, body=?, modValue=?, "
				+ "rewardPoints=?, modifiedDate=? WHERE messageID=?";
		List queryParams = new ArrayList();
		MessageVO messageVO = forumMessage.getMessageVO();
		queryParams.add(messageVO.getSubject());
		queryParams.add(messageVO.getBody());
		queryParams.add(new Integer(0));
		// getRewardPoints
		queryParams.add(new Integer(0));
		// queryParams.add(new Integer(messageVO.getRewardPoints()));

		long now = System.currentTimeMillis();
		String saveDateTime = ToolsUtil.dateToMillis(now);
		String displayDateTime = constants.getDateTimeDisp(saveDateTime);
		queryParams.add(saveDateTime);

		queryParams.add(forumMessage.getMessageId());
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_MESSAGE);
			// propertyDaoSql.deleteProperties(Constants.MESSAGE,
			// forumMessage.getMessageId());
			// propertyDaoSql.insertProperties(Constants.MESSAGE,
			// forumMessage.getMessageId(), forumMessage.getPropertys());

		} catch (Exception e) {
			logger.error("messageId=" + forumMessage.getMessageId() + " happend " + e);
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.dao.MessageDao#deleteMessage(com.jdon.jivejdon.domain.model
	 * .ForumMessage)
	 */
	public void deleteMessage(Long forumMessageId) throws Exception {
		String DELETE_MESSAGE = "DELETE FROM jiveMessage WHERE messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(forumMessageId);
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_MESSAGE);

		} catch (Exception e) {
			logger.error(e);
			throw new Exception("messageId=" + forumMessageId + " happend " + e);
		}
	}

	public void deleteThread(Long threadId) throws Exception {
		String DELETE_THREAD = "DELETE FROM jiveThread WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadId);
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_THREAD);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("threadId=" + threadId + " happend " + e);
		}
	}

}
