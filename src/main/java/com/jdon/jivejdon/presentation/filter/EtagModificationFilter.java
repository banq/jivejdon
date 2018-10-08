/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.filter;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.action.util.ForumUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * check if modified in all forums, if no, return 304;
 * 
 * 
 * 
 * configuration in web.xml but invalidate for urlrewrite
 * 
 * <filter> <filter-name>EtagModificationFilter</filter-name> <filter-class>
 * com.jdon.jivejdon.presentation.filter.EtagModificationFilter </filter-class>
 * <init-param> <param-name>defaulr.expire.seconds</param-name>
 * <param-value>86400</param-value><!-- per one day check if modified if not
 * return 304 --> </init-param>
 * 
 * </filter>
 * 
 * 
 */
public class EtagModificationFilter implements Filter {

	private int default_expireSeconds = 1 * 24 * 60 * 60;
	
	private ServletContext servletContext= null;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		ForumMessage lastpost = ForumUtil.getForumsLastModifiedDate(servletContext);
		long modelLastModifiedDate = lastpost.getModifiedDate2();
		if (!ToolsUtil.checkHeaderCache(default_expireSeconds, modelLastModifiedDate, (HttpServletRequest) request, (HttpServletResponse) response)) {
			return;
		}
		chain.doFilter(request, response);
		return;
	}

	/**
	 * Unused.
	 */
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String default_expire = config.getInitParameter("defaulr.expire.seconds");
		if (!UtilValidate.isEmpty(default_expire)) {
			default_expireSeconds = Integer.parseInt(default_expire);
		}
		servletContext = config.getServletContext();

	}
}
