package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver  implements Startable {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final ApprovedListSpec approvedListSpec = new ApprovedListSpec();
	private AtomicReference<Collection<Long>>  list;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
		this.list = new AtomicReference<>();
	}

	public void start() {

		Runnable task = new Runnable() {
			public void run() {
				clear();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 60 * 60 * 6,
				60 * 60 * 6, TimeUnit.SECONDS);

	}

	private void clear() {
		this.list = new AtomicReference<>();
	}

	public void stop() {
		try {
			ScheduledExecutorUtil.scheduExecStatic.shutdownNow();
		} catch (Exception e) {
		}
	}


	public Collection<Long> getList(int start, int count) {
		if (list.get() == null)
			synchronized (this) {
				if (list.get() == null)
					init();
			}
		return list.get().stream().skip(start).limit(count).collect(Collectors.toList());
	}

	private synchronized void init() {
		Collection<Long> listInit = new ArrayList<>();
		for (int i = 0; i < 75; i = i + 15) {
			listInit.addAll(threadApprovedNewList.getApprovedThreads(i));
		}
		listInit = listInit.stream().collect(Collectors.toMap((threadId) -> forumMessageQueryService
				.getThread(threadId), threadId -> threadId, (e1, e2) -> e1,
				() -> new ConcurrentSkipListMap<ForumThread, Long>(new HomePageComparator(approvedListSpec))))
				.values();

		this.list.set(listInit);		
	}

	

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
