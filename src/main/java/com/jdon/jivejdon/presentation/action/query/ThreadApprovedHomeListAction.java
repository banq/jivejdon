package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.mapreduce.HomepageListSolver;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.strutsutil.ModelListAction;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadApprovedHomeListAction extends ModelListAction {
	private HomepageListSolver homepageListSolver;
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	public HomepageListSolver getHomepageListSolver() {
		if (homepageListSolver == null)
			homepageListSolver = (HomepageListSolver) WebAppUtil
					.getComponentInstance("homepageListSolver", this.servlet.getServletContext());
		return homepageListSolver;
	}

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

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start >= 150 || start % count != 0)
			return new PageIterator();

//		Collection<Long> list = getThreadApprovedNewList().getApprovedThreads(start);
		Collection<Long> list = getHomepageListSolver().getList( start,  count);
		if (list != null)
			return new PageIterator(list.size(), list.toArray(new Long[0]));
		else
			return new PageIterator();
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		try {
			return getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
