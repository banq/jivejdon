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

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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
		if (forumId == null || (!UtilValidate.isInteger(forumId)) || forumId.length() > 10) {
			Debug.logError(" getPageIterator error : forum is null", module);
			return new PageIterator();
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
				this.servlet.getServletContext());
		Forum forum = forumService.getForum(new Long(forumId));
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
		if (forumId == null || (!UtilValidate.isInteger(forumId)) || forumId.length() > 10) {
			Debug.logError("customizeListForm error : forumId  is not Integer or > 10, ip=" +
					request.getRemoteAddr(), module);
			return;
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
				this.servlet.getServletContext());
		Forum forum = forumService.getForum(new Long(forumId));
		ForumThread forumThread = forum.getForumState().getLatestPost().getForumThread();
		if (forumThread == null)
			throw new Exception("thread is null forumId latestPost=" + forumId);

		modelListForm.setOneModel(forumThread);

		if (request.getSession(false) != null) {
			boolean[] authenticateds = getAuthedListForm(actionForm, request);
			MessageListForm messageListForm = (MessageListForm) actionForm;
			messageListForm.setAuthenticateds(authenticateds);
		}


	}


}
