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
package com.jdon.jivejdon.spi.component.sitemap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.RootMessage;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.PageIteratorSolverFixed;
import com.jdon.jivejdon.util.ToolsUtil;

@Component("sitemapRepository")
@Introduce("modelCache")
public class SitemapRepositoryImpl implements SitemapRepository {
	private final static Logger logger = LogManager.getLogger(SitemapRepositoryImpl.class);

	private JdbcTempSource jdbcTempSource;

	private PageIteratorSolverFixed pageIteratorSolver;

	private Constants constants;

	public SitemapRepositoryImpl(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolverFixed(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.constants = constants;
	}

	public JdbcTempSource getJdbcTempSource() {
		return jdbcTempSource;
	}

	public void clearCacheOfItem() {
		pageIteratorSolver.clearCache();
	}

	@Override
	public void insert(Url url) throws Exception {
		try {
			String sql = "INSERT INTO sitemap (id , url , name, creationDate) " + "VALUES (?, ?, ?, ?)";
			List queryParams = new ArrayList();
			queryParams.add(url.getUrlId());
			queryParams.add(url.getIoc());
			queryParams.add(url.getName());
			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			queryParams.add(saveDateTime);
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearCacheOfItem();

	}

	@Override
	public void update(Url url) throws Exception {
		try {
			String sql = "update sitemap set name=?, url=? where id=?";
			List queryParams = new ArrayList();
			queryParams.add(url.getName());
			queryParams.add(url.getIoc());
			queryParams.add(url.getUrlId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearCacheOfItem();

	}

	@Override
	public void delete(Url url) throws Exception {
		String sql = "delete from sitemap where id=?";
		List queryParams = new ArrayList();
		queryParams.add(url.getUrlId());
		jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		clearCacheOfItem();

	}

	@Around
	public Url getUrl(long Id) {
		String GET_FIELD = "select  * from sitemap where id = ?";
		List queryParams = new ArrayList();
		queryParams.add(Id);

		Url ret = null;

		try {
			List list = pageIteratorSolver.queryMultiObject(queryParams, GET_FIELD);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = new Url();
				ret.setName((String) map.get("name"));
				ret.setIoc((String) map.get("url"));
				String saveDateTime = ((String) map.get("creationDate")).trim();
				String displayDateTime = constants.getDateTimeDisp(saveDateTime);
				ret.setCreationDate(displayDateTime);
				ret.setUrlId(Id);
			}
		} catch (Exception e) {
			logger.error("getUrl" + e);
		}
		return ret;
	}

	@Override
	public PageIterator getUrls(int start, int count) throws Exception {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from sitemap  ORDER BY creationDate DESC";
		String GET_ALL_ITEMS = "select id  from sitemap  ORDER BY creationDate DESC";
		return pageIteratorSolver.getDatas("", GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, start, count);

	}

	public boolean checkUrl(String ioc) {
		String GET_FIELD = "select  * from sitemap where url = ?";
		List queryParams = new ArrayList();
		queryParams.add(ioc);
		boolean ret = false;
		try {
			List list = pageIteratorSolver.queryMultiObject(queryParams, GET_FIELD);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = true;
			}
		} catch (Exception e) {
			logger.error("getUrl" + e);
		}
		return ret;
	}

	/**
	 * 获取虚拟的ForumThread对象，用于生成URL
	 * @param threadId
	 * @return
	 */
	public ForumThread getVirtualForumThread(Long threadId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbcTempSource.getDataSource().getConnection();

			// 从token表和jiveMessage表同时获取token和subject
			String combinedSQL = "SELECT t.token, m.subject FROM token t " +
								"LEFT JOIN jiveThread th ON t.threadID = th.threadID " +
								"LEFT JOIN jiveMessage m ON th.rootMessageID = m.messageID " +
								"WHERE t.threadID = ?";
			pstmt = conn.prepareStatement(combinedSQL);
			pstmt.setLong(1, threadId);
			rs = pstmt.executeQuery();

			String token = null;
			String subject = null;
			if (rs.next()) {
				token = rs.getString("token");
				subject = rs.getString("subject");
			}
			rs.close();
			pstmt.close();

			// 确保token和subject是final或 effectively final
			final String finalToken = token;
			final String finalSubject = subject;

			// 创建虚拟的ForumMessage对象
			ForumMessage forumMessage = new ForumMessage(threadId) {
				@Override
				public MessageVO getMessageVO() {
					// 创建一个新的MessageVO对象并设置subject
					MessageVO messageVO = new MessageVO().builder().subject(finalSubject != null ? finalSubject : "").body("").build();
					return messageVO;
				}
			};

			// 创建虚拟的ForumThread对象
			ForumThread thread = new ForumThread((RootMessage) forumMessage, threadId) {
				@Override
				public String getToken() {
					return finalToken != null ? finalToken : "";
				}
			};

			return thread;
		} catch (Exception e) {
			logger.error("Error creating virtual ForumThread for threadId: " + threadId, e);
			return null;
		} finally {
			// 关闭资源
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				logger.error("Error closing database resources", e);
			}
		}
	}
	
	/**
	 * 批量获取虚拟的ForumThread对象，用于生成URL
	 * @param start 起始位置
	 * @param count 数量
	 * @return
	 */
	public List<ForumThread> getVirtualForumThreads(int start, int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ForumThread> threads = new ArrayList<>();

		try {
			conn = jdbcTempSource.getDataSource().getConnection();

			// 使用LIMIT子句进行分页查询，获取threadId、token和subject
			String combinedSQL = "SELECT th.threadID, t.token, m.subject FROM jiveThread th " +
								"LEFT JOIN token t ON th.threadID = t.threadID " +
								"LEFT JOIN jiveMessage m ON th.rootMessageID = m.messageID " +
								"ORDER BY th.threadID " +
								"LIMIT ? OFFSET ?";
			pstmt = conn.prepareStatement(combinedSQL);
			pstmt.setInt(1, count);
			pstmt.setInt(2, start);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				final Long threadId = rs.getLong("threadID");
				final String token = rs.getString("token");
				final String subject = rs.getString("subject");

				// 创建虚拟的ForumMessage对象
				ForumMessage forumMessage = new ForumMessage(threadId) {
					@Override
					public MessageVO getMessageVO() {
						// 创建一个新的MessageVO对象并设置subject
						MessageVO messageVO = new MessageVO().builder().subject(subject != null ? subject : "").body("").build();
						return messageVO;
					}
				};

				// 创建虚拟的ForumThread对象
				ForumThread thread = new ForumThread((RootMessage) forumMessage, threadId) {
					@Override
					public String getToken() {
						return token != null ? token : "";
					}
				};
				
				threads.add(thread);
			}
		} catch (Exception e) {
			logger.error("Error creating virtual ForumThreads for start: " + start + ", count: " + count, e);
		} finally {
			// 关闭资源
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				logger.error("Error closing database resources", e);
			}
		}
		
		return threads;
	}
}