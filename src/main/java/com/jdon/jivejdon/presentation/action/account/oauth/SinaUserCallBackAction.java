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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.account.SinaOAuthSubmitter;
import com.jdon.jivejdon.manager.account.sina.AccessToken;
import com.jdon.jivejdon.manager.weibo.UserConnectorAuth;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.repository.subscription.SubscriptionInitFactory;
import com.jdon.jivejdon.service.account.OAuthAccountService;
import com.jdon.util.UtilValidate;

public class SinaUserCallBackAction extends Action {
	private final static Logger logger = LogManager.getLogger(SinaUserCallBackAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		String verifier = request.getParameter("code");
		if (UtilValidate.isEmpty(verifier)) {
			logger.error("code is null " + verifier);
			return mapping.findForward("failure");
		}

		Map<String, String> subParams = (Map<String, String>) session.getAttribute("subscriptionParameters");
		if (subParams == null) {
			logger.error("subParams is null ");
			return mapping.findForward("failure");
		}

		try {
			String forwdUrl = mapping.findForward("success").getPath();
			String domainUrl = CallUtil.getDomainUrl(request, forwdUrl, subParams);
			SinaOAuthSubmitter sinaOAuthSubmitter = (SinaOAuthSubmitter) WebAppUtil.getComponentInstance("sinaOAuthSubmitter", request);
			AccessToken accessToken = sinaOAuthSubmitter.getAccessTokenByCode(verifier, domainUrl);
			if (UtilValidate.isEmpty(accessToken.getAccessToken())) {
				logger.error("SINA accessToken RETURN null ");
				return mapping.findForward("failure");
			}

			String userId = (String) subParams.get("userId");
			if (userId == null) {
				OAuthAccountService oAuthAccountService = (OAuthAccountService) WebAppUtil.getService("oAuthAccountService", request);
				Account account = oAuthAccountService.saveSina(accessToken);
				if (account.isAnonymous()) {
					logger.error("save account error: " + accessToken.getAccessToken());
					throw new Exception("save account error");
				}
				subParams.put("j_username", account.getUsername());
				subParams.put("j_password", oAuthAccountService.createPassword(accessToken.getUid()));
				subParams.put("rememberMe", "true");
			} else {
				UserConnectorAuth userConnectorAuth = new UserConnectorAuth(userId, SubscriptionInitFactory.SINAWEIBO, accessToken);
				userConnectorAuth.setExpireTime(System.currentTimeMillis() + Long.parseLong(accessToken.getExpireIn()) * 1000);
				sinaOAuthSubmitter.saveSinaWeiboAuth(userConnectorAuth);
				forwdUrl = mapping.findForward("success2").getPath();
			}
			domainUrl = CallUtil.getDomainUrl(request, forwdUrl, subParams);
			response.sendRedirect(domainUrl);
		} catch (Exception e) {
			logger.error("SinaUserCallBackAction error:" + e);

		}
		return mapping.findForward("failure");

	}

}
