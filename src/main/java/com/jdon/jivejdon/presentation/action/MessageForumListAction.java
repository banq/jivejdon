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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 */
public class MessageForumListAction extends MessageListAction {
	private final static String module = MessageForumListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		Debug.logVerbose("enter getPageIterator", module);
		String forumId = request.getParameter("forum");
		if (forumId == null || (!StringUtils.isNumeric(forumId)) || forumId.length() > 10) {
			Debug.logError(" getPageIterator error : forum is null", module);
			return new PageIterator();
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
				this.servlet.getServletContext());
		Forum forum = forumService.getForum(Long.parseLong(forumId));
		ForumThread forumThread = forum.getForumState().getLatestPost().getForumThread();
		return getForumMessageQueryService().getMessages(forumThread.getThreadId(), start, count);
	}

	/**
	 * set ModelListAction donot load directly a model from cache.
	 */
	protected boolean isEnableCache() {
		return false;
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm,
								  HttpServletRequest request, ModelListForm modelListForm)
			throws Exception {
		String forumId = request.getParameter("forum");
		if (forumId == null || (!StringUtils.isNumeric(forumId)) || forumId.length() > 10) {
			Debug.logError("customizeListForm error : forumId  is not Integer or > 10, ip=" +
					request.getRemoteAddr(), module);
			return;
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
				this.servlet.getServletContext());
		Forum forum = forumService.getForum(Long.parseLong(forumId));
		ForumThread forumThread = forum.getForumState().getLatestPost().getForumThread();
		if (forumThread == null)
			throw new Exception("thread is null forumId latestPost=" + forumId);

		modelListForm.setOneModel(forumThread);


	}


}
