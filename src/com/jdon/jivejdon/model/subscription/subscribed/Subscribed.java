package com.jdon.jivejdon.model.subscription.subscribed;

public interface Subscribed {

	Long getSubscribeId();

	String getName();

	int getSubscribeType();

	void updateSubscriptionCount(int count);

}
