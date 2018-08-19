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
package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.Property;

public class PropertysForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection propertys;

	private int maxSize = 50;

	public PropertysForm() {
		propertys = new ArrayList();
		for (int i = 0; i < maxSize; i++) {
			propertys.add(new Property());
		}
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public Collection getPropertys() {
		return propertys;
	}

	public Property getProperty(int index) {
		return (Property) ((List) propertys).get(index);
	}

	public void setProperty(int index, Property property) {
		propertys.add(property);
	}

	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (getPropertys().size() > maxSize) {
			errors.add("max length is " + maxSize);
			return;
		}
	}

}
