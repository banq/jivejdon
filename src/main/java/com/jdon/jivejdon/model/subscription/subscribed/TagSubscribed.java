package com.jdon.jivejdon.model.subscription.subscribed;

import com.jdon.jivejdon.model.ThreadTag;

public class TagSubscribed implements Subscribed {

	public final static int TYPE = 2;

	protected ThreadTag tag;

	private final long tagId;
	private long threadId;

	public TagSubscribed(long tagId, long threadId) {
		this.tagId = tagId;
		this.threadId = threadId;
	}

	public TagSubscribed(long tagId) {
		this.tagId = tagId;
	}

	public long getTagId() {
		return tagId;
	}

	public long getThreadId() {
		return threadId;
	}

	public ThreadTag getTag() {
		return tag;
	}

	public void setTag(ThreadTag tag) {
		this.tag = tag;
	}

	public Long getSubscribeId() {
		return tagId;
	}

	public int getSubscribeType() {
		return TagSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		tag.updateSubscriptionCount(count);
	}

	@Override
	public String getName() {
		if (tag != null)
			return tag.getTitle();
		else
			return "";
	}

}
