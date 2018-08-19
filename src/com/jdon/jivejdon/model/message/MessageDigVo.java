package com.jdon.jivejdon.model.message;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;

/**
 * 
 * @author oojdon
 * 
 */
public class MessageDigVo {

	private final long messageId;

	private int number = -1;

	private DomainMessage digCountAsyncResult;

	public MessageDigVo(long messageId) {
		super();
		this.messageId = messageId;
	}

	public int getDigCount(LazyLoaderRole lazyLoaderRole) {
		if (number == -1) {
			preloadDigCount(lazyLoaderRole);
		}
		return number;
	}

	public void preloadDigCount(LazyLoaderRole lazyLoaderRole) {
		try {
			if (digCountAsyncResult == null) {
				digCountAsyncResult = lazyLoaderRole.loadMessageDigCount(messageId);
				Object o = digCountAsyncResult.getEventResult();
				if (o != null) {
					number = (Integer) o;
					digCountAsyncResult.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void increment(LazyLoaderRole lazyLoaderRole) {
		if (number == -1) {
			preloadDigCount(lazyLoaderRole);
		}
		number++;

	}

	public long getMessageId() {
		return messageId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
