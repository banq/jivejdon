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

package com.jdon.jivejdon.domain.model.message.output.html;

import com.jdon.jivejdon.domain.model.message.output.beanutil.FilterBeanInfo;

/**
 * BeanInfo class for the Newline filter.
 */
public class NewlineBeanInfo extends FilterBeanInfo {

	public static final String[] PROPERTY_NAMES = new String[0];

	public NewlineBeanInfo() {
		super();
	}

	public Class getBeanClass() {
		return Newline.class;
	}

	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	public String getName() {
		return "Newline";
	}
}