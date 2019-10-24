package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.util.ContainerUtil;

public class MethodDispatchAction extends DispatchAction {
	private final static Logger logger = LogManager.getLogger(MethodDispatchAction.class);

	public ActionForward deleteUserMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("enter userMessageListDelete");
		String username = request.getParameter("username");
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		forumMessageService.deleteUserMessages(username);

		String deluserprofile = request.getParameter("deluserprofile");
		if (deluserprofile != null)
			request.getRequestDispatcher(deluserprofile).forward(request, response);
		return mapping.findForward("deleteUserMessages");
	}

	public ActionForward deleteCacheKey(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ContainerUtil containerUtil = (ContainerUtil) WebAppUtil.getComponentInstance("containerUtil", request);
		containerUtil.getCacheManager().getCache().remove(request.getParameter("cacheKey"));
		return mapping.findForward("deleteCacheKey");
	}

}
