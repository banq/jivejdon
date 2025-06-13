package com.jdon.jivejdon.presentation.action.query;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigComparator;
import com.jdon.strutsutil.ModelListAction;

public class ThreadApprovedNewListAction extends ModelListAction {
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext());

		return threadApprovedNewList;
	}
//
//	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ActionForward actionForward = super.execute(actionMapping, actionForm,request,response);
//		ModelListForm listForm = this.getModelListForm(actionMapping, actionForm, request);
//		List sortedList = (List)listForm.getList().stream().sorted(new HomePageComparator()).collect(Collectors.toList());
//		listForm.setList(sortedList);
//		return actionForward;
//	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start >= ThreadApprovedNewList.maxSize || start % 30 != 0)
			return new PageIterator();

		Collection<Long> list = getThreadApprovedNewList().getApprovedThreads(start, count).stream()
				.sorted(new ThreadDigComparator(getForumMessageQueryService())).collect(Collectors.toList());
		int maxSize = ThreadApprovedNewList.maxSize;
		if (getThreadApprovedNewList().getMaxSize() != -1) {
			maxSize = getThreadApprovedNewList().getMaxSize();
		}
		if (list != null)
			return new PageIterator(maxSize, list.toArray(new Long[0]));
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
