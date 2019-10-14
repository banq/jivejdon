package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.component.viewcount.ThreadViewCounterJob;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.filter.SpamFilterTooFreq;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

public class ViewThreadAction extends Action {

	private CustomizedThrottle customizedThrottle;
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadViewCounterJob threadViewCounterJob;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.getWriter().close();
//		String requestType = request.getHeader("X-Requested-With");
//		if (requestType == null || !requestType.equalsIgnoreCase("XMLHttpRequest")) {
//			response.sendError(404);
//			return null;
//		}

		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!UtilValidate.isInteger(threadId)) || threadId.length() > 10) {
			((HttpServletResponse) response).sendError(404);
			return null;
		}

		// remove search robot
		Pattern robotPattern = (Pattern) this.servlet.getServletContext().getAttribute
				(SpamFilterTooFreq.BOTNAME);
		if (robotPattern != null && isPermittedRobot(request, robotPattern)) {
			response.sendError(404);
			return null;
		}

		if (!checkSpamHit(threadId, request)) {
			((HttpServletResponse) response).sendError(404);
			return null;
		}

		try {
			ForumThread thread = getForumMessageQueryService().getThread(new Long(threadId));
			if (thread == null) {
				((HttpServletResponse) response).sendError(404);
				return null;
			}


			if (!thread.getState().latestPostIsNull()) {
					thread.addViewCount();
					getThreadViewCounterJob().checkViewCounter(thread);
			}
		} catch (Exception e) {
			Debug.logError(" viewThread error:" + e);
		}
		return null;
	}

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}


	public ThreadViewCounterJob getThreadViewCounterJob() {
		if (threadViewCounterJob == null)
			threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance
					("threadViewCounterJob", this.servlet.getServletContext());
		return threadViewCounterJob;
	}

	private boolean isPermittedRobot(HttpServletRequest request, Pattern robotPattern) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0 && robotPattern.matcher(userAgent
					.toLowerCase()).matches()) {
				return true;
			}
		}
		return false;
	}


	private boolean checkSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance
					("customizedThrottle", this.servlet.getServletContext());
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), "threads");
		return customizedThrottle.processHitFilter(hitKey);
	}

}
