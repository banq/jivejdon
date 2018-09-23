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
package com.jdon.jivejdon.presentation.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * web.xml <filter-name>ExpiresFilter</filter-name>
 * <filter-class>com.jdon.jivejdon
 * .presentation.filter.ExpiresFilter</filter-class>
 * 
 * 
 * request.setAttribute("myExpire", 5 * 24 * 60 * 60);
 * 
 * expireFilter not effects /jivejdon/xxxx (messageListAction.shtml)
 * 
 * @author banq
 * 
 */
public class ExpiresFilter extends fr.xebia.servlet.filter.ExpiresFilter {
	private static final String HEADER_CACHE_CONTROL = "Cache-Control";
	private static final String HEADER_EXPIRES = "Expires";

	public void onBeforeWriteResponseBody(HttpServletRequest request, XHttpServletResponse response) {

		if (!isEligibleToExpirationHeaderGeneration(request, response)) {
			return;
		}

		Date expirationDate = getExpirationDate(request, response);
		if (request.getParameter("myExpire") != null || request.getAttribute("myExpire") != null) {
			long myExpireL = 0;
			// myExpire unit is seconds
			if (request.getAttribute("myExpire") != null)
				myExpireL = (Long) request.getAttribute("myExpire");
			else if (request.getParameter("myExpire") != null)
				myExpireL = Integer.parseInt(request.getParameter("myExpire"));
			long myExpireDateL = System.currentTimeMillis() + myExpireL * 1000;
			expirationDate = new Date(myExpireDateL);
		}
		if (expirationDate == null)
			return;

		String maxAgeDirective = "max-age=" + ((expirationDate.getTime() - System.currentTimeMillis()) / 1000);

		String cacheControlHeader = response.getCacheControlHeader();
		String newCacheControlHeader = (cacheControlHeader == null) ? maxAgeDirective : cacheControlHeader + ", " + maxAgeDirective;
		response.setHeader(HEADER_CACHE_CONTROL, newCacheControlHeader);
		response.setDateHeader(HEADER_EXPIRES, expirationDate.getTime());

	}

}
