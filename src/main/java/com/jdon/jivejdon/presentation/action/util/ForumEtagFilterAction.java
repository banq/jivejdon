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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

/**
 * for struts-config.xml
 * 
 * <action path="/query/threadApprovedNewList"
 * type="com.jdon.jivejdon.presentation.action.util.ForumEtagFilterAction"
 * name="threadListForm" scope="request" validate="false"> <forward
 * name="success" path="/query/threadApprovedNewList2.shtml"/> </action>
 * 
 * /query/threadApprovedNewList2.shtml is true orginal action.
 * 
 * @author banq
 * 
 */
public class ForumEtagFilterAction extends Action {
	public final static String NEWLASMESSAGE = "NEWLASMESSAGE";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", this.servlet.getServletContext());
		String forumId = request.getParameter("forum");
		if (forumId == null)
			forumId = request.getParameter("forumId");

		ForumMessage lastpost = null;
		if ((forumId == null) || !UtilValidate.isInteger(forumId)) {
			lastpost = ForumUtil.getForumsLastModifiedDate(this.servlet.getServletContext());
		} else {
			Forum forum = forumService.getForum(new Long(forumId));
			if (forum == null)
				return actionMapping.findForward("success");
			lastpost = forum.getForumState().getLastPost();
		}
		if (lastpost == null)
			return actionMapping.findForward("success");
		int expire = 24 * 60 * 60;
		long modelLastModifiedDate = lastpost.getModifiedDate2();
		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;
		}
		return actionMapping.findForward("success");

	}

}
