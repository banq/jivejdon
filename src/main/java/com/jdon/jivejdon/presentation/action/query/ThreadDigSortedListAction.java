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
import com.jdon.jivejdon.domain.model.query.HoThreadCriteria;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

public class ThreadDigSortedListAction extends ModelListAction {
	  private final static String module = ThreadDigSortedListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}



	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		if ( start % 30 != 0)
			return new PageIterator();

		HoThreadCriteria queryCriteria = new HoThreadCriteria();

		String dateRange = "1";
		if (request.getParameter("dateRange") != null)
			dateRange = request.getParameter("dateRange");
		queryCriteria.setDateRange(dateRange);
		Debug.logVerbose("ThreadDigSortedListAction dateRange=" + dateRange + " count=" + count, module);

		String digCountWindowS = request.getParameter("digCountWindow");
		queryCriteria.setDigCountWindow(UtilValidate.isEmpty(digCountWindowS) ? 5 : Integer.parseInt(digCountWindowS));

		// Use the new getDigThreads method in ForumMessageQueryService
		return getForumMessageQueryService().getDigThreads(queryCriteria, start, count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumThread thread = null;
		try {
			thread = getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {

		}
		return thread;
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm,
								  HttpServletRequest request, ModelListForm modelListForm)
			throws Exception {
		ForumService forumService = (ForumService) WebAppUtil.getService
				("forumService", this.servlet.getServletContext());
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
