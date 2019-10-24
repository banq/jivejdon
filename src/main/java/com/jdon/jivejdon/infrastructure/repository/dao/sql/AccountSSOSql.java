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

import com.jdon.jivejdon.domain.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class AccountSSOSql {
	private final static Logger logger = LogManager.getLogger(AccountSSOSql.class);

	private JdbcTempSource jdbcTempSSOSource;
	private SequenceDao sequenceDao;

	/**
	 * @param jdbcTempSSOSource
	 */
	public AccountSSOSql(JdbcTempSource jdbcTempSSOSource, SequenceDao sequenceDao) {
		this.jdbcTempSSOSource = jdbcTempSSOSource;
		this.sequenceDao = sequenceDao;
	}

	public String getRoleNameByUserId(String userId) {
		logger.debug("enter getRoleName for userId=" + userId);
		String SQL = "SELECT RL.name FROM role as RL,  users_roles as RU WHERE RU.roleid = RL.roleid  and RU.userId = ?";
		//String SQL = "SELECT name FROM role where roleid=(SELECT roleid FROM users_roles WHERE userid =? )";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		String roleName = null;
		try {
			Object ret = jdbcTempSSOSource.getJdbcTemp().querySingleObject(queryParams, SQL);
			if (ret != null) {
				roleName = (String) ret;
			}
		} catch (Exception se) {
			logger.error(userId + " error:" + se);
		}
		return roleName;
	}

	public String getRoleNameFByusername(String username) {
		logger.debug("enter getAccountByName for username=" + username);
		String SQL = "SELECT RL.name, 'Roles' FROM role as RL, user as U ,  users_roles as RU WHERE U.userid = RU.userid and RU.roleid = RL.roleid  and U.name = ?";
		//String SQL = "SELECT name FROM role where roleid=(SELECT roleid FROM users_roles WHERE userid = ( SELECT  userId FROM user  WHERE name=? ) )";
		List queryParams = new ArrayList();
		queryParams.add(username);
		String roleName = null;
		try {
			List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				logger.debug("found the role");
				Map map = (Map) iter.next();
				roleName = ((String) map.get("name")).trim();
			}
		} catch (Exception se) {
			logger.error(username + " error:" + se);
		}
		return roleName;
	}

	public PasswordassitVO getPasswordassit(String userId) {
		String SQL = "SELECT * from passwordassit where userId = ?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		PasswordassitVO passwordassitVO = new PasswordassitVO(userId, "", "");
		try {
			List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				logger.debug("found the passwordassit");
				Map map = (Map) iter.next();

				String pt = ((String) map.get("passwdtype"));
				String ps = ((String) map.get("passwdanswer"));
				passwordassitVO = new PasswordassitVO(userId, pt, ps);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return passwordassitVO;
	}

	public void insertSSOServer(Account account) throws Exception {
		logger.debug("enter insertSSOServer");
		try {
			// the user has `` ,because mysql user is special
			String INSERT_USER = "REPLACE INTO `user` (userId,password,name,email) VALUES(?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(account.getUserId());
			queryParams.add(ToolsUtil.hash(account.getPassword()));
			queryParams.add(account.getUsername());
			queryParams.add(account.getEmail());

			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, INSERT_USER);

			String INSERT_USER_ROLE = "REPLACE INTO users_roles(userId,roleId) VALUES(?,?)";
			List queryParams2 = new ArrayList();
			queryParams2.add(account.getUserId());
			String roleId = getRoleId(account.getRoleName());
			logger.debug(" the roleName: " + account.getRoleName() + " roleId =" + roleId);
			queryParams2.add(roleId);
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams2, INSERT_USER_ROLE);

		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	public void insertPasswordassit(PasswordassitVO passwordassitVO) throws Exception {
		logger.debug("enter insertPasswordassit");
		try {

			String INSERT_PASSWORDASSIT = "REPLACE INTO passwordassit(userId,passwdtype,passwdanswer) VALUES(?,?,?)";
			List queryParams3 = new ArrayList();
			queryParams3.add(passwordassitVO.getUserId());
			queryParams3.add(passwordassitVO.getPasswdtype());
			queryParams3.add(passwordassitVO.getPasswdanswer());
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams3, INSERT_PASSWORDASSIT);

		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	private String getRoleId(String roleName) throws Exception {
		try {
			String roleId = getRoleName(roleName);
			if (roleId == null) {
				roleId = createRole(roleName);
			}
			return roleId;
		} catch (Exception ex) {
			logger.error(ex);
			throw new Exception(ex);
		}
	}

	public String getRoleName(String roleName) {
		if (!UtilValidate.isAlphanumeric(roleName))
			return null;
		String SQL = "SELECT roleId FROM role WHERE name=? ";
		List queryParams = new ArrayList();
		queryParams.add(roleName);
		String roleId = null;
		try {
			List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				roleId = ((String) map.get("roleId")).trim();
			}
		} catch (Exception se) {
			logger.warn(se);
		}
		return roleId;
	}

	private String createRole(String roleName) throws Exception {
		logger.debug("enter insertSSOServer");

		String INSERT_ROLE = "INSERT INTO role(roleId, name) VALUES(?,?)";
		String roleId = null;
		try {
			Long roleIDInt = sequenceDao.getNextId(Constants.USER);

			List queryParams = new ArrayList();
			roleId = roleIDInt.toString().trim();
			queryParams.add(roleId);
			queryParams.add(roleName);

			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, INSERT_ROLE);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
		return roleId;
	}

	public void updateSSOServer(Account account) throws Exception {
		logger.debug("enter updayeSSOServer");

		try {
			String SAVE_USER = "UPDATE user SET password=?,name=?,email=? " + " WHERE userId=?";
			List queryParams = new ArrayList();
			queryParams.add(ToolsUtil.hash(account.getPassword()));
			queryParams.add(account.getUsername());
			queryParams.add(account.getEmail());
			queryParams.add(account.getUserIdLong());
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, SAVE_USER);

		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	public void updatePasswordassit(PasswordassitVO passwordassitVO) throws Exception {
		logger.debug("enter updatePasswordassit");
		insertPasswordassit(passwordassitVO);

	}

	public void deleteSSOServer(Account account) throws Exception {
		logger.debug("enter deleteSSOServer");
		String SQL = "DELETE FROM user WHERE userId=?";
		List queryParams = new ArrayList();
		queryParams.add(account.getUserId());
		try {
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, SQL);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	public void deletePasswordassit(String userId) throws Exception {
		logger.debug("enter deletePasswordassit");
		String SQL = "DELETE FROM passwordassit WHERE userId=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		try {
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, SQL);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

}
