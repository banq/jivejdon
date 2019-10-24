package com.jdon.jivejdon.infrastructure.component.subscription;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.jdon.annotation.Component;
import com.jdon.jivejdon.infrastructure.component.account.SinaOAuthSubmitter;
import com.jdon.jivejdon.infrastructure.component.email.SubscriptionEmail;
import com.jdon.jivejdon.infrastructure.component.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.infrastructure.component.subscription.action.QueueListerner;
import com.jdon.jivejdon.infrastructure.component.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.infrastructure.component.subscription.action.ShortMsgActionList;
import com.jdon.jivejdon.infrastructure.component.weibo.TecentWeiboSubmitter;
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
	public final TecentWeiboSubmitter tecentWeboSubmitter;

	public final ShortMessageFactory shortMessageFactory;

	private final QueueListerner queueListerner;

	private final NotifySubscribedFactory notifySubscribedFactory;

	public SubscriptionNotify(SubscriptionDao subscriptionDao, ShortMessageFactory shortMessageFactory, AccountFactory accountFactory,
			SubscriptionEmail subscriptionEmail, SinaOAuthSubmitter sinaWeboSubmitter, TecentWeiboSubmitter tecentWeboSubmitter,
			QueueListerner queueListerner, NotifySubscribedFactory notifySubscribedFactory) {
		this.subscriptionDao = subscriptionDao;
		this.shortMessageFactory = shortMessageFactory;
		this.accountFactory = accountFactory;
		this.subscriptionEmail = subscriptionEmail;
		this.sinaWeboSubmitter = sinaWeboSubmitter;
		this.tecentWeboSubmitter = tecentWeboSubmitter;
		this.queueListerner = queueListerner;
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
					subaction.setSubscription(sub);
					subaction.setSubscriptionNotify(this);
					subaction.setNotifySubscribed(notifySubscribed);
					if (subaction instanceof ShortMsgAction) {
						shortMsgActionList.addShortMsgAction((ShortMsgAction) subaction);
						if (!queueListerner.contains(shortMsgActionList))
							queueListerner.offer(shortMsgActionList);
					} else
						queueListerner.offer(subaction);
				}
				sub.getSubscriptionActionHolder().clear();

			}
		} catch (Exception e) {
			logger.error("sendsub erro:" + e);
		}
	}
}
