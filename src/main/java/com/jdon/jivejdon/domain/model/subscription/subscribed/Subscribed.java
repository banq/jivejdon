package com.jdon.jivejdon.domain.model.subscription.subscribed;

public interface Subscribed {

	Long getSubscribeId();

	String getName();

	int getSubscribeType();

	void updateSubscriptionCount(int count);

}
