package com.jdon.jivejdon.model.event;

import com.jdon.jivejdon.model.ForumMessage;

public class MessageRemovedEvent {

    private final ForumMessage forumMessage;

    public MessageRemovedEvent(ForumMessage forumMessage) {
        this.forumMessage = forumMessage;
    }

    public ForumMessage getForumMessage() {
        return forumMessage;
    }
}
