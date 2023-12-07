package com.jdon.jivejdon.presentation.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.PropertyService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadPropertys;

/**
 * displays the sticky thread list
 * 
 * @author oojdon
 * 
 */
public class StickyListAction extends Action {
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PropertyService propertyService = (PropertyService) WebAppUtil.getService("propertyService", this.servlet.getServletContext());

		String forumId = request.getParameter("forumId");
		setStickyThreadForAllForums(propertyService, request);
		setStickyThreadForThisForum(propertyService, request, forumId);

		return actionMapping.findForward("success");

	}

	private void setStickyThreadForThisForum(PropertyService propertyService, HttpServletRequest request, String forumId) throws Exception {
		PageIterator stickyids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.STICKY);
		PageIterator announceids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.ANNOUNCE);

		List<ForumThread> stickyList = getThreadList(stickyids);
		List<ForumThread> announceList = getThreadList(announceids);

		if (forumId != null && !forumId.equals("")) {
			filterForumThread(stickyList, Long.parseLong(forumId));
			filterForumThread(announceList, Long.parseLong(forumId));
		}

		request.setAttribute("stickyList", stickyList);
		request.setAttribute("announceList", announceList);

	}

	private void setStickyThreadForAllForums(PropertyService propertyService, HttpServletRequest request) throws Exception {
		PageIterator stickyids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.STICKY_ALL);
		PageIterator announceids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.ANNOUNCE_ALL);

		request.setAttribute("stickyList_all", getThreadList(stickyids));
		request.setAttribute("announceList_all", getThreadList(announceids));
	}

	private void filterForumThread(List<ForumThread> threads, Long forumId) {
		for (Iterator<ForumThread> iterator = threads.iterator(); iterator.hasNext();) {
			ForumThread t = iterator.next();
			long fid = t.getForum().getForumId();
			if (fid != forumId)
				iterator.remove();
		}
	}

	private List<ForumThread> getThreadList(PageIterator ids) throws Exception {
		List<ForumThread> list = new ArrayList<ForumThread>();
		while (ids.hasNext()) {
			Long id = (Long) ids.next();
			ForumThread thread = getForumMessageQueryService().getThread(id);
			list.add(thread);
		}
		return list;

	}

}
