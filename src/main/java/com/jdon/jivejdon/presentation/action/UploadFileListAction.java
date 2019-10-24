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
package com.jdon.jivejdon.presentation.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.jivejdon.domain.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.UpLoadFileForm;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.property.UploadService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class UploadFileListAction extends Action {
	private final static Logger logger = LogManager.getLogger(UploadFileListAction.class);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("enter UploadFileListAction");
		ModelListForm listForm = (ModelListForm) actionForm;
		String messageId = request.getParameter("parentId");
		Long messageIdL = new Long(0);
		if (!UtilValidate.isEmpty(messageId)) {
			messageIdL = new Long(messageId);
			authFilter(request, messageIdL);
		} else {
			logger.debug("no paramter parentId, it is create!");
		}
		UploadService uploadService = (UploadService) WebAppUtil.getService("uploadService", request);
		Collection list = uploadService.getAllUploadFiles(messageIdL);
		listForm.setList(list);

		return actionMapping.findForward("success");
	}

	private void authFilter(HttpServletRequest request, Long messageId) {
		logger.debug("enter authFilter");
		UpLoadFileForm upLoadFileForm = (UpLoadFileForm) FormBeanUtil.lookupActionForm(request, "upLoadFileForm");
		if (upLoadFileForm == null) {
			logger.debug("uploadFileForm don't existed,please config acion filter for upload view in struts-config-upload");
			return;
		}
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		Account account = accountService.getloginAccount();
		if (account == null) {
			upLoadFileForm.setAuthenticated(false);
			return;
		}
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		ForumMessage forumMessage = forumMessageService.getMessage(messageId);
		boolean isallowEdit = forumMessageService.checkIsAuthenticated(forumMessage);
		upLoadFileForm.setAuthenticated(isallowEdit);

	}

}
