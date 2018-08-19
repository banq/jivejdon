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
package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;

public class MessageViewAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String messageId = request.getParameter("messageId");
		if (messageId == null | messageId.length() == 0)
			return mapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext());
		ForumMessage message = forumMessageService.getMessage(Long.parseLong(messageId));

		MessageForm messageForm = (MessageForm) form;
		try {
			PropertyUtils.copyProperties(messageForm, message);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return mapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);

	}
}
