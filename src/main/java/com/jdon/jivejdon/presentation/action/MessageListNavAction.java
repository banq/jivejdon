package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <%-- /forum/messageNavList.shtml == > MessageListNavAction ==> navf.jsp ==>
 * (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
 *
 * @author banq
 *
 */
public class MessageListNavAction extends Action {
	private final static Logger logger = LogManager.getLogger(MessageListNavAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageListForm messageListForm = (MessageListForm) FormBeanUtil.lookupActionForm(request, "messageListForm"); // same
		// as struts-config-message.xml
		if (messageListForm == null) {
			logger.error(" MessageListNavAction error : messageListForm is null");
			return mapping.findForward("failure");
		}

		String messageId = request.getParameter("message");
		String forumIds = request.getParameter("forum");
		if ((messageId == null) || (!UtilValidate.isInteger(messageId)) || (forumIds == null)) {
			logger.error(" MessageListNavAction error : message  or forum is null");
			return mapping.findForward("failure");
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService",
				this.servlet.getServletContext());
		Forum forum = forumService.getForum(new Long(forumIds));
		if (forum == null) {
			logger.error(" MessageListNavAction error : not found forum =" + forumIds);
			return mapping.findForward("failure");
		}

		ForumMessage lastPost = forum.getForumState().getLatestPost();
		if (lastPost != null) {
			Long lastMessageId = lastPost.getMessageId();
			Long threadId = lastPost.getForumThread().getThreadId();
			if (lastMessageId.longValue() >= (new Long(messageId)).longValue()) {
				ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
				redirect.setPath("/message/messageListOwner.shtml?thread=" + Long.toString(threadId));
				return redirect;
			}
		}
		request.setAttribute("forumId", new Long(forumIds));
		request.setAttribute("messageId", new Long(messageId));
		return mapping.findForward("navf");
	
	}

}
