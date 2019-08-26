package com.jdon.jivejdon.model.event;

import com.jdon.jivejdon.model.message.AnemicMessageDTO;

public class TopicMessageCreateCommand {

	private final AnemicMessageDTO forumMessageDTO;

	public TopicMessageCreateCommand(AnemicMessageDTO forumMessageDTO) {
		super();
		this.forumMessageDTO = forumMessageDTO;
	}

	public AnemicMessageDTO getForumMessageDTO() {
		return forumMessageDTO;
	}
}
