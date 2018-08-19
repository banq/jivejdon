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
package com.jdon.jivejdon.presentation.action.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelBaseAction;

public class ServiceMethodSimpleAction extends ModelBaseAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String service = request.getParameter("service");
		if (service == null) {
			return actionMapping.findForward("failure");
		}
		String method = request.getParameter("method");
		if (method == null) {
			return actionMapping.findForward("failure");
		}

		Object[] methodParas = new Object[] {};
		String id = request.getParameter("id");
		if (id != null)
			methodParas = new Object[] { Long.parseLong(id) };

		Object result = WebAppUtil.callService(service, method, methodParas, request);
		if (result != null)
			request.setAttribute(result.getClass().getSimpleName(), result);
		ActionForward sucessaf = actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		ActionForward actionnameaf = actionMapping.findForward(method);
		if (sucessaf != null) {
			return sucessaf;
		} else if (actionnameaf != null) {
			return actionnameaf;
		} else {
			return null;
		}
	}

}
