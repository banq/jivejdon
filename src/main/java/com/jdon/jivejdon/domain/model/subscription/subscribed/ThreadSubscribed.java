package com.jdon.jivejdon.domain.model.subscription.subscribed;

import com.jdon.jivejdon.domain.model.ForumThread;

public class ThreadSubscribed implements Subscribed {

	public final static int TYPE = 1;

	protected ForumThread forumThread;

	private final long subscribedId;

	public ThreadSubscribed(long subscribedId) {
		super();
		this.subscribedId = subscribedId;
	}

	public Long getSubscribeId() {
		return subscribedId;
	}


	public ForumThread getForumThread() {
		return forumThread;
	}

	public void setForumThread(ForumThread thread) {
		this.forumThread = thread;
	}

	public int getSubscribeType() {
		return ThreadSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		forumThread.getState().updateSubscriptionCount(count);
	}

	@Override
	public String getName() {
		if (forumThread != null)
			return forumThread.getName();
		else
			return "";
	}

}
