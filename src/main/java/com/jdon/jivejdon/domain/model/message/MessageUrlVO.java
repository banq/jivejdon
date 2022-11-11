package com.jdon.jivejdon.domain.model.message;

public final class MessageUrlVO {

	private final String imageUrl;

	private final String thumbnailUrl;

	private final String linkUrl;

	

	public MessageUrlVO(String linkUrl, String thumbnailUrl, String imageUrl) {
		this.thumbnailUrl = thumbnailUrl;
		this.linkUrl = linkUrl;
		this.imageUrl = imageUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}



	
}
