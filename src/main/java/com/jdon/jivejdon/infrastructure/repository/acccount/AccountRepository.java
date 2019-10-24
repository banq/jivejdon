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
package com.jdon.jivejdon.infrastructure.repository.acccount;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;

public interface AccountRepository {

	PageIterator getAccountByNameLike(String username, int start, int count);

	void createAccount(Account account) throws Exception;

	void updateAccount(Account account) throws Exception;

	void deleteAccount(Account account) throws Exception;

	PageIterator getAccounts(int start, int count);

	void updateAccountEmailValidate(Account account) throws Exception;

	PasswordassitVO getPasswordassit(String userId);

	void insertPasswordassit(PasswordassitVO passwordassitVO) throws Exception;

	void updatePasswordassit(PasswordassitVO passwordassitVO) throws Exception;

	void deletePasswordassit(String userId) throws Exception;
}
