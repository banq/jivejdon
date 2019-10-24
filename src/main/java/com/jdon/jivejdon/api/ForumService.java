/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.service;

import java.util.Collection;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.Forum;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface ForumService {

	Forum getForum(Long id);

	void createForum(EventModel em);

	void updateForum(EventModel em);

	void deleteForum(EventModel em);

	PageIterator getForums(int start, int count);

	public Collection<Forum> getForums();

	// <!--
	// /admin/doRebuildIndex.shtml?service=forumService&method=doRebuildIndex
	// -->
	void doRebuildIndex();

	void clearCache();

}
