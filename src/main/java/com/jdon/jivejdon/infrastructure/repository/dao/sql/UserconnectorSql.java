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
package com.jdon.jivejdon.repository.dao.sql;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.infrastructure.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.domain.model.account.OAuthUserVO;
import com.jdon.jivejdon.repository.acccount.Userconnector;
import com.jdon.jivejdon.util.ToolsUtil;

@Component
public class UserconnectorSql implements Userconnector {
	private final static Logger logger = LogManager.getLogger(UserconnectorSql.class);

	private JdbcTempSource jdbcTempSource;

	public UserconnectorSql(JdbcTempSource jdbcTempSource) {
		super();
		this.jdbcTempSource = jdbcTempSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.dao.sql.Userconnector#saveSinaWeiboUserconn
	 * (java.lang.String, com.jdon.jivejdon.infrastructure.component.weibo.userConnectorAuth)
	 */
	@Override
	public void saveUserConnectorAuth(UserConnectorAuth userConnectorAuth) {
		List queryParams = new ArrayList();
		try {
			String ADD_SQL = "REPLACE INTO userconnector(userId, conntype, connuser, connpasswd, datas, expiredate)" + " VALUES (?,?,?,?,?,?)";
			queryParams.add(userConnectorAuth.getUserId());
			queryParams.add(userConnectorAuth.getType());
			queryParams.add(userConnectorAuth.getConnuser());
			queryParams.add(userConnectorAuth.getConnpasswdEncode());

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(byteArrayOutputStream);
			oout.writeObject(userConnectorAuth.getAccessToken());
			byte[] datas = byteArrayOutputStream.toByteArray();
			oout.close();
			byteArrayOutputStream.close();
			queryParams.add(datas);

			String saveDateTime = ToolsUtil.dateToMillis(userConnectorAuth.getExpireTime());
			queryParams.add(saveDateTime);

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
		} catch (Exception e) {
			// if (e.getMessage().indexOf("SQLException") != -1)
			// creatTable();
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.dao.sql.Userconnector#removeSinaWeiboUserconn
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void removeUserConnectorAuth(String userId, String type) {
		List queryParams = new ArrayList();
		try {
			String DEL_SQL = "DELETE FROM userconnector WHERE userId=? and conntype=?";
			queryParams.add(userId);
			queryParams.add(type);
			jdbcTempSource.getJdbcTemp().operate(queryParams, DEL_SQL);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.dao.sql.Userconnector#loadSinaWeiboUserconn
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public UserConnectorAuth loadUserConnectorAuth(String userId, String type) {
		logger.debug("enter loadForum for id:" + userId);
		String LOAD_SQL = "SELECT * FROM userconnector WHERE userId=? and conntype=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		queryParams.add(type);

		UserConnectorAuth userConnectorAuth = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				String conntype = (String) map.get("conntype");
				byte[] datas = (byte[]) map.get("datas");
				ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(datas));
				Serializable accessToke = (Serializable) oin.readObject();
				oin.close();
				userConnectorAuth = new UserConnectorAuth(userId, conntype, accessToke);
				userConnectorAuth.setConnpasswdDecode((String) map.get("connpasswd"));
				String expiredate = (String) map.get("expiredate");
				userConnectorAuth.setExpireTime(Long.parseLong(expiredate));

			}
		} catch (Exception e) {
			logger.error(e);
		}
		return userConnectorAuth;
	}

	public String getUserId(String weiboUserId) {

		String LOAD_SQL = "SELECT userId FROM oauthuser WHERE weiboUserId=?";
		List queryParams = new ArrayList();
		queryParams.add(weiboUserId);

		String userId = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				userId = (String) map.get("userId");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return userId;
	}

	public void saveOAuthUserVO(OAuthUserVO weiboUserVO) {
		List queryParams = new ArrayList();
		try {
			String ADD_SQL = "REPLACE INTO oauthuser(weiboUserId,userId, nickname, description, url, profileImageUrl)" + " VALUES (?,?,?,?,?,?)";
			queryParams.add(weiboUserVO.getOAuthUserId());
			queryParams.add(weiboUserVO.getUserConnectorAuth().getUserId());
			queryParams.add(weiboUserVO.getNickname());
			queryParams.add(weiboUserVO.getDescription());
			queryParams.add(weiboUserVO.getUrl());
			queryParams.add(weiboUserVO.getProfileImageUrl());

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void deleteWeiboUserVO(String userId) {
		List queryParams = new ArrayList();
		try {
			String DEL_SQL = "DELETE FROM oauthuser WHERE userId=? ";
			queryParams.add(userId);
			jdbcTempSource.getJdbcTemp().operate(queryParams, DEL_SQL);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
