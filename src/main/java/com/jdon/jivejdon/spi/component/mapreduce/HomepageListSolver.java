package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final ApprovedListSpec approvedListSpec = new ApprovedListSpec();
	private Collection<Long> list;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public Collection<Long> getList(int start, int count) {
		if (start == 0 || list  == null){
		 synchronized(this) {
		    list = Collections.synchronizedList(new ArrayList<>());;
		    for (int i = 0; i < 75; i = i + 15) {
			   list.addAll(threadApprovedNewList.getApprovedThreads(i));
		    }
			list = list.stream().collect(Collectors.toMap((threadId) -> forumMessageQueryService
				.getThread(threadId), threadId -> threadId, (e1, e2) -> e1,
				() -> new ConcurrentSkipListMap<ForumThread, Long>(new HomePageComparator(approvedListSpec)))).values();
		 }		
	    }   
		return list.stream().skip(start).limit(count).collect(Collectors.toList());
	}

	

//		for (Long threadId : list) {
//			ForumThread thread = forumMessageQueryService.getThread(threadId);
//			sorted_map.put(thread, thread.getThreadId());
//		}
//		return sorted_map.values();

}
