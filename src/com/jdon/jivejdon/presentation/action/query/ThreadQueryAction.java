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
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;

/**
 * ThreadQueryAction is call from Form by /query/threadViewQuery.shtml
 * ThreadQueryAction important is in query date format. need queryForm but
 * ThreadHotAction donot need queryForm. int ThreadQueryAction. queryForm is not
 * created by it, queryForm is created by before action : QueryViewAction.
 * ThreadHotAction is simple than ThreadQueryAction.
 * 
 * HOT1: date inpute is date range such as before 1 days, 2 days, 365 days HOT2:
 * date inpute is from yyyy-mm-dd to yyyy-mm-dd
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class ThreadQueryAction extends ModelListAction {
	public final static String QUERY_TYPE1 = "HOT1";
	public final static String QUERY_TYPE2 = "HOT2";

	private final static String module = ThreadQueryAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		Debug.logVerbose("enter ThreadQueryAction ....", module);
		QueryForm qForm = (QueryForm) FormBeanUtil.lookupActionForm(request, "queryForm");
		if (qForm == null) {
			Debug.logError(" ThreadQueryForm is null, at first call /query/threadViewQuery.shtml :", module);
		}
		// save queryCriteria for html:link multi params
		request.setAttribute("paramMaps", qForm.getParamMaps());

		QueryCriteria queryCriteria = create(qForm);
		PageIterator pi = getForumMessageQueryService().getHotThreads(queryCriteria, start, count);
		Debug.logVerbose("found pi " + pi.getAllCount(), module);
		return pi;
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

	protected QueryCriteria create(QueryForm qForm) {

		HoThreadCriteria queryCriteria = new HoThreadCriteria();
		queryCriteria.setMessageReplyCountWindow(qForm.getMessageReplyCountWindow());
		String queryType = qForm.getQueryType();
		// client: index.jsp threadList.jsp
		if (queryType.equals(QUERY_TYPE1)) {// HOT1 is dataRange
			Debug.logVerbose("queryType is " + QUERY_TYPE1, module);
			queryCriteria.setDateRange(qForm.getDateRange());
			queryCriteria.setForumId(qForm.getForumId());
			// fun(queryCriteria, qForm);
			Debug.logVerbose("dateRange=" + qForm.getDateRange(), module);
			return queryCriteria;
			// client: queryView.jsp
		} else if (queryType.equals(QUERY_TYPE2)) {
			Debug.logVerbose("queryType is " + QUERY_TYPE2, module);
			queryCriteria.setForumId(qForm.getForumId());
			queryCriteria.setFromDate(qForm.getFromDate());
			queryCriteria.setToDate(qForm.getToDate());
			Debug.logVerbose("fromDate=" + queryCriteria.getFromDateString(), module);
			Debug.logVerbose("toDate=" + queryCriteria.getToDateString(), module);
			return queryCriteria;
		} else {
			return queryCriteria;
		}
	}
	/**
	 * 
	 * @param hmc
	 * @param qForm
	 * 
	 * 
	 *            private void fun(HoThreadCriteria hmc, QueryForm qForm) {
	 * 
	 * 
	 *            String date = hmc.getFromDateString(); int dateSlash1 =
	 *            date.indexOf("/"); int dateSlash2 = date.lastIndexOf("/");
	 *            String month = date.substring(0, dateSlash1); String day =
	 *            date.substring(dateSlash1 + 1, dateSlash2); String year =
	 *            date.substring(dateSlash2 + 1);
	 * 
	 *            qForm.setFromDate(year + "-" + month + "-" + day);
	 * 
	 *            date = hmc.getToDateString(); dateSlash1 = date.indexOf("/");
	 *            dateSlash2 = date.lastIndexOf("/"); month = date.substring(0,
	 *            dateSlash1); day = date.substring(dateSlash1 + 1, dateSlash2);
	 *            year = date.substring(dateSlash2 + 1);
	 * 
	 *            qForm.setToDate(year + "-" + month + "-" + day);
	 * 
	 * 
	 *            }
	 */
}
