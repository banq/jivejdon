package com.jdon.jivejdon.manager.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageQueryService;

import java.util.Collection;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumMessageQueryService forumMessageQueryService;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public Collection<Long> getList() {
//		TreeMap<ForumThread, Long> sorted_map = new TreeMap<ForumThread, Long>(new
//				HomePageComparator());
		Collection<Long> list = threadApprovedNewList.getApprovedThreads(0);
		return list.stream().collect(Collectors.toMap((threadId) -> forumMessageQueryService
				.getThread(threadId), threadId -> threadId, (e1, e2) ->
				e1, () -> new TreeMap<ForumThread, Long>(new HomePageComparator()))).values();

	}

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
