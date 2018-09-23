package com.jdon.jivejdon.manager.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ForumThreadTemp;
import com.jdon.jivejdon.model.query.HotThreadSpecification;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

public class HotThreadQueryManager implements Startable {
	private final static Logger logger = LogManager.getLogger(HotThreadQueryManager.class);

	protected MessageQueryDao messageQueryDao;

	protected ForumFactory forumBuilder;

	protected AccountFactory accountFactory;

	private final Map<String, List> hotThreadKeys;

	public HotThreadQueryManager(MessageQueryDao messageQueryDao, AccountFactory accountFactory, ForumFactory forumBuilder) {
		super();
		this.messageQueryDao = messageQueryDao;
		this.accountFactory = accountFactory;
		this.forumBuilder = forumBuilder;
		this.hotThreadKeys = new ConcurrentHashMap();
	}

	@Override
	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				hotThreadKeys.clear();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 0, 60 * 60 * 6, TimeUnit.SECONDS);

	}

	/**
	 * get hot thread before several days, not too long
	 */
	public PageIterator getHotThreadPageKeys(QueryCriteria qc, int start, int count) {
		try {
			List resultSortedIDs = getHotThreadKeys(qc);
			if (resultSortedIDs.size() > 0) {
				List pageIds = new ArrayList(resultSortedIDs.size());
				for (int i = start; i < start + count; i++) {
					if (i < resultSortedIDs.size()) {
						pageIds.add(resultSortedIDs.get(i));
					} else
						break;
				}
				logger.debug("return PageIterator size=" + pageIds.size());
				return new PageIterator(resultSortedIDs.size(), pageIds.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageIterator();
	}

	public List getHotThreadKeys(QueryCriteria qc) {
		String cacheKey = qc.toString() + qc.getMessageReplyCountWindow();
		logger.debug("enter getHotThreadKeys, key=" + cacheKey);
		List resultSortedIDs = (List) hotThreadKeys.get(cacheKey);
		if (resultSortedIDs == null) {
			logger.debug("not found it in cache, create it");
			resultSortedIDs = createSortedIDs(qc);
			if (resultSortedIDs.size() > 0) {
				if (hotThreadKeys.size() > 100)
					hotThreadKeys.clear();
				hotThreadKeys.put(cacheKey, resultSortedIDs);
				logger.debug("resultSortedIDs.size() == " + resultSortedIDs.size());
			} else {
				logger.debug("resultSortedIDs.size() == 0");
			}
		}
		return resultSortedIDs;
	}

	private List createSortedIDs(QueryCriteria qc) {
		List resultSortedIDs = new ArrayList();
		try {
			Collection resultIDs = messageQueryDao.getThreads(qc);
			List threads = new LinkedList();
			Iterator iter = resultIDs.iterator();
			while (iter.hasNext()) {
				Long threadId = (Long) iter.next();
				int messageCount = messageQueryDao.getMessageCount(threadId);
				if (messageCount > qc.getMessageReplyCountWindow()) {// messageCount
																		// inluce
																		// root
																		// message
					// construte a empty forumthread only include messageCount;
					// we donot get a full forumThread, that will cost memory.
					ForumThread forumThread = new ForumThreadTemp();
					forumThread.setThreadId(threadId);
					forumThread.getState().setMessageCount(messageCount);
					threads.add(forumThread);
				}
			}
			logger.debug(" found messageCount > " + qc.getMessageReplyCountWindow() + " size=" + threads.size());
			HotThreadSpecification hotspec = new HotThreadSpecification();
			// Collection only sort several threads ,not many threads
			hotspec.sortByMessageCount(threads);
			iter = threads.iterator();
			while (iter.hasNext()) {
				ForumThread thread = (ForumThread) iter.next();
				resultSortedIDs.add(thread.getThreadId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSortedIDs;
	}

	@Override
	public void stop() {
		try {
			hotThreadKeys.clear();
			ScheduledExecutorUtil.scheduExecStatic.shutdownNow();
		} catch (Exception e) {
		}

	}

}
