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
package com.jdon.jivejdon.presentation.action.admin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.cache.CacheableWrapper;
import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.ClassUtil;

public class CacheKeysAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelListForm mlform = (ModelListForm) form;
		ContainerUtil containerUtil = (ContainerUtil) WebAppUtil.getComponentInstance("containerUtil", 
				this.servlet.getServletContext());

		PageIterator pageIterator = null;
		String skey = request.getParameter("key");
		if (skey != null && skey.length() != 0) {
			pageIterator = containerUtil.searchCacheKeys(skey, mlform.getStart(), mlform.getCount());
		} else {
			pageIterator = containerUtil.getCacheKeys(mlform.getStart(), mlform.getCount());
		}
		mlform.setAllCount(pageIterator.getAllCount());
		List list = new ArrayList(pageIterator.getSize());
		while (pageIterator.hasNext()) {
			Object key = pageIterator.next();
			Object value = containerUtil.getCacheManager().getCache().get(key);
			String className = value.getClass().getName();
			if (value instanceof CacheableWrapper) {
				CacheableWrapper cw = (CacheableWrapper) value;
				className = getAllFields(cw.getCachedValue());
			}
			OneOneDTO one = new OneOneDTO(key, className);
			list.add(one);
		}
		mlform.setList(list);
		return mapping.findForward("success");

	}

	private String getAllFields(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append(obj).append(" \n <br>");
		sb.append("fields:").append(" \n <br>");
		for (final Field field : ClassUtil.getAllDecaredFields(obj.getClass())) {
			field.setAccessible(true);
			try {
				Object fo = field.get(obj);
				sb.append(field.getName()).append("=").append(fo).append(" \n <br>");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
