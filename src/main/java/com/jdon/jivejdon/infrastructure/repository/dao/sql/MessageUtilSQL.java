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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.infrastructure.repository.builder.MessageInitFactory;

@Component()
public class MessageUtilSQL {
	private final static Logger logger = LogManager.getLogger(MessageUtilSQL.class);

	protected JdbcTempSource jdbcTempSource;

	public MessageUtilSQL(JdbcTempSource jdbcTempSource) {
		super();
		this.jdbcTempSource = jdbcTempSource;
	}

	public boolean isRoot(Long messageId) {
		boolean isRoot = false;
		String SQL = "SELECT messageID from jiveMessage WHERE parentMessageID IS NULL AND messageID=?";
		List queryParams = new ArrayList();
		queryParams.add(messageId);
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			if (list.size() > 0)
				isRoot = true;
		} catch (Exception e) {
			logger.error("isRoot error:" + e + " messageId=" + messageId);
		}
		return isRoot;
	}

}
