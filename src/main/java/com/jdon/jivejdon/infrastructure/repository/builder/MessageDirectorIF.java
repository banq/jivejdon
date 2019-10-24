package com.jdon.jivejdon.repository.builder;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;

public interface MessageDirectorIF {

    ForumMessage getMessage(Long messageId);

    ForumMessage getRootMessage(Long messageId, ForumThread forumThread);

    void setThreadDirector(ThreadDirectorIF threadDirectorIF);
}
