/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.event.domain.producer.read;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.util.OneOneDTO;

/**
 * this is the Role of DCI. and is domain events publisher
 * 
 * @author banq
 * 
 */
@Introduce("message")
public class LazyLoaderRole {

	@Send("rolename")
	public DomainMessage loadRolename(String userId) {
		return new DomainMessage(userId);
	}

	@Send("subscriptionCounter")
	public DomainMessage loadSubscriptionNumbers(OneOneDTO oneOneDTO) {
		return new DomainMessage(oneOneDTO);
	}

	@Send("accountMessageCounter")
	public DomainMessage loadAccountMessageCount(Long userId) {
		return new DomainMessage(userId);
	}

	@Send("reloadMessageVO")
	public DomainMessage reloadMessageVO(long messageId) {
		return new DomainMessage(messageId);
	}

	@Send("loadMessageProperties")
	public DomainMessage loadMessageProperties(long messageId) {
		return new DomainMessage(messageId);
	}

	@Send("loadMessageDigCount")
	public DomainMessage loadMessageDigCount(Long messageId) {
		return new DomainMessage(messageId);
	}

	@Send("loadTreeModel")
	public DomainMessage loadTreeModel(long threadId) {
		return new DomainMessage(threadId);
	}

	@Send("loadUploadFiles")
	public DomainMessage loadUploadFiles(String parentId) {
		return new DomainMessage(parentId);
	}

	@Send("loadNewShortMessageCount")
	public DomainMessage loadNewShortMessageCount(String userId) {
		return new DomainMessage(userId);
	}

	@Send("loadForum")
	public DomainMessage loadForum(Long forumId) {
		return new DomainMessage(forumId);
	}

	@Send("projectStateFromEventSource")
	public DomainMessage projectStateFromEventSource(Long threadId) {
		return new DomainMessage(threadId);
	}

	@Send("loadForumState")
	public DomainMessage loadForumState(Long forumId) {
		return new DomainMessage(forumId);
	}

	@Send("loadViewCount")
	public DomainMessage loadViewCount(Long threadId) {
		return new DomainMessage(threadId);
	}

	@Send("loadReBlog")
	public DomainMessage loadReBlog(Long messageId) {
		return new DomainMessage(messageId);
	}

}
