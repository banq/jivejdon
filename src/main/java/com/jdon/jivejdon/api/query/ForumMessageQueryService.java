package com.jdon.jivejdon.api.query;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;

import java.util.List;

public interface ForumMessageQueryService {

	/**
	 * a Messages Collection of a ForumThread for batch inquiry
	 */
	PageIterator getMessages(Long threadId, int start, int count);


	/**
	 * a Messages Collection of their parenMessage for deleteMessage display
	 */
	PageIterator getRecursiveMessages(Long messageId, int start, int count);

	/**
	 * all forum's topic collection
	 * 
	 * @param forumId
	 * @param start
	 * @param count
	 * @return
	 */
	PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort);

	PageIterator getThreads(int start, int count, ThreadListSpec threadListSpec);

	/**
	 * get the thread collection include prev/current/next threads collection
	 * 
	 * @param currentThreadId
	 * @return ListIterator
	 */
	List getThreadsPrevNext(Long currentThreadId);

	PageIterator getHotThreads(QueryCriteria messageQueryCriteria, int start, int count);

	PageIterator getDigThreads(QueryCriteria messageQueryCriteria, int start, int count);

	PageIterator getMessages(QueryCriteria messageQueryCriteria, int start, int count);

	PageIterator getThreads(QueryCriteria qc, int start, int count);

	PageIterator searchMessages(String query, int start, int count);

	PageIterator searchThreads(String query, int start, int count);


	ForumThread getThread(Long threadId);

	ForumMessage getMessage(Long messageId);

	PageIterator getThreadListByUser(String userId, int start, int count);

	PageIterator getMesageListByUser(String userId, int start, int count);

}
