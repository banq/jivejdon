package com.jdon.jivejdon.domain.event;

import com.jdon.jivejdon.domain.model.ForumMessage;

public class MessageRemovedEvent {

    private final ForumMessage forumMessage;

    public MessageRemovedEvent(ForumMessage forumMessage) {
        this.forumMessage = forumMessage;
    }

    public ForumMessage getForumMessage() {
        return forumMessage;
    }
}
