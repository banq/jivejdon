/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.presentation.action.shortmessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.presentation.form.ShortMessageForm;
import com.jdon.jivejdon.service.ShortMessageService;
import com.jdon.strutsutil.FormBeanUtil;

/**
 * SidebarShortmessageController.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: 2008-5-22
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public class SaveShortmessageAction extends Action {

	private Logger logger = LogManager.getLogger(SaveShortmessageAction.class);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ShortMessageService shortMessageService = (ShortMessageService) WebAppUtil.getService("shortMessageService", request);
		logger.debug(" " + shortMessageService);
		ShortMessageForm form = (ShortMessageForm) actionForm;
		String operation = request.getAttribute("operation").toString();
		logger.debug(form + " " + operation);
		return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
	}
}
