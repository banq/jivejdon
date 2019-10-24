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

import junit.framework.TestCase;

import com.jdon.jivejdon.domain.model.realtime.notification.ContentFormatConverter;

public class NotificationLocatorTest extends TestCase {

	private NotificationLocator notificationLocator;

	protected void setUp() throws Exception {
		super.setUp();
		notificationLocator = new NotificationLocator(new ContentFormatConverter("540", "ZH_CN", "", ""));

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddNotification() {
		Notification notification = new Notification();
		notification.setContent("bed");
		notificationLocator.addNotification(notification);

		notification = new Notification();
		notification.setContent("987");
		notificationLocator.addNotification(notification);

		notification = new Notification();
		notification.setContent("fgh");
		notificationLocator.addNotification(notification);

		notification = new Notification();
		notification.setContent("12");
		notificationLocator.addNotification(notification);

	}

	public void testCheckNotification() {
		Notification notification = new Notification();
		notification.setContent("new9");
		notificationLocator.addNotification(notification);

		notification = notificationLocator.checkNotification();
		System.out.print("count =" + notification.getContent());

		notification = new Notification();
		notification.setContent("newfffff");
		notificationLocator.addNotification(notification);

		notification = new Notification();
		notification.setContent("newggggggg");
		notificationLocator.addNotification(notification);

		notification = notificationLocator.checkNotification();
		System.out.print("count =" + notification.getContent());

		notification = notificationLocator.checkNotification();
		System.out.print("count =" + notification.getContent());

		notification = new Notification();
		notification.setContent("newggggggg");
		notificationLocator.addNotification(notification);

		notification = notificationLocator.checkNotification();
		System.out.print("count =" + notification.getContent());

	}

}
