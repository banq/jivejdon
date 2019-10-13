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
package com.jdon.jivejdon.repository.property;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.model.property.Property;
import com.jdon.jivejdon.model.util.CachedCollection;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	 * @see com.jdon.jivejdon.repository.property.PropertyFactory#saveForumPropertys(int,
	 * java.util.Collection)
	 */
	public void saveForumPropertys(int id, Collection props) {
		try {
			Collection propss = new ArrayList();
			Iterator iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.FORUM, new Long(id));
			containerUtil.getCacheManager().getCache().remove(new Long(id));
			containerUtil.clearCache(new Long(id));
			propertyDao.saveProperties(Constants.FORUM, new Long(id), propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.property.PropertyFactory#saveThreadPropertys(int,
	 * java.util.Collection)
	 */
	public void saveThreadPropertys(int threadID, Collection props) {
		try {
			Collection propss = new ArrayList();
			Iterator iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.THREAD, new Long(threadID));
			containerUtil.getCacheManager().getCache().remove(new Integer(threadID));
			propertyDao.saveProperties(Constants.THREAD, new Long(threadID), propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.property.PropertyFactory#getForumPropertys(java.lang
	 * .Long)
	 */
	public Collection getForumPropertys(Long id) {
		CachedCollection cc = (CachedCollection) containerUtil.getModelFromCache(id, CachedCollection.class);
		if (cc == null) {
			Collection props = propertyDao.getProperties(Constants.FORUM, id);
			if ((props != null) && (props.size() != 0)) {
				cc = new CachedCollection(Integer.toString(Constants.FORUM), props);
				containerUtil.addModeltoCache(id, cc);
			} else
				return new ArrayList();
		}
		return cc.getList();
	}

	public void saveUserPropertys(Long userId, Collection props) {
		try {
			Collection propss = new ArrayList();
			Iterator iter = props.iterator();
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

	public Collection getUserPropertys(Long userId) {
		CachedCollection cc = (CachedCollection) containerUtil.getModelFromCache(userId, CachedCollection.class);
		if (cc == null) {
			Collection props = propertyDao.getProperties(Constants.USER, userId);
			if ((props != null) && (props.size() != 0)) {
				cc = new CachedCollection(Integer.toString(Constants.USER), props);
				containerUtil.addModeltoCache(userId, cc);
			} else
				return new ArrayList();
		}
		return cc.getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.property.PropertyFactory#getThreadPropertys(int)
	 */
	public Collection getThreadPropertys(int threadID) {
		Collection props = (Collection) containerUtil.getCacheManager().getCache().get(new Integer(threadID));
		if (props == null) {
			props = propertyDao.getProperties(Constants.THREAD, new Long(threadID));
			if ((props != null) && (props.size() != 0))
				containerUtil.getCacheManager().getCache().put(threadID, props);
			else
				return new ArrayList();
		}
		return props;
	}

}
