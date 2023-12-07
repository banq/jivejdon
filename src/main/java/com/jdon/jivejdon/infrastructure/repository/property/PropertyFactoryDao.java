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
package com.jdon.jivejdon.infrastructure.repository.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.util.CachedCollection;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.util.UtilValidate;

public class PropertyFactoryDao implements PropertyFactory {
	private final static Logger logger = LogManager.getLogger(PropertyFactoryDao.class);

	private PropertyDao propertyDao;

	private ContainerUtil containerUtil;

	public PropertyFactoryDao(PropertyDao propertyDao, ContainerUtil containerUtil) {
		super();
		this.propertyDao = propertyDao;
		this.containerUtil = containerUtil;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.PropertyFactory#
	 * saveForumPropertys(int, java.util.Collection)
	 */
	public void saveForumPropertys(Long id, Collection<Property> props) {
		try {
			Collection<Property> propss = new ArrayList<Property>();
			Iterator<Property> iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.FORUM, id);
			containerUtil.getCacheManager().getCache().remove(id);
			containerUtil.clearCache(id);
			propertyDao.saveProperties(Constants.FORUM, id, propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.PropertyFactory#
	 * saveThreadPropertys(int, java.util.Collection)
	 */
	public void saveThreadPropertys(Long threadID, Collection<Property> props) {
		try {
			Collection<Property> propss = new ArrayList<Property>();
			Iterator<Property> iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.THREAD, threadID);
			containerUtil.getCacheManager().getCache().remove(threadID);
			propertyDao.saveProperties(Constants.THREAD, threadID, propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.PropertyFactory#
	 * getForumPropertys(java.lang .Long)
	 */
	public Collection<Property> getForumPropertys(Long id) {
		CachedCollection cc = (CachedCollection) containerUtil.getModelFromCache(id, CachedCollection.class);
		if (cc == null) {
			Collection<Property> props = propertyDao.getProperties(Constants.FORUM, id);
			if ((props != null) && (props.size() != 0)) {
				cc = new CachedCollection(Integer.toString(Constants.FORUM), props);
				containerUtil.addModeltoCache(id, cc);
			} else
				return new ArrayList<Property>();
		}
		return cc.getList();
	}

	public void saveUserPropertys(Long userId, Collection<Property> props) {
		try {
			Collection<Property> propss = new ArrayList<Property>();
			Iterator<Property> iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.USER, userId);
			containerUtil.getCacheManager().getCache().remove(userId);
			containerUtil.clearCache(userId);
			propertyDao.saveProperties(Constants.USER, userId, propss);
		} catch (Exception e) {
			logger.error(" saveUserPropertys error: " + e);

		}
	}

	public Collection<Property> getUserPropertys(Long userId) {
		CachedCollection cc = (CachedCollection) containerUtil.getModelFromCache(userId, CachedCollection.class);
		if (cc == null) {
			Collection<Property> props = propertyDao.getProperties(Constants.USER, userId);
			if ((props != null) && (props.size() != 0)) {
				cc = new CachedCollection(Integer.toString(Constants.USER), props);
				containerUtil.addModeltoCache(userId, cc);
			} else
				return new ArrayList<Property>();
		}
		return cc.getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.PropertyFactory#
	 * getThreadPropertys(int)
	 */
	public Collection<Property> getThreadPropertys(Long threadID) {
		Collection<Property> props = (Collection) containerUtil.getCacheManager().getCache().get(threadID);
		if (props == null) {
			props = propertyDao.getProperties(Constants.THREAD, threadID);
			if ((props != null) && (props.size() != 0))
				containerUtil.getCacheManager().getCache().put(threadID, props);
			else
				return new ArrayList<Property>();
		}
		return props;
	}

}
