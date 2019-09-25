package com.jdon.jivejdon.repository.builder;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

public interface MessageDirectorIF {

    ForumMessage getMessage(Long messageId);

    ForumMessage getMessage(Long messageId, ForumThread forumThread);

    void setThreadDirector(ThreadDirectorIF threadDirectorIF);
}
