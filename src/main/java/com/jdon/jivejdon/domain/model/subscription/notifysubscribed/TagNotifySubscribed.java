package com.jdon.jivejdon.domain.model.subscription.notifysubscribed;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.TagNotifyMessage;
import com.jdon.util.StringUtil;

public class TagNotifySubscribed implements NotifySubscribed {

	protected final ThreadTag tag;
	protected final ForumThread thread;
	private final TagNotifyMessage tagNotifyMessage;

	public TagNotifySubscribed(ThreadTag tag, ForumThread thread, TagNotifyMessage tagNotifyMessage) {
		super();
		this.tag = tag;
		this.thread = thread;
		this.tagNotifyMessage = tagNotifyMessage;
	}

	public Long getSubscribeId() {
		return tag.getTagID();
	}

	public void updateSubscriptionCount(int count) {
		tag.updateSubscriptionCount(count);
	}

	public ForumThread getThread() {
		return thread;
	}

	public TagNotifyMessage getTagNotifyMessage() {
		return tagNotifyMessage;
	}

	public ShortMessage createShortMessage(Subscription subscription) {
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(tagNotifyMessage.getNotifyTitle());
		shortMessage.setMessageFrom(tagNotifyMessage.getNotifier());
		//
		shortMessage.setAccount(subscription.getAccount());
		shortMessage.setMessageTo(subscription.getAccount().getUsername());

		// http://www.jdon.com/jivejdon/threadId#messageId
		String newSubscribedUrl = StringUtil.replace(tagNotifyMessage.getNotifyUrlTemp(), "tagId", tag.getTagID().toString());
		String body = "" + thread.getName() + ":" + newSubscribedUrl;
		shortMessage.setMessageBody(body);
		shortMessage.setMessageTitle(tag.getTitle() + "-" + shortMessage.getMessageTitle());

		return shortMessage;
	}

}
