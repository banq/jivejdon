package com.jdon.jivejdon.infrastructure.repository.search;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.MessageUtilSQL;
import com.jdon.jivejdon.util.PageIteratorSolverFixed;

public class ReBuildIndex implements Runnable {
	private final static Logger logger = LogManager.getLogger(ReBuildIndex.class);

	private Object lock = new Object();

	private boolean busy = false;

	private MessageSearchProxy messageSearchProxy;

	private ForumFactory forumAbstractFactory;

	protected JdbcTempSource jdbcTempSource;


	public ReBuildIndex(ForumFactory forumAbstractFactory, JdbcTempSource jdbcTempSource) {
		messageSearchProxy = new MessageSearchProxy(true);
		this.forumAbstractFactory = forumAbstractFactory;
		this.jdbcTempSource = jdbcTempSource;
	}

	public void run() {
		synchronized (lock) {
			// If another index operation is already occuring, do nothing.
			if (busy) {
				return;
			}
			busy = true;
		}
		try {
			// Do a rebuild if we were told to do so, or if the index
			// has never been built before.
			rebuildIndex();
		} finally {
			// Reset state of the search component to idle.
			busy = false;
			if (messageSearchProxy != null)
				messageSearchProxy.stop();
		}
		logger.warn("rebuildIndex finished!");
	}

	private final void rebuildIndex() {
		int start = 0;
		int count = 30;
		boolean found = false;
		PageIterator pi = getAllThreads(start, count);
		int allCount = pi.getAllCount();
		while ((start < allCount) && (!found)) {// loop all
			while (pi.hasNext()) {
				Long threadId = (Long) pi.next();
				addMessage(threadId);

			}
			if (found)
				break;
			start = start + count;
			logger.debug("rebuildIndex start = " + start + " count = " + count);
			pi = getAllThreads(start, count);
		}
        logger.error("rebuildIndex ok!");
	}

	private void addMessage(Long threadId) {
		try {
			ForumThread thread = forumAbstractFactory.getThread(threadId).get();
			ForumMessage message = thread.getRootMessage();
			AnemicMessageDTO anemicMessageDTO = new AnemicMessageDTO();
			anemicMessageDTO.setMessageId(message.getMessageId());
			anemicMessageDTO.setForum(message.getForum());
			anemicMessageDTO.setAccount(message.getAccount());
			anemicMessageDTO.setMessageVO(message.getMessageVO());
			anemicMessageDTO.setForumThread(message.getForumThread());
			messageSearchProxy.createMessage(anemicMessageDTO);
            Thread.sleep(5000);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public PageIterator getAllThreads(int start, int count) {
    String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread";
    String GET_ALL_ITEMS = "select threadID from jiveThread ORDER BY threadID ASC LIMIT ?, ?";
    Collection<Object> countParams = new ArrayList<>(1);
    Collection<Object> params = new ArrayList<>(2);
    params.add(start);
    params.add(count);

    int allCount = 0;
    Collection<Long> threadIDs = new ArrayList<>();
    try {
        Object allCountObj = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
        if (allCountObj instanceof Long)
            allCount = ((Long) allCountObj).intValue();
        else if (allCountObj instanceof Integer)
            allCount = (Integer) allCountObj;

        @SuppressWarnings("unchecked")
        java.util.List<java.util.Map<String, Object>> list =
            jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
        for (java.util.Map<String, Object> map : list) {
            threadIDs.add((Long) map.get("threadID"));
        }
    } catch (Exception e) {
        logger.error("getAllThreads SQL error: " + e);
        return new PageIterator();
    }
    return new PageIterator(allCount, threadIDs.toArray());
}

}
