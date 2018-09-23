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
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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

import org.apache.logging.log4j.*;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeyDiff;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.util.UtilValidate;

/**
 * Ban clients that fetching too frequency.
 * 
 * this Ban action is in Interval such as 30 minutes.
 * 
 * @author banq
 * 
 */
public class SpamFilterTooFreq implements Filter {
	private final static Logger log = LogManager.getLogger(SpamFilterTooFreq.class);

	private CustomizedThrottle customizedThrottle;

	protected Pattern robotPattern;

	private boolean isFilter = false;

	private final Pattern numPattern = Pattern.compile("/[0-9]*|/.*/[a-zA-Z_0-9]*");

	public final static String BOTNAME = "botname";

	private ServletContext servletContext;

	public void init(FilterConfig config) throws ServletException {
		servletContext = config.getServletContext();
		// check for possible robot pattern
		String robotPatternStr = config.getInitParameter("referrer.robotCheck.userAgentPattern");
		if (!UtilValidate.isEmpty(robotPatternStr)) {
			// Parse the pattern, and store the compiled form.
			try {
				robotPattern = Pattern.compile(robotPatternStr);
				config.getServletContext().setAttribute(SpamFilterTooFreq.BOTNAME, robotPattern);
			} catch (Exception e) {
				// Most likely a PatternSyntaxException; log and continue as if
				// it is not set.
				log.error("Error parsing referrer.robotCheck.userAgentPattern value '" + robotPatternStr + "'.  Robots will not be filtered. ", e);
			}
		}

		Runnable startFiltertask = new Runnable() {
			public void run() {
				isFilter = true;
			}
		};
		// per one hour start check
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(startFiltertask, 60, 60 * 60, TimeUnit.SECONDS);

		Runnable stopFiltertask = new Runnable() {
			public void run() {
				isFilter = false;
				if (customizedThrottle != null) {
					// when stop .clear the check cache.
					customizedThrottle.clearCache();
				}
			}
		};
		// after 5 Mintues stop it.
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(stopFiltertask, 60 * 10, 60 * 65, TimeUnit.SECONDS);

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!isFilter) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (!httpRequest.getRequestURI().contains("registerCode"))
			if (!isPermittedRobot(httpRequest)) {
				String path = httpRequest.getServletPath();
				if (numPattern.matcher(path).matches()) {
					int slash = path.lastIndexOf("/");
					String id = path.substring(slash + 1, path.length());
					if (!checkSpamHit(id, httpRequest)) {
						log.warn("spammer,  fetching too frequency:" + httpRequest.getRequestURI() + " remote:" + httpRequest.getRemoteAddr());
						disableSessionOnlines(httpRequest);
						if (!response.isCommitted())
							response.reset();
						HttpServletResponse httpResponse = (HttpServletResponse) response;
						httpResponse.sendError(503);
						return;
					}
				}
			}
		chain.doFilter(request, response);
		return;
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

	private boolean checkSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", servletContext);
		}
		HitKeyIF hitKey = new HitKeyDiff(request.getRemoteAddr(), id);
		return customizedThrottle.processHitFilter(hitKey);
	}

	public void destroy() {

	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

}
