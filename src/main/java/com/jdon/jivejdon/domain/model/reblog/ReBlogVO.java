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
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.util.LazyLoader;
import com.jdon.jivejdon.domain.model.util.Many2ManyDTO;

public class ReBlogVO extends LazyLoader {

	private final ForumThread thread;

	private volatile Collection<ForumThread> threadTos;

	private volatile Collection<ForumThread> threadFroms;

	private volatile boolean load;

	public ReBlogVO(ForumThread thread) {
		this.thread = thread;
	}

	private ReBlogVO(){
        this.thread = null;
	}

	public Collection<ForumThread> getThreadTos() {
		loadAscResult();
		return threadTos;
	}

	public Collection<ForumThread> getThreadFroms() {
		loadAscResult();
		return threadFroms;
	}

	public void loadAscResult() {
		if (!load) {
			super.setDomainMessage(null);
			Many2ManyDTO many2ManyDTO = super.loadResult().map(value -> (Many2ManyDTO) value).orElse(null);
			if (many2ManyDTO != null) {
				threadTos = many2ManyDTO.getChildern();
				threadFroms = many2ManyDTO.getParent();
				
			}
			load = true;
		}

	}

	@Override
	public DomainMessage getDomainMessage() {
		return this.thread.lazyLoaderRole.loadReBlog(thread.getThreadId());
	}

	public void refresh() {
		load = false;
	}

}
