package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.query.MultiCriteria;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class UserMessageListAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(UserMessageListAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("enter UserMessageListAction ....");
		String username = request.getParameter("username");
		if (UtilValidate.isEmpty(username)) {
			return new PageIterator();
		}

		logger.debug("queryType is MultiCriteria");
		MultiCriteria queryCriteria = new MultiCriteria();
		queryCriteria.setUsername(username);
		queryCriteria.setFromDate("1970", "01", "01"); // all date
		logger.debug("fromDate=" + queryCriteria.getFromDateString());
		logger.debug("toDate=" + queryCriteria.getToDateString());

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", 
				this.servlet.getServletContext());
		return forumMessageQueryService.getMessages(queryCriteria, start, count);
	}

	/**
	 * set ModelListAction donot load directly a model from cache.
	 */
	protected boolean isEnableCache() {
		return false;
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", 
				this.servlet.getServletContext());
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getMessage((Long) key);
	}

}
