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

import java.util.concurrent.LinkedBlockingDeque;

public class AutoDiscardingDeque<E> extends LinkedBlockingDeque<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutoDiscardingDeque() {
		super();
	}

	public AutoDiscardingDeque(int capacity) {
		super(capacity);
	}

	@Override
	public synchronized boolean offerFirst(E e) {
		if (remainingCapacity() == 0) {
			removeLast();
		}
		super.offerFirst(e);
		return true;
	}
}