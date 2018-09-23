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
package com.jdon.jivejdon.presentation.action.blog;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.SubscriptionService;
import com.jdon.strutsutil.ModelListAction;

public class FollowerListAction extends ModelListAction {

	private final static Logger logger = LogManager.getLogger(FollowerListAction.class);

	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		String subscribedId = request.getParameter("userId");
		if (subscribedId == null)
			return new PageIterator();

		int subscribeType = Integer.parseInt(request.getParameter("subscribeType"));
		if (subscribeType != AccountSubscribed.TYPE) {
			return new PageIterator();
		}

		SubscriptionService subscriptionService = (SubscriptionService) WebAppUtil
				.getService("subscriptionService", this.servlet.getServletContext());
		return subscriptionService.getFollower(Long.parseLong(subscribedId), start, count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", this.servlet.getServletContext());
		return accountService.getAccount(Long.parseLong((String) key));
	}
}
