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
package com.jdon.jivejdon.presentation.action.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;

public class ForumUtil {
    private final static ConcurrentMap<String, Object> serviceCache = new ConcurrentHashMap<>();

	private static ForumMessageQueryService getForumMessageQueryService(ServletContext sc) {
		return (ForumMessageQueryService) serviceCache.computeIfAbsent("forumMessageQueryService",
				k -> WebAppUtil.getService("forumMessageQueryService", sc));
	}

	public static long getForumsLastModifiedDate(ServletContext sc) {
		PageIterator pi = getForumMessageQueryService(sc).getThreads(0, 1, new ThreadListSpec());
		return pi.hasNext() ? getForumMessageQueryService(sc).getThread((Long) pi.next()).getCreationDate2()
				: System.currentTimeMillis();

	}

}
