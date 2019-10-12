package com.jdon.jivejdon.service.subscription;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.subscription.Subscription;

public interface SubscriptionService {

	public Subscription initSubscription(EventModel em);

	public void createSubscription(EventModel em);

	public Subscription getSubscription(Long id);

	public void updateSubscription(EventModel em);

	public void deleteSubscription(EventModel em);

	public PageIterator getSubscriptionsForTag(String userId, int start, int count);

	public PageIterator getSubscriptionsForAccount(String userId, int start, int count);

	public PageIterator getSubscriptionsForThread(String userId, int start, int count);

	public PageIterator getSubscriptionsForForum(String userId, int start, int count);

	public PageIterator getFollowing(Long userId, int start, int count);

	public PageIterator getFollower(Long subscribedId, int start, int count);

	PageIterator getThreadListByUserFollowing(String userId, int start, int count);

	public Integer checkSubscription(Long subscribedId);

}
