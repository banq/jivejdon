package com.jdon.jivejdon.domain.model.subscription.notifysubscribed;

import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.AccountNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.subscribed.AccountSubscribed;
import com.jdon.util.StringUtil;

public class AccountNotifySubscribed implements NotifySubscribed {

	protected final Account account;
	protected final ForumMessage message;

	private final AccountNotifyMessage accountNotifyMessage;

	public AccountNotifySubscribed(Account account, ForumMessage message, AccountNotifyMessage accountNotifyMessage) {
		super();
		this.account = account;
		this.message = message;
		this.accountNotifyMessage = accountNotifyMessage;
	}

	public Account getAccount() {
		return account;
	}

	public int getSubscribeType() {
		return AccountSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		account.updateSubscriptionCount(count);
	}

	public AccountNotifyMessage getAccountNotifyMessage() {
		return accountNotifyMessage;
	}

	public ShortMessage createShortMessage(Subscription subscription) {
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(accountNotifyMessage.getNotifyTitle());
		shortMessage.setMessageFrom(accountNotifyMessage.getNotifier());
		shortMessage.setAccount(subscription.getAccount());
		shortMessage.setMessageTo(subscription.getAccount().getUsername());

		String newSubscribedUrl = StringUtil.replace(accountNotifyMessage.getNotifyUrlTemp(), "threadId", message.getForumThread().getThreadId()
				.toString());
		String body =  "" + message.getForumThread().getName() + ":" + message.getMessageVO().getShortBody(90);
		shortMessage.setMessageBody(body.substring(0, body.length() > 90 ? 90 : body.length()) + " " + newSubscribedUrl);
		shortMessage.setMessageTitle(account.getUsername() + "-" + shortMessage.getMessageTitle());
		return shortMessage;
	}

	@Override
	public Long getSubscribeId() {
		return account.getUserIdLong();
	}

}
