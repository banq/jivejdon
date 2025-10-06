package com.jdon.jivejdon.presentation.action;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.util.UtilValidate;

/**
 * response the ajax request for getting dig count
 * 
 * @author oojdon
 * 
 */
public class MessageDigAction extends Action {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {


		String messageId = request.getParameter("messageId");
		if (UtilValidate.isEmpty(messageId) || !messageId.matches("\\d+")) {
			response.sendError(404);
			return null;
		}

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());


		Long key = Long.valueOf(messageId);
		ForumMessage message = forumMessageQueryService.getMessage(key);
		if (message == null) {
			response.sendError(404);
			return null;
		}

		ForumThread thread = forumMessageQueryService.getThread(message.getForumThread().getThreadId());
		int digCount = thread.messaegDigAction(request.getRemoteAddr());

		try {
			response.setContentType("text/html");
			response.getWriter().print(digCount);
		} catch (Exception e) {
			response.sendError(404);
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



}
