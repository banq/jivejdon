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

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

public class ThreadEtagFilter extends Action {
	public final static String NEWLASMESSAGE = "NEWLASMESSAGE";
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (request.getParameter("nocache") != null) { // for just modified and
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		}

		// browser cache expire time; default is one day
		int expire = 1 * 24 * 60 * 60;
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!UtilValidate.isInteger(threadId)) || threadId.length()>10) {
			response.sendError(404);
			return null;
		}
		
		long threadIdl = 0;
		try{
		    threadIdl = Long.parseLong(threadId);
		}catch(Exception e){
			response.sendError(404);
			return null;
		}

		ForumThread forumThread = getForumMessageQueryService().getThread(threadIdl);
		if (forumThread == null) {
			response.sendError(404);
			return null;
		}
		long modelLastModifiedDate = forumThread.getState().getModifiedDate2();

		// in 15 days the message expire will be shorter;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(modelLastModifiedDate));
		c.add(Calendar.DATE, 15);

		Calendar tc = Calendar.getInstance();
		tc.setTime(new Date());
		if (c.after(tc)) {
			expire = 24 * 60 * 60;
		}

		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;// response is 304
		}

		return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
	}

}
