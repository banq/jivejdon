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
import com.jdon.jivejdon.spi.component.mapreduce.HomepageListSolver;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigList;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.strutsutil.ModelListForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ThreadDigListAction extends Action {
	private ThreadViewCounterJob threadViewCounterJob;
	private ForumMessageQueryService forumMessageQueryService;
	
	private ThreadViewCounterJob getThreadViewCounterJob() {
		if (threadViewCounterJob == null)
			threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob",
					this.servlet.getServletContext());
		return threadViewCounterJob;
	}

	private ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
		forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getComponentInstance("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		int DigsListMAXSize = 30;
		String wSize = request.getParameter("wSize") ;
		if(wSize != null && wSize.length() > 0){
			DigsListMAXSize = Integer.parseInt(wSize);
		}
		ModelListForm threadListForm = (ModelListForm) form;
		ThreadApprovedNewList threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil.getComponentInstance("threadApprovedNewList",
				this.servlet.getServletContext());
		Collection<Long> digList = threadApprovedNewList.getThreadDigList().getDigThreadIds(DigsListMAXSize);
		Collection<ForumThread> digThreads = new ArrayList<>();
		if (wSize != null) {
			digThreads = digList.stream().skip((int) (digList.size() * Math.random()))
					.filter(e -> getThreadViewCounterJob().getThreadIdsList().contains(e))
					.map(e -> getForumMessageQueryService().getThread(e)).collect(Collectors.toList());
		}else{
			digThreads = digList.stream().map(e -> getForumMessageQueryService().getThread(e)).collect(Collectors.toList());
		}
		threadListForm.setList(digThreads);
		threadListForm.setAllCount(digThreads.size());
		return mapping.findForward("success");
	}
}
