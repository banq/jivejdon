/**
 * Copyright 2005 Jdon.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jdon.jivejdon.presentation.sitemap;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.jdon.model.ModelForm;

/**
 * 
 * @author banq
 */
public class UrlForm extends ModelForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long urlId;
	private String ioc;
	private String name;

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIoc() {
		return ioc;
	}

	public void setIoc(String ioc) {
		this.ioc = ioc;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors actionErrors = null;
		ArrayList errorList = new ArrayList();
		doValidate(mapping, request, errorList);
		request.setAttribute("errors", errorList);
		if (!errorList.isEmpty()) {
			actionErrors = new ActionErrors();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("global.error"));
		}
		return actionErrors;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (this.getAction().equals("delete"))
			return;
		addErrorIfStringEmpty(errors, "Url is required", getIoc());

		try {
			if (!getIoc().startsWith("https://")) {
                if (!getIoc().startsWith("http://")) {
                    errors.add("Url must be http:// or https://");
                    return;
                }
            }
		} catch (Exception e) {
			return;
		}
	}

	/* Protected Methods */

	protected void addErrorIfStringEmpty(List errors, String message, String value) {
		if (value == null || value.trim().length() < 1) {
			errors.add(message);
		}
	}

}
