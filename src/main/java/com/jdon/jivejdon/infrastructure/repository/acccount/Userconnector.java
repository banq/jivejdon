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
package com.jdon.jivejdon.repository.acccount;

import com.jdon.jivejdon.infrastructure.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.domain.model.account.OAuthUserVO;

public interface Userconnector {

	public abstract void saveUserConnectorAuth(UserConnectorAuth userConnectorAuth);

	public abstract void removeUserConnectorAuth(String userId, String type);

	public abstract UserConnectorAuth loadUserConnectorAuth(String userId, String type);

	public String getUserId(String weiboUserId);

	public void saveOAuthUserVO(OAuthUserVO weiboUserVO);

	public void deleteWeiboUserVO(String weiboUserId);

}