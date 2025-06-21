package com.jdon.jivejdon.spi.component.viewcount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.property.ThreadPropertys;
import com.jdon.jivejdon.domain.model.thread.ViewCounter;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

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

	private final Map<Long,ViewCounter> viewcounters;

	private final PropertyDao propertyDao;
	private final ThreadViewCountParameter threadViewCountParameter;
	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadViewCounterJobImp(final PropertyDao propertyDao,
			final ThreadViewCountParameter threadViewCountParameter, ScheduledExecutorUtil scheduledExecutorUtil) {
		this.viewcounters = new ConcurrentHashMap<>();
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
		List<ViewCounter> viewCounters2 = new ArrayList<>(this.viewcounters.values());
		for (ViewCounter viewCounter : viewCounters2) {
			if (viewCounter.isDirty() && viewCounter.getViewCount() != -1 && viewCounter.getViewCount() != 0) {
				saveItem(viewCounter);
				viewCounter.clearDirty();
				// 只移除已写入的 key，避免并发丢失
				this.viewcounters.remove(viewCounter.getThreadId(), viewCounter);
			}
		}
	}

	private void saveItem(ViewCounter viewCounter) {
		try {
			Property property = new Property();
			property.setName(ThreadPropertys.VIEW_COUNT);
			property.setValue(Long.toString(viewCounter.getViewCount()));
			propertyDao.updateProperty(Constants.THREAD, viewCounter.getThreadId(), property);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob#
	 * saveViewCounter (com.jdon.jivejdon.domain.model.ForumThread)
	 */
	@Override
	public boolean saveViewCounter(ViewCounter viewCounter) {
		return viewcounters.putIfAbsent(viewCounter.getThreadId(), viewCounter) == null;
	}

	public ViewCounter getViewCounter(Long threadId){
		return viewcounters.get(threadId);
	}

	public List<Long> getThreadIdsList() {
		return viewcounters.values().stream().map(e -> e.getThreadId()).collect(Collectors.toList());
	}

}
