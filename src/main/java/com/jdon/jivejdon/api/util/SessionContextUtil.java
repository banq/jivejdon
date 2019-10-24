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
package com.jdon.jivejdon.api.util;

import com.jdon.jivejdon.domain.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.container.visitor.data.SessionContextSetup;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class SessionContextUtil {
	private final static Logger logger = LogManager.getLogger(SessionContextUtil.class);
	public static final String ACCOUNT = "Account";

	protected final AccountFactory accountFactory;
	private ContainerUtil containerUtil;

	/**
	 * @param accountDao
	 */
	public SessionContextUtil(ContainerUtil containerUtil, AccountFactory accountFactory) {
		this.containerUtil = containerUtil;
		this.accountFactory = accountFactory;
	}

	public boolean isLogin(SessionContext sessionContext) {
		if (sessionContext == null)
			return false;
		Account account = (Account) sessionContext.getArrtibute(ACCOUNT);
		if (account == null)
			return false;
		else
			return true;
	}

	public Account getLoginAccount(SessionContext sessionContext) {
		if (sessionContext == null)
			return null;
		Account account = null;
		try {
			account = (Account) sessionContext.getArrtibute(ACCOUNT);
			if (account == null) {
				account = loadAccount(sessionContext);
				if (account != null) {
					sessionContext.setArrtibute(ACCOUNT, account);
					logger.debug("save Account to session: accout.userId" + account.getUserId());

					account.setPostIP(getClientIP(sessionContext));
					// set user IP
					logger.debug(" got the account, userId:" + account.getUserId() + " " + ACCOUNT.hashCode() + " role=" + account.getRoleName());

					// preload
					account.getMessageCount();
				}
			}

			if (account == null)
				return account;

			// refresh account in cache is same as that in session.
			this.containerUtil.addModeltoCache(account.getUserId(), account);

		} catch (Exception e) {
			logger.debug(" getLoginAccount error: " + e);
		}

		return account;
	}

	protected Account loadAccount(SessionContext sessionContext) {
		Account account = null;
		String username = getPrinciple(sessionContext);
		if (username != null) {
			account = new Account();
			account.setUsername(username);
			account = accountFactory.getFullAccount(account);
		}
		return account;

	}

	public String getClientIP(SessionContext sessionContext) {
		SessionContextSetup sessionContextSetup = containerUtil.getSessionContextSetup();
		return (String) sessionContextSetup.getArrtibute(SessionContextSetup.REMOTE_ADDRESS, sessionContext);
	}

	private String getPrinciple(SessionContext sessionContext) {
		SessionContextSetup sessionContextSetup = containerUtil.getSessionContextSetup();
		String principleName = (String) sessionContextSetup.getArrtibute(SessionContextSetup.PRINCIPAL_NAME, sessionContext);
		if (principleName == null) {
			logger.debug("the login principle name is null");
		} else
			logger.debug(" the login name is:" + principleName);
		return principleName;
	}

}
