package com.jdon.jivejdon.repository.dao;

import java.util.Collection;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.subscription.Subscription;

public interface SubscriptionDao {

	Long isSubscription(Long subscribedID, String userId);

	void createSubscription(Subscription subscription);

	Subscription getSubscription(Long id);

	void deleteSubscription(Long subscriptionID);

	Collection<Subscription> getSubscriptionsForsubscribed(Long subscribedID);

	Collection<Subscription> getSubscriptions(String userId);

	int getSubscriptionsForsubscribedCount(Long subscribedID);

	int getSubscriptionsCount(String userId, int subscribedtype);

	PageIterator getSubscriptions(String userId, int subscribedtype, int start, int count);

	PageIterator getFollowing(String userId, int subscribedtype, int start, int count);

	PageIterator getFollower(Long subscribedID, int start, int count);
}
