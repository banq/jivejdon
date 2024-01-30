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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.spi.component.email.ValidateCodeEmail;

public class EmailValidateAction extends Action {
	private final static Logger logger = LogManager.getLogger(EmailValidateAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// validateCodeEmail
		String userId = request.getParameter("userId");
		if (userId == null)
			return null;

		String validateCode = request.getParameter("validateCode");
		if (validateCode == null)
			return null;

		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());
		Account account = accountService.getAccount(Long.parseLong(userId));
		if (account == null)
			return null;

		try {
			ValidateCodeEmail validateCodeEmail = (ValidateCodeEmail) WebAppUtil.getComponentInstance("validateCodeEmail", 
					this.servlet.getServletContext());

			if (validateCodeEmail.emailValidate(account.getUserId(), Integer.parseInt(validateCode))) {
				logger.debug("emailValidate passed =" + account.getUserId());
				account.setEmailValidate(true);
				accountService.updateAccountEmailValidate(account);
				return mapping.findForward("ok");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		ActionErrors actionErrors = new ActionErrors();
		actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("emailValidate.error"));
		saveErrors(request, actionErrors);
		return mapping.findForward("error");

	}
}
