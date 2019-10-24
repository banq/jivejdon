package com.jdon.jivejdon.presentation.action.tag;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

public class MethodDispatchAction extends DispatchAction {
	private final static Logger logger = LogManager.getLogger(MethodDispatchAction.class);
	private CustomizedThrottle customizedThrottle;

	public ActionForward tags(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		Collection c = new ArrayList();
		request.setAttribute("TAGS", c);
		String q = request.getParameter("term");
		if (UtilValidate.isEmpty(q))
			q = request.getParameter("q");
		if (UtilValidate.isEmpty(q) || q.indexOf("%20") != -1)
			return mapping.findForward("tags");
		if (q.length() > 10)
			return mapping.findForward("tags");

		if (!checkSpamHit("tags", request)) {
			logger.error("MethodDispatchAction this is spam =" + request.getRemoteAddr());
			return mapping.findForward("tags");
		}

		TagService othersService = (TagService) WebAppUtil.getService("othersService", this
				.servlet.getServletContext());
		c = othersService.tags(q);
		request.setAttribute("TAGS", c);
		return mapping.findForward("tags");
	}

	private boolean checkSpamHit(String oid, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance
					("customizedThrottle", this.servlet.getServletContext());
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), oid);
		return customizedThrottle.processHitFilter(hitKey);
	}

	public ActionForward savetags(ActionMapping mapping, ActionForm form, HttpServletRequest
			request, HttpServletResponse response) throws Exception {
		logger.debug("enter savetags");
		String[] tagTitle = request.getParameterValues("tagTitle");
		String threadId = request.getParameter("threadId");
		if (!checkAuth(threadId, request)) {
			logger.error("savetags error : no auth" + request.getRemoteAddr());
			mapping.findForward("savetags");
		}
		TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
		othersService.saveTag(new Long(threadId), tagTitle);
		return mapping.findForward("savetags");
	}

	public boolean checkAuth(String threadId, HttpServletRequest request) {
		boolean isOK = false;
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService",
				request);
		Account account = accountService.getloginAccount();
		if (account == null) return isOK;
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService
				("forumMessageService", request);
		ForumThread forumThread = forumMessageService.getThread(Long.parseLong
				(threadId));
		if (forumThread != null)
			isOK = forumMessageService.checkIsAuthenticated(forumThread.getRootMessage());
		return isOK;
	}
}
