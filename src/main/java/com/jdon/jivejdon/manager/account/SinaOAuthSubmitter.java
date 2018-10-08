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
package com.jdon.jivejdon.manager.account;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.manager.account.sina.AccessToken;
import com.jdon.jivejdon.manager.weibo.UserConnectorAuth;
import com.jdon.jivejdon.model.account.OAuthUserVO;
import com.jdon.jivejdon.repository.Userconnector;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weibo4j.Oauth;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.HttpClient;
import weibo4j.http.Response;
import weibo4j.model.PostParameter;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component("sinaOAuthSubmitter")
public class SinaOAuthSubmitter {
	private final static Logger logger = LogManager.getLogger(SinaOAuthSubmitter.class);

	public final SinaOAuthParamVO oAuthParamVO;

	protected Userconnector userconnector;

	protected HttpClient client = new HttpClient();

	public SinaOAuthSubmitter(SinaOAuthParamVO oAuthParamVO, Userconnector userconnector) {
		super();
		this.oAuthParamVO = oAuthParamVO;
		this.userconnector = userconnector;

	}

	public static void main(String[] args) throws WeiboException, IOException {
		Oauth oauth = new Oauth();
		BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
		System.out.println(oauth.authorize("code"));
		System.out.print("Hit enter when it's done.[Enter]:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();
		Log.logInfo("code: " + code);
		try {
			System.out.println(oauth.getAccessTokenByCode(code));
		} catch (WeiboException e) {
			if (401 == e.getStatusCode()) {
				Log.logInfo("Unable to get the access token.");
			} else {
				e.printStackTrace();
			}
		}
	}

	public void submitWeibo(String content, UserConnectorAuth userConnectorAuth) {
		try {
			AccessToken accessToken = (AccessToken) userConnectorAuth.getAccessToken();
			update(accessToken, content);
		} catch (Exception e) {
			logger.error("submitWeibo error:" + e);
		}
	}

	public void saveSinaWeiboAuth(UserConnectorAuth userConnectorAuth) {
		if (userConnectorAuth.getAccessToken() != null) {
			userconnector.saveUserConnectorAuth(userConnectorAuth);
		}

	}

	public String authorizeURL(String backurl, String response_type) {
		return oAuthParamVO.authorizeURL + "?client_id=" + oAuthParamVO.CONSUMER_KEY +
				"&redirect_uri=" + backurl + "&response_type=" + response_type;

	}

	public AccessToken getAccessTokenByCode(String code, String backurl) throws WeiboException {
		Response rs = client.post(oAuthParamVO.accessTokenURL, new PostParameter[]{
						new PostParameter("client_id", oAuthParamVO.CONSUMER_KEY), new
						PostParameter("client_secret", oAuthParamVO.CONSUMER_SECRET),
						new PostParameter("grant_type", "authorization_code"), new PostParameter
						("code", code), new PostParameter("redirect_uri", backurl)},
				false, null);
		return new AccessToken(rs);
	}

	public void update(AccessToken access, String content) throws Exception {
		try {
			content = content + " http://";
			Response rs = client.post(oAuthParamVO.baseURL + "statuses/share" +
							".json",
					new PostParameter[]{new PostParameter(
							"status", content)}, access.getAccessToken());
			Status status = new Status(rs);

			if (status != null && UtilValidate.isEmpty(status.getText())) {
				logger.error("update weibo error :" + status + "\n " +
						"content:" + content);
			} else
				logger.debug("Successfully updated the status to [" + status.getText() + "].");

		} catch (Exception e) {
			logger.error("update error:" + e + " " + content);
			throw new Exception(e);
		}
	}

	public OAuthUserVO getUserInfo(AccessToken access) throws Exception {
		OAuthUserVO weiboUser = null;
		try {
			Response rs = client.get(oAuthParamVO.baseURL + "users/show.json",
					new PostParameter[]{new PostParameter("uid", access.getUid())}, access
							.getAccessToken());
			User user = new User(rs);

			String userId = access.getUid();
			String atName = user.getScreenName();
			String userDomainURL = user.getUserDomain();
			String profileUrl = "";
			if (user.getProfileImageURL() != null)
				profileUrl = user.getProfileImageURL().toString();
			weiboUser = new OAuthUserVO(userId, atName, user.getDescription(), userDomainURL,
					profileUrl);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return weiboUser;

	}

}
