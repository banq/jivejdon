/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.model;

import com.jdon.annotation.Model;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class ForumMessageReply extends ForumMessage {
	private static final long serialVersionUID = 1L;

	private ForumMessage parentMessage;

	public ForumMessageReply(Long messageId, ForumMessage parentMessage) {
		super(messageId);
		this.parentMessage = parentMessage;
	}

	public ForumMessageReply() {
		super();
	}

	/**
	 * parentMessage is not full Object, only has messageId if you use it ,you
	 * must getMessage() again by its messageId
	 * 
	 * @return Returns the parentMessage.
	 */
	public ForumMessage getParentMessage() {
		return parentMessage;
	}

	/**
	 * @param parentMessage
	 *            The parentMessage to set.
	 */
	public void setParentMessage(ForumMessage parentMessage) {
		this.parentMessage = parentMessage;
	}

	public boolean isRoot() {
		return false;
	}
}
