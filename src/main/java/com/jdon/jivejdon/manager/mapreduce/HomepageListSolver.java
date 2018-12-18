package com.jdon.jivejdon.manager.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.ForumFactory;

import java.util.Collection;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component("homepageListSolver")
public class HomepageListSolver {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumFactory forumFactory;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumFactory forumFactory) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumFactory = forumFactory;
	}

	public Collection<Long> getList() {
//		TreeMap<ForumThread, Long> sorted_map = new TreeMap<ForumThread, Long>(new
//				HomePageComparator());
		Collection<Long> list = threadApprovedNewList.getApprovedThreads(0);
		return list.stream().collect(Collectors.toMap((threadId) -> forumFactory.getThread
						(threadId).orElse(null), threadId -> threadId, (e1, e2) ->
						e1, // Merge Function
				() -> new TreeMap<ForumThread, Long>(new HomePageComparator()))).values();

	}

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
