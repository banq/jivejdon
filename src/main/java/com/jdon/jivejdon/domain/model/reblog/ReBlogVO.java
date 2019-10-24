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
package com.jdon.jivejdon.domain.model.reblog;

import java.util.Collection;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.util.LazyLoader;
import com.jdon.jivejdon.domain.model.util.OneManyDTO;

public class ReBlogVO extends LazyLoader {

	private final long messageId;

	private LazyLoaderRole lazyLoaderRole;

	private volatile Collection<ForumMessage> messageTos;

	private volatile ForumMessage messageFrom;

	private volatile boolean load;

	public ReBlogVO(long messageId, LazyLoaderRole lazyLoaderRole) {
		super();
		this.messageId = messageId;
		this.lazyLoaderRole = lazyLoaderRole;
	}

	public Collection<ForumMessage> getMessageTos() {
		if (this.messageTos == null && lazyLoaderRole != null && !load) {
			OneManyDTO oneManyDTO = (OneManyDTO) super.loadResult();
			if (oneManyDTO != null) {
				loadAscResult(oneManyDTO);
			}
		}
		return messageTos;
	}

	public void associateThread(ForumThread thread) {
		Collection<ForumMessage> messageTos = getMessageTos();
		messageTos.add(thread.getRootMessage());
	}

	public ForumMessage getMessageFrom() {
		if (this.messageFrom == null && lazyLoaderRole != null && !load) {
			OneManyDTO oneManyDTO = (OneManyDTO) super.loadResult();
			if (oneManyDTO != null) {
				loadAscResult(oneManyDTO);
			}
		}
		return messageFrom;
	}

	private void loadAscResult(OneManyDTO oneManyDTO) {
		messageTos = oneManyDTO.getChildern();
		messageFrom = (ForumMessage) oneManyDTO.getParent();
		load = true;
	}

	@Override
	public DomainMessage getDomainMessage() {
		return lazyLoaderRole.loadReBlog(messageId);
	}
}
