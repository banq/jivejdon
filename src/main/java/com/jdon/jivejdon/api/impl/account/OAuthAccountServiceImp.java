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
package com.jdon.jivejdon.api.imp.account;

import java.util.ArrayList;
import java.util.List;

import net.oauth.OAuthAccessor;

import com.jdon.annotation.Service;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.spi.component.account.GoogleOAuthSubmitter;
import com.jdon.jivejdon.spi.component.account.GoolgeOAuthParamVO;
import com.jdon.jivejdon.spi.component.account.SinaOAuthSubmitter;
import com.jdon.jivejdon.spi.component.account.sina.AccessToken;
import com.jdon.jivejdon.spi.component.weibo.TecentWeiboSubmitter;
import com.jdon.jivejdon.spi.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.spi.component.weibo.WeiboTransferParamVO;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.account.OAuthUserVO;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountRepository;
import com.jdon.jivejdon.infrastructure.repository.property.UploadRepository;
import com.jdon.jivejdon.infrastructure.repository.acccount.Userconnector;
import com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.account.OAuthAccountService;
import com.jdon.jivejdon.api.util.JtaTransactionUtil;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import com.tencent.weibo.oauthv2.OAuthV2;

@Service("oAuthAccountService")
public class OAuthAccountServiceImp implements OAuthAccountService {
	private final static String module = OAuthAccountServiceImp.class.getName();

	private final SequenceDao sequenceDao;

	private final Userconnector userconnector;

	private final AccountFactory accountFactory;

	private final SinaOAuthSubmitter sinaOAuthSubmitter;

	private final WeiboTransferParamVO weiboTransferParamVO;

	private final TecentWeiboSubmitter tecentWeiboSubmitter;

	private final GoogleOAuthSubmitter googleOAuthSubmitter;

	protected final AccountRepository accountRepository;

	protected final AccountService accountService;

	protected final UploadRepository uploadRepository;

	protected JtaTransactionUtil jtaTransactionUtil;

	public OAuthAccountServiceImp(SinaOAuthSubmitter sinaWeiboSubmitter, SequenceDao sequenceDao, Userconnector userconnector,
			AccountFactory accountFactory, WeiboTransferParamVO weiboTransferParamVO, AccountRepository accountRepository,
			TecentWeiboSubmitter tecentWeiboSubmitter, GoogleOAuthSubmitter googleOAuthSubmitter, AccountService accountService,
			UploadRepository uploadRepository, JtaTransactionUtil jtaTransactionUtil) {
		super();
		this.sequenceDao = sequenceDao;
		this.userconnector = userconnector;
		this.sinaOAuthSubmitter = sinaWeiboSubmitter;
		this.accountFactory = accountFactory;
		this.weiboTransferParamVO = weiboTransferParamVO;
		this.accountRepository = accountRepository;
		this.tecentWeiboSubmitter = tecentWeiboSubmitter;
		this.googleOAuthSubmitter = googleOAuthSubmitter;
		this.accountService = accountService;
		this.uploadRepository = uploadRepository;
		this.jtaTransactionUtil = jtaTransactionUtil;
	}

	public Account saveSina(AccessToken accessToken) {
		if (accessToken == null)
			return null;

		Account account = null;
		try {
			OAuthUserVO oAuthUserVO = sinaOAuthSubmitter.getUserInfo(accessToken);
			if (oAuthUserVO == null) {
				return null;
			}
			boolean isNew = false;
			String userId = userconnector.getUserId(oAuthUserVO.getOAuthUserId());
			if (userId == null) {
				// not exist, create new
				Long userIDInt = sequenceDao.getNextId(Constants.USER);
				Debug.logVerbose("new userIDInt =" + userIDInt, module);
				userId = Long.toString(userIDInt);
				isNew = true;
			}

			UserConnectorAuth userConnectorAuth = new UserConnectorAuth(userId, SubscriptionInitFactory.SINAWEIBO, accessToken);
			userConnectorAuth.setExpireTime(System.currentTimeMillis() + Long.parseLong(accessToken.getExpireIn()) * 1000);
			oAuthUserVO.setUserConnectorAuth(userConnectorAuth);

			jtaTransactionUtil.beginTransaction();
			userconnector.saveUserConnectorAuth(userConnectorAuth);
			userconnector.saveOAuthUserVO(oAuthUserVO);

			// if (isNew) {
			Account accountnew = transferSina(oAuthUserVO);
			saveAccount(accountnew);
			saveSinaAccountProfile(accountnew, oAuthUserVO);
			saveProfileImg(oAuthUserVO, "jpg");
			// }
			jtaTransactionUtil.commitTransaction();
			account = accountFactory.getFullAccount(userId);
		} catch (Exception e) {
			Debug.logError("saveSina AccessToken error:" + e, module);
			jtaTransactionUtil.rollback();
		}
		return account;
	}

	private void saveAccount(Account accountnew) throws Exception {
		try {
			Account accountOld = accountFactory.getFullAccountForUsername(accountnew.getUsername());
			if (accountOld != null && !accountOld.isAnonymous()) {
				accountRepository.deleteAccount(accountOld);
			}
			accountRepository.createAccount(accountnew);
		} catch (Exception e) {
			Debug.logError("saveAccount:" + e, module);
			throw new Exception(e);
		}
	}

	/**
	 * not save image into jivejdon database, just display it. only save
	 * infomation include image url.
	 * 
	 * @param weiboUser
	 * @param imageType
	 */
	private void saveProfileImg(OAuthUserVO weiboUser, String imageType) {
		if (UtilValidate.isEmpty(weiboUser.getProfileImageUrl()))
			return;
		try {
			// URL url = new URL(weiboUser.getProfileImageUrl());
			// BufferedImage image = ImageIO.read(url.openStream());
			// image = ImageUtils.resizeImage(image, imageType, 120, 140);
			// byte[] datas = ImageUtils.getImageByteArray(image);
			UploadFile uf = new UploadFile();
			uf.setParentId(weiboUser.getUserConnectorAuth().getUserId());
			uf.setContentType("image/" + imageType);
			// uf.setData(datas);
			uf.setName(weiboUser.getNickname());
			uf.setDescription(weiboUser.getProfileImageUrl());

			uploadRepository.saveUpload(uf);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void saveSinaAccountProfile(Account accountnew, OAuthUserVO weiboUser) throws Exception {

		List propertys = new ArrayList();
		Property property = new Property();
		property.setName(weiboTransferParamVO.SINA_URL_name);
		String weiboUrl = "<a href='" + weiboTransferParamVO.SINA_URL_Prefix + weiboUser.getUrl() + "'>@" + weiboUser.getNickname() + "</a>";
		property.setValue(weiboUrl);
		propertys.add(property);

		property = new Property();
		property.setName(weiboTransferParamVO.SINA_DES_name);
		property.setValue(weiboUser.getDescription());
		propertys.add(property);

		accountService.saveUserpropertys(accountnew.getUserId(), propertys);

	}

	public Account transferSina(OAuthUserVO oAuthUser) {
		Account account = new Account();
		account.setUserId(oAuthUser.getUserConnectorAuth().getUserId());
		String oAuthuserId = oAuthUser.getOAuthUserId();
		if (oAuthuserId.length() > 5)
			oAuthuserId = oAuthuserId.substring(oAuthuserId.length() - 5, oAuthuserId.length());
		account.setUsername(weiboTransferParamVO.SINA_NIKCNAME_Prefix + oAuthuserId);
		String password = createPassword(oAuthUser.getOAuthUserId());
		account.setPassword(password);
		account.setEmail(oAuthuserId + weiboTransferParamVO.SINA_EMAIL_URL_Suffix);
		account.setEmailVisible(false);
		// different with normal user :Role.user
		account.setRoleName(Role.SINAUSER);
		return account;
	}

	public String createPassword(String uid) {
		if (uid.length() > 4)
			return uid.substring(0, 4);
		else
			return uid;
	}

	public Account saveTecent(OAuthV2 oAuthV2) {
		if (oAuthV2 == null)
			return null;

		Account account = null;
		try {
			OAuthUserVO oAuthUserVO = tecentWeiboSubmitter.getUserInfo(oAuthV2);
			if (oAuthUserVO == null)
				return account;

			boolean isNew = false;
			String userId = userconnector.getUserId(oAuthUserVO.getOAuthUserId());
			if (userId == null) {
				// not exist, create new
				Long userIDInt = sequenceDao.getNextId(Constants.USER);
				Debug.logVerbose("new userIDInt =" + userIDInt, module);
				userId = Long.toString(userIDInt);
				isNew = true;
			}

			UserConnectorAuth userConnectorAuth = new UserConnectorAuth(userId, SubscriptionInitFactory.TECENTWEIBO, oAuthV2);
			oAuthUserVO.setUserConnectorAuth(userConnectorAuth);

			jtaTransactionUtil.beginTransaction();
			userconnector.saveUserConnectorAuth(userConnectorAuth);
			userconnector.saveOAuthUserVO(oAuthUserVO);

			// if (isNew) {
			Account accountnew = transferTecent(oAuthUserVO);
			saveAccount(accountnew);
			saveTecentAccountProfile(accountnew, oAuthUserVO);
			saveProfileImg(oAuthUserVO, "png");
			// }
			jtaTransactionUtil.commitTransaction();
			account = accountFactory.getFullAccount(userId);
		} catch (Exception e) {
			Debug.logError("saveTecent AccessToken error:" + e, module);
			jtaTransactionUtil.rollback();
		}
		return account;

	}

	private void saveTecentAccountProfile(Account accountnew, OAuthUserVO weiboUser) {
		List propertys = new ArrayList();
		Property property = new Property();
		property.setName(weiboTransferParamVO.TECENT_URL_name);
		String weiboUrl = "<a href='" + weiboTransferParamVO.TECENT_URL_Prefix + weiboUser.getUrl() + "'>@" + weiboUser.getNickname() + "</a>";
		property.setValue(weiboUrl);
		propertys.add(property);

		property = new Property();
		property.setName(weiboTransferParamVO.TECENT_DES_name);
		property.setValue(weiboUser.getDescription());
		propertys.add(property);

		accountService.saveUserpropertys(accountnew.getUserId(), propertys);
	}

	public Account transferTecent(OAuthUserVO weiboUser) {
		Account account = new Account();
		account.setUserId(weiboUser.getUserConnectorAuth().getUserId());
		String weibouesr = weiboUser.getOAuthUserId();
		if (weibouesr.length() > 5)
			weibouesr = weibouesr.substring(weibouesr.length() - 5, weibouesr.length());

		account.setUsername(weiboTransferParamVO.TECENT_NIKCNAME_Prefix + weibouesr);
		OAuthV2 at = (OAuthV2) weiboUser.getUserConnectorAuth().getAccessToken();
		String password = createPassword(at.getAccessToken());
		account.setPassword(password);
		account.setEmail(weibouesr + weiboTransferParamVO.TECENT_EMAIL_URL_Suffix);
		account.setEmailVisible(false);
		// different with normal user :Role.user
		account.setRoleName(Role.TECENTUSER);
		return account;
	}

	@Override
	public Account saveGoogle(OAuthAccessor accessToken) {
		if (accessToken == null)
			return null;

		Account account = null;
		try {
			OAuthUserVO oAuthUserVO = googleOAuthSubmitter.getUserInfo(accessToken);
			if (oAuthUserVO == null)
				return account;

			boolean isNew = false;
			String userId = userconnector.getUserId(oAuthUserVO.getOAuthUserId());
			if (userId == null) {
				// not exist, create new
				Long userIDInt = sequenceDao.getNextId(Constants.USER);
				Debug.logVerbose("new userIDInt =" + userIDInt, module);
				userId = Long.toString(userIDInt);
				isNew = true;
			}

			UserConnectorAuth userConnectorAuth = new UserConnectorAuth(userId, "google", accessToken);
			oAuthUserVO.setUserConnectorAuth(userConnectorAuth);

			jtaTransactionUtil.beginTransaction();
			userconnector.saveUserConnectorAuth(userConnectorAuth);
			userconnector.saveOAuthUserVO(oAuthUserVO);

			// if (isNew) {
			Account accountnew = transferGoogle(oAuthUserVO);
			saveAccount(accountnew);
			// saveProfileImg(oAuthUserVO, "jpg");
			// }
			jtaTransactionUtil.commitTransaction();
			account = accountFactory.getFullAccount(userId);
		} catch (Exception e) {
			Debug.logError("savegoogle AccessToken error:" + e, module);
			jtaTransactionUtil.rollback();
		}
		return account;
	}

	public Account transferGoogle(OAuthUserVO weiboUser) {
		Account account = new Account();
		account.setUserId(weiboUser.getUserConnectorAuth().getUserId());
		String weibouesr = weiboUser.getOAuthUserId();
		if (weibouesr.length() > 5)
			weibouesr = weibouesr.substring(weibouesr.length() - 5, weibouesr.length());
		account.setUsername(GoolgeOAuthParamVO.NIKCNAME_Prefix + weibouesr);
		OAuthAccessor at = (OAuthAccessor) weiboUser.getUserConnectorAuth().getAccessToken();
		String password = createPassword(at.accessToken);
		account.setPassword(password);
		account.setEmail(weibouesr + GoolgeOAuthParamVO.EMAIL_URL_Suffix);
		account.setEmailVisible(false);
		// different with normal user :Role.user
		account.setRoleName(Role.OAuthUSER);
		return account;
	}

	public void saveWeiboAuth(UserConnectorAuth userConnectorAuth) {
		if (userConnectorAuth.getAccessToken() != null) {
			userconnector.saveUserConnectorAuth(userConnectorAuth);
		}

	}
}
