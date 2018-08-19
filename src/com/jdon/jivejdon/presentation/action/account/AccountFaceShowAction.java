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
package com.jdon.jivejdon.presentation.action.account;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.presentation.action.UploadShowAction;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.util.UtilValidate;

public class AccountFaceShowAction extends UploadShowAction {

	protected UploadFile getUploadFile(String oid, String id) {
		if (UtilValidate.isEmpty(oid))
			return null;
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", this.servlet.getServletContext());
		Account account = accountService.getAccount(Long.parseLong(oid));
		if (account.isAnonymous())
			return null;
		return account.getAttachment().getUploadFile();
	}
}
