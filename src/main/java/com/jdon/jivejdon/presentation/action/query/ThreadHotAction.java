/*
 * Copyright 2003-2006 the original author or authors.
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

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.HoThreadCriteria;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

/**
 * difference with ThreadPopularAction ThreadPopularAction is simple, only for
 * one page , no multi pages. ThreadPopularAction is no messageReplyCountWindow,
 * donot need sorted by message replies ThreadHotAction is simple than
 * ThreadQueryAction.
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class ThreadHotAction extends ModelListAction {

	private final static String module = ThreadHotAction.class.getName();

	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		HoThreadCriteria queryCriteria = new HoThreadCriteria();

		String dateRange = "1";
		if (request.getParameter("dateRange") != null)
			dateRange = request.getParameter("dateRange");
		queryCriteria.setDateRange(dateRange);
		Debug.logVerbose("ThreadHotAction dateRange=" + dateRange + " count=" + count, module);

		String messageReplyCountWindowS = request.getParameter("messageReplyCountWindow");
		int messageReplyCountWindow = 10; // if reply num is greate than 10,
											// it
		// is hot thread
		if (!UtilValidate.isEmpty(messageReplyCountWindowS)) {
			messageReplyCountWindow = Integer.parseInt(messageReplyCountWindowS);
		}
		queryCriteria.setMessageReplyCountWindow(messageReplyCountWindow);

		return getForumMessageQueryService().getHotThreads(queryCriteria, start, count);
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
