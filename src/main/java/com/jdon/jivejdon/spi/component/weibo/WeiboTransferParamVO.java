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
package com.jdon.jivejdon.infrastructure.component.weibo;

public class WeiboTransferParamVO {

	public final String SINA_NIKCNAME_Prefix;

	public final String SINA_EMAIL_URL_Suffix;

	public final String SINA_URL_name;

	public final String SINA_URL_Prefix;

	public final String SINA_DES_name;

	public final String TECENT_NIKCNAME_Prefix;

	public final String TECENT_EMAIL_URL_Suffix;

	public final String TECENT_URL_name;

	public final String TECENT_URL_Prefix;

	public final String TECENT_DES_name;

	public WeiboTransferParamVO(String sINA_NIKCNAME, String sINA_EMAIL_URL, String sINA_URL, String sINA_URL_Prefix, String sINA_DES,
			String tECENT_NIKCNAME, String tECENT_EMAIL_URL, String tECENT_URL, String tECENT_URL_Prefix, String tECENT_DES) {
		super();
		this.SINA_NIKCNAME_Prefix = sINA_NIKCNAME;
		this.SINA_EMAIL_URL_Suffix = sINA_EMAIL_URL;
		this.SINA_URL_name = sINA_URL;
		this.SINA_URL_Prefix = sINA_URL_Prefix;
		this.SINA_DES_name = sINA_DES;

		this.TECENT_NIKCNAME_Prefix = tECENT_NIKCNAME;
		this.TECENT_EMAIL_URL_Suffix = tECENT_EMAIL_URL;
		this.TECENT_URL_name = tECENT_URL;
		this.TECENT_URL_Prefix = tECENT_URL_Prefix;
		this.TECENT_DES_name = tECENT_DES;
	}

}
