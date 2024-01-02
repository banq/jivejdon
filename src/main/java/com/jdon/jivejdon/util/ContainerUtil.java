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
package com.jdon.jivejdon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.jdon.annotation.Component;
import com.jdon.container.visitor.data.SessionContextSetup;
import com.jdon.controller.cache.CacheManager;
import com.jdon.controller.model.PageIterator;
import com.jdon.domain.model.cache.ModelKey;
import com.jdon.domain.model.cache.ModelManager;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Component("containerUtil")
public class ContainerUtil {
	
	private final static ConcurrentHashMap<String, Pattern> patterns = new ConcurrentHashMap<>();

	private SessionContextSetup sessionContextSetup;
	private ModelManager modelManager;
	private CacheManager cacheManager;

	/**
	 * @param containerCallback
	 */
	public ContainerUtil(SessionContextSetup sessionContextSetup, ModelManager modelManager, CacheManager cacheManager) {
		this.sessionContextSetup = sessionContextSetup;
		this.modelManager = modelManager;
		this.cacheManager = cacheManager;
	}

	public boolean isInCache(Object key, Class modelClass) {
		ModelKey modelKey = new ModelKey(key, modelClass);
		return modelManager.containInCache(modelKey);
	}

	public Object getModelFromCache(Object key, Class modelClass) {
		ModelKey modelKey = new ModelKey(key, modelClass);
		return modelManager.getCache(modelKey);
	}

	public void addModeltoCache(Object key, Object model) {
		if (model == null)
			return;
		ModelKey modelKey = new ModelKey(key, model.getClass());
		modelManager.addCache(modelKey, model);
	}

	public void clearCache(Object key) {
		try {
			modelManager.removeCache(key);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void clearAllModelCache() {
		modelManager.clearCache();
		cacheManager.clear();
	}

	public SessionContextSetup getSessionContextSetup() {
		return sessionContextSetup;
	}

	/**
	 * @return Returns the cacheManager.
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public PageIterator getCacheKeys(int start, int count) {
		Collection keys = cacheManager.getCache().keySet();
		if (keys == null || keys.size() == 0)
			return new PageIterator();

		List pageIds = new ArrayList(keys.size());
		int i = 0;
		for (Object o : keys) {
			if (i >= start && i < start + count) {
				pageIds.add(o);
			}
			if (i >= start + count)
				break;
			i++;
		}
		return new PageIterator(keys.size(), pageIds.toArray());
	}

	public PageIterator searchCacheKeys(String skey, int start, int count) {
		Collection keys = cacheManager.getCache().keySet();
		if (keys == null || keys.size() == 0)
			return new PageIterator();

		Pattern domainPattern = patterns.computeIfAbsent(skey, k->Pattern.compile(".*(" + skey + ").*"));
		List searchKeys = new ArrayList(keys.size());
		for (Object o : keys) {
			if (domainPattern.matcher(o.toString()).matches()) {
				searchKeys.add(o);
			}
		}

		List pageIds = new ArrayList(searchKeys.size());
		int i = 0;
		for (Object o : searchKeys) {
			if (i >= start && i < start + count) {
				pageIds.add(o);
			}
			if (i >= start + count)
				break;
			i++;
		}
		return new PageIterator(searchKeys.size(), pageIds.toArray());
	}
}
