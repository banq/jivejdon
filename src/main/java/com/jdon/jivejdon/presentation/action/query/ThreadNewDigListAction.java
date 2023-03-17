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
package com.jdon.jivejdon.presentation.action.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.mapreduce.HomepageListSolver;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 */
public class ThreadNewDigListAction extends ModelListAction {
	private final static String module = ThreadNewDigListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadViewCounterJob threadViewCounterJob;
	private HomepageListSolver homepageListSolver;
	private ThreadApprovedNewList threadApprovedNewList;

	
	private ThreadApprovedNewList getThreadApprovedNewList(){
		if (threadApprovedNewList == null)
		   threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil.getComponentInstance("threadApprovedNewList",
				this.servlet.getServletContext());
	    return threadApprovedNewList;
	}

	public HomepageListSolver getHomepageListSolver() {
		if (homepageListSolver == null)
			homepageListSolver = (HomepageListSolver) WebAppUtil
					.getComponentInstance("homepageListSolver", this.servlet.getServletContext());
		return homepageListSolver;
	}

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	private ThreadViewCounterJob getThreadViewCounterJob() {
		if (threadViewCounterJob == null)
			threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob",
					this.servlet.getServletContext());
		return threadViewCounterJob;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		int DigsListMAXSize = 20;
		Collection<Long> allIds = new ArrayList<Long>();
		allIds = getThreadApprovedNewList().getThreadDigList().getDigs(DigsListMAXSize).stream()
				.filter(e -> e.getRootMessage().getDigCount() > 1).map(e -> e.getThreadId())
				.collect(Collectors.toList());

		// Collection<Long> allIds = new ArrayList<Long>();
		// allIds = getThreadViewCounterJob().getThreadIdsList().stream()
		// 		.filter(e -> !getHomepageListSolver().getList().contains(e)).collect(Collectors.toList());
		return new PageIterator(allIds.size(), allIds.toArray());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
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

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			ModelListForm modelListForm) throws Exception {
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
				this.servlet.getServletContext());
		String forumId = request.getParameter("forum");
		if (forumId == null)
			forumId = request.getParameter("forumId");

		Forum forum = null;
		if ((forumId == null) || !UtilValidate.isInteger(forumId) || forumId.length() > 10) {
			forum = new Forum();
			forum.setName("主题总表");
		} else {
			forum = forumService.getForum(new Long(forumId));
		}
		if (forum == null)
			throw new Exception("forum is null forumid=" + forumId);
		modelListForm.setOneModel(forum);
	}

}
