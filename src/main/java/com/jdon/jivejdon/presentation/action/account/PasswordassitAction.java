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

import java.util.HashMap;
import java.util.Map;

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
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;
import com.jdon.jivejdon.presentation.action.account.oauth.CallUtil;
import com.jdon.jivejdon.presentation.form.SkinUtils;

public class PasswordassitAction extends Action {
	private final static Logger logger = LogManager.getLogger(PasswordassitAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userId = request.getParameter("userId");
		String passwdtype = request.getParameter("passwdtype");
		String passwdanswer = request.getParameter("passwdanswer");
		PasswordassitVO passwordassitVO = new PasswordassitVO(userId, passwdtype, passwdanswer);
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());

		String action = request.getParameter("action");
		try {
			if (action == null || action.equals("create")) {
				String actionType = request.getParameter("actionType");
				if (actionType == null) {
					passwordassitVO = new PasswordassitVO("", "", "");
					request.setAttribute("passwordassitVO", passwordassitVO);
					return mapping.findForward("create");
				} else {
					String username = request.getParameter("username");
					Account account = accountService.getAccountByName(username);
					if (!account.isAnonymous()) {
						passwordassitVO = new PasswordassitVO(account.getUserId(), passwdtype, passwdanswer);
						accountService.insertPasswordassit(passwordassitVO);
						String forwdUrl = mapping.findForward("success").getPath();
						Map subParams = new HashMap();
						subParams.put("j_username", account.getUsername());
						subParams.put("j_password", request.getParameter("password"));
						subParams.put("rememberMe", "true");

						String domainUrl = CallUtil.getDomainUrl(request, forwdUrl, subParams);
						response.sendRedirect(domainUrl);
					} else {
						return mapping.findForward("failure");
					}
				}
			} else if (action.equals("edit")) {
				String actionType = request.getParameter("actionType");
				if (actionType == null) {
					if (userId != null)
						passwordassitVO = accountService.getPasswordassit(userId);
					String username = request.getParameter("username");
					if (username != null) {
						Account account = accountService.getAccountByName(username);
						userId = account.getUserId();
						passwordassitVO = accountService.getPasswordassit(userId);
					}
					if (passwordassitVO == null)
						passwordassitVO = new PasswordassitVO(userId, "", "");
					request.setAttribute("passwordassitVO", passwordassitVO);
					return mapping.findForward("edit");
				} else {
					accountService.updatePasswordassit(passwordassitVO);
					request.setAttribute("passwordassitVO", passwordassitVO);
					return mapping.findForward("success");
				}
			} else if (action.equals("delete")) {
				accountService.deletePasswordassit(userId);
				return mapping.findForward("success");
			} else if (action.equals("forgetPasswd")) {
				String step = request.getParameter("step");
				if (step.equals("1")) {
					String username = request.getParameter("username");
					Account account = accountService.getAccountByName(username);
					if (!account.isAnonymous()) {
						passwordassitVO = accountService.getPasswordassit(account.getUserId());
						request.setAttribute("passwordassitVO", passwordassitVO);
						return mapping.findForward("success");
					} else {
						ActionErrors actionErrors = new ActionErrors();
						actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.nouser"));
						saveErrors(request, actionErrors);
						return mapping.findForward("failure");
					}
				} else if (step.equals("2")) {
					if (!SkinUtils.verifyRegisterCode(request.getParameter("registerCode"), request)) {
						ActionErrors actionErrors = new ActionErrors();
						actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("registerCode.dismatch"));
						saveErrors(request, actionErrors);
						return mapping.findForward("failure");
					}

					boolean ret = accountService.forgetPasswd(passwordassitVO);
					if (!ret) {
						ActionErrors actionErrors = new ActionErrors();
						actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("wrong.answer"));
						saveErrors(request, actionErrors);
						return mapping.findForward("failure");
					}
					Account account = accountService.getAccount(Long.parseLong(userId));
					String email = account.getEmail();
					int len = email.indexOf("@");
					String s1 = email.substring(1, len);
					String n = "";
					for (int i = 1; i < len; i++) {
						n = n + "*";
					}
					email = email.replaceAll(s1, n);
					request.setAttribute("email", email);
					return mapping.findForward("success");
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}
}
