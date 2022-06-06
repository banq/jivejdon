package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumMessageQueryService forumMessageQueryService;
	private Collection<Long> list;
	private long lastFetchTime;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public Collection<Long> getList() {
		if (list == null) {
			list = fetchList();
			lastFetchTime = System.currentTimeMillis();
		}

		long diff = TimeUnit.HOURS.convert(Math.abs(System.currentTimeMillis() - lastFetchTime), TimeUnit.MILLISECONDS);
		if (diff > 5) {
			list = fetchList();
			lastFetchTime = System.currentTimeMillis();
		}

		return list;

	}

	public Collection<Long> fetchList() {
		Collection<Long> list = new ArrayList<>();
		for (int i = 0; i < 120; i = i + 15) {
			list.addAll(threadApprovedNewList.getApprovedThreads(i));
		}
		list = list.parallelStream().collect(Collectors.toMap((threadId) -> forumMessageQueryService
				.getThread(threadId), threadId -> threadId, (e1, e2) -> e1,
				() -> new TreeMap<ForumThread, Long>(new HomePageComparator()))).values();
		return list.parallelStream().skip(0).limit(10).collect(Collectors.toList());

	}

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
