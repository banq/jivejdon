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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.service.ForumService;

public class ForumUtil {


	public static ForumMessage getForumsLastModifiedDate(ServletContext sc) {
		Collection<Long> listF = new ArrayList();
		Map<Long, ForumMessage> maps = new HashMap();
		try {
			ForumService forumService = (ForumService) WebAppUtil.getService("forumService", sc);
			PageIterator pageIterator = forumService.getForums(0, 10);
			while (pageIterator.hasNext()) {
				Forum forum = forumService.getForum((Long) pageIterator.next());
				listF.add(forum.getForumState().getLastPost().getMessageId());
				maps.put(forum.getForumState().getLastPost().getMessageId(), forum.getForumState().getLastPost());
			}
			return maps.get(Collections.max(listF));
		} catch (Exception ex) {
		}
		return null;

	}

}
