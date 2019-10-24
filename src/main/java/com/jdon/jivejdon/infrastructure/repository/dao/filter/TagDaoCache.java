/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.infrastructure.repository.dao.filter;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.TagDaoSql;
import com.jdon.jivejdon.util.ContainerUtil;

@Introduce("modelCache")
public class TagDaoCache extends TagDaoSql {
	private ContainerUtil containerUtil;

	public TagDaoCache(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, SequenceDao sequenceDao) {
		super(jdbcTempSource, containerUtil, sequenceDao);
		this.containerUtil = containerUtil;
	}

	@Around()
	public ThreadTag getThreadTag(Long tagID) {
		ThreadTag threadTag = super.getThreadTag(tagID);
		return threadTag;
	}

	public void updateThreadTag(ThreadTag threadTag) throws Exception {
		super.updateThreadTag(threadTag);
		containerUtil.clearCache(threadTag.getTagID());
	}

}
