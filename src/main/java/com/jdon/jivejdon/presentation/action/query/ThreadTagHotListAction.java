package com.jdon.jivejdon.presentation.action.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigComparator;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadTagList;
import com.jdon.strutsutil.ModelListForm;

public class ThreadTagHotListAction extends Action {

	private ThreadApprovedNewList threadApprovedNewList;
	private ForumMessageQueryService forumMessageQueryService;

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext());

		return threadApprovedNewList;
	}

	private ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelListForm tagsHotListForm = (ModelListForm) form;
		ThreadTagList threadTagList = getThreadApprovedNewList().getThreadTagList();
		List<ThreadTag> tags = threadTagList.getThreadTags();
		tagsHotListForm.setList(tags);
		tagsHotListForm.setAllCount(tags.size());

		int offset = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
		int count = request.getParameter("count") == null ? 5 : Integer.parseInt(request.getParameter("count"));
		int firstTag = Math.max(0, offset);
		int lastTag = Math.min(tags.size(), firstTag + Math.max(0, count));
		TagService tagService = (TagService) WebAppUtil.getService("othersService", this.servlet.getServletContext());
		Map<String, List<ForumThread>> taggedThreadsByTag = new HashMap<>();
		for (int i = firstTag; i < lastTag; i++) {
			Long tagID = tags.get(i).getTagID();
			PageIterator pageIterator = tagService.getTaggedThread(tagID, 0, 10);
			TreeSet<Long> threadIds = Arrays.stream(pageIterator.getKeys())
					.map(id -> (Long) id)
					.filter(id -> getForumMessageQueryService().getThread(id) != null)
					.collect(Collectors.toCollection(
							() -> new TreeSet<>(new ThreadDigComparator(getForumMessageQueryService()))));
			List<ForumThread> threads = threadIds.stream()
					.map(id -> getForumMessageQueryService().getThread(id))
					.collect(Collectors.toList());
			taggedThreadsByTag.put(tagID.toString(), threads);
		}
		request.setAttribute("taggedThreadsByTag", taggedThreadsByTag);
		// request.setAttribute("Tags_ImageUrls", threadTagList.getImageUrls());
		return mapping.findForward("success");
	}
}
