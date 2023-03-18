package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.thread.ViewCounter;
import com.jdon.jivejdon.presentation.filter.SpamFilterTooFreq;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * response the ajax request for getting dig count
 * 
 * @author oojdon
 * 
 */
public class MessageListIPFilterAction extends Action {

	private CustomizedThrottle customizedThrottle;
	private ThreadViewCounterJob threadViewCounterJob;
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	private ThreadViewCounterJob getThreadViewCounterJob() {
		if (threadViewCounterJob == null)
			threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob",
					this.servlet.getServletContext());
		return threadViewCounterJob;
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// remove search robot
		Pattern robotPattern = (Pattern) this.servlet.getServletContext().getAttribute(SpamFilterTooFreq.BOTNAME);
		if (robotPattern != null && isPermittedRobot(request, robotPattern)) {
			response.sendError(404);
			return null;
		}

		String threadId = request.getParameter("othread");
		if (UtilValidate.isEmpty(threadId)) {
			response.sendError(404);
			return null;
		}

		if (!checkSpamHit(threadId, request)) {
			response.sendError(404);
			return null;
		}

		ForumThread forumThread = getForumMessageQueryService().getThread(new Long(threadId));
		if (forumThread == null) {
			response.sendError(404);
			return null;
		}

		ViewCounter viewCounter = getThreadViewCounterJob().getViewCounter(Long.parseLong(threadId));
		if(viewCounter != null)
		  if (!viewCounter.checkIP(request.getRemoteAddr())){
			 response.sendError(404);
			 return null;
		}
		   
        return actionMapping.findForward("success");
	}

	private boolean isPermittedRobot(HttpServletRequest request, Pattern robotPattern) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0 && robotPattern.matcher(userAgent.toLowerCase()).matches()) {
				return true;
			}
		}
		return false;
	}

	private boolean checkSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", this.servlet.getServletContext());
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), "threads");
		return customizedThrottle.processHitFilter(hitKey);
	}

}
