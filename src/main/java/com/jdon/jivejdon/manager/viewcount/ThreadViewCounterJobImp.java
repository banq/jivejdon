package com.jdon.jivejdon.manager.viewcount;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.property.Property;
import com.jdon.jivejdon.model.property.ThreadPropertys;
import com.jdon.jivejdon.model.thread.ViewCounter;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * a cache used for holding view count of ForumThread the data that in the cache
 * will be flushed to database per one hour
 * 
 * @author oojdon banq
 * 
 */

@Component("threadViewCounterJob")
public class ThreadViewCounterJobImp implements Startable, ThreadViewCounterJob {
	private final static Logger logger = LogManager.getLogger(ThreadViewCounterJobImp.class);

	private final ConcurrentMap<Long, ViewCounter> concurrentHashMap;

	private final PropertyDao propertyDao;
	private final ThreadViewCountParameter threadViewCountParameter;
	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadViewCounterJobImp(final PropertyDao propertyDao, final ThreadViewCountParameter threadViewCountParameter,
			ScheduledExecutorUtil scheduledExecutorUtil) {
		this.concurrentHashMap = new ConcurrentHashMap<Long, ViewCounter>();
		this.propertyDao = propertyDao;
		this.threadViewCountParameter = threadViewCountParameter;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				writeDB();
			}
		};
		// flush to db per one hour
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, threadViewCountParameter.getInitdelay(), threadViewCountParameter.getDelay(),
				TimeUnit.SECONDS);
	}

	// when container down or undeploy, active this method.
	public void stop() {
		writeDB();
		concurrentHashMap.clear();
		this.scheduledExecutorUtil.stop();
	}

	public void writeDB() {
		// construct a immutable map, not effect old map.
		Map<Long, ViewCounter> viewCounters = new HashMap(concurrentHashMap);
		concurrentHashMap.clear();
		for (long threadId : viewCounters.keySet()) {
			ViewCounter viewCounter = viewCounters.get(threadId);
			if (viewCounter.getViewCount() != viewCounter.getLastSavedCount() && viewCounter
					.getViewCount() != -1 && viewCounter.getViewCount() != 0) {
				saveItem(viewCounter);
				viewCounter.setLastSavedCount(viewCounter.getViewCount());
			}
		}
	}

	private void saveItem(ViewCounter viewCounter) {
		try {
			Property property = new Property();
			property.setName(ThreadPropertys.VIEW_COUNT);
			property.setValue(Long.toString(viewCounter.getViewCount()));
			propertyDao.updateProperty(Constants.THREAD, viewCounter.getThread().getThreadId(), property);
		} catch (Exception e) {
			logger.error(e);
		} finally {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.viewcount.ThreadViewCounterJob#checkViewCounter
	 * (com.jdon.jivejdon.model.ForumThread)
	 */
	@Override
	public boolean checkViewCounter(ForumThread thread) {
		if (!concurrentHashMap.containsKey(thread.getThreadId())) {
			concurrentHashMap.put(thread.getThreadId(), thread.getViewCounter());
			return true;
		}
		return false;
	}

	private int getFromDB(Long threadId) {
		Integer number = null;
		Property p = propertyDao.getThreadProperty(threadId, ThreadPropertys.VIEW_COUNT);
		if (p != null)
			number = Integer.valueOf(p.getValue());
		else
			number = new Integer(0);

		return number;
	}

}
