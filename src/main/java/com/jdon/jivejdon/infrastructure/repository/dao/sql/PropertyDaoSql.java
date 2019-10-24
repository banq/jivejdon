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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */

public class PropertyDaoSql implements PropertyDao, Startable {
	private final static Logger logger = LogManager.getLogger(PropertyDaoSql.class);
	private JdbcTempSource jdbcTempSource;

	private Map tables = new HashMap();

	protected PageIteratorSolver pageIteratorSolver;

	/**
	 * @param jdbcTempSource
	 */
	public PropertyDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil) {

		this.jdbcTempSource = jdbcTempSource;
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());

		Property property = new Property();
		property.setName("jiveForumProp");
		property.setValue("forumID");
		tables.put(new Integer(Constants.FORUM), property);

		property = new Property();
		property.setName("jiveThreadProp");
		property.setValue("threadID");
		tables.put(new Integer(Constants.THREAD), property);

		property = new Property();
		property.setName("jiveMessageProp");
		property.setValue("messageID");
		tables.put(new Integer(Constants.MESSAGE), property);

		property = new Property();
		property.setName("jiveUserProp");
		property.setValue("userID");
		tables.put(new Integer(Constants.USER), property);

	}

	public Property getThreadProperty(Long id, String name) {
		Property tproperty = (Property) tables.get(new Integer(Constants.THREAD));
		String LOAD_PROPERTIES = "SELECT name, propValue FROM " + tproperty.getName() + " WHERE name=? and " + tproperty.getValue() + "=?";

		List queryParams = new ArrayList();

		queryParams.add(name);
		queryParams.add(id);

		Property property = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_PROPERTIES);
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				property = new Property();
				Map map = (Map) iter.next();
				property.setName((String) map.get("name"));
				property.setValue((String) map.get("propValue"));
			}
		} catch (Exception e) {
			logger.error("name=" + name + " id=" + id + e);
		}
		return property;
	}

	public Property getMessageProperty(Long id, String name) {
		Property tproperty = (Property) tables.get(new Integer(Constants.MESSAGE));
		String LOAD_PROPERTIES = "SELECT name, propValue FROM " + tproperty.getName() + " WHERE name=? and " + tproperty.getValue() + "=?";

		List queryParams = new ArrayList();

		queryParams.add(name);
		queryParams.add(id);

		Property property = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_PROPERTIES);
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				property = new Property();
				Map map = (Map) iter.next();
				property.setName((String) map.get("name"));
				property.setValue((String) map.get("propValue"));
			}
		} catch (Exception e) {
			logger.error("name=" + name + " id=" + id + e);
		}
		return property;
	}

	public Property getUserProperty(Long userId, String name) {
		Property tproperty = (Property) tables.get(new Integer(Constants.USER));
		String LOAD_PROPERTIES = "SELECT name, propValue FROM " + tproperty.getName() + " WHERE name=? and " + tproperty.getValue() + "=?";

		List queryParams = new ArrayList();

		queryParams.add(name);
		queryParams.add(userId);

		Property property = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_PROPERTIES);
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				property = new Property();
				Map map = (Map) iter.next();
				property.setName((String) map.get("name"));
				property.setValue((String) map.get("propValue"));
			}
		} catch (Exception e) {
			logger.error("name=" + name + " id=" + userId + e);
		}
		return property;
	}

	public Collection getProperties(int type, Long id) {
		Property tproperty = (Property) tables.get(new Integer(type));
		String LOAD_PROPERTIES = "SELECT name, propValue FROM " + tproperty.getName() + " WHERE " + tproperty.getValue() + "=?";

		List queryParams = new ArrayList();
		queryParams.add(id);
		Collection c = new ArrayList();

		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_PROPERTIES);
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Property property = new Property();
				property.setName((String) map.get("name"));
				property.setValue((String) map.get("propValue"));
				c.add(property);
			}
		} catch (Exception e) {
			logger.error("type=" + type + " id=" + id + e);
		}
		return c;

	}

	public void saveProperties(int type, Long id, Collection c) {
		if (c == null)
			return;
		Property tproperty = (Property) tables.get(new Integer(type));
		try {
			Iterator iter = c.iterator();
			List queryParams = new ArrayList();
			while (iter.hasNext()) {
				Property property = (Property) iter.next();
				if (!UtilValidate.isEmpty(property.getName()) && UtilValidate.isEmpty(property.getValue())) {
					// if a property's value is null , delete it from db.
					deleteProperty(Constants.USER, id, property);
				} else {
					queryParams.add(id);
					queryParams.add(property.getName());
					queryParams.add(property.getValue());
					String INSERT_PROPERTY = "REPLACE INTO " + tproperty.getName() + "(" + tproperty.getValue() + ",name,propValue) VALUES(?,?,?)";
					jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_PROPERTY);
					queryParams.clear();
					pageIteratorSolver.clearCache();
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void updateProperty(int type, Long id, Property property) {
		Property tproperty = (Property) tables.get(new Integer(type));
		try {
			List queryParams = new ArrayList();
			if (!UtilValidate.isEmpty(property.getName()) && UtilValidate.isEmpty(property.getValue())) {
				// if a property's value is null , delete it from db.
				deleteProperty(Constants.USER, id, property);
			} else {
				queryParams.add(id);
				queryParams.add(property.getName());
				queryParams.add(property.getValue());
				String INSERT_PROPERTY = "REPLACE INTO " + tproperty.getName() + "(" + tproperty.getValue() + ",name,propValue) VALUES(?,?,?)";
				jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_PROPERTY);
				queryParams.clear();
				pageIteratorSolver.clearCache();
			}
		} catch (Exception e) {
			logger.error("type=" + type + " id=" + id + e);
		}
	}

	public void deleteProperties(int type, Long id) {
		Property tproperty = (Property) tables.get(new Integer(type));
		try {
			String DELETE_PROPERTIES = "DELETE FROM " + tproperty.getName() + " WHERE " + tproperty.getValue() + "=?";
			List queryParams = new ArrayList();
			queryParams.add(id);
			jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_PROPERTIES);
			pageIteratorSolver.clearCache();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void deleteProperty(int type, Long id, Property property) {
		Property tproperty = (Property) tables.get(new Integer(type));
		try {
			String DELETE_PROPERTIES = "DELETE FROM " + tproperty.getName() + " WHERE " + tproperty.getValue() + "=?" + " and  name='"
					+ property.getName() + "'";

			List queryParams = new ArrayList();
			queryParams.add(id);
			jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_PROPERTIES);
			pageIteratorSolver.clearCache();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public PageIterator getIdsByNameAndValue(int type, String name, String value) {
		Property tproperty = (Property) tables.get(new Integer(type));
		String LOAD_COUNT_PROPERTIES = "SELECT count(1)  FROM " + tproperty.getName() + " WHERE name=? and propValue=?";
		String LOAD_PROPERTIES = "SELECT " + tproperty.getValue() + " FROM " + tproperty.getName() + " WHERE name=? and propValue=?";

		List queryParams = new ArrayList();
		queryParams.add(name);
		queryParams.add(value);
		return pageIteratorSolver.getPageIterator(LOAD_COUNT_PROPERTIES, LOAD_PROPERTIES, queryParams, 0, 100);

	}

	public PageIterator getIdsByName(int type, String name) {
		Property tproperty = (Property) tables.get(new Integer(type));
		String LOAD_COUNT_PROPERTIES = "SELECT count(1)  FROM " + tproperty.getName() + " WHERE name=?";
		String LOAD_PROPERTIES = "SELECT " + tproperty.getValue() + " FROM " + tproperty.getName() + " WHERE name=?";

		List queryParams = new ArrayList();
		queryParams.add(name);
		return pageIteratorSolver.getPageIterator(LOAD_COUNT_PROPERTIES, LOAD_PROPERTIES, queryParams, 0, 100);

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// this.tables.clear();

	}
}
