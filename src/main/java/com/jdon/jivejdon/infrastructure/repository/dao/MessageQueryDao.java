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
package com.jdon.jivejdon.infrastructure.repository.dao;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.treepatterns.model.TreeModel;

import java.util.Collection;
import java.util.List;

/**
 * all query batch operation for ForumThread or ForumMessage
 * 
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface MessageQueryDao {

	TreeModel getTreeModel(Long threadId, Long rootMessageId);

	PageIterator getMessages(Long threadId, int start, int count);


	PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort);

	PageIterator getThreads(int start, int count, ThreadListSpec threadListSpec);

	/*
	 * get the threads collection include prev/cuurent/next threads.
	 */
	List getThreadsPrevNext(Long forumId, Long currentThreadId);


	int getMessageCountOfUser(Long userId);

	PageIterator getMessages(QueryCriteria qc, int start, int count);

	PageIterator getThreads(QueryCriteria qc, int start, int count);

	Collection getThreads(QueryCriteria msc);

	Long getLatestPostMessageId(Long threadId);

	Long getForumLatestPostMessageId(Long forumId);


	int getMessageCount(Long threadId);

	int getForumMessageCount(Long threadId);

	// text search
	Collection find(String query, int start, int count);

	Collection findThread(String query, int start, int count);

	int findThreadsAllCount(String query);


	public PageIterator getThreadListByUser(String userId, int start, int count);

	public PageIterator getMesageListByUser(String userId, int start, int count);

}
