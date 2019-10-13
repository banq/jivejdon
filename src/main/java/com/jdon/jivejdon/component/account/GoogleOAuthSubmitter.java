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

import static net.oauth.OAuth.HMAC_SHA1;
import static net.oauth.OAuth.OAUTH_SIGNATURE_METHOD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.ParameterStyle;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient3.HttpClient3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weibo4j.org.json.JSONObject;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.manager.weibo.UserConnectorAuth;
import com.jdon.jivejdon.model.account.OAuthUserVO;
import com.jdon.jivejdon.repository.acccount.Userconnector;
import com.jdon.util.UtilValidate;

@Component("googleOAuthSubmitter")
public class GoogleOAuthSubmitter {
	private final static Logger logger = LogManager.getLogger(GoogleOAuthSubmitter.class);

	public static final OAuthClient CLIENT = new OAuthClient(new HttpClient3());

	protected Userconnector userconnector;

	protected final GoolgeOAuthParamVO goolgeOAuthParamVO;

	public GoogleOAuthSubmitter(Userconnector userconnector, GoolgeOAuthParamVO goolgeOAuthParamVO) {
		super();
		this.userconnector = userconnector;
		this.goolgeOAuthParamVO = goolgeOAuthParamVO;
	}

	public void saveSinaWeiboAuth(UserConnectorAuth userConnectorAuth) {
		if (userConnectorAuth.getAccessToken() != null) {
			userconnector.saveUserConnectorAuth(userConnectorAuth);
		}

	}

	public OAuthAccessor request(String backUrl) {
		OAuthConsumer consumer = new OAuthConsumer(backUrl, goolgeOAuthParamVO.CONSUMER_KEY, goolgeOAuthParamVO.CONSUMER_SECRET,
				goolgeOAuthParamVO.oAuthServiceProvider);
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		try {
			List<OAuth.Parameter> parameters = OAuth.newList(OAuth.OAUTH_CALLBACK, backUrl);
			parameters.add(new OAuth.Parameter("scope", goolgeOAuthParamVO.scope));
			CLIENT.getRequestTokenResponse(accessor, null, parameters);
		} catch (Exception e) {
			logger.error("request error:" + e);
		}
		return accessor;
	}

	public OAuthAccessor requstAccessToken(OAuthAccessor accessor, String oauthVerifier) throws Exception {
		OAuthMessage result = null;
		try {
			List<OAuth.Parameter> parameters = OAuth.newList(OAuth.OAUTH_VERIFIER, oauthVerifier);
			result = CLIENT.getAccessToken(accessor, null, parameters);
		} catch (Exception e) {
			OAuthProblemException problem = new OAuthProblemException(OAuth.Problems.PARAMETER_ABSENT);
			problem.setParameter(OAuth.Problems.OAUTH_PARAMETERS_ABSENT, OAuth.OAUTH_TOKEN);
			problem.getParameters().putAll(result.getDump());
			logger.error("request error:" + problem.getMessage());
			throw problem;
		}
		return accessor;
	}

	public OAuthUserVO getUserInfo(OAuthAccessor accessor) throws Exception {
		OAuthUserVO weiboUser = null;
		InputStream in = null;
		try {
			accessor.consumer.setProperty(OAUTH_SIGNATURE_METHOD, HMAC_SHA1);
			OAuthMessage message = accessor.newRequestMessage(OAuthMessage.GET, goolgeOAuthParamVO.userInfo, null);
			OAuthMessage result = CLIENT.invoke(message, ParameterStyle.AUTHORIZATION_HEADER);

			in = result.getBodyAsStream();
			String jsonStr = getString(new InputStreamReader(in, Charset.forName("UTF-8")));
			JSONObject json = new JSONObject(jsonStr);
			String id = json.getString("id");
			String atName = json.getString("name");
			String description = json.getString("name");
			String url = "";
			if (!UtilValidate.isEmpty(json.getString("link")))
				url = json.getString("link");// t.qq.com/name
			String profileurl = "";
			if (!UtilValidate.isEmpty(json.getString("picture")))
				profileurl = json.getString("picture");
			weiboUser = new OAuthUserVO(id, atName, description, url, profileurl);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (in != null)
				in.close();
		}
		return weiboUser;

	}

	public static String getString(Reader in) throws IOException {
		StringBuilder s = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(in);
			String str = null;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
				s.append(str);
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			in.close();
		}
		return s.toString();
	}
}
