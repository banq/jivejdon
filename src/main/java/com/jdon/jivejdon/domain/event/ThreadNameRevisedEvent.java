package com.jdon.jivejdon.domain.event;

public class ThreadNameSavedEvent {

	private final Long threadId;

	private final String name;

	public ThreadNameSavedEvent(Long threadId, String name) {
		this.threadId = threadId;
		this.name = name;
	}

	public Long getThreadId() {
		return threadId;
	}

	public String getName() {
		return name;
	}
}
