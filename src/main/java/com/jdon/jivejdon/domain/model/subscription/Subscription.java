package com.jdon.jivejdon.domain.model.subscription;

import com.jdon.annotation.Model;
import com.jdon.jivejdon.spi.component.subscription.SubscriptionAction;
import com.jdon.jivejdon.spi.component.subscription.SubscriptionActionHolder;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.subscription.subscribed.Subscribed;

@Model
public class Subscription {

	private Long subscriptionId;

	private Account account;

	private Subscribed subscribed;

	private String creationDate;

	private String notifymode;

	private SubscriptionActionHolder subscriptionActionHolder;

	public Subscription() {
		subscriptionActionHolder = new SubscriptionActionHolder();

	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Subscribed getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Subscribed subscribed) {
		this.subscribed = subscribed;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public int getSubscribeType() {
		if (subscribed != null)
			return subscribed.getSubscribeType();
		return -1;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getNotifymode() {
		return notifymode;
	}

	public void setNotifymode(String notifymode) {
		this.notifymode = notifymode;
	}

	public void updateSubscriptionCount(int count) {
		subscribed.updateSubscriptionCount(count);
	}

	// for Jsp set/get see subAccountList.jsp
	public boolean getActionType(String actionClassS) {
		boolean ret = subscriptionActionHolder.hasActionType(actionClassS);
		return ret;
	}

	public SubscriptionActionHolder getSubscriptionActionHolder() {
		return subscriptionActionHolder;
	}

	public void addAction(SubscriptionAction subscriptionAction) {

		subscriptionActionHolder.addAction(subscriptionAction);
	}

	public void setSubscriptionActionHolder(SubscriptionActionHolder subscriptionActionHolder) {
		this.subscriptionActionHolder = subscriptionActionHolder;
	}

}
