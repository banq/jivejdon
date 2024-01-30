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
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageListOwnerAction extends ModelListAction {
	private final static String module = MessageListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;	

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		Debug.logVerbose("enter getPageIterator", module);
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!StringUtils.isNumeric(threadId)) || threadId.length() > 10) {
			Debug.logError(" getPageIterator error : threadId is null", module);
			return new PageIterator();
		}

		return getForumMessageQueryService().getMessages(Long.parseLong(threadId), start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 * .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		Debug.logVerbose("enter findModelByKey", module);

		// getXXX can be intercepted by cacheinterceptor before accessing
		// ForumMessageServiceShell
		ForumMessage forumMessage = getForumMessageQueryService().getMessage((Long) key);

		return forumMessage;
	}

	/**
	 * set ModelListAction donot load directly a model from cache.
	 */
	protected boolean isEnableCache() {
		return false;
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			ModelListForm modelListForm) throws Exception {
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!StringUtils.isNumeric(threadId)) || threadId.length() > 10) {
			Debug.logError("customizeListForm error : threadId is null", module);
			return;
		}

		try {
			ForumThread forumThread = getForumMessageQueryService().getThread(Long.parseLong(threadId));
			if (forumThread == null)
				throw new Exception("thread is null " + threadId);

			modelListForm.setOneModel(forumThread);

			if (request.getSession(false) != null) {
				boolean[] authenticateds = getAuthedListForm(actionForm, request);
				MessageListForm messageListForm = (MessageListForm) actionForm;
				messageListForm.setAuthenticateds(authenticateds);
				
				request.setAttribute("principal", request.getUserPrincipal());
			}

		} catch (Exception e) {
			Debug.logError(" customizeListForm err:", module);
			return;
		}

	}

	protected boolean[] getAuthedListForm(ActionForm actionForm, HttpServletRequest request) {
		MessageListForm messageListForm = (MessageListForm) actionForm;
		boolean[] authenticateds = new boolean[messageListForm.getList().size()];

		// if there is no session, no login user, ignore it.
		if (request.getSession(false) == null)
			return authenticateds;

		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());
		Account account = accountService.getloginAccount();
		if (account == null)
			return authenticateds;// if login need auth check

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
				request);
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
