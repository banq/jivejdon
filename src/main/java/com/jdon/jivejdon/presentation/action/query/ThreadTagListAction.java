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

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadContext;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;

public class ThreadTagListAction extends ModelListAction {
	private final static String module = ThreadTagListAction.class.getName();
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

	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		String threadId = request.getParameter("threadId");
		if (threadId == null || !StringUtils.isNumeric(threadId) || threadId.length() > 10) {
			return null;
		}
		ForumThread thread = getForumMessageQueryService().getThread(Long.parseLong(threadId));
		if (thread == null)
			return null;

		try {
			ThreadContext threadContext = (ThreadContext)WebAppUtil.getComponentInstance("threadContext", request);
		    Set<Long> threadIdsPN = threadContext.getThreadListInContext(thread);
	        return new PageIterator(threadIdsPN.size(), threadIdsPN.toArray());
		} catch (Exception e) {
			return new PageIterator();
		}

	}


	public Object findModelIFByKey(HttpServletRequest request, Object key) {
        ForumThread thread = null;
        try {
            thread = getForumMessageQueryService().getThread((Long) key);
        } catch (Exception e) {
            Debug.logError("getThread error:" + e, module);
        }
        return thread;
    }
}
