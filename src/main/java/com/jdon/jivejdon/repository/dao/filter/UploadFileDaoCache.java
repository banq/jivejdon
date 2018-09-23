/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.repository.dao.filter;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.repository.dao.sql.UploadFileDaoSql;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * @author banq(http://www.jdon.com)
 * 
 */
@Introduce("modelCache")
public class UploadFileDaoCache extends UploadFileDaoSql {

	public UploadFileDaoCache(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil) {
		super(jdbcTempSource, containerUtil);
	}

	@Around()
	public UploadFile getUploadFile(String objectId) {
		UploadFile uploadFile = super.getUploadFile(objectId);
		return uploadFile;
	}

	public void createUploadFile(UploadFile uploadFile) {
		super.createUploadFile(uploadFile);
		pageIteratorSolver.clearCache();

	}

	public void deleteUploadFile(String objectId) {
		super.deleteUploadFile(objectId);
		pageIteratorSolver.clearCache();

	}

}
