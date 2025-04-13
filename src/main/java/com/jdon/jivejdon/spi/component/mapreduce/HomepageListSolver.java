package com.jdon.jivejdon.spi.component.mapreduce;


import java.util.Collection;


import com.jdon.annotation.Component;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver  {

	private final ThreadApprovedNewList threadApprovedNewList;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService,
							  ThreadViewCounterJob threadViewCounterJob) {
		this.threadApprovedNewList = threadApprovedNewList;
	}


	public Collection<Long> getList(int start, int count) {
		return threadApprovedNewList.getApprovedThreads(30);
	}

	

}
