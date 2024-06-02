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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadContext;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageListAction extends ModelListAction {
	private final static String module = MessageListAction.class.getName();
	private ConcurrentMap<String, Object> serviceCache = new ConcurrentHashMap<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private ForumMessageQueryService getForumMessageQueryService() {
		return (ForumMessageQueryService) serviceCache.computeIfAbsent("forumMessageQueryService",
				k -> WebAppUtil.getService("forumMessageQueryService",
						this.servlet.getServletContext()));
	}

	private ThreadViewCounterJob getThreadViewCounterJob() {
		return (ThreadViewCounterJob) serviceCache.computeIfAbsent("threadViewCounterJob" ,k ->WebAppUtil.getComponentInstance("threadViewCounterJob",
					this.servlet.getServletContext()));
	}

	private ThreadContext getThreadContext() {
		return (ThreadContext) serviceCache.computeIfAbsent("threadContext" ,k ->WebAppUtil.getComponentInstance("threadContext",
		this.servlet.getServletContext()));
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
		if (threadId == null || threadId.length() > 12 || !StringUtils.isNumeric(threadId)) {
			Debug.logError(" getPageIterator error : threadId is null" + threadId, module);
			return new PageIterator();
		}
	
		try {
			PageIterator pageIterator = getForumMessageQueryService().getMessages(Long.parseLong(threadId), start, count);
			if(pageIterator == null || pageIterator.getAllCount() == 0)  {
				Debug.logError(" getPageIterator error : thread is null"  + threadId, module);
				return new PageIterator();
			}
			

			CompletableFuture<List<ForumThread>> future = CompletableFuture.supplyAsync(() -> {
				return getForumMessageQueryService().getThread(Long.parseLong(threadId));
			}).thenApplyAsync(forumThread -> {
				forumThread.getReBlogVO().loadAscResult();
				return getThreadContext().createsThreadLinks(forumThread);
			});
			serviceCache.putIfAbsent(threadId, future);

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

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			ModelListForm modelListForm) throws Exception {
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!StringUtils.isNumeric(threadId)) || threadId.length() > 10) {
			Debug.logError("customizeListForm error : threadId is null", module);
			return;
		}

		try {
			@SuppressWarnings("unchecked")
			CompletableFuture<List<ForumThread>> future = (CompletableFuture<List<ForumThread>>) serviceCache.get(threadId);
			request.setAttribute("threadLinkList", future.join());
			serviceCache.remove(threadId);

			ForumThread forumThread = getForumMessageQueryService().getThread(Long.parseLong(threadId));
			if (forumThread == null)
		    	return;

			modelListForm.setOneModel(forumThread);

			executorService.execute(() -> {
				getThreadViewCounterJob().saveViewCounter(forumThread.addViewCount(request.getRemoteAddr()));
			});

		} catch (Exception e) {
			Debug.logError(" customizeListForm err:" + threadId, module);
			return;
		}

	}
	

}
