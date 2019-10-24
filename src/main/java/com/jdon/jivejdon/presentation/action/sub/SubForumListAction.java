/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.presentation.action.sub;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.*;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.subscription.SubscriptionService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class SubForumListAction extends ModelListAction {

	private final static Logger logger = LogManager.getLogger(SubForumListAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("enter getPageIterator");
		String userId = request.getParameter("userId");
		if ((userId == null) || (!UtilValidate.isInteger(userId))) {
			logger.error(" getPageIterator error : userId is null");
			return new PageIterator();
		}
		SubscriptionService subscriptionService = (SubscriptionService) WebAppUtil.getService("subscriptionService", request);
		return subscriptionService.getSubscriptionsForForum(userId, start, count);
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
