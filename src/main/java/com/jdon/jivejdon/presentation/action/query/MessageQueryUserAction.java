package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

/**
 * used for threadViewQuery.shtml?queryType=userMessageQueryAction&userID=62934
 * 
 * 
 */
public class MessageQueryUserAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(MessageQueryUserAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("enter MessageQueryAction ....");
		QueryForm qForm = (QueryForm) FormBeanUtil.lookupActionForm(request, "queryForm");
		if (qForm == null) {
			qForm = new QueryForm();
			loadForumOptions(request, qForm);
		}

		// save queryCriteria for html:link multi params
		request.setAttribute("paramMaps", qForm.getParamMaps());

		MultiCriteria queryCriteria = new MultiCriteria();
		queryCriteria.setForumId(qForm.getForumId());

		String userId = request.getParameter("userID");
		if (userId == null) {
			userId = request.getParameter("user"); // for old version
		}
		if (userId == null) {
			userId = request.getParameter("userId"); // for old version
		}
		if (UtilValidate.isEmpty(userId)) {
			logger.error("userId == null");
			return new PageIterator();
		}

		qForm.setUserID(userId);
		queryCriteria.setUserID(userId);

		qForm.setFromDate("2000-01-01");
		queryCriteria.setFromDate(qForm.getFromDate());
		logger.debug("fromDate=" + queryCriteria.getFromDateString());
		logger.debug("toDate=" + queryCriteria.getToDateString());

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
				this.servlet.getServletContext());
		return forumMessageQueryService.getMessages(queryCriteria, start, count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext());
		logger.debug(" key calss type = " + key.getClass().getName());
		ForumMessage message = forumMessageService.getMessage((Long) key);
		return message;
	}

	private void loadForumOptions(HttpServletRequest request, QueryForm qForm) {
		if (qForm.getForums().size() != 0) {
			return;
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", this.servlet.getServletContext());
		PageIterator pi = forumService.getForums(0, 30);
		Object[] ids = pi.getKeys();
		for (int i = 0; i < ids.length; i++) {
			qForm.getForums().add(forumService.getForum((Long) ids[i]));
		}
	}

}
