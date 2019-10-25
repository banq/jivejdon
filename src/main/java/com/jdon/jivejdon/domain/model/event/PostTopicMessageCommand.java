package com.jdon.jivejdon.domain.model.event;

import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;

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
