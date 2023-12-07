package com.jdon.jivejdon.presentation.action.sub;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.subscription.SubscriptionService;
import com.jdon.strutsutil.ModelListAction;

public class SubThreadListAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(SubThreadListAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("enter getPageIterator");
		String userId = request.getParameter("userId");
		if ((userId == null) || (!StringUtils.isNumeric(userId))) {
			logger.error(" getPageIterator error : userId is null");
			return new PageIterator();
		}
		SubscriptionService subscriptionService = (SubscriptionService) WebAppUtil.getService("subscriptionService", request);
		return subscriptionService.getSubscriptionsForThread(userId, start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 * .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		logger.debug("enter findModelByKey");
		SubscriptionService subscriptionService = (SubscriptionService) WebAppUtil.getService("subscriptionService", request);
		return subscriptionService.getSubscription((Long) key);
	}

}
