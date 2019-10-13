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
package com.jdon.jivejdon.presentation.action.account.oauth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.oauth.OAuthAccessor;

import org.apache.logging.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.component.account.GoogleOAuthSubmitter;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.service.account.OAuthAccountService;

public class GoogleCallBackAction extends Action {
	private final static Logger logger = LogManager.getLogger(GoogleCallBackAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		String verifier = request.getParameter("oauth_verifier");
		if (verifier != null) {
			OAuthAccessor resToken = (OAuthAccessor) session.getAttribute("resToken");
			if (resToken != null) {
				GoogleOAuthSubmitter googleOAuthSubmitter = (GoogleOAuthSubmitter) WebAppUtil.getComponentInstance("googleOAuthSubmitter", request);
				OAuthAccessor accessToken = googleOAuthSubmitter.requstAccessToken(resToken, verifier);

				if (accessToken.accessToken == null)
					return mapping.findForward("failure");

				OAuthAccountService oAuthAccountService = (OAuthAccountService) WebAppUtil.getService("oAuthAccountService", request);
				Account account = oAuthAccountService.saveGoogle(accessToken);

				Map<String, String> subParams = (Map<String, String>) session.getAttribute("subscriptionParameters");
				if (subParams != null && account != null) {
					String forwdUrl = mapping.findForward("success").getPath();
					subParams.put("j_username", account.getUsername());
					subParams.put("j_password", oAuthAccountService.createPassword(accessToken.accessToken));
					subParams.put("rememberMe", "true");

					String domainUrl = CallUtil.getDomainUrl(request, forwdUrl, subParams);
					response.sendRedirect(domainUrl);
				}
			}
		}
		return mapping.findForward("failure");
	}
}
