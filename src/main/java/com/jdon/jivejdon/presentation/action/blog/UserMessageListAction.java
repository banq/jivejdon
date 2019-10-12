package com.jdon.jivejdon.presentation.action.blog;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.*;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.AccountProfileForm;
import com.jdon.jivejdon.service.query.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;

/**
 * used for threadViewQuery.shtml?queryType=userMessageQueryAction&userID=62934
 * 
 * 
 */
public class UserMessageListAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(UserMessageListAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("enter MessageQueryAction ....");
		AccountProfileForm accountProfileForm = (AccountProfileForm) FormBeanUtil.lookupActionForm(request, "accountProfileForm");
		if (accountProfileForm == null) {
			logger.debug("not found accountProfileForm");
			return new PageIterator();
		}
		String userId = accountProfileForm.getUserId();

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
				this.servlet.getServletContext());
		return forumMessageQueryService.getMesageListByUser(userId, start, count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext());
		logger.debug(" key calss type = " + key.getClass().getName());
		ForumMessage message = forumMessageService.getMessage((Long) key);
		return message;
	}

}
