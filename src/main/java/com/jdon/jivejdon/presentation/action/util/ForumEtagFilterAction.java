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
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.util.ToolsUtil;
import org.apache.commons.lang3.StringUtils;
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
	private final static int expire = 12 * 60 * 60;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forumId = request.getParameter("forum");
		if (forumId == null)
			forumId = request.getParameter("forumId");		
		if (forumId == null || !StringUtils.isNumeric(forumId) || forumId.length() > 10) {
			if (!ToolsUtil.checkHeaderCacheExpire(expire, request, response)) {
				return null;// response is 304
			}
			return actionMapping.findForward("success");
		}

		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", this.servlet.getServletContext());
		Long forumIDL = Long.parseLong(forumId);
		Forum forum = forumService.getForum(forumIDL);
		if (forum == null) {
			if (!ToolsUtil.checkHeaderCacheExpire(expire, request, response)) {
				return null;// response is 304
			}
			return actionMapping.findForward("success");
		}

		long latestPostTime = 0;
		if (forum.getForumState().getLatestPost() != null) {
			latestPostTime = forum.getForumState().getLatestPost().getModifiedDate2();
		} 
		if (latestPostTime == 0) {
			if (!ToolsUtil.checkHeaderCacheExpire(expire, request, response)) {
				return null;// response is 304
			}
			return actionMapping.findForward("success");
		}

		if (!ToolsUtil.checkHeaderCache(expire, latestPostTime, request, response)) {
			return null;// response is 304
		}
		return actionMapping.findForward("success");

	}

}