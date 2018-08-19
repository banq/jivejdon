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

import java.net.URI;
import java.util.Scanner;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.account.OAuthUserVO;
import com.jdon.util.UtilValidate;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.utils.QHttpClient;

@Component("tecentWeiboSubmitter")
public class TecentWeiboSubmitter {
	private final static Logger logger = LogManager.getLogger(TecentWeiboSubmitter.class);

	public final TecentWeiboOAuthParamVO weiboOAuthParamVO;

	public TecentWeiboSubmitter(TecentWeiboOAuthParamVO weiboOAuthParamVO) {
		super();
		this.weiboOAuthParamVO = weiboOAuthParamVO;
	}

	public void submitWeibo(String content, UserConnectorAuth userConnectorAuth, String clientip) {
		try {
			OAuthV2 accessToken = (OAuthV2) userConnectorAuth.getAccessToken();
			update(accessToken, content, clientip);
		} catch (Exception e) {
			logger.error("submitWeibo error:" + e);
		}
	}

	public String request(String backUrl) {
		String authURL = "";
		try {

			OAuthV2 oAuth = new OAuthV2(weiboOAuthParamVO.CONSUMER_KEY, weiboOAuthParamVO.CONSUMER_SECRET, backUrl);

			QHttpClient qHttpClient = new QHttpClient(2, 2, 5000, 5000, null, null);
			OAuthV2Client.setQHttpClient(qHttpClient);

			// 获取request token
			try {
				authURL = OAuthV2Client.generateAuthorizationURL(oAuth);
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				qHttpClient.shutdownConnection();
			}

			// 检查是否正确取得request token
			if (oAuth.getStatus() != 0) {
				logger.error("Get generateAuthorizationURL failed!=" + oAuth.getStatus());
				return null;
			}

			return authURL;
		} catch (Exception e) {
			logger.error("request error:" + e);
			return null;
		} finally {
		}
	}

	public OAuthV2 getAccessTokenByCode(String responseData, String backurl) {

		OAuthV2 oAuth = new OAuthV2(weiboOAuthParamVO.CONSUMER_KEY, weiboOAuthParamVO.CONSUMER_SECRET, backurl);

		if (OAuthV2Client.parseAuthorization(responseData, oAuth)) {
			System.out.println("Parse Authorization Information Successfully");
		} else {
			System.out.println("Fail to Parse Authorization Information");
			return null;
		}

		// 检查是否正确取得授权码
		if (oAuth.getStatus() == 2) {
			logger.error("Get Authorization Code failed!");
			return null;
		}

		QHttpClient qHttpClient = new QHttpClient(2, 2, 5000, 5000, null, null);
		OAuthV2Client.setQHttpClient(qHttpClient);

		// 换取access token
		oAuth.setGrantType("authorize_code");
		try {
			OAuthV2Client.accessToken(oAuth);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("OAuthV2Client accessToken:" + e1);
			return null;
		} finally {
			qHttpClient.shutdownConnection();
		}

		// 检查是否正确取得access token
		if (oAuth.getStatus() == 3) {
			logger.error("Get Access Token failed!");
			return null;
		}
		return oAuth;

	}

	public void update(OAuthV2 oAuth, String content, String clientip) {
		QHttpClient qHttpClient = new QHttpClient(2, 2, 5000, 5000, null, null);
		OAuthV2Client.setQHttpClient(qHttpClient);

		try {
			TAPI tAPI = new TAPI(oAuth.getOauthVersion(), qHttpClient);// 根据oAuth配置对应的连接管理器

			String format = "xml";
			// 取得返回结果
			tAPI.add(oAuth, format, content, clientip);
		} catch (Exception e) {
			logger.error("update failed!" + e);
		} finally {
			qHttpClient.shutdownConnection();
		}
	}

	public OAuthUserVO getUserInfo(OAuthV2 oAuth) {
		QHttpClient qHttpClient = new QHttpClient(2, 2, 5000, 5000, null, null);
		OAuthV2Client.setQHttpClient(qHttpClient);

		OAuthUserVO weiboUser = null;
		try {
			UserAPI uAPI = new UserAPI(oAuth.getOauthVersion(), qHttpClient);// 根据oAuth配置对应的连接管理器
			String userInfo = uAPI.info(oAuth, "json");
			logger.error("userInfo=" + userInfo);
			JSONObject responseJsonObject = JSONObject.fromObject(userInfo);
			JSONObject json = (JSONObject) responseJsonObject.get("data");
			String id = json.getString("openid");
			String atName = json.getString("nick");
			String description = json.getString("introduction");
			String url = json.getString("name");// t.qq.com/name
			String profileurl = "";
			if (!UtilValidate.isEmpty(json.getString("head")))
				profileurl = json.getString("head") + "/100"; // 腾讯头像必须提供大小才能调用
			weiboUser = new OAuthUserVO(id, atName, description, url, profileurl);
			uAPI.shutdownConnection();
		} catch (Exception e) {
			logger.error("getUserInfo failed!" + e + " oAuth=" + oAuth.getAccessToken());
		} finally {
			qHttpClient.shutdownConnection();
		}
		return weiboUser;

	}

	public static void main(String[] args) {
		TecentWeiboSubmitter tecentWeiboSubmitter = new TecentWeiboSubmitter(new TecentWeiboOAuthParamVO("801210431",
				"ab5aa7fc9eb0c29957aa2cf605d8aa89"));
		String backurl = "http://127.0.0.1:8080/account/oauth/tecentUserCallBackAction.shtml";
		String authorizationUrl = tecentWeiboSubmitter.request(backurl);

		// 调用外部浏览器
		if (!java.awt.Desktop.isDesktopSupported()) {

			System.err.println("Desktop is not supported (fatal)");
			System.exit(1);
		}
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		if (desktop == null || !desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {

			System.err.println("Desktop doesn't support the browse action (fatal)");
			System.exit(1);
		}
		try {
			desktop.browse(new URI(authorizationUrl));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Input the authorization information (eg: code=CODE&openid=OPENID&openkey=OPENKEY) :");
		Scanner in = new Scanner(System.in);
		String responseData = in.nextLine();
		in.close();

		backurl = "http://127.0.0.1:8080/account/oauth/tecentUserCallBackAction.shtml";
		OAuthV2 oAuthV2 = tecentWeiboSubmitter.getAccessTokenByCode(responseData, backurl);

		OAuthUserVO oAuthUserVO = tecentWeiboSubmitter.getUserInfo(oAuthV2);
		System.out.print("nick:" + oAuthV2.getAccessToken());

		tecentWeiboSubmitter.update(oAuthV2, "测试发表文字微博", "127.0.0.1");

	}

}
