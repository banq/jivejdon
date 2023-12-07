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

public class ThreadDigSortedListAction extends ModelListAction {
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext
							());

		return threadApprovedNewList;
	}

	@Override
	public PageIterator getPageIterator(HttpServletRequest httpServletRequest, int start, int
			count) {

		if (start >= ThreadApprovedNewList.maxSize || start % count != 0)
			return new PageIterator();

		ThreadDigList messageDigList = getThreadApprovedNewList().getThreadDigList();
		int maxSize = ThreadApprovedNewList.maxSize;
		if (getThreadApprovedNewList().getMaxSize() != -1) {
			maxSize = getThreadApprovedNewList().getMaxSize();
		}
		
		PageIterator pageIterator =  messageDigList.getPageIterator(start, count);
		pageIterator.setAllCount(maxSize);
		return pageIterator;
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
			forum = forumService.getForum(new Long(forumId));
		}
		if (forum == null)
			throw new Exception("forum is null forumid=" + forumId);
		modelListForm.setOneModel(forum);
	}
}
