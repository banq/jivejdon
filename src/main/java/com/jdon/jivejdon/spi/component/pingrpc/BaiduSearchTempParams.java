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
package com.jdon.jivejdon.spi.component.pingrpc;

public class BaiduSearchTempParams {

	private final String linkSubmitUrl, host, domain, bdToken, urltemp;

	public BaiduSearchTempParams(String linkSubmitUrl, String host, String domain, String bdToken, String urltemp) {
		this.linkSubmitUrl = linkSubmitUrl;
		this.host = host;
		this.domain = domain;
		this.bdToken = bdToken;
		this.urltemp = urltemp;
	}

	public String getLinkSubmitUrl() {
		return linkSubmitUrl;
	}

	public String getDomain() {
		return domain;
	}

	public String getBdToken() {
		return bdToken;
	}

	public String getUrltemp() {
		return urltemp;
	}

	public String getHost() {
		return host;
	}

}
