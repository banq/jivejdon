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

import com.jdon.jivejdon.spi.component.subscription.SubscriptionAction;
import com.jdon.jivejdon.spi.component.subscription.SubscriptionNotify;
import com.jdon.jivejdon.spi.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.NotifySubscribed;

public class EmailAction implements SubscriptionAction {

	private Subscription subscription;

	private SubscriptionNotify subscriptionNotify;

	private NotifySubscribed notifySubscribed;

	public EmailAction(Subscription subscription) {
		super();
		this.subscription = subscription;
	}

	public EmailAction() {
	}

	@Override
	public void exec() {
		// 添加更完善的空值检查
		if (subscription == null) {
			System.err.print("subscription is null in EmailAction");
			return;
		}
		
		if (subscriptionNotify == null) {
			System.err.print("subscriptionNotify is null in EmailAction");
			return;
		}
		
		if (subscriptionNotify.subscriptionEmail == null) {
			System.err.print("subscriptionEmail is null in EmailAction");
			return;
		}
		
		if (notifySubscribed == null) {
			System.err.print("notifySubscribed is null in EmailAction");
			return;
		}
		
		try {
			subscriptionNotify.subscriptionEmail.send(subscription.getAccount(), notifySubscribed.createShortMessage(subscription));
		} catch (Exception e) {
			System.err.print("Error in EmailAction.exec(): " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public void setUserConnectorAuth(UserConnectorAuth userConnectorAuth) {
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

	public Subscription getSubscription() {
		return subscription;
	}

}
