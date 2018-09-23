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

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.block.IPBanListManagerIF;

/**
 * Ban clients that has banned.
 * 
 * see configuration in web.xml
 * 
 * diable, only check ip in this filter
 * 
 */
public class SpamFilter implements Filter {
	private final static Logger log = LogManager.getLogger(SpamFilter.class);

	protected IPBanListManagerIF iPBanListManagerIF;

	private ServletContext servletContext;

	private final HashSet<String> safeips = new HashSet(500);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (safeips.contains(request.getRemoteAddr())) {
			chain.doFilter(request, response);
			return;
		}

		if (safeips.size() > 500) {
			safeips.clear();
		}

		if (iPBanListManagerIF == null)
			iPBanListManagerIF = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", servletContext);

		if (!httpRequest.getRequestURI().contains("registerCode")) {
			if (isSpam(httpRequest)) {
				log.debug("spammer, giving 'em a 503");
				safeips.remove(request.getRemoteAddr());
				disableSessionOnlines(httpRequest);
				if (!response.isCommitted())
					response.reset();
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendError(503);
				return;
			}
		}

		safeips.add(request.getRemoteAddr());
		chain.doFilter(request, response);
		return;
	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	/**
	 * Process the incoming request to extract referrer info and pass it on to
	 * the referrer processing queue for tracking.
	 * 
	 * @returns true if referrer was spam, false otherwise
	 */
	protected boolean isSpam(HttpServletRequest request) {
		return isSpamForThrottle2(request);
	}

	protected boolean isSpamForThrottle2(HttpServletRequest request) {
		if (iPBanListManagerIF.isBanned(request.getRemoteAddr())) {
			String userAgent = request.getHeader("User-Agent");
			String referrerUrl = request.getHeader("Referer");
			log.warn("it is spam : processing referrer for " + request.getRequestURI() + " referrerUrl=" + referrerUrl + " userAgent=" + userAgent
					+ " ip=" + request.getRemoteAddr());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Unused.
	 */
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		servletContext = config.getServletContext();

	}
}
