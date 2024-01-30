package com.jdon.jivejdon.presentation.filter;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.util.Debug;

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
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());
		Account account = accountService.getloginAccount();
		if (account == null) {
			messageForm.setAuthenticated(false);// only logined user can post
			return mapping.findForward("failure");
		}
		if ((messageForm.getAction() != null) && (!messageForm.getAction().equals(MessageForm.CREATE_STR))) {
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", 
					this.servlet.getServletContext());
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
		return mapping.findForward(messageForm.getAction());
	}

	protected boolean beReblog() {
		boolean ret = false;

		return ret;
	}

}
