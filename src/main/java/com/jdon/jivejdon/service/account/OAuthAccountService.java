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
package com.jdon.jivejdon.service;

import net.oauth.OAuthAccessor;

import com.jdon.jivejdon.manager.account.sina.AccessToken;
import com.jdon.jivejdon.manager.weibo.UserConnectorAuth;
import com.jdon.jivejdon.model.Account;
import com.tencent.weibo.oauthv2.OAuthV2;

public interface OAuthAccountService {

	public Account saveSina(AccessToken accessToken);

	public Account saveTecent(OAuthV2 accessToken);

	public Account saveGoogle(OAuthAccessor accessToken);

	public String createPassword(String uid);

	void saveWeiboAuth(UserConnectorAuth userConnectorAuth);
}