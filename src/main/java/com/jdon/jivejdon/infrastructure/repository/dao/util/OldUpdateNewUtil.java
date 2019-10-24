package com.jdon.jivejdon.repository.dao.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.repository.dao.sql.AccountSSOSql;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSSOSource;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;

public class OldUpdateNewUtil implements Runnable {
	private final static Logger logger = LogManager.getLogger(OldUpdateNewUtil.class);

	private AccountDao accountDao;
	private AccountSSOSql accountSSOSql;
	private JdbcTempSSOSource jdbcTempSSOSource;
	private JdbcTempSource jdbcTempSource;

	public OldUpdateNewUtil(AccountDao accountDao, AccountSSOSql accountSSOSql, JdbcTempSSOSource jdbcTempSSOSource, JdbcTempSource jdbcTempSource,
			ContainerUtil containerUtil) {
		super();
		this.accountDao = accountDao;
		this.accountSSOSql = accountSSOSql;
		this.jdbcTempSSOSource = jdbcTempSSOSource;
		this.jdbcTempSource = jdbcTempSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.AccountDao#getAccounts(int, int)
	 */
	public void run() {

		logger.error("UserDBTransfer");
		// updatePasswords();
		logger.error("updatePasswords");
		// insertjiveusertosecurity();
		logger.error("all ok");
	}

	public void updatePasswords() {
		logger.debug("enter updateAccountPassword");
		String SQL = "select userId, password from user ";
		try {
			List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(new ArrayList(), SQL);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				String userId = ((String) map.get("userId")).trim();
				String password = ((String) map.get("password")).trim();
				String newPassword = ToolsUtil.hash(password);
				updatePassword(userId, newPassword);
			}
		} catch (Exception se) {
			logger.warn(se);
		}
	}

	private void updatePassword(String userId, String password) {
		try {
			String SAVE_USER = "UPDATE user SET password=? WHERE userId=?";
			List queryParams = new ArrayList();
			queryParams.add(password);
			queryParams.add(userId);
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, SAVE_USER);
		} catch (Exception se) {
			logger.warn(se);
		}
	}

	public void insertjiveusertosecurity() {

		logger.debug("UserDBTransfer");
		String roleId = accountSSOSql.getRoleName(Role.USER);
		try {
			Iterator iter = getjiveUserUserIDs().iterator();
			Long userId = null;
			Account account;
			while (iter.hasNext()) {
				userId = (Long) iter.next();
				logger.debug("update account Id=" + userId);
				account = accountDao.getAccount(userId.toString());
				insertSSODB(account, roleId);
			}
			logger.debug("insertjiveusertosecurity OK!");
		} catch (Exception se) {
			logger.error(se);
		}
	}

	private Collection getjiveUserUserIDs() {

		logger.debug("getjiveUserUserIDs");

		List queryParams = new ArrayList();
		Collection result = new ArrayList();
		try {
			String GET_ALL_ITEMS = "select userID from jiveUser ";
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, GET_ALL_ITEMS);
			Iterator iter = list.iterator();
			Long userId = null;
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				userId = (Long) map.get("userID");
				if (userId.longValue() == 1) // not update Admin
					continue;
				result.add(userId);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return result;
	}

	private void insertSSODB(Account account, String userRoleId) {
		logger.debug("enter insertSSOServer");
		try {
			// the user has `` ,because mysql user is special
			String INSERT_USER = "REPLACE INTO `user` (userId,password,name,email) VALUES(?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(account.getUserId());
			queryParams.add(account.getPassword());
			queryParams.add(account.getUsername());
			queryParams.add(account.getEmail());

			jdbcTempSSOSource.getJdbcTemp().operate(queryParams, INSERT_USER);

			String INSERT_USER_ROLE = "REPLACE INTO users_roles(userId,roleId) VALUES(?,?)";
			List queryParams2 = new ArrayList();
			queryParams2.add(account.getUserId());
			queryParams2.add(userRoleId);
			logger.debug(" the roleName: " + account.getRoleName() + " roleId =" + userRoleId);
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams2, INSERT_USER_ROLE);

			String INSERT_PASSWORDASSIT = "REPLACE INTO passwordassit(userId,passwdtype,passwdanswer) VALUES(?,?,?)";
			List queryParams3 = new ArrayList();
			queryParams3.add(account.getUserId());
			queryParams3.add("");
			queryParams3.add("");
			jdbcTempSSOSource.getJdbcTemp().operate(queryParams3, INSERT_PASSWORDASSIT);

		} catch (Exception e) {
			logger.error(e);
		}
	}

}
