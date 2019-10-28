package com.jdon.jivejdon.domain.event;

public class ThreadNameRevisedEvent {

	private final Long threadId;

	private final String name;

	public ThreadNameRevisedEvent(Long threadId, String name) {
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
