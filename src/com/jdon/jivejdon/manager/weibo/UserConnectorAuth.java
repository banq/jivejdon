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
package com.jdon.jivejdon.manager.weibo;

import java.io.Serializable;

import com.jdon.util.StringUtil;

public class UserConnectorAuth {

	private final String userId;
	private final String type;
	private final Serializable accessToken;

	private long expireTime;

	private String connuser;
	private String connpasswd;

	public UserConnectorAuth(String userId, String type, Serializable accessToken) {
		this.userId = userId;
		this.type = type;
		this.accessToken = accessToken;
	}

	public UserConnectorAuth(String userId, String type, Serializable accessToken, long expireTime) {
		this.userId = userId;
		this.type = type;
		this.accessToken = accessToken;
		this.expireTime = expireTime;
	}

	public Serializable getAccessToken() {
		return accessToken;
	}

	public String getType() {
		return type;
	}

	public String getConnuser() {
		return connuser;
	}

	public void setConnuser(String connuser) {
		this.connuser = connuser;
	}

	public String getConnpasswd() {
		return connpasswd;
	}

	public void setConnpasswd(String connpasswd) {
		this.connpasswd = connpasswd;
	}

	public void setConnpasswdDecode(String connpasswd) {
		if (this.connpasswd == null)
			return;
		this.connpasswd = StringUtil.decodeString(connpasswd);
	}

	public String getConnpasswdEncode() {
		if (this.connpasswd == null)
			return null;
		return StringUtil.encodeString(this.connpasswd);

	}

	public String getUserId() {
		return userId;
	}

	public boolean isEmpty() {
		if (this.accessToken == null)
			return true;
		else
			return false;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

}
