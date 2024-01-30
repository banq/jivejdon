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

public class AccountPropsAction extends Action {
	private final static Logger logger = LogManager.getLogger(AccountPropsAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter save proprs");

		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		Account account = accountService.getloginAccount();
		if (account == null) {
			return mapping.findForward("failure");
		}

		AccountProfileForm accountProfileForm = (AccountProfileForm) form;
		Collection props = accountProfileForm.getPropertys();
		accountService.saveUserpropertys(account.getUserId(), props);

		return mapping.findForward("success");
	}

}
