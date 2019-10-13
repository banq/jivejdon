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
package com.jdon.jivejdon.component.account.sina;

import weibo4j.http.Response;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import java.io.Serializable;

public class AccessToken extends WeiboResponse implements Serializable {

	private static final long serialVersionUID = 6986530164134648944L;
	private String accessToken;
	private String expireIn;
	private String refreshToken;
	private String uid;

	public AccessToken(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			accessToken = json.getString("access_token");
			expireIn = json.getString("expires_in");
			refreshToken = json.getString("refresh_token");
			uid = json.getString("uid");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
		}
	}

	AccessToken(String res) throws WeiboException, JSONException {
		super();
		JSONObject json = new JSONObject(res);
		accessToken = json.getString("access_token");
		expireIn = json.getString("expires_in");
		refreshToken = json.getString("refresh_token");
		uid = json.getString("uid");
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getExpireIn() {
		return expireIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
		result = prime * result + ((expireIn == null) ? 0 : expireIn.hashCode());
		result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessToken other = (AccessToken) obj;
		if (accessToken == null) {
			if (other.accessToken != null)
				return false;
		} else if (!accessToken.equals(other.accessToken))
			return false;
		if (expireIn == null) {
			if (other.expireIn != null)
				return false;
		} else if (!expireIn.equals(other.expireIn))
			return false;
		if (refreshToken == null) {
			if (other.refreshToken != null)
				return false;
		} else if (!refreshToken.equals(other.refreshToken))
			return false;
		return true;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "AccessToken [" + "accessToken=" + accessToken + ", expireIn=" + expireIn + ", " +
				"refreshToken=" + refreshToken + ",uid=" + uid + "]";
	}

}
