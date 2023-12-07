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
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigList;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;

/**
 * Random list dig collection
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 */
public class ThreadRandomDigListAction extends ModelListAction {
	private final static String module = ThreadRandomDigListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil.getComponentInstance("threadApprovedNewList",
					this.servlet.getServletContext());

		return threadApprovedNewList;
	}

	@Override
	public PageIterator getPageIterator(HttpServletRequest httpServletRequest, int start, int count) {
		ThreadDigList messageDigList = getThreadApprovedNewList().getThreadDigList();
		return messageDigList.getRandomPageIterator(count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumThread thread = null;
		try {
			thread = getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {

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
		if ((forumId == null) || !StringUtils.isNumeric(forumId) || forumId.length() > 10) {
			forum = new Forum();
			forum.setName("主题总表");
		} else {
			forum = forumService.getForum(Long.parseLong(forumId));
		}
		if (forum == null)
			throw new Exception("forum is null forumid=" + forumId);
		modelListForm.setOneModel(forum);
	}

}
