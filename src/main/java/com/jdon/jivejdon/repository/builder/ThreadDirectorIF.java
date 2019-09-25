package com.jdon.jivejdon.repository.builder;

import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

public interface ThreadDirectorIF {
    @Around
    ForumThread getThread(Long threadId) throws Exception;

    @Around
    ForumThread getThread(Long threadId, ForumMessage rootMessage) throws Exception;
}
