package com.jdon.jivejdon.model.event;

import com.jdon.jivejdon.model.message.AnemicMessageDTO;

public class PostTopicMessageCommand {

	private final AnemicMessageDTO forumMessageDTO;

	public PostTopicMessageCommand(AnemicMessageDTO forumMessageDTO) {
		super();
		this.forumMessageDTO = forumMessageDTO;
	}

	public AnemicMessageDTO getForumMessageDTO() {
		return forumMessageDTO;
	}
}
