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
package com.jdon.jivejdon.domain.model.util;

import java.util.Optional;

import com.jdon.domain.message.DomainMessage;

public abstract class LazyLoader {

	protected volatile DomainMessage domainMessage;

	public Object loadBlockedResult() {
		if (domainMessage == null)
			preload();
		Object loadedResult = null;
		try {
			loadedResult = domainMessage.getBlockEventResult();
			if (loadedResult != null)
				domainMessage.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loadedResult;
	}

	/**
	 * 
	 * if return a COllection, should be a empty arrayList.
	 * 
	 * @return
	 */
	public Optional<Object> loadResult() {
		if (domainMessage == null)
			preload();
		Object loadedResult = null;
		try {
			loadedResult = domainMessage.getEventResult();
			if (loadedResult != null)
				domainMessage.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(loadedResult) ;

	}

	public void preload() {
		try {
			if (domainMessage == null)
				domainMessage = getDomainMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		if (this.domainMessage != null) {
			domainMessage.clear();
		}
	}

	public void setDomainMessage(DomainMessage domainMessage) {
		this.domainMessage = domainMessage;
	}

	public abstract DomainMessage getDomainMessage();

}
