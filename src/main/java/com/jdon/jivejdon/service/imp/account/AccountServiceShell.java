/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.service.imp.account;

import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.annotation.intercept.Stateful;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.manager.account.AccountManager;
import com.jdon.jivejdon.manager.email.ForgotPasswdEmail;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.account.PasswordassitVO;
import com.jdon.jivejdon.repository.acccount.AccountFactory;
import com.jdon.jivejdon.repository.acccount.AccountRepository;
import com.jdon.jivejdon.repository.property.PropertyFactory;
import com.jdon.jivejdon.repository.acccount.Userconnector;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;
import com.jdon.jivejdon.service.util.SessionContextUtil;
import com.jdon.util.Debug;

@Stateful
public class AccountServiceShell extends AccountServiceImp {
	private final static String module = AccountServiceShell.class.getName();

	protected SessionContextUtil sessionContextUtil;

	protected SessionContext sessionContext;

	protected ResourceAuthorization resourceAuthorization;

	private AccountManager accountManager;

	public AccountServiceShell(AccountFactory accountFactory, AccountRepository accountRepository, AccountManager accountManager,
			SequenceDao sequenceDao, SessionContextUtil sessionContextUtil, JtaTransactionUtil jtaTransactionUtil,
			ResourceAuthorization resourceAuthorization, ForgotPasswdEmail forgotPasswdEmail, PropertyFactory propertyFactory,
			Userconnector userconnector) {
		super(accountFactory, accountRepository, sequenceDao, jtaTransactionUtil, propertyFactory, userconnector);
		this.sessionContextUtil = sessionContextUtil;
		this.resourceAuthorization = resourceAuthorization;
		this.accountManager = accountManager;

	}

	public Account getloginAccount() {
		return sessionContextUtil.getLoginAccount(sessionContext);
	}

	public void createAccount(EventModel em) {
		String ip = sessionContextUtil.getClientIP(sessionContext);
		String chkKey = "REGISTER" + ip.substring(0, 8);
		if (accountManager.contains(chkKey)) {
			em.setErrors("only.register.one.times");
		} else {
			super.createAccount(em);
			if (em.getErrors() == null || em.getErrors().isEmpty())
				accountManager.addChkKey(chkKey);

		}
	}

	public Account getLoginAccountByName(String username) {
		Account account = getloginAccount();
		if (account != null)
			// if now is Admin
			if (resourceAuthorization.isAdmin(account) && !username.equals("admin"))
				return super.getAccountByName(username);
			else
				return account;
		else
			return super.getAccountByName(username);

	}

	public void updateAccount(EventModel em) {
		Debug.logVerbose("enter updateAccount", module);
		Account accountInput = (Account) em.getModelIF();
		try {
			Account account = getloginAccount();
			if (account == null) {
				Debug.logError("this user not login", module);
				return;
			}
			if (resourceAuthorization.isOwner(account, accountInput)) {
				super.updateAccount(accountInput);
			}
		} catch (Exception daoe) {
			Debug.logError(" updateAccount error : " + daoe, module);
			em.setErrors(Constants.ERRORS);
		}
	}

	public void deleteAccount(EventModel em) {
		Account accountInput = (Account) em.getModelIF();
		try {
			Account account = getloginAccount();
			if (resourceAuthorization.isOwner(account, accountInput)) {
				super.deleteAccount(accountInput);
			}
		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
	}

	public void updateAccountAttachment(EventModel em) {
		Account accountInput = (Account) em.getModelIF();
		Account account = this.getAccount(accountInput.getUserIdLong());
		account.setUploadFile(true);
	}

	public boolean forgetPasswd(PasswordassitVO passwordassitVO) {
		boolean ret = false;
		String chkKey = "REGISTER" + sessionContextUtil.getClientIP(sessionContext).substring(0, 8);
		if (!accountManager.contains(chkKey)) {
			try {
				ret = accountManager.forgetPasswdAction(passwordassitVO);
				accountManager.addChkKey(chkKey);
			} catch (Exception e) {
			}
		}
		return ret;
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
