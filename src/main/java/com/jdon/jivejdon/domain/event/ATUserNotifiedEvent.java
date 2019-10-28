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
package com.jdon.jivejdon.domain.event;

import java.util.Collection;

import com.jdon.jivejdon.domain.model.account.Account;

public class ATUserNotifiedEvent {

	private final Account fromAccount;

	private final Collection<String> toUsernames;

	private final String subject;

	private final Long messageIdId;

	public ATUserNotifiedEvent(Account fromAccount, Collection<String> toUsernames, String subject, Long messageIdId) {
		super();
		this.fromAccount = fromAccount;
		this.toUsernames = toUsernames;
		this.subject = subject;
		this.messageIdId = messageIdId;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public Collection<String> getToUsernames() {
		return toUsernames;
	}

	public String getSubject() {
		return subject;
	}

	public Long getMessageIdId() {
		return messageIdId;
	}

}
