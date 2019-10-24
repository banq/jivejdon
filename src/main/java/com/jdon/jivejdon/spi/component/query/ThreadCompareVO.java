package com.jdon.jivejdon.spi.component.query;

public class ThreadCompareVO {
    private final Long threadId;
    private final int messageCount;

    public ThreadCompareVO(Long threadId, int messageCount) {
        this.threadId = threadId;
        this.messageCount = messageCount;
    }

    public Long getThreadId() {
        return threadId;
    }

    public int getMessageCount() {
        return messageCount;
    }
}
