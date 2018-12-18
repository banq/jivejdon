package com.jdon.jivejdon.model.event;

import com.jdon.jivejdon.model.ForumMessage;

public class TopicMessageCreateCommand {

	private final ForumMessage forumMessageDTO;

	public TopicMessageCreateCommand(ForumMessage forumMessageDTO) {
		super();
		this.forumMessageDTO = forumMessageDTO;
	}

	public ForumMessage getForumMessageDTO() {
		return forumMessageDTO;
	}
}
