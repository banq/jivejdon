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
package com.jdon.jivejdon.presentation.action.account;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.presentation.form.AccountProfileForm;

/**
 * 
 * <action name="accountProfileForm"
 * path="/account/protected/accountProfileForm"
 * type="com.jdon.jivejdon.presentation.action.account.AccountProfileAction"
 * parameter="action" scope="request" validate="false"> <forward name="ok"
 * path="/account/protected/profile.jsp" /> </action>
 * 
 * 
 * <!-- display user's profile info --> <action name="accountProfileForm"
 * path="/account/accountProfile"
 * type="com.jdon.jivejdon.presentation.action.account.AccountProfileAction"
 * parameter="action" scope="request" validate="false"> <forward name="ok"
 * path="/account/profileView.jsp" /> </action>
 * 
 * 
 * here is pass into parameter username:
 * 
 * <action name="accountProfileForm" path="/blog/accountProfile"
 * type="com.jdon.jivejdon.presentation.action.account.AccountProfileAction"
 * scope="request" validate="false"> <forward name="success"
 * path="/blog/userThreadsAction.shtml" /> <forward name="failure"
 * path="/common/permError.jsp" /> </action>
 * 
 * 
 * @author banq
 * 
 */
public class AccountProfileAction extends Action {
	private final static Logger logger = LogManager.getLogger(AccountProfileAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter AccountProfileAction");
		String userId = request.getParameter("userId");
		String username = request.getParameter("username");

		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());
		Account account = null;
		if (userId != null) {
			account = accountService.getAccount(Long.parseLong(userId));
		} else if (username != null) {
			account = accountService.getAccountByName(username);
			if (account != null && !account.getUsername().equalsIgnoreCase(username)) {
				logger.error("the user is not for target, username=" + username + " account username=" + account.getUsername());
				return mapping.findForward("failure");
			}
		}
		if (account == null) {
			logger.error("not found blog, userId=" + userId + " username=" + username + " from=" + request.getRemoteAddr());
			return mapping.findForward("failure");
		}

		AccountProfileForm accountProfileForm = (AccountProfileForm) form;

		accountProfileForm.setAccount(account);
		accountProfileForm.setUserId(account.getUserId());

		Collection oldpropertys = accountService.getUserpropertys(account.getUserId());
		accountProfileForm.setPropertys(oldpropertys);

		Account accountLogin = accountService.getloginAccount();
		if (accountLogin != null && accountLogin.getUserId().equals(account.getUserId()) && mapping.findForward("successlogin") != null) {
			return mapping.findForward("successlogin");
		} else
			return mapping.findForward("success");

	}

}
