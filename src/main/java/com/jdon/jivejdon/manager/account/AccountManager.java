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
package com.jdon.jivejdon.manager.account;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.manager.email.ForgotPasswdEmail;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.account.PasswordassitVO;
import com.jdon.jivejdon.repository.acccount.AccountFactory;
import com.jdon.jivejdon.repository.acccount.AccountRepository;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.util.StringUtil;

@Component
public class AccountManager implements Startable {

	private Set cachedOneTimes = new HashSet();

	private final ScheduledExecutorUtil scheduledExecutorUtil;

	private ForgotPasswdEmail forgotPasswdEmail;

	private final AccountRepository accountRepository;

	protected final AccountFactory accountFactory;

	public AccountManager(AccountFactory accountFactory, AccountRepository accountRepository, ForgotPasswdEmail forgotPasswdEmail,
			ScheduledExecutorUtil scheduledExecutorUtil) {
		super();
		this.forgotPasswdEmail = forgotPasswdEmail;
		this.accountRepository = accountRepository;
		this.accountFactory = accountFactory;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				cachedOneTimes.clear();
			}
		};
		// per ten mintue
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, 60, 60 * 60 * 2, TimeUnit.SECONDS);
	}

	public void stop() {
		cachedOneTimes.clear();
	}

	public boolean contains(String chkKey) {
		return this.cachedOneTimes.contains(chkKey);
	}

	public void addChkKey(String chkKey) {
		this.cachedOneTimes.add(chkKey);
	}

	public boolean forgetPasswdAction(PasswordassitVO passwordassitVOParam) throws Exception {

		boolean ret = false;
		try {
			PasswordassitVO passwordassitVO = accountRepository.getPasswordassit(passwordassitVOParam.getUserId());
			if (passwordassitVO == null)
				throw new Exception();

			if ((passwordassitVO.getPasswdanswer().equalsIgnoreCase(passwordassitVOParam.getPasswdanswer()))
					&& (passwordassitVO.getPasswdtype().equalsIgnoreCase(passwordassitVOParam.getPasswdtype()))) {
				Account account = this.accountFactory.getFullAccount(passwordassitVOParam.getUserId());
				String newpasswd = StringUtil.getPassword(8);
				account.setPassword(newpasswd);
				accountRepository.updateAccount(account);
				forgotPasswdEmail.send(account, newpasswd);
				ret = true;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return ret;
	}
}
