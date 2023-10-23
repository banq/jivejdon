package com.jdon.jivejdon.presentation.action.query;



import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadTagList;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

public class ThreadTagHotList2Action extends ModelListAction {
	private final static String module = ThreadTagHotList2Action.class.getName();

	private ThreadApprovedNewList threadApprovedNewList;
	private ForumMessageQueryService forumMessageQueryService;

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

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		String tagID = request.getParameter("tagID");
		if (tagID == null || !UtilValidate.isInteger(tagID) || tagID.length() > 10) {
			return new PageIterator();
		}

		TagService othersService = (TagService) WebAppUtil.getService("othersService",
				this.servlet.getServletContext());
		ThreadTag tag = othersService.getThreadTag(new Long(tagID));
		if (tag == null)
			return new PageIterator();
		request.setAttribute("TITLE", tag.getTitle());
		request.setAttribute("threadTag", tag);

		ThreadTagList threadTagList = getThreadApprovedNewList().getThreadTagList();
		TreeSet<Long> threadIds = threadTagList.getTagThreadIds(Long.parseLong(tagID));
		if(threadIds.size()<5){
			TaggedThreadListSpec taggedThreadListSpec = new TaggedThreadListSpec();
		    taggedThreadListSpec.setTagID(new Long(tagID));
			return othersService.getTaggedThread(taggedThreadListSpec, start, count);
		}
		return new PageIterator(threadIds.size(), threadIds.toArray());
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
