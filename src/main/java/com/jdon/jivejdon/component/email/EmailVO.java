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
package com.jdon.jivejdon.component.email;

public class EmailVO {

	private final String toName;
	private final String toEmail;
	private final String fromName;
	private final String fromEmail;
	private final String subject;
	private final String body;
	private final String format;

	public EmailVO(String toName, String toEmail, String fromName, String fromEmail, String subject, String body, String format) {
		super();
		this.toName = toName;
		this.toEmail = toEmail;
		this.fromName = fromName;
		this.fromEmail = fromEmail;
		this.subject = subject;
		this.body = body;
		this.format = format;
	}

	public String getToName() {
		return toName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getFormat() {
		return format;
	}

	public boolean isEmpty() {
		if (toEmail == null || fromEmail == null || subject == null || body == null) {
			return true;
		} else
			return false;

	}

}
