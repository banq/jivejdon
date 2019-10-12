package com.jdon.jivejdon.repository.search;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.query.MessagePageIteratorSolver;
import com.jdon.model.query.PageIteratorSolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class ReBuildIndex implements Runnable {
	private final static Logger logger = LogManager.getLogger(ReBuildIndex.class);

	private Object lock = new Object();

	private boolean busy = false;

	private MessageSearchProxy messageSearchProxy;

	private ForumFactory forumAbstractFactory;

	protected PageIteratorSolver pageIteratorSolver;

	public ReBuildIndex(ForumFactory forumAbstractFactory, MessagePageIteratorSolver messagePageIteratorSolver) {
		messageSearchProxy = new MessageSearchProxy(true);
		this.forumAbstractFactory = forumAbstractFactory;
		this.pageIteratorSolver = messagePageIteratorSolver.getPageIteratorSolver("ReBuildIndex");

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
			// Reset state of the search manager to idle.
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
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread ";

		String GET_ALL_ITEMS = "select threadID  from jiveThread ";
		Collection params = new ArrayList(1);
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start, count);
	}

}
