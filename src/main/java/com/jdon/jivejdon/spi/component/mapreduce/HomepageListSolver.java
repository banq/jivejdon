package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpecForMod;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

import java.util.ArrayList;
import java.util.Arrays;
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

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public Collection<Long> getList(int maxSize) {
		
		Collection<Long> list = new ArrayList<>();
		for (int i = 0; i < 75; i = i + 15) {
			list.addAll(threadApprovedNewList.getApprovedThreads(i));
		}
		
		list = list.stream().collect(Collectors.toMap((threadId) -> forumMessageQueryService
				.getThread(threadId), threadId -> threadId, (e1, e2) -> e1,
				() -> new TreeMap<ForumThread, Long>(new HomePageComparator()))).values();
		return list.stream().skip(0).limit(maxSize).collect(Collectors.toList());
	}

	

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
