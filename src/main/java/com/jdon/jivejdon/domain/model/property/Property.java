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
package com.jdon.jivejdon.domain.model.property;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Searchable
public class Property implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2505749413671249227L;

	private String name;

	@SearchableProperty
	private String value;

	private String type;

	public Property() {
	}

	/**
	 * @param name
	 * @param value
	 */
	public Property(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return name + " " + type + " " + value;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

}
