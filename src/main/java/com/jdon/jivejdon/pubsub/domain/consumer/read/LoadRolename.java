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
package com.jdon.jivejdon.pubsub.domain.consumer.read;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.repository.dao.sql.AccountSSOSql;
import com.jdon.util.UtilValidate;

@Consumer("rolename")
public class LoadRolename implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(LoadRolename.class);

	private AccountSSOSql accountSSOSql;

	public LoadRolename(AccountSSOSql accountSSOSql) {
		super();
		this.accountSSOSql = accountSSOSql;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {

		String userId = (String) event.getDomainMessage().getEventSource();
		String rolename = this.accountSSOSql.getRoleNameByUserId(userId.toString());
		if (UtilValidate.isEmpty(rolename)) {
			logger.error("role is null, userid=" + userId);
		}
		event.getDomainMessage().setEventResult(rolename);
	}

}
