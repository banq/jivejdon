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
package com.jdon.jivejdon.component.subscription.action;

import com.jdon.jivejdon.component.subscription.SubscriptionAction;
import com.jdon.jivejdon.component.subscription.SubscriptionNotify;
import com.jdon.jivejdon.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.notifysubscribed.NotifySubscribed;

public class ShortMsgAction implements SubscriptionAction {

	private Subscription subscription;

	private SubscriptionNotify subscriptionNotify;

	private NotifySubscribed notifySubscribed;

	public ShortMsgAction() {
	}

	@Override
	public void exec() {
		try {
			// one account only send one message
			ShortMessage shortMessage = notifySubscribed.createShortMessage(subscription);
			if (subscriptionNotify.shortMessageFactory.getNewShortMessageCount(shortMessage) < 5)
				subscriptionNotify.shortMessageFactory.sendShortMessage(shortMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public SubscriptionNotify getSubscriptionNotify() {
		return subscriptionNotify;
	}

	public void setSubscriptionNotify(SubscriptionNotify subscriptionNotify) {
		this.subscriptionNotify = subscriptionNotify;
	}

	public void setUserConnectorAuth(UserConnectorAuth userConnectorAuth) {
	}

	public NotifySubscribed getNotifySubscribed() {
		return notifySubscribed;
	}

	public void setNotifySubscribed(NotifySubscribed notifySubscribed) {
		this.notifySubscribed = notifySubscribed;
	