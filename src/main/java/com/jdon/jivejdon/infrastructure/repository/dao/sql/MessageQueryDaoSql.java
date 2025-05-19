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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.domain.model.query.QuerySpecification;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.QuerySpecDBModifiedDate;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.infrastructure.repository.query.MessagePageIteratorSolver;
import com.jdon.model.query.block.Block;
import com.jdon.treepatterns.model.TreeModel;

/**
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class MessageQueryDaoSql implements MessageQueryDao {
	private final static Logger logger = LogManager.getLogger(MessageQueryDaoSql.class);

	protected JdbcTempSource jdbcTempSource;
	protected MessagePageIteratorSolver messagePageIteratorSolver;

	// protected PageIteratorSolverFixed pageIteratorSolver;

	/**
	 * @param jdbcTempSource
	 */
	public MessageQueryDaoSql(JdbcTempSource jdbcTempSource, MessagePageIteratorSolver messagePageIteratorSolver) {
		this.messagePageIteratorSolver = messagePageIteratorSolver;
		this.jdbcTempSource = jdbcTempSource;
	}

	public TreeModel getTreeModel(Long threadId, Long rootMessageId) {
		logger.debug("getTreeModel from jdbc for threadId=" + threadId);
		TreeModel treeModel = new TreeModel(rootMessageId.longValue());

		// get the messagId collection only except the root messageId
		String SQL = "SELECT messageID, parentMessageID, creationDate FROM jiveMessage " + "WHERE threadID=? AND parentMessageID IS NOT NULL "
				+ "ORDER BY creationDate ASC";
		// parentMessageID IS NOT NULL or parentMessageID != 0 ?
		List queryParams = new ArrayList();
		queryParams.add(threadId);
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, SQL);

			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Long messageID = (Long) map.get("messageID");
				Long parentMessageID = (Long) map.get("parentMessageID");
				treeModel.addChild(parentMessageID.longValue(), messageID.longValue());
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return treeModel;
	}

	public int getMessageCountOfUser(Long userId) {
		String USER_MESSAGE_COUNT = "SELECT count(*) FROM jiveMessage WHERE jiveMessage.userID=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, USER_MESSAGE_COUNT);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
		} catch (Exception e) {
			logger.error("userId=" + userId + " error:" + e);
		}
		return count;
	}

	public PageIterator getThreadListByUser(String userId, int start, int count) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage where parentMessageID IS NULL AND userID=? ";

		String GET_ALL_ITEMS = "select threadID  from jiveMessage WHERE userID=? AND parentMessageID IS NULL ORDER BY modifiedDate DESC";

		Collection params = new ArrayList(1);
		params.add(userId);
		return messagePageIteratorSolver.getPageIteratorSolver(userId).getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start,
				count);

	}

	public PageIterator getMesageListByUser(String userId, int start, int count) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage where parentMessageID IS NOT NULL AND userID=? ";

		String GET_ALL_ITEMS = "select messageID  from jiveMessage WHERE userID=? AND parentMessageID IS NOT NULL ORDER BY modifiedDate DESC";

		Collection params = new ArrayList(1);
		params.add(userId);
		return messagePageIteratorSolver.getPageIteratorSolver(userId).getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start,
				count);

	}

	public int getMessageCount(Long threadId) {
		logger.debug("enter getMessageCount  for threadId:" + threadId);
		String ALL_THREADS = "SELECT count(1) from jiveMessage WHERE threadID=? ";
		List queryParams = new ArrayList();
		queryParams.add(threadId);
		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, ALL_THREADS);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
		} catch (Exception e) {
			logger.error(e);
		}
		return count;
	}

	public int getForumMessageCount(Long forumId) {
		logger.debug("enter getMessageCount  for forumId:" + forumId);
		String ALL_THREADS = "SELECT count(1) from jiveMessage WHERE forumID=? ";
		List queryParams = new ArrayList();
		queryParams.add(forumId);
		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, ALL_THREADS);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
		} catch (Exception e) {
			logger.error(e);
		}
		return count;
	}

	/**
	 * get laste message from the message db
	 * 
	 */
	public Long getLatestPostMessageId(Long threadId) {
		logger.debug("enter getLatestPostMessageId  for threadId:" + threadId);
		String LAST_MESSAGES = "SELECT messageID from jiveMessage WHERE  threadID = ? ORDER BY modifiedDate DESC";
		List queryParams2 = new ArrayList();
		queryParams2.add(threadId);

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

	public Long getForumLatestPostMessageId(Long forumId) {
		logger.debug("enter getLatestPostMessageId  for forumId:" + forumId);
		String LAST_MESSAGES = "SELECT messageID from jiveMessage WHERE  forumID = ? ORDER BY modifiedDate DESC";
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

	public boolean isRoot(Long messageId) {
		boolean isRoot = false;
		String SQL = "SELECT messageID from jiveMessage WHERE parentMessageID IS NULL AND messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(messageId);
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			if (list.size() > 0)
				isRoot = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return isRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.MessageDao#getMessages(java.lang.String, int,
	 * int)
	 */
	public PageIterator getMessages(Long threadId, int start, int count) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage where threadID=? ";

		String GET_ALL_ITEMS = "select messageID  from jiveMessage WHERE threadID=? ORDER BY creationDate ASC LIMIT ?, ?";

		// 查询总数
        Collection<Long> countParams = new ArrayList<>();
        countParams.add(threadId);

		Collection<Long> params = new ArrayList<>();
		params.add(threadId);
		params.add(new Long(start));
		params.add(new Long(count));
		Collection<Long> messageIDs = new ArrayList<>();
		Integer allCount = null;;
		try {
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
			if (allCounto instanceof Long)// for mysql 5
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue(); // for mysql 4

			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				messageIDs.add((Long) map.get("messageID"));
			}
		} catch (Exception e) {
			logger.error("getMessages threadId=" + threadId + " happend " + e);
			return new PageIterator();		
		}

		return new PageIterator(allCount.intValue(), messageIDs.toArray());
	}

	public PageIterator getMessages(int start, int count) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage  ";

		String GET_ALL_ITEMS = "select messageID  from jiveMessage ORDER BY creationDate DESC";

		Collection params = new ArrayList(1);
		return messagePageIteratorSolver.getPageIteratorSolver("getMessages").getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start,
				count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.MessageDao#getThreads(java.lang.String, int,
	 * int)
	 */
	public PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread where forumId=? ";

		String GET_ALL_ITEMS = "select threadID  from jiveThread WHERE forumId=? " + " ORDER BY creationDate  " + resultSort.toString();

		Collection params = new ArrayList(1);
		params.add(forumId);
		return messagePageIteratorSolver.getPageIteratorSolver(forumId.toString()).getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS,
				params, start, count);
	}

	public PageIterator getThreads(int start, int count, ThreadListSpec threadListSpec) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread " + threadListSpec.getResultSortSQL();

		String GET_ALL_ITEMS = "select threadID  from jiveThread  " + threadListSpec.getResultSortSQL();

		return messagePageIteratorSolver.getPageIteratorSolver("getThreads").getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "", start, count);
	}

	/*
	 * get the threads collection include prev/cuurent/next threads.
	 */
	public List getThreadsPrevNext(Long forumId, Long currentThreadId) {
		String GET_ALL_ITEMS = "select threadID  from jiveThread WHERE forumId=? ORDER BY modifiedDate DESC ";
		Collection params = new ArrayList(1);
		params.add(forumId);
		Block block = messagePageIteratorSolver.getPageIteratorSolver(forumId.toString()).locate(GET_ALL_ITEMS, params, currentThreadId);
		if (block == null) {
			return new ArrayList();
		} else {
			return block.getList();
		}

	}

	

	/**
	 * return all threadId satify by QueryCriteria
	 */
	public Collection getThreads(QueryCriteria qc) {
		Collection result = new TreeSet();
		try {
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();

			StringBuilder itemIDsSQL = new StringBuilder("SELECT threadID FROM jiveMessage ");
			itemIDsSQL.append(qs.getWhereSQL());
			itemIDsSQL.append(qs.getResultSortSQL());
			// itemIDsSQL.append(" GROUP BY threadID O RDER BY msgCount DESC ");
			logger.debug("GET_threadID Collection=" + itemIDsSQL);

			List sqlresult = jdbcTempSource.getJdbcTemp().queryMultiObject(qs.getParams(), itemIDsSQL.toString());
			Iterator iter = sqlresult.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Long threadID = (Long) map.get("threadID");
				result.add(threadID);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return result;

	}

	public PageIterator getMessages(QueryCriteria mqc, int start, int count) {
		logger.debug("enter getMessagesSQL for QueryCriteria");
		return getQueryCriteriaResult("messageID", mqc, start, count);
	}

	/**
	 * Template that return QueryCriteria
	 * 
	 * @param keyName
	 *            the key name that included in PageIterator key collection,
	 *            such as threadId or messageId
	 */
	public PageIterator getQueryCriteriaResult(String keyName, QueryCriteria qc, int start, int count) {
		logger.debug("enter getQueryCriteriaResult");
		try {
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();

			StringBuilder allCountSQL = new StringBuilder("SELECT count(1)  FROM jiveMessage ");
			allCountSQL.append(qs.getWhereSQL());

			StringBuilder itemIDsSQL = new StringBuilder("SELECT " + keyName + " FROM jiveMessage ");
			itemIDsSQL.append(qs.getWhereSQL());
			itemIDsSQL.append(qs.getResultSortSQL());
			logger.debug("GET_ALL_ITEMS=" + itemIDsSQL);
			return messagePageIteratorSolver.getPageIteratorSolver("getQueryCriteriaResult").getPageIterator(allCountSQL.toString(),
					itemIDsSQL.toString(), qs.getParams(), start, count);

		} catch (Exception e) {
			e.printStackTrace();
			return new PageIterator();
		}
	}

	public PageIterator getMessageReplys(QueryCriteria qc, int start, int count) {
		logger.debug("enter getMessagesSQL for QueryCriteria");
		try {
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();

			StringBuilder allCountSQL = new StringBuilder("SELECT count(1)  FROM jiveMessage ");
			allCountSQL.append(qs.getWhereSQL());

			StringBuilder itemIDsSQL = new StringBuilder("SELECT messageID FROM jiveMessage ");
			itemIDsSQL.append(qs.getWhereSQL());
			itemIDsSQL.append(" and parentMessageID IS NOT NULL ");
			itemIDsSQL.append(qs.getResultSortSQL());
			logger.debug("GET_ALL_ITEMS=" + itemIDsSQL);
			return messagePageIteratorSolver.getPageIteratorSolver("getMessageReplys").getPageIterator(allCountSQL.toString(),
					itemIDsSQL.toString(), qs.getParams(), start, count);

		} catch (Exception e) {
			e.printStackTrace();
			return new PageIterator();
		}
	}

	public PageIterator getThreads(QueryCriteria qc, int start, int count) {
		try {
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse("jiveMessage");

			StringBuilder allCountSQL = new StringBuilder("SELECT count(1)  FROM jiveMessage ");
			allCountSQL.append(qs.getWhereSQL());

			StringBuilder itemIDsSQL = new StringBuilder("SELECT jiveMessage.threadID  FROM jiveMessage ");
			itemIDsSQL.append(qs.getWhereSQL());
			itemIDsSQL.append(" and jiveMessage.parentMessageID IS NULL ");
			itemIDsSQL.append(qs.getResultSortSQL("creationDate"));
			logger.debug("GET_ALL_ITEMS=" + itemIDsSQL);
			return messagePageIteratorSolver.getPageIteratorSolver("getThreads").getPageIterator(allCountSQL.toString(), itemIDsSQL.toString(),
					qs.getParams(), start, count);

		} catch (Exception e) {
			e.printStackTrace();
			return new PageIterator();
		}
	}

	public PageIterator popularThreads(QueryCriteria queryCriteria, int count) {
		String POPULAR_THREADS_AllCOUNT = "SELECT  count(1)  FROM jiveMessage WHERE " + "modifiedDate > ?  ";
		String POPULAR_THREADS = "SELECT threadID, count(1) AS msgCount FROM jiveMessage "
				+ "WHERE modifiedDate > ? GROUP BY threadID ORDER BY msgCount DESC";
		List queryParams = new ArrayList();
		queryParams.add(queryCriteria.getFromDateNoMillisString());
		return messagePageIteratorSolver.getPageIteratorSolver("popularThreads").getPageIterator(POPULAR_THREADS_AllCOUNT, POPULAR_THREADS,
				queryParams, 0, count);
	}
}
