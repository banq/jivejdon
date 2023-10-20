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
package com.jdon.jivejdon.domain.model;

import com.jdon.annotation.Model;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.FilterPipleSpec;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.domain.model.property.Property;

import java.util.Collection;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public final class ForumMessageReply extends ForumMessage {
	private static final long serialVersionUID = 1L;

	private final ForumMessage parentMessage;

	protected ForumMessageReply(ForumMessage parentMessage) {
		super(parentMessage.getForumThread().getThreadId());
		this.parentMessage = parentMessage;
	}

	/**
	 * parentMessage is not full Object, only has messageId if you use it ,you must
	 * getMessage() again by its messageId
	 * 
	 * @return Returns the parentMessage.
	 */
	public ForumMessage getParentMessage() {
		return parentMessage;
	}

	
	public boolean isRoot() {
		return false;
	}

	public void build(long messageId, MessageVO messageVO, Forum forum, Account account,
			String creationDate, long modifiedDate, FilterPipleSpec filterPipleSpec, Collection<UploadFile> uploads,
			Collection<Property> props, HotKeys hotKeys, ForumMessage parentMessage) {
		super.build(messageId, messageVO, forum, account, creationDate, modifiedDate, filterPipleSpec,
				uploads, props, hotKeys);

	}

}
