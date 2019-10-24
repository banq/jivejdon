package com.jdon.jivejdon.domain.model.subscription.messsage;

public class TagNotifyMessage {

	private final String notifyTitle;
	private final String notifyUrlTemp;
	private final String notifier;

	public TagNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
		this.notifyTitle = notifyTitle;
		this.notifyUrlTemp = notifyUrlTemp;
		this.notifier = notifier;

	}

	public String getNotifyTitle() {
		return notifyTitle;
	}

	public String getNotifyUrlTemp() {
		return notifyUrlTemp;
	}

	public String getNotifier() {
		return notifier;
	}

}
