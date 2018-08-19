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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMap {

	private final Map map;

	public ImmutableMap() {
		this(Collections.EMPTY_MAP);
	}

	public ImmutableMap immutablePut(Object key, Object value) {
		Map newMap = new HashMap(map);
		newMap.put(key, value);
		return new ImmutableMap(newMap);// 创新新的不可变Map
	}

	public ImmutableMap immutableRemove(Object key) {
		Map newMap = new HashMap(map);
		newMap.remove(key);
		return new ImmutableMap(newMap);
	}

	private ImmutableMap(Map delegate) {
		this.map = Collections.unmodifiableMap(delegate);
	}

	public void clear() {
		map.clear();
	}

	public ImmutableMap clone() {
		Map newMap = new HashMap(map);
		return new ImmutableMap(newMap);// 创新新的不可变Map
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public int size() {
		return map.size();
	}

}
