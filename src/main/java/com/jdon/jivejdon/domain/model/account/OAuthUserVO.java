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
package com.jdon.jivejdon.domain.model.account;

import com.jdon.jivejdon.spi.component.weibo.UserConnectorAuth;

public class OAuthUserVO {

	private final String oAuthUserId;

	private final String nickname;

	private final String description;

	private final String url; // 用户博客地址

	private final String profileImageUrl; // 自定义图像

	private UserConnectorAuth userConnectorAuth;

	public OAuthUserVO(String oAuthUserId, String nickname, String description, String url, String profileImageUrl) {
		super();
		this.oAuthUserId = oAuthUserId;
		this.nickname = nickname;
		this.description = description;
		this.url = url;
		this.profileImageUrl = profileImageUrl;
	}

	public String getOAuthUserId() {
		return oAuthUserId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public UserConnectorAuth getUserConnectorAuth() {
		return userConnectorAuth;
	}

	public void setUserConnectorAuth(UserConnectorAuth userConnectorAuth) {
		this.userConnectorAuth = userConnectorAuth;
	}

}
