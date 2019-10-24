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

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.block.ErrorBlockerIF;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Ban clients that not refer from owner domain, or other spammer that not in
 * web..xml
 * 
 * this Ban action is in Interval such as 30 minutes.
 * 
 * @author banq
 * 
 */
public class SpamFilterRefer implements Filter {
	private final static Logger log = LogManager.getLogger(SpamFilterRefer.class);

	protected Pattern domainPattern;

	protected Pattern robotPattern;

	public static String DP = "domainPattern";

	protected ErrorBlockerIF errorBlocker;

	private ServletContext servletContext;

	public void init(FilterConfig config) throws ServletException {
		servletContext = config.getServletContext();

		String domainPatternStr = config.getInitParameter("referrer.domain.namePattern");
		if (!UtilValidate.isEmpty(domainPatternStr)) {
			try {
				domainPattern = Pattern.compile(domainPatternStr);
				if (domainPattern != null)
					config.getServletContext().setAttribute(SpamFilterRefer.DP, domainPattern);
			} catch (Exception e) {
				log.error("Error parsingreferrer.domain.namePattern value '" + domainPattern, e);
			}
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (!isPermittedReferer(httpRequest)){
				log.debug("spammer, not permitted referer :" + httpRequest.getRequestURI() + " refer:" + httpRequest.getHeader("Referer")
						+ " remote:" + httpRequest.getRemoteAddr());
				disableSessionOnlines(httpRequest);
				if (!response.isCommitted())
					response.reset();
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendError(400);
				return;
			}

		if (errorBlocker == null)
			errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", servletContext);

		chain.doFilter(new XSSRequestWrapper(httpRequest, errorBlocker), response);
		return;
	}

	private boolean isPermittedReferer(HttpServletRequest request) {
		if (request.getRequestURI().endsWith("/"))
			return true;
		// referer is in parameter
		if (request.getParameter("Referer") != null) {
			if (domainPattern.matcher(request.getParameter("Referer").toLowerCase()).matches()) {
				return true;
			}
		}
		String referrerUrl = request.getHeader("Referer");
		if (referrerUrl == null) {
			if (request.getRemoteAddr().equalsIgnoreCase("127.0.0.1") || request.getRemoteAddr().equalsIgnoreCase("0:0:0:0:0:0:0:1")
					|| request.getRemoteAddr().equalsIgnoreCase("localhost")) {
				return true;
			}
		} else if (referrerUrl != null && referrerUrl.length() > 0 && domainPattern != null) {
			if (domainPattern.matcher(referrerUrl.toLowerCase()).matches()) {
				return true;
			}
		}
		return false;
	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	private boolean isPermittedRobot(HttpServletRequest request) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0 && robotPattern.matcher(userAgent.toLowerCase()).matches()) {
				disableSessionOnlines(request);// although permitted, but
				// disable session.
				return true;
			}
		}
		return false;
	}

	public void destroy() {

	}

}
