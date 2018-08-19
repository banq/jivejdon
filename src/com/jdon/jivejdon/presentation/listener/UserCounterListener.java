/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.presentation.listener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * UserCounterListener class used to count the current number of active users
 * for the applications. Does this by counting how many user objects are stuffed
 * into the session. It Also grabs these users and exposes them in the servlet
 * context.
 * 
 * @web.listener
 */
public class UserCounterListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
	public static final String COUNT_KEY = "userCounter";
	public static final String OnLineUser_KEY = "onLineUser";

	// count not correct.
	private int activeSessions;
	private ServletContext servletContext = null;

	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		servletContext.setAttribute(COUNT_KEY, this);
		servletContext.setAttribute(OnLineUser_KEY, new CopyOnWriteArrayList());
	}

	public void contextDestroyed(ServletContextEvent event) {
		activeSessions = 0;
	}

	public void sessionCreated(HttpSessionEvent se) {
		activeSessions++;
	}

	// see common/security.jsp
	public void sessionDestroyed(HttpSessionEvent se) {
		activeSessions--;
		HttpSession session = se.getSession();
		String username = (String) session.getAttribute("online");
		if (username == null) {
			return;
		}
		List userList = (List) servletContext.getAttribute(OnLineUser_KEY);
		userList.remove(username);

	}

	// see common/security.jsp
	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		if (!"online".equals(hsbe.getName())) {
			return;
		}
		String username = (String) hsbe.getValue();
		if (username == null) {
			return;
		}
		List userList = (List) servletContext.getAttribute(OnLineUser_KEY);
		userList.add(username);
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		if (!"online".equals(se.getName())) {
			return;
		}
		String username = (String) se.getValue();
		if (username == null) {
			return;
		}
		List userList = (List) servletContext.getAttribute(OnLineUser_KEY);
		userList.remove(username);

	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
	}

	// header_Body_Body.jsp call this method
	public int getActiveSessions() {
		return activeSessions;
	}
}