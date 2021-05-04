package com.jdon.jivejdon.spi.component.viewcount;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.property.ThreadPropertys;
import com.jdon.jivejdon.domain.model.thread.ViewCounter;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

	private final Set<ViewCounter> viewcounters;

	private final PropertyDao propertyDao;
	private final ThreadViewCountParameter threadViewCountParameter;
	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadViewCounterJobImp(final PropertyDao propertyDao,
			final ThreadViewCountParameter threadViewCountParameter, ScheduledExecutorUtil scheduledExecutorUtil) {
		this.viewcounters = new TreeSet<>(new ThreadViewComparator());
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
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, threadViewCountParameter.getInitdelay(),
				threadViewCountParameter.getDelay(), TimeUnit.SECONDS);
	}

	// when container down or undeploy, active this method.
	public void stop() {
		writeDB();
		viewcounters.clear();
		this.scheduledExecutorUtil.stop();
	}

	public void writeDB() {
		// construct a immutable set
		List<ViewCounter> viewCounters2 = new ArrayList<>(this.viewcounters);
		this.viewcounters.clear();
		for (ViewCounter viewCounter : viewCounters2) {
			if (viewCounter.getViewCount() != viewCounter.getLastSavedCount() && viewCounter.getViewCount() != -1
					&& viewCounter.getViewCount() != 0) {
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
	 * @see com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob#
	 * saveViewCounter (com.jdon.jivejdon.domain.model.ForumThread)
	 */
	@Override
	public void saveViewCounter(ForumThread thread) {
		viewcounters.add(thread.getViewCounter());
	}

	public List<Long> getThreadIdsList() {
		SortedSet<ViewCounter> sortedset = new TreeSet<ViewCounter>(new ThreadViewComparator());
		sortedset.addAll(viewcounters);
		return sortedset.parallelStream().map(e -> e.getThread().getThreadId()).collect(Collectors.toList());
	}

}
