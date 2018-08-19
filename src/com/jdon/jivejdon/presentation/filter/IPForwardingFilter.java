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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class IPForwardingFilter implements Filter {

	private static final String X_FORWARDED_FOR = "x-forwarded-for";

	private boolean enabled = true;

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (enabled) {
			HttpServletRequest request = (HttpServletRequest) srequest;
			final String realIp = request.getHeader(X_FORWARDED_FOR);

			if (realIp != null) {
				filterChain.doFilter(new HttpServletRequestWrapper(request) {
					public String getRemoteAddr() {
						return realIp;
					}

					public String getRemoteHost() {
						return realIp;
					}
				}, response);

				return;
			}
		}

		filterChain.doFilter(srequest, response);

	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
