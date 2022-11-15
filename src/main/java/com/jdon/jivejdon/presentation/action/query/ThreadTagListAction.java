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
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigList;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.UtilValidate;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ThreadTagListAction extends Action {
	private TagService tagService;
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public TagService getTagService() {
		if (tagService == null)
			tagService = (TagService) WebAppUtil.getService("othersService", this.servlet.getServletContext());
		return tagService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String threadId = request.getParameter("threadId");
		if (threadId == null || !UtilValidate.isInteger(threadId) || threadId.length() > 10) {
			return mapping.findForward("failure");
		}
		String tagID = request.getParameter("tagID");
		if (tagID == null || !UtilValidate.isInteger(tagID) || tagID.length() > 10) {
			return mapping.findForward("failure");
		}

		Collection<ForumThread> threadList = getForumThreadsForTag(tagID, Long.parseLong(threadId));				

		ModelListForm threadListForm = (ModelListForm) form;
		threadListForm.setList(threadList);
		threadListForm.setAllCount(threadList.size());
		return mapping.findForward("success");
	}

	private Collection<ForumThread> getForumThreadsForTag(String tagID, final Long threadId) {
		TaggedThreadListSpec taggedThreadListSpec = new TaggedThreadListSpec();
		taggedThreadListSpec.setTagID(Long.parseLong(tagID));
		PageIterator pageIterator = getTagService().getTaggedThread(taggedThreadListSpec, 0, 10);
		Collection<ForumThread> threads = new ArrayList<ForumThread>();
		Set<Long> foundThreadIds = new HashSet<Long>();
		while (pageIterator.hasNext()) {
			Long threadId1 = (Long) pageIterator.next();
			if (threadId1.longValue() != threadId.longValue() && !foundThreadIds.contains(threadId1)) {
				threads.add( getForumMessageQueryService().getThread(threadId1));
				foundThreadIds.add(threadId1);				
			}
		}		
		return threads;
	}
}
