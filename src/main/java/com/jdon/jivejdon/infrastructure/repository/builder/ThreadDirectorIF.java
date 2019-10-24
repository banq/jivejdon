package com.jdon.jivejdon.infrastructure.repository.builder;

import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;

public interface ThreadDirectorIF {
    @Around
    ForumThread getThread(Long threadId) throws Exception;

    @Around
    ForumThread getThread(Long threadId, ForumMessage rootMessage) throws Exception;
}
