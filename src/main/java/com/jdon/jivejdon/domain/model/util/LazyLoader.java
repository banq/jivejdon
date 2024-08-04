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
import java.util.concurrent.atomic.AtomicReference;

import com.jdon.domain.message.DomainMessage;

public abstract class LazyLoader {

	protected final AtomicReference<DomainMessage> domainMessageRef = new AtomicReference<>(null);

	public Object loadBlockedResult() {

		// 尝试获取 domainMessage
		DomainMessage domainMessage = domainMessageRef.get();

		// 如果 domainMessage 为 null，调用 preload 方法进行预加载
		if (domainMessage == null) {
			preload(); // 可能会设置 domainMessageRef
			domainMessage = domainMessageRef.get(); // 再次获取更新后的 domainMessage
		}

		Object loadedResult = null;
		try {
			if (domainMessage != null) {
				loadedResult = domainMessage.getBlockEventResult();
				// 如果需要的话，清除 domainMessage
				// ((YourDomainMessageType) domainMessage).clear();
			}
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
		// 尝试获取 domainMessage
		DomainMessage domainMessage = domainMessageRef.get();

		// 如果 domainMessage 为 null，调用 preload 方法进行预加载
		if (domainMessage == null) {
			preload(); // 可能会设置 domainMessageRef
			domainMessage = domainMessageRef.get(); // 再次获取更新后的 domainMessage
		}

		Object loadedResult = null;
		try {
			if (domainMessage != null) {
				loadedResult = domainMessage.getEventResult();
				// 如果需要的话，清除 domainMessage
				// ((YourDomainMessageType) domainMessage).clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(loadedResult);

	}

	public void preload() {
		try {
			// 尝试将 domainMessageRef 设置为从 getDomainMessage() 获取的值
			domainMessageRef.compareAndSet(null, getDomainMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		if (domainMessageRef.get() != null) {
			domainMessageRef.get().clear();
		}
	}

	public void setDomainMessage(DomainMessage domainMessage) {
		domainMessageRef.set(domainMessage);
	}

	public abstract DomainMessage getDomainMessage();

}
