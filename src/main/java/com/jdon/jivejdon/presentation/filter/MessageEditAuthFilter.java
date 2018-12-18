package com.jdon.jivejdon.presentation.filter;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.util.Debug;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * the filter for Message edit Action,
 *
 * @author banq
 *
 */
public class MessageEditAuthFilter extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Debug.logVerbose("enter MessageEditAuthFilter");
		MessageForm messageForm = (MessageForm) form;
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		com.jdon.jivejdon.model.Account account = accountService.getloginAccount();
		if (account == null) {
			messageForm.setAuthenticated(false);// only logined user can post
			return mapping.findForward("success");
		}
		if ((messageForm.getAction() != null) && (!messageForm.getAction().equals(MessageForm.CREATE_STR))) {
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
			ForumMessage forumMessage = null;
			if (messageForm.getMessageId() != null)
			 forumMessage = forumMessageService.getMessage(messageForm.getMessageId());
			else if (messageForm.getForumThread().getThreadId() != null){
				ForumThread forumThread = forumMessageService.getThread(new
						Long(messageForm.getForumThread().getThreadId()));
				if (forumThread != null)
					forumMessage = forumThread.getRootMessage();
			}
			boolean result = forumMessageService.checkIsAuthenticated(forumMessage);
			messageForm.setAuthenticated(result);
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", this.servlet.getServletContext());
		Collection<Forum> forums = forumService.getForums();
		request.setAttribute("forums", forums);
		return mapping.findForward("success");
	}

	protected boolean beReblog() {
		boolean ret = false;

		return ret;
	}

}
