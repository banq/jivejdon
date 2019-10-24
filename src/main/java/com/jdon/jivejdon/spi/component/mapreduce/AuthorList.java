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
package com.jdon.jivejdon.infrastructure.component.mapreduce;

import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.api.account.AccountService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class AuthorList {

	public final static int AuthorsListMAXSize = 15;

	private final ConcurrentHashMap<Long, Integer> authors;
	private final AccountService accountService;


	public AuthorList(AccountService accountService) {
		this.accountService = accountService;
		this.authors = new ConcurrentHashMap();
	}

	public void clear() {
		authors.clear();
	}

	public void addAuthor(Account account) {
		if (account.isAnonymous())
			return;
		authors.put(account.getUserIdLong(), account.getMessageCount());

	}


	public Collection<Account> getAuthors() {

		TreeMap<Long, Integer> sorted_map = createTreeMap();
		sorted_map.putAll(authors);

		List newAcounts = new ArrayList();
		int i = 0;
		for (Long userId : sorted_map.keySet()) {
			newAcounts.add(accountService.getAccount(userId));
			if (i > AuthorsListMAXSize) {
				break;
			}
			i++;

		}
		return Collections.unmodifiableList(newAcounts);

	}

	public TreeMap<Long, Integer> createTreeMap() {
		return new TreeMap<Long, Integer>(new Comparator<Long>() {
			public int compare(Long userId1, Long userId2) {
				if (userId1.longValue() == userId1.longValue())
					return 0;
				Account account1 = accountService.getAccount(userId1);
				Account account2 = accountService.getAccount(userId2);
				if (account1.getMessageCount() > account2.getMessageCount()) {
					return -1; // returning the first object
				} else if (account1.getMessageCount() < account2.getMessageCount()) {
					return 1;
				} else {
					if (userId1.longValue() > userId2.longValue())
						return -1;
					else
						return 1;
				}
			}
		});
	}
}
