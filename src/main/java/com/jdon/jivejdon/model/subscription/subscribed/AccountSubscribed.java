package com.jdon.jivejdon.model.subscription.subscribed;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;

public class AccountSubscribed implements Subscribed {

	public final static int TYPE = 3;

	protected Account account;
	protected ForumMessage message;

	private final long subscribedId;
	private long messageId;

	public AccountSubscribed(long subscribedId, long messageId) {
		super();
		this.subscribedId = subscribedId;
		this.messageId = messageId;
	}

	public AccountSubscribed(long subscribedId) {
		super();
		this.subscribedId = subscribedId;
	}

	public Long getSubscribeId() {
		return subscribedId;
	}

	public void setSubscribeId(Long subscribeId) {
		account.setUserIdLong(subscribeId);
	}

	public long getMessageId() {
		return messageId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getSubscribeType() {
		return AccountSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		account.updateSubscriptionCount(count);
	}

	@Override
	public String getName() {
		if (account != null)
			return account.getUsername();
		else
			return "";
	}

}
