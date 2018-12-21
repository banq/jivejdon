package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <%-- /forum/messageNavList.shtml
 * == > MessageListNavAction ==> navf.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+)
 * --%>
 *
 * @author banq
 *
 */
public class MessageListNavAction extends Action {
	private final static Logger logger = LogManager.getLogger(MessageListNavAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageListForm messageListForm = (MessageListForm) FormBeanUtil.lookupActionForm(request, "messageListForm"); // same
		// as struts-config-message.xml
		if (messageListForm == null) {
			logger.error(" MessageListNavAction error : messageListForm is null");
			return mapping.findForward("failure");
		}

		String messageId = request.getParameter("message");
		if ((messageId == null) || (!UtilValidate.isInteger(messageId))) {
			logger.error(" MessageListNavAction error : messageId is null");
			return mapping.findForward("failure");
		}

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", this.servlet.getServletContext());
		ForumMessage forumMessage = forumMessageService.getMessage(Long.parseLong(messageId));
		if (forumMessage == null) {
			logger.error(" MessageListNavAction error : not found forumMessage");
			return mapping.findForward("failure");
		}

		long threadId = forumMessage.getForumThread().getThreadId();

		messageListForm.setStart(0);// diaplay
		request.setAttribute("start", 0);
		request.setAttribute("threadId", threadId);
		request.setAttribute("messageId", new Long(messageId));
		return mapping.findForward("success");

	}

}
