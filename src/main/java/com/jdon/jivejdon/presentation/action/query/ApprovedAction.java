package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.UtilValidate;
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
	  if (request.getParameter("tagID") != null)
	  	 return getPageIteratorTagID(request, start, count);
	  else
	  	 return getPageIteratorAll(request, start, count);

	}

	public PageIterator getPageIteratorAll(HttpServletRequest request, int start, int count) {
		ThreadListSpec threadListSpec = null;
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		threadListSpec = new ThreadListSpec();
		threadListSpec.setResultSort(resultSort);
		return getForumMessageQueryService().getThreads(start, count, threadListSpec);
	}

	public PageIterator getPageIteratorTagID(HttpServletRequest request, int start, int count) {
		TagService othersService = (TagService) WebAppUtil.getService("othersService", this.servlet.getServletContext());
		String tagID = request.getParameter("tagID");
		if (tagID == null || !StringUtils.isNumeric(tagID) || tagID.length()>10) {
			return new PageIterator();
		}
		ThreadTag tag = othersService.getThreadTag(new Long(tagID));
		if (tag == null)
			return new PageIterator();
		request.setAttribute("TITLE", tag.getTitle());
		request.setAttribute("threadTag", tag);
		return othersService.getTaggedThread(new Long(tagID), start, count);
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
