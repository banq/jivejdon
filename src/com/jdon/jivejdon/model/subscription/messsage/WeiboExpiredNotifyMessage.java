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
package com.jdon.jivejdon.model.subscription.messsage;


public class WeiboExpiredNotifyMessage {

	private final String notifyTitle;
	private final String notifyUrlTemp;
	private final String notifier;

	public WeiboExpiredNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
		super();
		this.notifyTitle = notifyTitle;
		this.notifyUrlTemp = notifyUrlTemp;
		this.notifier = notifier;

	}

	public String getNotifyUrlTemp() {
		return notifyUrlTemp;
	}

	public String getNotifyTitle() {
		return notifyTitle;
	}

	public String getNotifier() {
		return notifier;
	}
}
