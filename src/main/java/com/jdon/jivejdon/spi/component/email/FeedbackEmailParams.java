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
package com.jdon.jivejdon.spi.component.email;

public class FeedbackEmailParams {

	private String jndiname;

	private String toName;
	private String toEmail;

	public FeedbackEmailParams(String jndiname, String toName, String toEmail) {
		this.jndiname = jndiname;
		this.toName = toName;
		this.toEmail = toEmail;
	}

	public String getJndiname() {
		return jndiname;
	}

	public void setJndiname(String jndiname) {
		this.jndiname = jndiname;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

}
