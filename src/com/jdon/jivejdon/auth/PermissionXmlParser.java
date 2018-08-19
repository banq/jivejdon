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
package com.jdon.jivejdon.auth;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.jdon.controller.config.DTDEntityResolver;
import com.jdon.jivejdon.model.auth.PermissionRule;
import com.jdon.util.Debug;
import com.jdon.util.FileLocator;

/**
 * @author <a href="mailto:banqJdon <AT>jdon.com">banq </a>
 * 
 */

public class PermissionXmlParser {
	private final static String module = PermissionXmlParser.class.getName();
	private FileLocator fileLocator = new FileLocator();

	private String configFileName;

	/**
	 * @param permissionConfigureName
	 */
	public PermissionXmlParser(String configFileName) {
		this.configFileName = configFileName;
	}

	public PermissionRule parse() {
		PermissionRule pr = null;
		try {
			if (configFileName == null)
				return new PermissionRule();

			Document doc = buildDocument(configFileName);
			if (doc == null)
				return new PermissionRule();

			Element root = doc.getRootElement();

			pr = parse(root);

			Debug.logVerbose("<!--   config load finished -->", module);
		} catch (Exception ex) {
			Debug.logError("configure FileName: " + configFileName + " parsed error: " + ex, module);
		}
		return pr;
	}

	private Document buildDocument(String configFileName) {
		Debug.logVerbose(" locate configure file  :" + configFileName, module);
		try {
			InputStream xmlStream = fileLocator.getConfPathXmlStream(configFileName);
			if (xmlStream == null) {
				Debug.logVerbose("can't locate file:" + configFileName, module);
				return null;
			} else {
				Debug.logVerbose(" configure file found :" + xmlStream, module);
			}

			SAXBuilder builder = new SAXBuilder();
			builder.setEntityResolver(new DTDEntityResolver());
			Document doc = builder.build(xmlStream);
			Debug.logVerbose(" got mapping file ", module);
			return doc;
		} catch (Exception e) {
			Debug.logError(" JDOMException error: " + e, module);
			return null;
		}
	}

	private PermissionRule parse(Element root) throws Exception {

		PermissionRule pr = new PermissionRule();
		List services = root.getChildren("service");
		Iterator iter = services.iterator();
		while (iter.hasNext()) {
			Element service = (Element) iter.next();
			String refName = service.getAttributeValue("ref");
			if (service.getChildren("method") != null) {
				Iterator i = service.getChildren("method").iterator();
				while (i.hasNext()) {
					Element method = (Element) i.next();
					String methodName = method.getAttributeValue("name");
					Debug.logVerbose(" method name=" + methodName, module);
					List roles = method.getChildren("role");
					Iterator ii = roles.iterator();
					while (ii.hasNext()) {
						Element role = (Element) ii.next();
						String roleName = role.getText();
						Debug.logVerbose(" role name=" + roleName, module);
						pr.putRule(refName, methodName, roleName);
					}
				}
			}
		}
		return pr;
	}

}