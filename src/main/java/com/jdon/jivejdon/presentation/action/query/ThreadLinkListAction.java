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
package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;

import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;

import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;
import com.jdon.strutsutil.ModelListForm;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collection;


public class ThreadLinkListAction extends Action {
	private TagService tagService;
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public TagService getTagService() {
		if (tagService == null)
			tagService = (TagService) WebAppUtil.getService("othersService", this.servlet.getServletContext());
		return tagService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String threadId = request.getParameter("threadId");
		if (threadId == null || threadId.length() == 0)
			return null;

		ForumThread thread = getForumMessageQueryService().getThread(new Long(threadId));
		if (thread == null)
			return null;

		try {
			ReBlogVO reBlogVO = thread.getRootMessage().getReBlogVO();
			if (reBlogVO == null)
				return null;

			final Collection<ForumThread> threadLinks = new ArrayList<>();
			for (ForumThread threadLink : reBlogVO.getThreadFroms()) {
				threadLinks.add(threadLink);
			}
			for (ForumThread threadLink : reBlogVO.getThreadTos()) {
				threadLinks.add(threadLink);
			}
			ModelListForm threadListForm = (ModelListForm) form;
			threadListForm.setList(threadLinks);
			threadListForm.setAllCount(threadLinks.size());
			return mapping.findForward("success");
		} catch (Exception e) {
			return null;
		}

	}

	
}
