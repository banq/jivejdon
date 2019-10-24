package com.jdon.jivejdon.domain.model.query;

import com.jdon.jivejdon.domain.model.ForumMessage;

public class MessageSearchSpec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ForumMessage message;

	private Long messageId;

	private String subject;

	private String body;

	private int resultAllCount;

	private boolean root;

	public MessageSearchSpec() {

	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public int getResultAllCount() {
		return resultAllCount;
	}

	public void setResultAllCount(int resultAllCount) {
		this.resultAllCount = resultAllCount;
	}

	public ForumMessage getMessage() {
		return message;
	}

	public void setMessage(ForumMessage message) {
		this.message = message;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
