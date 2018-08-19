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
package com.jdon.jivejdon.model.shortmessage;

import java.util.Observable;
import java.util.Observer;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.Account;

public class AccountSMState implements Observer {

	private Account account;

	private volatile DomainMessage countAsyncResult;

	private int newShortMessageCount = -1;

	public AccountSMState(Account account) {
		this.account = account;
	}

	public int getNewShortMessageCount() {
		if (newShortMessageCount == -1 && account.lazyLoaderRole != null) {
			if (countAsyncResult == null) {
				countAsyncResult = account.lazyLoaderRole.loadNewShortMessageCount(account.getUserId());
				return 0;// first time donot return the value;
			} else {
				Object asynResult = countAsyncResult.getEventResult();
				if (asynResult != null) {
					newShortMessageCount = (Integer) asynResult;
					countAsyncResult.clear();
				}
			}
		}
		return newShortMessageCount;
	}

	public void addOneNewMessage(int count) {
		newShortMessageCount = getNewShortMessageCount() + count;
	}

	public void update(Observable obj, Object arg) {
		if (arg == null)
			return;
		newShortMessageCount = getNewShortMessageCount() - 1;
	}

	public void reload() {
		countAsyncResult = account.lazyLoaderRole.loadNewShortMessageCount(account.getUserId());
		newShortMessageCount = -1;
	}

}
