package com.jdon.jivejdon.domain.model.subscription.messsage;

public class ThreadNotifyMessage {

	private final String notifyTitle;
	private final String notifyUrlTemp;
	private final String notifier;

	public ThreadNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
		super();
		this.notifyTitle = notifyTitle;
		this.notifyUrlTemp = notifyUrlTemp;
		this.notifier = notifier;

	}

	public String getNotifyUrlTemp() {
		return notifyUrlTemp;
	}

	public String getNotifyTitle() {
		return notifyTitle;
	}

	public String getNotifier() {
		return notifier;
	}

}
