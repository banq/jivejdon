package com.jdon.jivejdon.domain.model.subscription.messsage;


/**
 * ShortMessage and NotifyMessage adpter
 * 
 * @author banq
 * 
 */
public class AccountNotifyMessage {
	private final String notifyTitle;
	private final String notifyUrlTemp;
	private final String notifier;

	public AccountNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
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
