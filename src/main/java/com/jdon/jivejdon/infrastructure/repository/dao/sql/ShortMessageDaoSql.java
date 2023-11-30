/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.*;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessageState;
import com.jdon.jivejdon.infrastructure.repository.dao.ShortMessageDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * ShortMessageDaoImp.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: 2008-5-21
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
@Introduce("modelCache")
public class ShortMessageDaoSql implements ShortMessageDao {

	private final static Logger logger = LogManager.getLogger(ShortMessageDaoSql.class);

	private JdbcTempSource jdbcTempSource;

	private Constants constants;

	private PageIteratorSolver pageIteratorSolver;

	public ShortMessageDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, Constants constants) {
		this.jdbcTempSource = jdbcTempSource;
		this.constants = constants;
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		;
	}

	/**
	 * 
	 */
	@Around()
	public ShortMessage findShortMessage(Long key) {
		logger.debug(" findShortMessage " + key);
		String LOAD_SMESSAGE = "SELECT msgId,userId,messageTitle,messageBody,messageFrom,messageTo, "
				+ "hasRead,hasSent,sendTime FROM jiveshortmsg WHERE msgId=?";
		List queryParams = new ArrayList();
		queryParams.add(key);
		ShortMessage shortMessage = null;
		ShortMessageState state = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SMESSAGE);
			Iterator iter = list.iterator();
			Map map = null;
			if (iter.hasNext()) {
				shortMessage = new ShortMessage();

				map = (Map) iter.next();
				shortMessage.setMsgId((Long) map.get("msgId"));
				shortMessage.setMessageTitle((String) map.get("messageTitle"));
				shortMessage.setMessageBody((String) map.get("messageBody"));
				shortMessage.setMessageFrom((String) map.get("messageFrom"));
				shortMessage.setMessageTo((String) map.get("messageTo"));

				Long userId = (Long) map.get("userId");
				Account account = new Account();
				account.setUserId(userId.toString());
				shortMessage.setAccount(account);

				state = new ShortMessageState();
				state.setSendTime(constants.getDateTimeDisp(((String) map.get("sendTime")).trim()));
				if (((Integer) map.get("hasRead")) == 1) {
					state.setHasRead(true);
				} else {
					state.setHasRead(false);
				}

				if (((Integer) map.get("hasSent")) == 1) {
					state.setHasSent(true);
				} else {
					state.setHasSent(false);
				}
				shortMessage.setShortMessageState(state);
			}
		} catch (Exception e) {
			logger.error("msgId=" + key + " happend  " + e);
		}
		return shortMessage;
	}

	/**
	 * 
	 */
	public void sendShortMessage(ShortMessage shortMessage) throws Exception {
		// 首先验证，发送的对象是不是论坛中的用户
		// 如果不是，抛出异常
		logger.debug("enter createShortMessage");
		String INSERT_SHORTMSG = "INSERT INTO jiveshortmsg(msgId,userId,messageTitle,messageBody,messageFrom,messageTo,"
				+ "sendTime,hasRead,hasSent) " + "VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			List queryParams = new ArrayList();
			queryParams.add(shortMessage.getMsgId());
			queryParams.add(shortMessage.getAccount().getUserId());
			queryParams.add(shortMessage.getMessageTitle());
			queryParams.add(shortMessage.getMessageBody());
			if (shortMessage.getMessageFrom() != null) {
				queryParams.add(shortMessage.getMessageFrom());
			} else
				queryParams.add(shortMessage.getAccount().getUsername());
			queryParams.add(shortMessage.getMessageTo());
			queryParams.add(shortMessage.getShortMessageState().getSendTime());
			if (shortMessage.getShortMessageState().isHasRead()) {
				queryParams.add(new Integer(1));
			} else {
				queryParams.add(new Integer(0));
			}
			if (shortMessage.getShortMessageState().isHasSent()) {
				queryParams.add(new Integer(1));
			} else {
				queryParams.add(new Integer(0));
			}
			jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_SHORTMSG);
			this.clearCache();
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	/**
	 * 更新消息
	 */
	public void updateShortMessate(ShortMessage shortMessage) throws Exception {
		// 首先验证，发送的对象是不是论坛中的用户
		// 如果不是，抛出异常
		logger.debug("enter updateShortMessate");
		String UUPDATE_SHORTMSG = "UPDATE jiveshortmsg set messageTitle=?,messageBody=?,messageTo=?," + "hasSent=?, hasRead = ? where msgId=? ";
		try {
			List queryParams = new ArrayList();
			queryParams.add(shortMessage.getMessageTitle());
			queryParams.add(shortMessage.getMessageBody());
			queryParams.add(shortMessage.getMessageTo());
			//
			if (shortMessage.getShortMessageState().isHasSent()) {
				queryParams.add(new Integer(1));
			} else {
				queryParams.add(new Integer(0));
			}

			if (shortMessage.getShortMessageState().isHasRead()) {
				queryParams.add(new Integer(1));
			} else {
				queryParams.add(new Integer(0));
			}
			//
			queryParams.add(shortMessage.getMsgId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, UUPDATE_SHORTMSG);
			this.clearCache();
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	/**
	 * @throws Exception
	 * 
	 */
	public void deleteShortMessage(Long msgId) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" deleteShortMessage " + msgId);
		String DEL_SMESSAGE = "delete FROM jiveshortmsg WHERE msgId=?";
		List queryParams = new ArrayList();
		queryParams.add(msgId);
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, DEL_SMESSAGE);
			this.clearCache();
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	public void deleteUserAllShortMessage(String userId) throws Exception {

		logger.debug(" deleteUserAllShortMessage " + userId);
		String DEL_SMESSAGE = "delete FROM jiveshortmsg WHERE userId=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, DEL_SMESSAGE);
			this.clearCache();
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	public void deleteUserRecAllShortMessage(String username) throws Exception {
		logger.debug(" deleteUserRecAllShortMessage " + username);
		String DEL_SMESSAGE = "delete FROM jiveshortmsg WHERE messageTo=?";
		List queryParams = new ArrayList();
		queryParams.add(username);
		try {
			jdbcTempSource.getJdbcTemp().operate(queryParams, DEL_SMESSAGE);
			this.clearCache();
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 */
	public PageIterator getShortMessages(int start, int count, Long userId) {
		logger.debug("enter getShortMessages ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveshortmsg where userId=" + userId;

		String GET_ALL_ITEMS = "select msgId from jiveshortmsg where userId=" + userId;

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "getShortMessages", start, count);
	}

	/**
	 * 
	 */
	public PageIterator getReceiveShortMessages(int start, int count, Account account) {
		logger.debug("enter getReceiveShortMessages ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveshortmsg where hasSent=1 and  messageTo='" + account.getUsername() + "'";

		String GET_ALL_ITEMS = "select msgId from jiveshortmsg where  hasSent=1 and  messageTo='" + account.getUsername()
				+ "' order by sendTime desc ";

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "getReceiveShortMessages", start, count);
	}

	/**
	 * 
	 */
	public PageIterator getSaveShortMessages(int start, int count, Account account) {
		logger.debug("enter getSaveShortMessages ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveshortmsg where hasSent =0 and messageFrom='" + account.getUsername() + "'";

		String GET_ALL_ITEMS = "select msgId from jiveshortmsg where hasSent =0 and messageFrom='" + account.getUsername()
				+ "' order by sendTime desc";

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "getSaveShortMessages", start, count);
	}

	/**
	 * 
	 */
	public PageIterator getSendShortMessages(int start, int count, Account account) {
		logger.debug("enter getSendShortMessages ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveshortmsg  where hasSent = 1 and messageFrom='" + account.getUsername() + "'";

		String GET_ALL_ITEMS = "select msgId from jiveshortmsg  where hasSent = 1 and messageFrom='" + account.getUsername()
				+ "' order by sendTime desc";

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "getSendShortMessages", start, count);
	}

	/**
	 * 清空缓存
	 * 
	 */
	public void clearCache() {
		pageIteratorSolver.clearCache();
	}

}
