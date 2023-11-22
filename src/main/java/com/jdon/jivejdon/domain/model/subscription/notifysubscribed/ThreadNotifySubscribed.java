package com.jdon.jivejdon.domain.model.subscription.notifysubscribed;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.util.StringUtil;

public class ThreadNotifySubscribed implements NotifySubscribed {

	protected final ForumThread forumThread;

	private ThreadNotifyMessage threadNotifyMessage;

	public ThreadNotifySubscribed(ForumThread forumThread, ThreadNotifyMessage threadNotifyMessage) {
		super();
		this.forumThread = forumThread;
		this.threadNotifyMessage = threadNotifyMessage;
	}

	public Long getSubscribeId() {
		return forumThread.getThreadId();
	}


	public ForumThread getForumThread() {
		return forumThread;
	}

	public void updateSubscriptionCount(int count) {
		forumThread.getState().updateSubscriptionCount(count);
	}

	public ThreadNotifyMessage getThreadNotifyMessage() {
		return threadNotifyMessage;
	}

	public void setThreadNotifyMessage(ThreadNotifyMessage threadNotifyMessage) {
		this.threadNotifyMessage = threadNotifyMessage;
	}

	public ShortMessage createShortMessage(Subscription subscription) {
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(threadNotifyMessage.getNotifyTitle());
		shortMessage.setMessageFrom(threadNotifyMessage.getNotifier());
		shortMessage.setAccount(subscription.getAccount());
		shortMessage.setMessageTo(subscription.getAccount().getUsername());

		// http://www.jdon.com/jivejdon/threadId#messageId
		String newSubscribedUrl = StringUtil.replace(threadNotifyMessage.getNotifyUrlTemp(), "threadId", forumThread.getThreadId().toString());		
		String body =  forumThread.getName() + " | " + Arrays.asList(forumThread.getTagTitles()).stream().collect(Collectors.joining(" #", " #", " "));
		shortMessage.setMessageBody(body.substring(0, body.length() > 90 ? 90 : body.length()) + " " + newSubscribedUrl);
		shortMessage.setMessageTitle(forumThread.getName() + "-" + shortMessage.getMessageTitle());

		return shortMessage;
	}

}
