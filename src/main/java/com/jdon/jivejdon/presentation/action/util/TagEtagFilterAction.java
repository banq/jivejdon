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

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.util.ToolsUtil;

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
public class TagEtagFilterAction extends Action {
	private final static int expire = 1 * 24 * 60 * 60;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String tagID = request.getParameter("tagID");
		if (tagID == null || !StringUtils.isNumeric(tagID) || tagID.length() > 10) {
			return actionMapping.findForward("success");
		}
		TagService othersService = (TagService) WebAppUtil.getService("othersService",
				this.servlet.getServletContext());

		Long tagIDL = Long.parseLong(tagID);
		ThreadTag tag = othersService.getThreadTag(tagIDL);
		if (tag == null)
			return actionMapping.findForward("success");

		if (!ToolsUtil.checkHeaderCache(expire, 1000000000000L +  (long)tag.getAssonum() * 10000, request,
				response)) {
			return null;// response is 304
		}
		return actionMapping.findForward("success");

	}

}
