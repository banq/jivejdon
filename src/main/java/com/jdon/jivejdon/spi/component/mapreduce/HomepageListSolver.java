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
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
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
	private final ThreadViewCounterJob threadViewCounterJob;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService,
							  ThreadViewCounterJob threadViewCounterJob) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
		this.list = new AtomicReference<>();
		this.threadViewCounterJob = threadViewCounterJob;
	}

	public void start() {

		Runnable task = new Runnable() {
			public void run() {
				clear();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task,   30 * 60 *1,
		   30 * 60 * 1, TimeUnit.SECONDS);

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
		// 第一步：将原始 listInit 放入 ConcurrentSkipListMap 进行排序
		ConcurrentSkipListMap<ForumThread, Long> sortedMap = listInit.stream()
				.collect(Collectors.toMap(
						(threadId) -> forumMessageQueryService.getThread(threadId),
						threadId -> threadId,
						(e1, e2) -> e1,
						() -> new ConcurrentSkipListMap<>(
								new HomePageComparator(approvedListSpec, threadViewCounterJob))));

		// 第二步：从 threadViewCounterJob 获取所有 ViewCounter，并筛选 5 天内的帖子
		threadViewCounterJob.getThreadIdsList().stream()
				.map(threadId -> forumMessageQueryService.getThread(threadId))
				.filter(thread -> {
					long diffDays = calculateDiffDays(thread);
					return diffDays <= 25; // 只保留 5 天内的帖子
				})
				.forEach(thread -> sortedMap.putIfAbsent(thread, thread.getThreadId())); // 添加到 sortedMap

		// 第三步：将排序结果赋值给 listInit
		listInit = new ArrayList<>(sortedMap.values());
		this.list.set(listInit);
	}

	private long calculateDiffDays(ForumThread thread) {
        long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getCreationDate2());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
	

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
