package com.jdon.jivejdon.service;

import java.util.List;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ThreadListSpec;

public interface ForumMessageQueryService {

	/**
	 * a Messages Collection of a ForumThread for batch inquiry
	 */
	PageIterator getMessages(Long threadId, int start, int count);

	PageIterator getMessages(int start, int count);

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
	 * @param start
	 * @param count
	 * @return ListIterator
	 */
	List getThreadsPrevNext(Long currentThreadId);

	PageIterator getHotThreads(QueryCriteria messageQueryCriteria, int start, int count);

	PageIterator getMessages(QueryCriteria messageQueryCriteria, int start, int count);

	PageIterator getMessageReplys(QueryCriteria messageQueryCriteria, int start, int count);

	PageIterator getThreads(QueryCriteria qc, int start, int count);

	PageIterator searchMessages(String query, int start, int count);

	PageIterator searchThreads(String query, int start, int count);

	PageIterator popularThreads(int popularThreadsWindow, int count);

	int locateTheMessage(Long threadId, Long messageId, int count);

	ForumThread getThread(Long threadId);

	ForumMessage getMessage(Long messageId);

	PageIterator getThreadListByUser(String userId, int start, int count);

	PageIterator getMesageListByUser(String userId, int start, int count);

}
