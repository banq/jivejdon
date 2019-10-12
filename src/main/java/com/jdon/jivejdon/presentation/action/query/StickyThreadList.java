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

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.property.ThreadPropertys;
import com.jdon.jivejdon.presentation.form.ThreadListForm;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.jivejdon.service.property.PropertyService;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

public class StickyThreadList extends Action {

	private Collection<ForumThread> stickyThreadList = new ArrayList();

	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public StickyThreadList() {
		Runnable task = new Runnable() {
			public void run() {
				stickyThreadList.clear();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 30, 60 * 60 * 48, TimeUnit.SECONDS);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ThreadListForm threadListForm = (ThreadListForm) form;

		Collection list = getStickyThreadList(request);
		threadListForm.setList(list);

		return mapping.findForward("success");

	}

	public Collection getStickyThreadList(HttpServletRequest request) {
		if (!stickyThreadList.isEmpty()) {
			return stickyThreadList;
		}

		Collection<ForumThread> results = new ArrayList();
		PropertyService propertyService = (PropertyService) WebAppUtil.getService("propertyService", this.servlet.getServletContext());
		try {
			PageIterator stickyids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.STICKY_ALL);

			while (stickyids.hasNext()) {
				Long id = (Long) stickyids.next();
				ForumThread thread = getForumMessageQueryService().getThread(id);
				results.add(thread);
			}
			stickyThreadList = results;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

}
