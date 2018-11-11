package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.ForumMessageQueryService;
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

	/**
	 * // Determine if we need to adjust the start index of the thread iterator.
	 * // If we're passed a message ID, we need to show the thread page that //
	 * messageID is contained on. this method has not good performance. not
	 * frequency call it
	 */
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
		if (forumMessage == null)
			return mapping.findForward("failure");

		long threadId = forumMessage.getForumThread().getThreadId();

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		int start = forumMessageQueryService.locateTheMessage(new Long(threadId), new Long(messageId), messageListForm.getCount());
		if (start == -1) {
			logger.error(" not locate messageId = " + messageId + " in threadId =" + threadId);
			return mapping.findForward("failure");
		}
		messageListForm.setStart(start);// diaplay
		request.setAttribute("start", start);
		request.setAttribute("threadId", threadId);
		request.setAttribute("messageId", new Long(messageId));
		return mapping.findForward("success");

	}

}
