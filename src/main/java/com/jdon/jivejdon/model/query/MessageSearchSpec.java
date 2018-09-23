package com.jdon.jivejdon.model.query;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageVO;

public class MessageSearchSpec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ForumMessage message;

	private Long messageId;

	private MessageVO messageVO;

	private int resultAllCount;

	private boolean root;

	public MessageSearchSpec() {
		messageVO = new MessageVO();
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

	public MessageVO getMessageVO() {
		return messageVO;
	}

	public void setMessageVO(MessageVO messageVO) {
		this.messageVO = messageVO;
	}

}
