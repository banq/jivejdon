package com.jdon.jivejdon.presentation.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.presentation.filter.SpamFilterTooFreq;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelDispAction;
import com.jdon.util.Debug;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewThreadAction extends ModelDispAction {

	private ForumMessageQueryService forumMessageQueryService;
	private ThreadViewCounterJob threadViewCounterJob;
	private CustomizedThrottle customizedThrottle;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		Pattern robotPattern = (Pattern) this.servlet.getServletContext().getAttribute(SpamFilterTooFreq.BOTNAME);
		if (robotPattern != null && isPermittedRobot(request, robotPattern)) {
			return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
		}
		

		String threadId = request.getParameter("threadId");
		if (threadId == null || threadId.length() == 0)
			return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);

		if (!checkSpamHit(threadId, request)) {
			return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
		}	
		try {
			ForumThread forumThread = getForumMessageQueryService().getThread(new Long(threadId));
			if (forumThread == null) {
				return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
			}

			//prepare for next step
			forumThread.getRootMessage().getReBlogVO().loadAscResult();

			forumThread.addViewCount(request.getRemoteAddr());
			getThreadViewCounterJob().saveViewCounter(forumThread.getViewCounter());
  
			return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		} catch (Exception e) {
			Debug.logError(" viewThread error");
		}
		return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
	}

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

	private boolean isPermittedRobot(HttpServletRequest request, Pattern robotPattern) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0
					&& robotPattern.matcher(userAgent.toLowerCase()).matches()) {
				return true;
			}
		}
		return false;
	}

	private boolean checkSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle",
					this.servlet.getServletContext());
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), "threads");
		return customizedThrottle.processHitFilter(hitKey);
	}


}
