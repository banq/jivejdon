package com.jdon.jivejdon.infrastructure.repository.builder;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.RootMessage;

public interface MessageDirectorIF {

    ForumMessage getMessage(Long messageId);

    RootMessage getRootMessage(Long messageId,Long threadId);

    void setThreadDirector(ThreadDirectorIF threadDirectorIF);
}
