package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.presentation.filter.SpamFilterTooFreq;
import com.jdon.jivejdon.api.ForumMessageService;
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
public class MessageDigAction extends Action {

	private CustomizedThrottle customizedThrottle;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		// remove search robot
		Pattern robotPattern = (Pattern) this.servlet.getServletContext().getAttribute(SpamFilterTooFreq.BOTNAME);
		if (robotPattern != null && isPermittedRobot(request, robotPattern)) {
			response.sendError(404);
			return null;
		}

		String messageId = request.getParameter("messageId");
		if (UtilValidate.isEmpty(messageId)) {
			response.sendError(404);
			return null;
		}

		if (!checkSpamHit(messageId, request)) {
			response.sendError(404);
			return null;
		}

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext());

		Long key = Long.valueOf(messageId);
		ForumMessage message = forumMessageService.getMessage(key);
		if (message == null) {
			response.sendError(404);
			return null;
		}
		// who has read can dig it.
		if (message.getForumThread().getViewCounter().checkIP(request.getRemoteAddr()))
			// if (!message.getPostip().equals(request.getRemoteAddr()))
			message.messaegDigAction();

		try {
			response.setContentType("text/html");
			response.getWriter().print(message.getDigCount());
			response.getWriter().close();
		} catch (Exception e) {
			if (response != null && response.getWriter() != null) {
				response.getWriter().close();
			}
		}
		// }
		return null;
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
