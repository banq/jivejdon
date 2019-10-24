/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.presentation.form.SkinUtils;
import com.jdon.util.Debug;
import com.jdon.util.RegisterCode;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class RegisterCodeAction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String module = RegisterCodeAction.class.getName();

	private CustomizedThrottle customizedThrottle;

	protected Pattern robotPattern;

	protected Pattern domainPattern;
	
	private ServletContext servletContext;

	// private List<String> bannedIP = new ArrayList();

	public void init(ServletConfig config) throws ServletException {
		// check for possible robot pattern
		String robotPatternStr = config.getInitParameter("referrer.robotCheck.userAgentPattern");
		if (!UtilValidate.isEmpty(robotPatternStr)) {
			// Parse the pattern, and store the compiled form.
			try {
				robotPattern = Pattern.compile(robotPatternStr);
			} catch (Exception e) {
				Debug.logError(e, module);
			}
		}

		String domainPatternStr = config.getInitParameter("referrer.domain.namePattern");
		if (!UtilValidate.isEmpty(domainPatternStr)) {
			try {
				domainPattern = Pattern.compile(domainPatternStr);
			} catch (Exception e) {
				Debug.logError(e, module);
			}
		}
		
		servletContext = config.getServletContext();

		/**
		 * Runnable task = new Runnable() { public void run() {
		 * bannedIP.clear(); } }; // flush to db per one hour
		 * scheduExec.scheduleWithFixedDelay(task, 60, 6000, TimeUnit.SECONDS);
		 */
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isPermitted(request)) {
			Debug.logError(" is not Permitted ", module);
			response.sendError(404);
			return;
		}

		// if it is a robot, not output
		if (isRobot(request)) {
			Debug.logError(" is robot ", module);
			response.sendError(404);
			return;
		}

		if (isSpamHit("RegisterCodeAction", request)) {
			Debug.logError(" isSpamHit ", module);
			customizedThrottle.addBanned(request.getRemoteAddr());
			response.sendError(404);
			return;
		}

		try {
			String registerCode = request.getParameter("registerCode");
			if (registerCode == null || registerCode.length() == 0) {
				renderImage(request, response);
				return;
			}

			if (SkinUtils.verifyRegisterCode(registerCode, request)) {
				CustomizedThrottle customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", servletContext);
				customizedThrottle.removeBanned(request.getRemoteAddr());
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.sendRedirect(request.getContextPath());
				return;
			}

			response.sendError(404);

		} catch (Exception ex) {
			Debug.logError(" renderImage error " + ex, module);
		}
	}

	private void renderImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream toClient = null;
		try {

			response.setContentType("images/jpeg");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			toClient = response.getOutputStream();

			RegisterCode registerCode = new RegisterCode();
			String rand = SkinUtils.getRegisterCode(request, response);
			registerCode.create(60, 20, rand, "Times New Roman", 18, 2, 16, toClient);
			toClient.flush();
		} catch (Exception e) {

		} finally {
			if (toClient != null)
				toClient.close();
		}

	}

	private boolean isRobot(HttpServletRequest request) {

		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0 && robotPattern.matcher(userAgent.toLowerCase()).matches()) {
				// disable session.
				return true;
			}
		}
		return false;
	}

	private boolean isPermitted(HttpServletRequest request) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String referrerUrl = request.getHeader("Referer");
		if (domainPattern != null) {
			if (referrerUrl != null && referrerUrl.length() > 0 && domainPattern.matcher(referrerUrl.toLowerCase()).matches()) {
				return true;
			}
		}

		// String clinetIp = request.getRemoteAddr();
		// if ((clinetIp !=null) && (clinetIp.indexOf("127.0.0.1") != -1))
		// return true; //if localhost debug, skip;
		return false;
	}

	private boolean isSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", servletContext);
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), id);
		return customizedThrottle.processHit(hitKey);
	}

	public void destroy() {
		// bannedIP.clear();
	}
}
