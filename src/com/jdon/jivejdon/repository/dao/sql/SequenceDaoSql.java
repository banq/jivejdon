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
package com.jdon.jivejdon.repository.dao.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compass.core.util.backport.java.util.concurrent.ConcurrentHashMap;

import com.jdon.jivejdon.repository.dao.SequenceDao;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
public class SequenceDaoSql implements SequenceDao {
	private final static Logger logger = LogManager.getLogger(SequenceDaoSql.class);

	private JdbcTempSource jdbcTempSource;

	private int INCREMENT = 15;

	private final Map<Integer, Long> maxIDs = new ConcurrentHashMap();

	private final Map<Integer, Long> currentIDs = new ConcurrentHashMap();

	/**
	 * @param jdbcTempSource
	 */
	public SequenceDaoSql(JdbcTempSource jdbcTempSource) {
		super();
		this.jdbcTempSource = jdbcTempSource;
		
	}
	
	private long  getMaxID(int idType){
		if (!maxIDs.containsKey(idType)){
			maxIDs.put(idType, new Long(01));
		}
		return maxIDs.get(idType).longValue();
		
	}
	
	private long  getCurrentID(int idType){
		if (!currentIDs.containsKey(idType))
			currentIDs.put(idType, new Long(01));
		return currentIDs.get(idType).longValue();
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.dao.SequenceDao#getNextId(java.lang.String)
	 */
	public Long getNextId(int idType) throws SQLException {
		if (!(getCurrentID(idType) < getMaxID(idType))) {
			getNextBlock(idType);
		}
		long currentID = getCurrentID(idType);
		currentID++;
		currentIDs.put(idType, currentID);
		return currentID;
	}

	private synchronized void getNextBlock(int idType) throws SQLException {

		String LOAD_ID = "select id from jiveID where idType = ?";
		List queryParams = new ArrayList();
		queryParams.add(new Integer(idType));

		Long currentID = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_ID);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				currentID = (Long) map.get("id");
			}
			if (currentID == null) {
				throw new SQLException("Error: A null sequence was returned from the database (could not get next " + idType + " sequence).");
			}

			// Increment the id to define our block.
			Long newID = currentID + INCREMENT;

			// The WHERE clause includes the last value of the id. This ensures
			// that an update will occur only if nobody else has performed an
			// update first.

			queryParams.clear();
			String updatesql = "UPDATE jiveID SET id=? WHERE idType=? AND id=?";
			queryParams.add(newID);
			queryParams.add(new Integer(idType));
			queryParams.add(currentID);

			jdbcTempSource.getJdbcTemp().operate(queryParams, updatesql);

			maxIDs.put(idType, new Long(newID));
			currentIDs.put(idType, new Long(currentID));
		} catch (Exception e) {
			logger.error(e);
			throw new SQLException(e.getMessage());
		}
	}

}
