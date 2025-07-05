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
import com.jdon.treepatterns.model.TreeModel;

/**
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class MessageQueryDaoSql implements MessageQueryDao {
	private final static Logger logger = LogManager.getLogger(MessageQueryDaoSql.class);

	protected JdbcTempSource jdbcTempSource;

	// protected PageIteratorSolverFixed pageIteratorSolver;

	/**
	 * @param jdbcTempSource
	 */
	public MessageQueryDaoSql(JdbcTempSource jdbcTempSource) {
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
		String GET_ALL_ITEMS = "select threadID from jiveMessage WHERE userID=? AND parentMessageID IS NULL ORDER BY modifiedDate DESC LIMIT ?, ?";

		// 查询总数参数
		List<String> countParams = new ArrayList<>();
		countParams.add(userId);
		// 分页参数
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(start);
		params.add(count);
		List<Long> threadIDs = new ArrayList<>();
		Integer allCount = null;
		try {
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
			if (allCounto instanceof Long)
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue();

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
			for (Map<String, Object> map : list) {
				threadIDs.add((Long) map.get("threadID"));
			}
		} catch (Exception e) {
			logger.error("getThreadListByUser userId=" + userId + " error: " + e);
			return new PageIterator();
		}
		return new PageIterator(allCount.intValue(), threadIDs.toArray());
	}

	public PageIterator getMesageListByUser(String userId, int start, int count) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage where parentMessageID IS NOT NULL AND userID=? ";
		String GET_ALL_ITEMS = "select messageID from jiveMessage WHERE userID=? AND parentMessageID IS NOT NULL ORDER BY modifiedDate DESC LIMIT ?, ?";

		// 查询总数参数
		List<String> countParams = new ArrayList<>();
		countParams.add(userId);
		// 分页参数
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(start);
		params.add(count);
		List<Long> messageIDs = new ArrayList<>();
		Integer allCount = null;
		try {
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
			if (allCounto instanceof Long)
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue();

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
			for (Map<String, Object> map : list) {
				messageIDs.add((Long) map.get("messageID"));
			}
		} catch (Exception e) {
			logger.error("getMesageListByUser userId=" + userId + " error: " + e);
			return new PageIterator();
		}
		return new PageIterator(allCount.intValue(), messageIDs.toArray());
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
		Integer allCount = null;
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

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.MessageDao#getThreads(java.lang.String, int,
	 * int)
	 */
	public PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread where forumId=? ";

		String GET_ALL_ITEMS = "select threadID from jiveThread WHERE forumId=? ORDER BY creationDate " + resultSort.toString() + " LIMIT ?, ?";

		// 查询总数参数
		Collection<Long> countParams = new ArrayList<>();
		countParams.add(forumId);

		// 分页参数
		Collection<Object> params = new ArrayList<>();
		params.add(forumId);
		params.add(start);
		params.add(count);
		Collection<Long> threadIDs = new ArrayList<>();
		Integer allCount = null;
		try {
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
			if (allCounto instanceof Long)
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue();

			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				threadIDs.add((Long) map.get("threadID"));
			}
		} catch (Exception e) {
			logger.error("getThreads forumId=" + forumId + " happend " + e);
			return new PageIterator();
		}

		return new PageIterator(allCount.intValue(), threadIDs.toArray());
	}

	public PageIterator getThreads(int start, int count, ThreadListSpec threadListSpec) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread " + threadListSpec.getResultSortSQL();
		String GET_ALL_ITEMS = "select threadID from jiveThread " + threadListSpec.getResultSortSQL() + " LIMIT ?, ?";

		// 查询总数参数
		Collection<Object> countParams = new ArrayList<>(); // 没有额外参数
		// 分页参数
		Collection<Object> params = new ArrayList<>();
		params.add(start);
		params.add(count);
		Collection<Long> threadIDs = new ArrayList<>();
		Integer allCount = null;
		try {
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
			if (allCounto instanceof Long)
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue();

			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				threadIDs.add((Long) map.get("threadID"));
			}
		} catch (Exception e) {
			logger.error("getThreads (ThreadListSpec) happend " + e);
			return new PageIterator();
		}
		return new PageIterator(allCount.intValue(), threadIDs.toArray());
	}

	/*
	 * get the threads collection include prev/cuurent/next threads.
	 */
	public List<Long> getThreadsPrevNext(Long forumId, Long currentThreadId) {
		List<Long> result = new ArrayList<>();
		try {
			// 前两个（不含当前），查出比当前ID小的最大2个，正序排列
			String prevSql = "SELECT threadID FROM jiveThread WHERE forumId=? AND threadID < ? ORDER BY threadID DESC LIMIT 2";
			List<Object> prevParams = new ArrayList<>();
			prevParams.add(forumId);
			prevParams.add(currentThreadId);
			List<Map<String, Object>> prevList = jdbcTempSource.getJdbcTemp().queryMultiObject(prevParams, prevSql);
			List<Long> prevIds = new ArrayList<>();
			for (Map<String, Object> map : prevList) {
				prevIds.add((Long) map.get("threadID"));
			}
			java.util.Collections.reverse(prevIds); // 反转为正序
			result.addAll(prevIds);
			result.add(currentThreadId);
			// 后两个（不含当前），正序
			String nextSql = "SELECT threadID FROM jiveThread WHERE forumId=? AND threadID > ? ORDER BY threadID ASC LIMIT 2";
			List<Object> nextParams = new ArrayList<>();
			nextParams.add(forumId);
			nextParams.add(currentThreadId);
			List<Map<String, Object>> nextList = jdbcTempSource.getJdbcTemp().queryMultiObject(nextParams, nextSql);
			for (Map<String, Object> map : nextList) {
				result.add((Long) map.get("threadID"));
			}
		} catch (Exception e) {
			logger.error("getThreadsPrevNext SQL error: " + e);
		}
		return result;
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

	public List<Long> getHotThreadIDs(QueryCriteria qc) {
		List<Long> threadIds = new ArrayList<>();
		try {
			// 1. 生成where条件和参数
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();
			StringBuilder sql = new StringBuilder("SELECT threadID, COUNT(*) AS message_count FROM jiveMessage ");
			sql.append(qs.getWhereSQL());
			sql.append(" GROUP BY threadID ");
			sql.append(" HAVING message_count > ? "); // 可选
			sql.append(" ORDER BY message_count DESC LIMIT 100");

			List<Object> params = new ArrayList<>();
			if (qs.getParams() != null)
				params.addAll(qs.getParams());
			params.add(qc.getMessageReplyCountWindow()); // 你的阈值

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, sql.toString());
			for (Map<String, Object> map : list) {
				threadIds.add((Long) map.get("threadID"));
			}
		} catch (Exception e) {
			logger.error("createSortedIDs error: ", e);
		}
		return threadIds;
	}

	public List<Long> getDigThreadIDs(QueryCriteria qc) {
		List<Long> threadIds = new ArrayList<>();
		try {
			// 生成 where 条件和参数
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();

			// 计算1000天前的时间戳（毫秒），并补齐15位字符串
			long now = System.currentTimeMillis();
			long days1000Millis = 1000L * 24 * 60 * 60 * 1000;
			long minCreationDate = now - days1000Millis;
			String minCreationDateStr = com.jdon.jivejdon.util.ToolsUtil.zeroPadString(String.valueOf(minCreationDate), 15);

			StringBuilder sql = new StringBuilder(
					"SELECT jm.threadID, SUM(CAST(jmp.propValue AS UNSIGNED)) AS dig_sum " +
							"FROM jiveMessage jm " +
							"JOIN jiveMessageProp jmp ON jm.messageID = jmp.messageID ");
			sql.append(qs.getWhereSQL());
			sql.append(" AND jmp.name = 'digNumber' ");
			sql.append(" AND jm.modifiedDate >= ? "); // 新增1000天内过滤
			sql.append("GROUP BY jm.threadID ");
			sql.append("HAVING dig_sum > ? "); // 这里用上阈值
			sql.append("ORDER BY dig_sum  DESC LIMIT 100");

			List<Object> params = new ArrayList<>();
			if (qs.getParams() != null)
				params.addAll(qs.getParams());
			params.add(minCreationDateStr); // creationDate >= ?
			params.add(qc.getDigCountWindow()); // 你的阈值

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, sql.toString());
			for (Map<String, Object> map : list) {
				threadIds.add((Long) map.get("threadID"));
			}
		} catch (Exception e) {
			logger.error("getDigThreadIDs error: ", e);
		}
		return threadIds;
	}

	public PageIterator getMessages(QueryCriteria qc, int start, int count) {
		try {
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();

			StringBuilder allCountSQL = new StringBuilder("SELECT count(1) FROM jiveMessage ");
			allCountSQL.append(qs.getWhereSQL());

			StringBuilder itemIDsSQL = new StringBuilder("SELECT messageID FROM jiveMessage ");
			itemIDsSQL.append(qs.getWhereSQL());
			itemIDsSQL.append(qs.getResultSortSQL());
			itemIDsSQL.append(" LIMIT ?, ?");
			logger.debug("GET_ALL_ITEMS=" + itemIDsSQL);

			// 查询总数参数
			List<Object> countParams = new ArrayList<>();
			if (qs.getParams() != null) countParams.addAll(qs.getParams());
			// 分页参数（复制原有参数并加上start和count）
			List<Object> params = new ArrayList<>(countParams);
			params.add(start);
			params.add(count);
			List<Long> messageIDs = new ArrayList<>();
			Integer allCount = null;
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, allCountSQL.toString());
			if (allCounto instanceof Long)
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue();

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTempSource.getJdbcTemp().queryMultiObject(params, itemIDsSQL.toString());
			for (Map<String, Object> map : list) {
				messageIDs.add((Long) map.get("messageID"));
			}
			return new PageIterator(allCount.intValue(), messageIDs.toArray());
		} catch (Exception e) {
			logger.error("getMessages(QueryCriteria) error: " + e);
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
			itemIDsSQL.append(" LIMIT ?, ?");
			logger.debug("GET_ALL_ITEMS=" + itemIDsSQL);

			// 查询总数参数
			Collection<Object> countParams = qs.getParams();
			// 分页参数（复制原有参数并加上start和count）
			Collection<Object> params = new ArrayList<>(qs.getParams());
			params.add(start);
			params.add(count);
			Collection<Long> threadIDs = new ArrayList<>();
			Integer allCount = null;
			Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, allCountSQL.toString());
			if (allCounto instanceof Long)
				allCount = ((Long) allCounto).intValue();
			else
				allCount = ((Integer) allCounto).intValue();

			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, itemIDsSQL.toString());
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				threadIDs.add((Long) map.get("threadID"));
			}
			return new PageIterator(allCount.intValue(), threadIDs.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			return new PageIterator();
		}
	}

	
}
