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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadContext;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageListAction extends ModelListAction {
	private final static String module = MessageListAction.class.getName();

	private ForumMessageQueryService getForumMessageQueryService() {
		return (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
				this.servlet.getServletContext());
	}

	private ThreadContext getThreadContext() {
		return (ThreadContext) WebAppUtil.getComponentInstance("threadContext",
				this.servlet.getServletContext());
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelListForm listForm = this.getModelListForm(actionMapping, actionForm, request);
		if (listForm == null) {
			return actionMapping.findForward("failure");
		}
		String threadId = request.getParameter("thread");
		if (threadId == null || threadId.length() > 12 || !StringUtils.isNumeric(threadId)) {
			Debug.logError(" getPageIterator error : threadId is null" + threadId, module);
			return actionMapping.findForward("failure");
		}

		long threadIdL = Long.parseLong(threadId);
		ForumThread forumThreadExist = getForumMessageQueryService().getThread(threadIdL);
		if (forumThreadExist == null) {
			Debug.logError(" getThread error : thread is gone" + threadId, module);
			return actionMapping.findForward("failure");
		}

		CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
			return getForumMessageQueryService().getThread(threadIdL);
		}).thenAccept(forumThread -> {
			forumThread.getReBlogVO().loadAscResult();
			request.setAttribute("threadPreNextList", getThreadContext().getThreadListInContext(forumThread));
			request.setAttribute("threadLinkList", getThreadContext().createsThreadLinks(forumThread));

		});

		try {
			if (forumThreadExist.getState().getMessageCount() > 0) {
				super.execute(actionMapping, listForm, request, response);
			}else{

				 listForm.setAllCount(1);
				 List<ForumMessage> list = new ArrayList<>(1);
				 list.add(forumThreadExist.getRootMessage());
				 listForm.setList(list);

			}
		} catch (Exception var9) {
			Debug.logError(" super.execute err: " + threadId, module);
		}

		CompletableFuture.allOf(future1).join();
		listForm.setOneModel(forumThreadExist);
		
		return actionMapping.findForward("success");
	}

	/*
	 * get a PageIterator Collection for the thread
	 * 
	 * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start % 15 != 0) {
			return new PageIterator();
		}

		Debug.logVerbose("enter getPageIterator", module);
        String threadId = request.getParameter("thread");
		try {
			PageIterator pageIterator = getForumMessageQueryService().getMessages(Long.parseLong(threadId), start, count);
			if (pageIterator == null || pageIterator.getAllCount() == 0) {
				Debug.logError(" getPageIterator error : thread is null" + threadId, module);
			}

			return pageIterator;
		} catch (Exception nfe) {
			return new PageIterator();
		}
	}

	/*
	 * get every message in a thread
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

	

}
