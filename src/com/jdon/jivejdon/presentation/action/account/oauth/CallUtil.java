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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CallUtil {

	public static String getDomainUrl(HttpServletRequest request, String forwdUrl, Map params) {
        String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
        domainUrl = domainUrl + request.getContextPath();
		domainUrl = domainUrl + forwdUrl + "?Referer=" + domainUrl;

		for (Object o : params.keySet()) {
			String s = (String) o;
			domainUrl = domainUrl + "&" + s + "=" + params.get(s);
		}

		return domainUrl;

	}

	public static String getCleanDomainUrl(HttpServletRequest request, String forwdUrl) {
        String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
        domainUrl = domainUrl + request.getContextPath();
		domainUrl = domainUrl + forwdUrl + "?Referer=" + domainUrl;

		return domainUrl;

	}

	public static Map<String, String> getParameters(HttpServletRequest request) {
		Map<String, String> result = new HashMap();
		Map requestMap = request.getParameterMap();
		for (Object o : requestMap.keySet()) {
			String s = (String) o;
			result.put(s, request.getParameter(s));
		}
		return result;

	}

}
