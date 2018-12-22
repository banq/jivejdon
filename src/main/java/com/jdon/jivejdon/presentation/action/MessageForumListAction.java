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
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageListAction extends ModelListAction {
	private final static String module = MessageListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		Debug.logVerbose("enter getPageIterator", module);
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!UtilValidate.isInteger(threadId)) || threadId.length()>10) {
			Debug.logError(" getPageIterator error : threadId is null", module);
			return new PageIterator();
		}

		return getForumMessageQueryService().getMessages(new Long(threadId), start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 * .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		Debug.logVerbose("enter findModelByKey", module);

		// getXXX can be intercepted by cacheinterceptor before accessing
		// ForumMessageServiceShell
		ForumMessage forumMessage = getForumMessageQueryService().getMessage((Long) key);
		if (forumMessage != null)
			forumMessage.preloadWhenView();
		return forumMessage;
	}

	/**
	 * set ModelListAction donot load directly a model from cache.
	 */
	protected boolean isEnableCache() {
		return false;
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, ModelListForm modelListForm)
			throws Exception {
		String threadId = request.getParameter("thread");
		String forumId = request.getParameter("forum");
		if ((threadId == null) && (forumId == null)) {
			Debug.logError("customizeListForm error : thread or forum is null", module);
			return;
		}
		if (threadId != null)
			if ((!UtilValidate.isInteger(threadId)) || threadId.length() > 10) {
				Debug.logError("customizeListForm error : thread  is not Integer or > 10, ip=" +
						request.getRemoteAddr(), module);
				return;
			}

		if (forumId != null)
			if ((!UtilValidate.isInteger(forumId)) || forumId.length() > 10) {
				Debug.logError("customizeListForm error : forumId  is not Integer or > 10, ip=" +
						request.getRemoteAddr(), module);
				return;
			}
		ForumThread forumThread = null;
		if (threadId != null)
			forumThread = getForumMessageQueryService().getThread(new Long(threadId));
		else if (forumId != null) {
			ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
					this.servlet.getServletContext());
			Forum forum = forumService.getForum(new Long(forumId));
			forumThread = forum.getForumState().getLastPost().getForumThread();
		}
		if (forumThread == null)
			throw new Exception("thread is null " + threadId);

		modelListForm.setOneModel(forumThread);

			if (request.getSession(false) != null) {
				boolean[] authenticateds = getAuthedListForm(actionForm, request);
				MessageListForm messageListForm = (MessageListForm) actionForm;
				messageListForm.setAuthenticateds(authenticateds);
			}


	}

	private boolean[] getAuthedListForm(ActionForm actionForm, HttpServletRequest request) {
		MessageListForm messageListForm = (MessageListForm) actionForm;
		boolean[] authenticateds = new boolean[messageListForm.getList().size()];

		// if there is no session, no login user, ignore it.
		if (request.getSession(false) == null)
			return authenticateds;

		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		com.jdon.jivejdon.model.Account account = accountService.getloginAccount();
		if (account == null)
			return authenticateds;// if login need auth check

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		int i = 0;
		for (Object o : messageListForm.getList()) {
			ForumMessage forumMessage = (ForumMessage) o;
			boolean result = forumMessageService.checkIsAuthenticated(forumMessage);
			authenticateds[i] = result;
			i++;
		}
		return authenticateds;
	}

}
