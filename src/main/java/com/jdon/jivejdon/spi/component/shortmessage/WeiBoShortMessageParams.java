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
package com.jdon.jivejdon.spi.component.shortmessage;

import com.jdon.jivejdon.domain.event.ATUserNotifiedEvent;
import com.jdon.util.StringUtil;

public class WeiBoShortMessageParams {

	private final String subject;
	private final String body;

	public WeiBoShortMessageParams(String subject, String body) {
		super();
		this.subject = subject;
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody(ATUserNotifiedEvent event) {
		String newSubscribedUrl = StringUtil.replace(body, "messageId", event.getMessageIdId().toString());
		newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "subject", event.getSubject());
		return newSubscribedUrl;
	}

}
