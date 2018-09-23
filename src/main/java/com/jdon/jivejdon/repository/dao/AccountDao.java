/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.repository.dao;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Account;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
public interface AccountDao {
	/**
	 * this for login
	 * 
	 * @param username
	 * @param password
	 * @return Account or null
	 */
	Account getAccount(String username, String password);

	/**
	 * this for service, not open
	 * 
	 * @param userId
	 * @return account
	 */
	Account getAccount(String userId);

	Long fetchAccountByName(String username);

	Long fetchAccountByEmail(String email);

	PageIterator getAccountByNameLike(String username, int start, int count);

	void createAccount(Account account) throws Exception;

	void updateAccount(Account account) throws Exception;

	void deleteAccount(Account account) throws Exception;

	PageIterator getAccounts(int start, int count);

	void updateAccountEmailValidate(Account account) throws Exception;

}
