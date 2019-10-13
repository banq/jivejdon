package com.jdon.jivejdon.repository.dao.filter;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.repository.subscription.SubscriptionInitFactory;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.repository.dao.sql.SubscriptionDaoSql;
import com.jdon.jivejdon.util.ContainerUtil;

@Component("subscriptionDaoCache")
@Introduce("modelCache")
public class SubscriptionDaoCache extends SubscriptionDaoSql {

	public SubscriptionDaoCache(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, Constants constants,
			SubscriptionInitFactory subscriptionInitFactory) {
		super(jdbcTempSource, constants, containerUtil, subscriptionInitFactory);

	}

	@Around()
	public Subscription getSubscription(Long id) {
		Subscription subscription = super.getSubscription(id);
		return subscription;
	}

	public void createSubscription(Subscription subscription) {
		super.createSubscription(subscription);
		clearCache();
	}

	public void deleteSubscription(Long subscriptionID) {
		super.deleteSubscription(subscriptionID);
		clearCache();
	}

	public void clearCache() {
		pageIteratorSolver.clearCache();
	}

}
