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

public class SubscriptionEmailParams {

	private String jndiname;

	private String fromEmail;

	public SubscriptionEmailParams(String jndiname, String fromEmail) {
		this.jndiname = jndiname;
		this.fromEmail = fromEmail;
	}

	public String getJndiname() {
		return jndiname;
	}

	public void setJndiname(String jndiname) {
		this.jndiname = jndiname;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

}
