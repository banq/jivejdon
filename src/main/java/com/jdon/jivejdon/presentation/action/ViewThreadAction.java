package com.jdon.jivejdon.presentation.action;

import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelDispAction;
import com.jdon.util.UtilValidate;

public class ViewThreadAction extends ModelDispAction {

	private ForumMessageQueryService forumMessageQueryService;
	private ThreadViewCounterJob threadViewCounterJob;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String threadId = request.getParameter("threadId");
		if (threadId == null || threadId.length() == 0 || !StringUtils.isNumeric(threadId) || threadId.length() > 20)
			return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);


		try {
			CompletableFuture.supplyAsync(() -> {
				return getForumMessageQueryService().getThread(Long.parseLong(threadId));
			}).thenAccept(forumThread -> {
				if (forumThread != null && !UtilValidate.isEmpty(request.getRemoteAddr()))
					getThreadViewCounterJob().saveViewCounter(forumThread.addViewCount(request.getRemoteAddr()));
			});
		} catch (Exception e) {

		}

		return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);

	}

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	
	private ThreadViewCounterJob getThreadViewCounterJob() {
		if (threadViewCounterJob == null)
			threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob",
					this.servlet.getServletContext());
		return threadViewCounterJob;
	}



}
