package com.jdon.jivejdon.domain.model.message;

public final class MessageUrlVO {

	private final String thumbnailUrl;

	private final String linkUrl;

	public MessageUrlVO(String linkUrl, String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
		this.linkUrl = linkUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}
}
