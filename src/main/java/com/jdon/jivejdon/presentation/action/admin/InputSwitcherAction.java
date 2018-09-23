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
package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.filter.InputSwitcherIF;

public class InputSwitcherAction extends Action {
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String actionS = request.getParameter("method");
		if ((actionS != null) && (actionS.equalsIgnoreCase("save"))) {
			save(actionMapping, actionForm, request, response);
		} else {// no delete,maybe list or add
			load(actionMapping, actionForm, request, response);
		}
		return actionMapping.findForward(actionS);
	}

	public void load(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		InputSwitcherIF inputSwitcher = (InputSwitcherIF) WebAppUtil.getComponentInstance("inputSwitcher", request);
		if (inputSwitcher.isInputPermit())
			request.setAttribute("des", inputSwitcher.getDes());

	}

	public void save(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String inputSwitcherS = request.getParameter("inputSwitcher");
		String des = request.getParameter("des");
		InputSwitcherIF inputSwitcher = (InputSwitcherIF) WebAppUtil.getComponentInstance("inputSwitcher", request);
		if ((inputSwitcherS != null) && (inputSwitcherS.equals("on")))
			inputSwitcher.setInputPermit(true, des);
		else
			inputSwitcher.setInputPermit(false, des);
	}

}
