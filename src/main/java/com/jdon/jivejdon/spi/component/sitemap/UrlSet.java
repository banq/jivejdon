/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.infrastructure.component.sitemap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "url")
public class UrlSet {

	private final String ioc;
	private final String priority;//= "1"

	public UrlSet() {
		super();
		this.ioc = "";
		this.priority = "";
	}

	
	public UrlSet(String ioc, String priority) {
		super();
		this.ioc = ioc;
		this.priority = priority;
	}

	@XmlElement(name = "loc")
	public String getIoc() {
		return ioc;
	}

	
	@XmlElement(name = "priority")
	public String getPriority() {
		return priority;
	}

	
}
