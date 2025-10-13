package com.jdon.jivejdon.spi.component.subscription;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.jdon.annotation.Component;
import com.jdon.jivejdon.spi.component.account.SinaOAuthSubmitter;
import com.jdon.jivejdon.spi.component.email.SubscriptionEmail;
import com.jdon.jivejdon.spi.component.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.spi.component.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.spi.component.subscription.action.ShortMsgActionList;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.event.SubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.NotifySubscribed;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.SubscriptionDao;

@Component("subscriptionNotify")
public class SubscriptionNotify {
	private final static Logger logger = LogManager.getLogger(SubscriptionNotify.class);

	private final SubscriptionDao subscriptionDao;

	private AccountFactory accountFactory;

	public final SubscriptionEmail subscriptionEmail;
	public final SinaOAuthSubmitter sinaWeboSubmitter;

	public final ShortMessageFactory shortMessageFactory;

	private final NotifySubscribedFactory notifySubscribedFactory;

	public SubscriptionNotify(SubscriptionDao subscriptionDao, ShortMessageFactory shortMessageFactory, AccountFactory accountFactory,
			SubscriptionEmail subscriptionEmail, SinaOAuthSubmitter sinaWeboSubmitter, 
			NotifySubscribedFactory notifySubscribedFactory) {
		this.subscriptionDao = subscriptionDao;
		this.shortMessageFactory = shortMessageFactory;
		this.accountFactory = accountFactory;
		this.subscriptionEmail = subscriptionEmail;
		this.sinaWeboSubmitter = sinaWeboSubmitter;
		this.notifySubscribedFactory = notifySubscribedFactory;

	}

	@Subscribe
	public void sendSub(SubscribedNotifyEvent subscribedNotifyEvent) {
		try {
			NotifySubscribed notifySubscribed = notifySubscribedFactory.createFullSubscribed(subscribedNotifyEvent);
			ShortMsgActionList shortMsgActionList = new ShortMsgActionList();

			Collection<Subscription> subscriptions = subscriptionDao.getSubscriptionsForsubscribed(notifySubscribed.getSubscribeId());
			for (Subscription sub : subscriptions) {

				sub.setAccount(accountFactory.getFullAccount(sub.getAccount()));

				for (SubscriptionAction subaction : sub.getSubscriptionActionHolder().getSubscriptionActions()) {
					// 添加空值检查
					if (subaction == null) {
						logger.warn("SubscriptionAction is null, skipping...");
						continue;
					}
					
					subaction.setSubscription(sub);
					subaction.setSubscriptionNotify(this);
					subaction.setNotifySubscribed(notifySubscribed);
					if (subaction instanceof ShortMsgAction) {
						shortMsgActionList.addShortMsgAction((ShortMsgAction) subaction);
						shortMsgActionList.exec();	
					} else
						subaction.exec();
				}
				sub.getSubscriptionActionHolder().clear();

			}
		} catch (Exception e) {
			logger.error("sendsub erro:" + e, e);
		}
	}
}
