package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

/**
 * used for threadViewQuery.shtml?queryType=userMessageQueryAction&userID=62934
 * 
 * not used!
 */
public class ThreadQueryUserAction extends ModelListAction {
	private final static String module = ThreadQueryUserAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		Debug.logVerbose("enter MessageQueryAction ....", module);
		QueryForm qForm = (QueryForm) FormBeanUtil.lookupActionForm(request, "queryForm");
		if (qForm == null) {
			qForm = new QueryForm();
			loadForumOptions(request, qForm);
		}

		// save queryCriteria for html:link multi params
		request.setAttribute("paramMaps", qForm.getParamMaps());

		MultiCriteria queryCriteria = new MultiCriteria();
		queryCriteria.setForumId(qForm.getForumId());

		String userId = request.getParameter("userID");
		if (userId == null) {
			userId = request.getParameter("user"); // for old version
		}
		if (userId == null) {
			userId = request.getParameter("userId"); // for old version
		}
		if (UtilValidate.isEmpty(userId)) {
			Debug.logError("userId == null", module);
			return new PageIterator();
		}

		qForm.setUserID(userId);
		queryCriteria.setUserID(userId);

		qForm.setFromDate("2000-01-01");
		queryCriteria.setFromDate(qForm.getFromDate());
		Debug.logVerbose("fromDate=" + queryCriteria.getFromDateString(), module);
		Debug.logVerbose("toDate=" + queryCriteria.getToDateString(), module);

		PageIterator pi = getForumMessageQueryService().getThreads(queryCriteria, start, count);
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

	private void loadForumOptions(HttpServletRequest request, QueryForm qForm) {
		if (qForm.getForums().size() != 0) {
			return;
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", this.servlet.getServletContext());
		PageIterator pi = forumService.getForums(0, 30);
		Object[] ids = pi.getKeys();
		for (int i = 0; i < ids.length; i++) {
			qForm.getForums().add(forumService.getForum((Long) ids[i]));
		}
	}

}
