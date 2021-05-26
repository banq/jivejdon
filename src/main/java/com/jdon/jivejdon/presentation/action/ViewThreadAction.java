package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.presentation.form.ThreadForm;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelDispAction;
import com.jdon.util.Debug;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewThreadAction extends ModelDispAction {

	private ForumMessageQueryService forumMessageQueryService;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String threadId = request.getParameter("threadId");
		if (threadId == null || threadId.length() == 0)
			return null;
		try {
			ForumThread thread = getForumMessageQueryService().getThread(new Long(threadId));
			ThreadForm threadForm = (ThreadForm) actionForm;
			PropertyUtils.copyProperties(threadForm, thread);
			return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		} catch (Exception e) {
			Debug.logError(" viewThread error:" + e);
		}
		return null;
	}

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

}
