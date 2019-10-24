package com.jdon.jivejdon.presentation.filter;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.jivejdon.presentation.form.ThreadForm;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.util.Debug;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class ThreadEditAuthFilter  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Debug.logVerbose("enter ThreadEditAuthFilter");
		ThreadForm threadForm = (ThreadForm) form;
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		Account account = accountService.getloginAccount();
		if (account == null) {
			threadForm.setAuthenticated(false);// only logined user can post
			return mapping.findForward("success");
		}
		if ((threadForm.getAction() != null) && (!threadForm.getAction().equals(MessageForm.CREATE_STR))) {
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
			ForumThread forumThread = forumMessageService.getThread(new
						Long(threadForm.getThreadId()));
			if (forumThread != null) {
				boolean result = forumMessageService.checkIsAuthenticated
						(forumThread.getRootMessage());
				threadForm.setAuthenticated(result);
			}
		}
		ForumService forumService = (ForumService) WebAppUtil.getService
				("forumService", this.servlet.getServletContext());
		Collection<Forum> forums = forumService.getForums();
		request.setAttribute("forums", forums);
		return mapping.findForward("success");
	}
}
