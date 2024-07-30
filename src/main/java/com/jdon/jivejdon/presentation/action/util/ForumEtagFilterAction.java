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
public class ForumEtagFilterAction extends Action {
	private final static int expire = 5 * 60 * 60;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long modelLastModifiedDate = ForumUtil.getForumsLastModifiedDate(this.servlet
				.getServletContext());
		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;
		}
		return actionMapping.findForward("success");

	}

}
