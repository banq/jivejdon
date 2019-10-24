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
package com.jdon.jivejdon.spi.component.sitemap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sitemap")
public class Sitemap {

	private final String ioc;

	private final String lastUpdateDate;

	public Sitemap() {
		this.ioc = null;
		this.lastUpdateDate = null;
	}

	public Sitemap(String ioc,  String lastUpdateDate) {		
		this.ioc = ioc;
		this.lastUpdateDate = lastUpdateDate;
	}

	@XmlElement(name = "loc")
	public String getIoc() {
		return ioc;
	}
	

	@XmlElement(name = "lastmod")
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	
}
