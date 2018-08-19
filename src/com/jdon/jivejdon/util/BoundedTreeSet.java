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
package com.jdon.jivejdon.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * A TreeSet that ensures it never grows beyond a max size. <code>last()</code>
 * is removed if the <code>size()</code> get's bigger then
 * <code>getMaxSize()</code>
 */

public class BoundedTreeSet<E> extends ConcurrentSkipListSet<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxSize = Integer.MAX_VALUE;

	public BoundedTreeSet(int maxSize) {
		super();
		this.setMaxSize(maxSize);
	}

	public BoundedTreeSet(int maxSize, Collection<? extends E> c) {
		super(c);
		this.setMaxSize(maxSize);
	}

	public BoundedTreeSet(int maxSize, Comparator<? super E> c) {
		super(c);
		this.setMaxSize(maxSize);
	}

	public BoundedTreeSet(int maxSize, SortedSet<E> s) {
		super(s);
		this.setMaxSize(maxSize);
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int max) {
		maxSize = max;
		adjust();
	}

	private void adjust() {
		if (maxSize < size()) {
			this.pollLast();
		}
	}

	public boolean add(E item) {
		boolean out = super.add(item);
		adjust();
		return out;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean out = super.addAll(c);
		adjust();
		return out;
	}
}