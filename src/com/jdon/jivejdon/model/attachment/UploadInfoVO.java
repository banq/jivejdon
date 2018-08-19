package com.jdon.jivejdon.model.attachment;

import com.jdon.jivejdon.model.ForumMessage;

/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 * 
 */

public class UploadInfoVO {

	private String imageId;

	private String oid;

	private String name;

	private String description;

	private boolean belongToMessage;

	private ForumMessage forumMessage;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public boolean isBelongToMessage() {
		return belongToMessage;
	}

	public void setBelongToMessage(boolean belongToMessage) {
		this.belongToMessage = belongToMessage;
	}

	public ForumMessage getForumMessage() {
		return forumMessage;
	}

	public void setForumMessage(ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
	}

}
