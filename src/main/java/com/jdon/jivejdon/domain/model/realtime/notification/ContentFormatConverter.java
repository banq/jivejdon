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
package com.jdon.jivejdon.domain.model.realtime.notification;

import java.util.Date;
import java.util.Locale;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.domain.model.realtime.Notification;
import com.jdon.util.StringUtil;
import com.ocpsoft.pretty.time.PrettyTime;

public class ContentFormatConverter {
	private String notifyTitle;
	private String notifyUrlTemp;
	private int notificationLifeCycle;
	private String locale;

	public ContentFormatConverter(String notificationLifeCycleS, String locale, String notifyTitle, String notifyUrlTemp) {
		super();
		this.notifyTitle = notifyTitle;
		this.notifyUrlTemp = notifyUrlTemp;
		if (!notificationLifeCycleS.isEmpty())
			notificationLifeCycle = Integer.parseInt(notificationLifeCycleS);
		else
			notificationLifeCycle = 540;
		this.locale = locale;

	}

	public Notification convertContent(Notification notification) {
		if (this.isEmpty())
			return notification;
		try {
			ForumMessageDTO message = (ForumMessageDTO) notification.getSource();
			if (message == null)
				return notification;
			String newSubscribedUrl = StringUtil.replace(notifyUrlTemp, "threadId", Long.toString(message.getThreadId()));
			newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "messageId", Long.toString(message.getMessageId()));
			newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "subject", message.getSubject());
			newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "notificationId", Long.toString(notification.getId()));
			newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "scopeSeconds", Integer.toString(notification.getScopeSeconds()));

			Date messageCreateDate = Constants.parseDateTime(message.getCreationDate());
			PrettyTime t = new PrettyTime(new Locale(locale)); // "ZH"
			t.setReference(new Date());

			notification.setSubject(t.format(messageCreateDate) + message.getUsername() + notifyTitle);
			notification.setContent(notification.getSubject() + " </br> " + newSubscribedUrl);

			notification.setScopeSeconds(this.notificationLifeCycle);
			notification.setSourceId(message.getThreadId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification;
	}

	public boolean isEmpty() {
		return notifyTitle.isEmpty() && notifyUrlTemp.isEmpty();

	}

}
