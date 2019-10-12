package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.jivejdon.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.realtime.Lobby;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.service.account.AccountService;

public class NewMsgCheckerAction extends Action {
	private final static Logger logger = LogManager.getLogger(NewMsgCheckerAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter checkReceiveShortMessages");
		AccountService accountService = null;
		if (request.getSession(false) != null)
			accountService = (AccountService) WebAppUtil.getService("accountService", request);
		else
			accountService = (AccountService) WebAppUtil.getService("accountService", this.servlet.getServletContext());
		Account account = accountService.getloginAccount();
		String username = "anonymous";
		int count = 0;
		if (account != null) {
			count = account.getNewShortMessageCount();
			username = account.getUsername();
		}
		if (count != 0) {
			request.setAttribute("NEWMESSAGES", count);
			return mapping.findForward("checkshortmsg");
		}
		Lobby lobby = null;
		if (request.getSession(false) != null)
			lobby = (Lobby) WebAppUtil.getComponentInstance("lobby", request);
		else
			lobby = (Lobby) WebAppUtil.getComponentInstance("lobby", this.servlet.getServletContext());
		Notification news = lobby.checkNotification();
		request.setAttribute("Notification", news);
		return mapping.findForward("checklobbymsg");

	}
}
