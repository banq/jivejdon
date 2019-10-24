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
package com.jdon.jivejdon.spi.component.pingrpc;

import com.google.common.eventbus.Subscribe;
import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.domain.model.realtime.Notification;
import com.jdon.jivejdon.util.ThreadTimer;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.URL;

@Component("blogPingClient")
public class BlogPingClient {
	private final static Logger logger = LogManager.getLogger("BlogPing");

	private final BlogPingServerUrl blogPingServerUrl;
	private final BlogPingTempParams blogPingTempParams;
	private final ThreadTimer threadTimer;

	public BlogPingClient(BlogPingTempParams blogPingTempParams, BlogPingServerUrl
			blogPingServerUrl, ThreadTimer threadTimer) {
		this.blogPingServerUrl = blogPingServerUrl;
		this.blogPingTempParams = blogPingTempParams;
		this.threadTimer = threadTimer;
	}


	@Subscribe
	public void allPing(Notification notification) {

		threadTimer.offer(new BlogPingSender(notification));

	}

	class BlogPingSender extends Thread {
		private final Notification notification;

		public BlogPingSender(Notification notification) {
			this.notification = notification;

		}

		public void run() {
			try {
				if (ToolsUtil.isDebug()) return;
				for (int i = 0; i < blogPingServerUrl.getServiceUrl().length; i++) {
					sendPing(notification, blogPingServerUrl.getServiceUrl()[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void sendPing(Notification notification, String blogPingServerUrl) {
			ForumMessageDTO message = (ForumMessageDTO) notification.getSource();
			if (message == null)
				return;

			String title = StringUtil.replace(blogPingTempParams.getTitle(), "#username#", message
					.getUsername());
			String blogUrl = StringUtil.replace(blogPingTempParams.getBlogUrl(), "#username#",
					message.getUsername());
			String UrlChanges = StringUtil.replace(blogPingTempParams.getUrlChanges(),
					"#threadId#", Long.toString(message.getThreadId()));
			String urlRSS = StringUtil.replace(blogPingTempParams.getUrlRSS(), "#username#",
					message.getUsername());
			logger.info(blogPingServerUrl + " title=" + title + " blogUrl=" + blogUrl + " " +
					"UrlChanges=" + UrlChanges + " urlRSS" + urlRSS);
			blogPing(blogPingServerUrl, title, blogUrl, UrlChanges, urlRSS);
		}

		public void blogPing(String serverUrl, String Title, String blogUrl, String UrlChanges,
							 String UrlRSS) {
			try {
				XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

				config.setServerURL(new URL(serverUrl));
				config.setUserAgent("jdon.com");
				XmlRpcClient client = new XmlRpcClient();
				client.setConfig(config);
				Object[] params = new Object[]{Title, blogUrl, UrlChanges, UrlRSS};
				String pMethodName = "weblogUpdates.extendedPing";
				Object result = client.execute(pMethodName, params);

				logger.info(result);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}


}
