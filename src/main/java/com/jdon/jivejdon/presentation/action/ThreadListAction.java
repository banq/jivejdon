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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpecForMod;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ThreadListAction extends ModelListAction {
	private final static String module = ThreadListAction.class.getName();

	private ConcurrentMap<String, Object> serviceCache = new ConcurrentHashMap<>();

	private ForumMessageQueryService getForumMessageQueryService() {
		return (ForumMessageQueryService) serviceCache.computeIfAbsent("forumMessageQueryService",
				k -> WebAppUtil.getService("forumMessageQueryService",
						this.servlet.getServletContext()));

	}

	private ForumService getForumService() {
		return (ForumService) serviceCache.computeIfAbsent("forumService",
				k -> WebAppUtil.getService("forumService",
						this.servlet.getServletContext()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start % 30 != 0) 
			return new PageIterator();
		String forumId = request.getParameter("forum");

		ThreadListSpec threadListSpec = null;
		ResultSort resultSort = new ResultSort();
		if (request.getParameterMap().containsKey("ASC")) {// DESC ASC
			resultSort.setOrder_ASCENDING();
			request.setAttribute("ASC", "ASC");// for display
			threadListSpec = new ThreadListSpec();
			threadListSpec.setResultSort(resultSort);
		} else if (request.getParameterMap().containsKey("DESC")) {
			resultSort.setOrder_DESCENDING();
			threadListSpec = new ThreadListSpec();
			threadListSpec.setResultSort(resultSort);
		} else {
			resultSort.setOrder_DESCENDING();
			threadListSpec = new ThreadListSpecForMod();
			threadListSpec.setResultSort(resultSort);
		}

		if (forumId == null)
			forumId = request.getParameter("forumId");
		if ((forumId == null) || !StringUtils.isNumeric(forumId) || forumId.length()>10) {
			return getForumMessageQueryService().getThreads(start, count, threadListSpec);
		} else
			return getForumMessageQueryService().getThreads(Long.parseLong(forumId), start, count,
					resultSort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 * .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumThread thread = null;
		try {
			thread = getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {
			Debug.logError("getThread error:" + e, module);
		}
		return thread;
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, ModelListForm modelListForm)
			throws Exception {
		String forumId = request.getParameter("forum");
		if (forumId == null)
			forumId = request.getParameter("forumId");

		Forum forum = null;
		if ((forumId == null) || !StringUtils.isNumeric(forumId) || forumId.length()>10) {
			forum = new Forum();
			forum.setName("");

		} else if(Long.valueOf(forumId).longValue()>200){
		//for the bot or DDOS attack
			forum = new Forum();
			forum.setName("");
		} else {
			forum = getForumService().getForum(Long.parseLong(forumId));
		}
		if (forum == null)
			throw new Exception("forum is null forumid=" + forumId);
		modelListForm.setOneModel(forum);
	}

}
