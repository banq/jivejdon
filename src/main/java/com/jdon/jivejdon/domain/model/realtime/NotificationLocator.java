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
package com.jdon.jivejdon.domain.model.realtime;

import java.util.Date;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.realtime.notification.ContentFormatConverter;
import com.jdon.jivejdon.domain.model.util.AutoDiscardingDeque;

public class NotificationLocator implements Startable {

	private AutoDiscardingDeque<Notification> notifications;

	private ContentFormatConverter contentFormatConverter;

	public NotificationLocator(ContentFormatConverter contentFormatConverter) {
		this.notifications = new AutoDiscardingDeque(10);
		this.contentFormatConverter = contentFormatConverter;

	}

	public void addNotification(Notification notification) {
		notifications.offerFirst(notification);
	}

	public Notification checkNotification() {
		Notification notification = notifications.peek();
		if (notification == null)
			return null;

		Date nowD = new Date();
		if (nowD.after(notification.getDeadDate())) {
			notifications.remove(notification);
			return null;
		}

		// refresh per 5 mins.
		if (notification.getContent() == null) {
			notification = contentFormatConverter.convertContent(notification);
		}
		return notification;

	}

	public void clear() {
		notifications.clear();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		notifications.clear();
	}

}
