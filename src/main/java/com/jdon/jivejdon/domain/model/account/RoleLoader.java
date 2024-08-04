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
package com.jdon.jivejdon.domain.model.account;

import java.util.concurrent.atomic.AtomicReference;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.util.LazyLoader;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

public class RoleLoader extends LazyLoader {

	private final long accountId;

	private final LazyLoaderRole lazyLoaderRole;

	private final AtomicReference<String> rolenameRef = new AtomicReference<>(null);

	public RoleLoader(long accountId, LazyLoaderRole lazyLoaderRole) {
		super();
		this.accountId = accountId;
		this.lazyLoaderRole = lazyLoaderRole;
	}

	
    //from chatGPT thread safe
	public String getRoleName() {
		// Use the rolenameRef to ensure thread-safe access to rolename
		String rolename = rolenameRef.get();
		if (rolename == null) {
            
			if (domainMessageRef.get() == null && lazyLoaderRole != null) {
				super.preload(); // Assuming preload is thread-safe
			}
			if (domainMessageRef.get() != null) {
				rolename = super.loadResult().map(value -> (String) value).orElse(null);
				// Use AtomicReference's compareAndSet to ensure atomic updates
				rolenameRef.compareAndSet(null, rolename);
			}
		}
		return rolenameRef.get();
	}

	@Override
	public DomainMessage getDomainMessage() {
		return lazyLoaderRole.loadRolename(Long.toString(this.accountId));
	}

}
