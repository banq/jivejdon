package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.util.ContainerUtil;
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
 */
public class MessageListNav2Action extends Action {
	private final static Logger logger = LogManager.getLogger(MessageListNavAction.class);

	/**
	 * // Determine if we need to adjust the start index of the thread iterator.
	 * // If we're passed a message ID, we need to show the thread page that //
	 * messageID is contained on. this method has not good performance. not
	 * frequency call it
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest
			request, HttpServletResponse response) throws Exception {
		MessageListForm messageListForm = (MessageListForm) FormBeanUtil.lookupActionForm(request,
				"messageListForm"); // same
		// as struts-config-message.xml
		if (messageListForm == null) {
			logger.error(" MessageListNavAction error : messageListForm is null");
			return mapping.findForward("failure");
		}

		String pMessageId = request.getParameter("pMessageId");
		String messageId = request.getParameter("messageId");
		if ((pMessageId == null) || (!UtilValidate.isInteger(pMessageId)) || messageId == null) {
			logger.error(" MessageListNavAction error : pMessageId or messageId is null");
			return mapping.findForward("failure");
		}

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService
				("forumMessageService", this.servlet.getServletContext());

		ForumMessage forumMessageParent = forumMessageService.getMessage(Long.parseLong
				(pMessageId));
		if (forumMessageParent == null) {
			logger.error(" not locate pMessageId = " + pMessageId);
			return mapping.findForward("failure");
		}

		ForumThread thread = forumMessageParent.getForumThread();
		long threadId = thread.getThreadId();
		Long lastMessageId = thread.getState().getLastPost().getMessageId();

		int start = locateTheMessage(new Long(threadId), lastMessageId, new Long(messageId),
				messageListForm.getCount());
		if (start == -1) {
			logger.error(" not locate lastMessageId = " + lastMessageId + " in threadId =" +
					threadId);
			return mapping.findForward("failure");
		}
		messageListForm.setStart(start);// diaplay
		request.setAttribute("start", start);
		request.setAttribute("threadId", threadId);
		request.setAttribute("messageId", new Long(messageId));
		return mapping.findForward("success");

	}

	/**
	 * by lastMessageId locate the new MessageId
	 */
	private int locateTheMessage(Long threadId, Long lastMessageId, Long messageId, int count) {
		int start = 0;
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", this.servlet.getServletContext());
		PageIterator pi = forumMessageQueryService.getMessages(threadId, start, count);
		int allCount = pi.getAllCount();
		int i = 1;
		while (start < allCount) {// loop all
			while (pi.hasNext()) {
				Long messageIdT = (Long) pi.next();
				if (messageIdT.longValue() == lastMessageId.longValue()) {
					if (i == count || ((i + 1) == count && messageId.longValue() != lastMessageId
							.longValue()))
						return start++;
					else
						return start;
				}
			}
			i++;
			start = start + count;
			pi = forumMessageQueryService.getMessages(threadId, start, count);
		}
		return -1;
	}

	private void pollNewMessage(String messageId) {

//		//waiting until com.jdon.jivejdon.event.domain.consumer.write.addReplyMessage
//		// .AddReplyMessageZ add this mode to cache.
		ContainerUtil containerUtil = (ContainerUtil) WebAppUtil
				.getComponentInstance("containerUtil", this.servlet.getServletContext());
		int waittimeout = 0;
		while (!containerUtil.isInCache(messageId, ForumMessageReply.class) && waittimeout < 5) {
			try {
				Thread.sleep((waittimeout++) * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
