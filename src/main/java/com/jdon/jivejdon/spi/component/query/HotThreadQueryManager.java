package com.jdon.jivejdon.spi.component.query;

import com.jdon.container.pico.Startable;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.query.HotThreadSpecification;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HotThreadQueryManager implements Startable {
	private final static Logger logger = LogManager.getLogger(HotThreadQueryManager.class);

	protected MessageQueryDao messageQueryDao;

	protected ForumFactory forumBuilder;

	protected AccountFactory accountFactory;

	private final Map<String, List> hotThreadKeys;

	private final Map<String, List> digThreadKeys;


	public HotThreadQueryManager(MessageQueryDao messageQueryDao, AccountFactory accountFactory, ForumFactory forumBuilder) {
		super();
		this.messageQueryDao = messageQueryDao;
		this.accountFactory = accountFactory;
		this.forumBuilder = forumBuilder;
		this.hotThreadKeys = new ConcurrentHashMap();
		this.digThreadKeys = new ConcurrentHashMap();
	}

	@Override
	public void start() {
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

	/**
	 * get hot thread before several days, not too long
	 */
	public PageIterator getDigThreadPageKeys(QueryCriteria qc, int start, int count) {
		try {
			List resultSortedIDs = getDigThreadKeys(qc);
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
			resultSortedIDs = messageQueryDao.getHotThreadIDs(qc);
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

	public List getDigThreadKeys(QueryCriteria qc) {
		String cacheKey = qc.toString() + qc.getDigCountWindow();
		logger.debug("enter getDigThreadKeys, key=" + cacheKey);
		List resultSortedIDs = (List) digThreadKeys.get(cacheKey);
		if (resultSortedIDs == null) {
			logger.debug("not found it in cache, create it");
			resultSortedIDs = messageQueryDao.getDigThreadIDs(qc);
			if (resultSortedIDs.size() > 0) {
				if (digThreadKeys.size() > 100)
					hotThreadKeys.clear();
				digThreadKeys.put(cacheKey, resultSortedIDs);
				logger.debug("resultSortedIDs.size() == " + resultSortedIDs.size());
			} else {
				logger.debug("resultSortedIDs.size() == 0");
			}
		}
		return resultSortedIDs;
	}

	private List<Long> createSortedIDs(QueryCriteria qc) {
        try {
            Collection resultIDs = messageQueryDao.getThreads(qc);
            SortedSet<ThreadCompareVO> threads = new TreeSet<>(new ThreadReplyComparator());
            Iterator iter = resultIDs.iterator();
            while (iter.hasNext()) {
                if (threads.size() > 100) break;
                Long threadId = (Long) iter.next();
                int messageCount = messageQueryDao.getMessageCount(threadId);
                if (messageCount > qc.getMessageReplyCountWindow()) {
                    threads.add(new ThreadCompareVO(threadId, messageCount));
                }
            }
            logger.debug(" found messageCount > " + qc.getMessageReplyCountWindow() + " size=" + threads.size());
            return threads.stream()
                    .map(ThreadCompareVO::getThreadId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    static class ThreadReplyComparator implements Comparator<ThreadCompareVO> {
        public int compare(ThreadCompareVO a, ThreadCompareVO b) {
            return Integer.compare(a.getMessageCount(), b.getMessageCount());
        }
    }

	@Override
	public void stop() {
		try {
			hotThreadKeys.clear();
			digThreadKeys.clear();
		} catch (Exception e) {
		}

	}

}
