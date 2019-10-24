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
package com.jdon.jivejdon.repository.subscription;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.infrastructure.component.subscription.SubscriptionAction;
import com.jdon.jivejdon.infrastructure.component.subscription.action.EmailAction;
import com.jdon.jivejdon.infrastructure.component.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.infrastructure.component.subscription.action.SinaWeiboAction;
import com.jdon.jivejdon.infrastructure.component.subscription.action.TecentWeiboAction;
import com.jdon.jivejdon.infrastructure.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.WeiboExpiredNotifyMessage;
import com.jdon.jivejdon.repository.dao.sql.UserconnectorSql;

import java.util.Map;

@Component
public class SubscriptionInitFactory {
	public static final String SINAWEIBO = "sinaweibo";
	public static final String TECENTWEIBO = "tecentweibo";

	protected Constants constants;
	private UserconnectorSql userconnectorSql;
	private WeiboExpiredNotifyMessage weiboExpiredNotifyMessage;

	public SubscriptionInitFactory(Constants constants, UserconnectorSql userconnectorSql, WeiboExpiredNotifyMessage weiboExpiredNotifyMessage) {
		super();
		this.constants = constants;
		this.userconnectorSql = userconnectorSql;
		this.weiboExpiredNotifyMessage = weiboExpiredNotifyMessage;
	}

	public SubscriptionAction create(String notifymode) {
		SubscriptionAction subscriptionAction = null;
		if (notifymode.equals(SINAWEIBO)) {
			subscriptionAction = new SinaWeiboAction(weiboExpiredNotifyMessage);
		} else if (notifymode.equals(TECENTWEIBO)) {
			subscriptionAction = new TecentWeiboAction(weiboExpiredNotifyMessage);
		}
		return subscriptionAction;

	}

	public Subscription populateSubscription(Map map) {
		Subscription subscription = new Subscription();
		subscription.setSubscriptionId((Long) map.get("subscriptionID"));

		String userId = (String) map.get("userId");
		Account accountin = new Account();
		accountin.setUserId(userId);
		subscription.setAccount(accountin);

		Integer subscribeType = (Integer) map.get("subscribedtype");
		Long subscribeID = (Long) map.get("subscribedID");

		subscription.setSubscribed(SubscribedFactory.createTransient(subscribeType.intValue(), subscribeID));

		String saveDateTime = ((String) map.get("creationDate")).trim();
		subscription.setCreationDate(constants.getDateTimeDisp(saveDateTime));

		Boolean sendmsg = (Boolean) map.get("sendmsg");
		if (sendmsg)
			subscription.addAction(new ShortMsgAction());

		Boolean sendemail = (Boolean) map.get("sendemail");
		if (sendemail) {
			subscription.addAction(new EmailAction(subscription));
		}

		String notifymode = (String) map.get("notifymode");
		subscription.setNotifymode(notifymode);
		if (notifymode != null && notifymode.length() != 0) {
			UserConnectorAuth userConnectorAuth = userconnectorSql.loadUserConnectorAuth(userId, notifymode);
			SubscriptionAction subscriptionAction = create(notifymode);
			if (userConnectorAuth != null && !userConnectorAuth.isEmpty() && subscriptionAction != null) {
				subscriptionAction.setUserConnectorAuth(userConnectorAuth);
				subscriptionAction.setSubscription(subscription);
				subscription.addAction(subscriptionAction);
			}
		}

		return subscription;
	}
}
