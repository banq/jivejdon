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
package com.jdon.jivejdon.infrastructure.component.account;

import net.oauth.OAuthServiceProvider;

public class GoolgeOAuthParamVO {

	public final static String NIKCNAME_Prefix = "googleID";

	public final static String EMAIL_URL_Suffix = "gmail.com";

	public final String CONSUMER_KEY;
	public final String CONSUMER_SECRET;

	public final OAuthServiceProvider oAuthServiceProvider;

	public final String scope;

	public final String userInfo;

	public GoolgeOAuthParamVO(String cONSUMER_KEY, String cONSUMER_SECRET, String requestTokenURL, String userAuthorizationURL,
			String accessTokenURL, String scope, String userInfo) {
		super();
		CONSUMER_KEY = cONSUMER_KEY;
		CONSUMER_SECRET = cONSUMER_SECRET;
		this.oAuthServiceProvider = new OAuthServiceProvider(requestTokenURL, userAuthorizationURL, accessTokenURL);
		this.scope = scope;
		this.userInfo = userInfo;
	}

}
