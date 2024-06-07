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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.infrastructure.repository.builder.MessageInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.ForumDao;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.PageIteratorSolverFixed;
import com.jdon.jivejdon.util.ToolsUtil;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class ForumDaoSql implements ForumDao {
	private final static Logger logger = LogManager.getLogger(ForumDaoSql.class);

	protected PageIteratorSolverFixed pageIteratorSolver;

	private JdbcTempSource jdbcTempSource;

	private PropertyDaoSql propertyDaoSql;

	private Constants constants;

	private MessageInitFactory messageFactory;

	public ForumDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, MessageInitFactory messageFactory,
			Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolverFixed(jdbcTempSource.getDataSource(),
				containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.propertyDaoSql = new PropertyDaoSql(jdbcTempSource, containerUtil);
		this.constants = constants;
		this.messageFactory = messageFactory;
	}

	public void clearCache() {
		pageIteratorSolver.clearCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.ForumDao#getForum(java.lang.String)
	 */
	public Forum getForum(Long id) {
		logger.debug("enter getForum for id:" + id);
		String LOAD_FORUM = "SELECT forumID, name, description, modDefaultThreadVal, "
				+ "modDefaultMsgVal, modMinThreadVal, modMinMsgVal, modifiedDate, "
				+ "creationDate FROM jiveForum WHERE forumID=?";
		List queryParams = new ArrayList();
		queryParams.add(id);

		Forum ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_FORUM);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = messageFactory.createForumCore(map);
				ret.setPropertys(propertyDaoSql.getProperties(Constants.FORUM, id));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.ForumDao#createForum(com.jdon.jivejdon.domain.model.
	 * Forum)
	 */
	public void createForum(Forum forum) {
		try {
			String ADD_FORUM = "INSERT INTO jiveForum(forumID, name, description, modDefaultThreadVal, "
					+ "modDefaultMsgVal, modMinThreadVal, modMinMsgVal, modifiedDate, creationDate)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(forum.getForumId());
			queryParams.add(forum.getName());
			queryParams.add(forum.getDescription());
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			queryParams.add(saveDateTime);
			forum.setModifiedDate(displayDateTime);
			queryParams.add(saveDateTime);
			forum.setCreationDate(displayDateTime);

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_FORUM);
			clearCache();

			propertyDaoSql.saveProperties(Constants.FORUM, forum.getForumId(), forum.getPropertys());
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.ForumDao#updateForum(com.jdon.jivejdon.domain.model.
	 * Forum)
	 */
	public void updateForum(Forum forum) {
		try {
			String SAVE_FORUM = "UPDATE jiveForum SET name=?, description=?, modDefaultThreadVal=?, "
					+ "modDefaultMsgVal=?, modMinThreadVal=?, modMinMsgVal=?, " + "modifiedDate=? WHERE forumID=?";
			List queryParams = new ArrayList();
			queryParams.add(forum.getName());
			queryParams.add(forum.getDescription());
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			queryParams.add(saveDateTime);
			forum.setModifiedDate(displayDateTime);

			queryParams.add(forum.getForumId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_FORUM);
			clearCache();

			propertyDaoSql.deleteProperties(Constants.FORUM, forum.getForumId());
			propertyDaoSql.saveProperties(Constants.FORUM, forum.getForumId(), forum.getPropertys());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.ForumDao#deleteForum(com.jdon.jivejdon.domain.model.
	 * Forum)
	 */
	public void deleteForum(Forum forum) {
		try {
			String sql = "DELETE FROM jiveForum WHERE forumID=?";
			List queryParams = new ArrayList();
			queryParams.add(forum.getForumId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
			clearCache();
			propertyDaoSql.deleteProperties(Constants.FORUM, forum.getForumId());
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.ForumDao#getForums(int, int)
	 */
	public PageIterator getForums(int start, int count) {
		logger.debug("enter getForums ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveForum ";

		String GET_ALL_ITEMS = "select forumID from jiveForum ";

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "", start, count);
	}

	/**
	 * get the thread count from message db.
	 * 
	 * @param forumId
	 * @return
	 */
	public int getMessageCount(Long forumId) {
		String ALL_THREADS = "SELECT count(1) from jiveMessage WHERE forumID=?";
		List queryParams = new ArrayList();
		queryParams.add(forumId);
		Integer count = null;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, ALL_THREADS);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
			logger.debug("found count:" + count);
		} catch (Exception e) {
			logger.error(e);
		}
		return count.intValue();
	}

	/**
	 * get the thread count from thread db.
	 * 
	 * @param forumId
	 * @return
	 */
	public int getThreadCount(Long forumId) {
		String ALL_MESSAGES = "SELECT count(1) from jiveThread WHERE forumID=?";
		List queryParams2 = new ArrayList();
		queryParams2.add(forumId);

		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams2, ALL_MESSAGES);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
			logger.debug("found count:" + count);
		} catch (Exception e) {
			logger.error(e);
		}
		return count;
	}

	public int getThreadCount() {
		String ALL_MESSAGES = "SELECT count(1) from jiveThread";
		List queryParams2 = new ArrayList();
		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams2, ALL_MESSAGES);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
			logger.debug("found count:" + count);
		} catch (Exception e) {
			logger.error(e);
		}
		return count;
	}

	/**
	 * get laste message from the message db
	 * 
	 */
	public Long getLatestPostMessageId(Long forumId) {
		String LAST_MESSAGES = "SELECT messageID from jiveMessage WHERE forumID=? ORDER BY modifiedDate DESC";
		List queryParams2 = new ArrayList();
		queryParams2.add(forumId);

		Long messageId = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams2, LAST_MESSAGES);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				messageId = (Long) map.get("messageID");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return messageId;
	}

}
