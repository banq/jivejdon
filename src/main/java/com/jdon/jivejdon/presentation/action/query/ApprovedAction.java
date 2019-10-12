package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.manager.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ApprovedAction extends ModelListAction {

	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}


	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		ThreadListSpec threadListSpec = null;
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		threadListSpec = new ThreadListSpec();
		threadListSpec.setResultSort(resultSort);
		return getForumMessageQueryService().getThreads(start, count, threadListSpec);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		try {
			return getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm,
								  HttpServletRequest request, ModelListForm modelListForm)
			throws Exception {
		Forum forum = new Forum();
		forum.setName("ThreadApprovedNewListAction");
		modelListForm.setOneModel(forum);
	}
}
