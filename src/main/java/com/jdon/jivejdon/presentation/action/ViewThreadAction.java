package com.jdon.jivejdon.presentation.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelDispAction;
import com.jdon.util.Debug;

public class ViewThreadAction extends ModelDispAction {

	private ForumMessageQueryService forumMessageQueryService;
	private ThreadViewCounterJob threadViewCounterJob;
	private final ExecutorService executorService = Executors.newFixedThreadPool(5);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	

		String threadId = request.getParameter("threadId");
		if (threadId == null || threadId.length() == 0 || !StringUtils.isNumeric(threadId) || threadId.length()>20)
			return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);

	
		try {
			ForumThread forumThread = getForumMessageQueryService().getThread(Long.parseLong(threadId));
			if (forumThread == null) {
				return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
			}

			// //prepare for next step
			// forumThread.getReBlogVO().loadAscResult();
			executorService.execute(() -> {
				getThreadViewCounterJob().saveViewCounter(forumThread.addViewCount(request.getRemoteAddr()));
			});
  
			return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		} catch (Exception e) {
			Debug.logError(" viewThread error");
		}
		return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
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
