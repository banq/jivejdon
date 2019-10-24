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
import com.jdon.jivejdon.spi.component.weibo.TecentWeiboSubmitter;

public class TecentUserCallAction extends Action {
	private final static Logger logger = LogManager.getLogger(TecentUserCallAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forwdUrl = mapping.findForward("success").getPath();
		String domainUrl = CallUtil.getCleanDomainUrl(request, forwdUrl);

		TecentWeiboSubmitter weiboSubmitter = (TecentWeiboSubmitter) WebAppUtil.getComponentInstance("tecentWeiboSubmitter", request);
		String authUrl = weiboSubmitter.request(domainUrl);
		if (authUrl != null) {
			HttpSession session = request.getSession();
			Map<String, String> params = CallUtil.getParameters(request);
			params.put("tecentbackurl", domainUrl);
			session.setAttribute("subscriptionParameters", params);
			response.sendRedirect(authUrl);
		} else {
			request.setAttribute("errors", "tecent weibo server error");
		}

		return mapping.findForward("failure");
	}
}
