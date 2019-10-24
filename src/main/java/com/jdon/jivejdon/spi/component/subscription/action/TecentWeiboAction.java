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
package com.jdon.jivejdon.spi.component.subscription.action;

import java.net.InetAddress;
import java.util.Date;

import com.jdon.jivejdon.spi.component.subscription.SubscriptionAction;
import com.jdon.jivejdon.spi.component.subscription.SubscriptionNotify;
import com.jdon.jivejdon.spi.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.WeiboExpiredNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.NotifySubscribed;
import com.jdon.util.StringUtil;

public class TecentWeiboAction implements SubscriptionAction {

	private Subscription subscription;

	private UserConnectorAuth userConnectorAuth;

	private SubscriptionNotify subscriptionNotify;

	private NotifySubscribed notifySubscribed;

	private final WeiboExpiredNotifyMessage weiboExpiredNotifyMessage;

	public TecentWeiboAction(WeiboExpiredNotifyMessage weiboExpiredNotifyMessage) {
		super();
		this.weiboExpiredNotifyMessage = weiboExpiredNotifyMessage;
	}

	public void exec() {

		if (subscription == null) {
			System.err.print("subscription is null in WeiboAction");
			return;
		}
		if (userConnectorAuth.isEmpty()) {
			return;
		}

		try {
			Date expireDate = new Date(userConnectorAuth.getExpireTime());
			Date nowDate = new Date(System.currentTimeMillis());
			if (expireDate.before(nowDate)) {
				System.err.print("expire :" + subscription.getAccount().getUsername());
				ShortMessage shortMessage = new ShortMessage();
				shortMessage.setMessageTitle(weiboExpiredNotifyMessage.getNotifyTitle() + this.getClass().getSimpleName());
				shortMessage.setMessageBody(StringUtil.replace(weiboExpiredNotifyMessage.getNotifyUrlTemp(), "weibouserId",
						userConnectorAuth.getUserId()));
				shortMessage.setMessageFrom(weiboExpiredNotifyMessage.getNotifier());
				shortMessage.setAccount(subscription.getAccount());
				shortMessage.setMessageTo(subscription.getAccount().getUsername());
				subscriptionNotify.shortMessageFactory.sendShortMessage(shortMessage);
			} else {
				String clientIp = InetAddress.getLocalHost().getHostAddress();
				subscriptionNotify.tecentWeboSubmitter.submitWeibo(notifySubscribed.createShortMessage(subscription).getMessageBody(),
						userConnectorAuth, clientIp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public UserConnectorAuth getUserConnectorAuth() {
		return userConnectorAuth;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public void setUserConnectorAuth(UserConnectorAuth userConnectorAuth) {
		this.userConnectorAuth = userConnectorAuth;
	}

	public SubscriptionNotify getSubscriptionNotify() {
		return subscriptionNotify;
	}

	public void setSubscriptionNotify(SubscriptionNotify subscriptionNotify) {
		this.subscriptionNotify = subscriptionNotify;
	}

	public NotifySubscribed getNotifySubscribed() {
		return notifySubscribed;
	}

	public void setNotifySubscribed(NotifySubscribed notifySubscribed) {
		this.notifySubscribed = notifySubscribed;
	}

}
