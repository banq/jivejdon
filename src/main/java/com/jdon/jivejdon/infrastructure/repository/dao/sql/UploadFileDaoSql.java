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
package com.jdon.jivejdon.repository.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;
import com.jdon.util.UtilValidate;

/**
 * @author banq(http://www.jdon.com)
 * 
 */
public abstract class UploadFileDaoSql implements com.jdon.jivejdon.repository.dao.UploadFileDao {
	private final static Logger logger = LogManager.getLogger(UploadFileDaoSql.class);

	protected PageIteratorSolver pageIteratorSolver;

	protected JdbcTempSource jdbcTempSource;

	public UploadFileDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.UploadFileDao#getUploadFile(java.lang.String)
	 */
	public UploadFile getUploadFile(String objectId) {
		if (!UtilValidate.isAlphanumeric(objectId))
			return null;
		logger.debug("enter getForum for id:" + objectId);
		String LOAD_SQL = "SELECT objectId, name, description, datas, messageId, size, contentType" + " FROM upload WHERE objectId=?";
		List queryParams = new ArrayList();
		queryParams.add(objectId);
		UploadFile ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = new UploadFile();
				Map map = (Map) iter.next();
				ret.setId((String) map.get("objectId"));
				ret.setName((String) map.get("name"));
				ret.setDescription((String) map.get("description"));
				ret.setData((byte[]) map.get("datas"));
				ret.setParentId((String) map.get("messageId"));
				ret.setSize(((Integer) map.get("size")).intValue());
				ret.setContentType((String) map.get("contentType"));

			}
		} catch (Exception se) {
			logger.error("getUploadFile objectId=" + objectId + se);
		}
		return ret;
	}

	public byte[] loadUploadEntity(String objectId) {
		if (!UtilValidate.isAlphanumeric(objectId))
			return null;

		logger.debug("enter loadUploadEntity for id:" + objectId);
		String LOAD_SQL = "SELECT  datas  FROM upload WHERE objectId=?";
		List queryParams = new ArrayList();
		queryParams.add(objectId);
		byte[] ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = (byte[]) map.get("datas");
			}
		} catch (Exception se) {
			logger.error("getUploadFile objectId=" + objectId + se);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.UploadFileDao#createUploadFile(com.jdon.strutsutil
	 * .file.UploadFile)
	 */
	public void createUploadFile(UploadFile uploadFile) {
		logger.debug("enter createUploadFile uploadId =" + uploadFile.getId());
		try {
			String ADD_SQL = "INSERT INTO upload(objectId, name, description, datas, messageId, size, creationDate, contentType) "
					+ " VALUES (?,?,?,?,?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(uploadFile.getId());
			queryParams.add(uploadFile.getName());
			String description = uploadFile.getDescription();
			if (description != null && description.length() > 10)
				description = description.substring(0, 6);
			queryParams.add(description);
			byte[] datas = uploadFile.getData();
			if (datas == null) {
				logger.warn("upload datas is null!");
			}
			queryParams.add(datas);
			queryParams.add(uploadFile.getParentId());
			queryParams.add(new Integer(uploadFile.getSize()));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			queryParams.add(saveDateTime);

			queryParams.add(uploadFile.getContentType());

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
			clearCache();

		} catch (Exception e) {
			logger.error("createUploadFile uploadId =" + uploadFile.getId() + e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.UploadFileDao#deleteUploadFile(java.lang.String)
	 */
	public void deleteUploadFile(String objectId) {
		if (!UtilValidate.isAlphanumeric(objectId))
			return;

		try {
			String sql = "DELETE FROM upload WHERE objectId=?";
			List queryParams = new ArrayList();
			queryParams.add(objectId);
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
			clearCache();
		} catch (Exception e) {
			logger.error("deleteUploadFile objectId" + objectId + e);
		}

	}

	public void deleteAllUploadFile(String parentId) {
		if (!UtilValidate.isAlphanumeric(parentId))
			return;

		try {
			String sql = "DELETE FROM upload WHERE messageId=?";
			List queryParams = new ArrayList();
			queryParams.add(parentId);
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
			clearCache();
		} catch (Exception e) {
			logger.error("deleteAllUploadFile parentId" + parentId + e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.UploadFileDao#getAdjunct(java.lang.Long, int,
	 * int)
	 */
	public Collection getAdjunctIDs(String messageId) {

		String GET_ALL_ITEMS = "select objectId from upload where messageId = ?";
		Collection params = new ArrayList(1);
		params.add(messageId);
		Collection results = new ArrayList();
		if (!UtilValidate.isAlphanumeric(messageId))
			return results;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				results.add((String) map.get("objectId"));
			}
		} catch (Exception se) {
			logger.error("getAdjunct messageId=" + messageId + se);
		}
		return results;
	}

	public void clearCache() {
		pageIteratorSolver.clearCache();
	}

	public PageIterator getUploadFiles(int start, int count) {
		logger.debug("enter getUploadFiles ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from upload ORDER BY creationDate DESC";

		String GET_ALL_ITEMS = "select objectId from upload ORDER BY creationDate DESC  ";

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "", start, count);

	}

}
