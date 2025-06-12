package com.jdon.jivejdon.spi.component.mapreduce;


import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver  {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumMessageQueryService forumMessageQueryService;
	private final ApprovedListSpec approvedListSpec ;
	private final ThreadViewCounterJob threadViewCounterJob;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService,
							  ApprovedListSpec approvedListSpec,
							  ThreadViewCounterJob threadViewCounterJob) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
		this.approvedListSpec = approvedListSpec;
		this.threadViewCounterJob = threadViewCounterJob;
	}

	public Collection<Long> getList(int start, int count) {
		return initList().stream().skip(start).limit(count).collect(Collectors.toList());
	}

	private Collection<Long>  initList() {
		Collection<Long> listInit = new ArrayList<>();
		for (int i = 0; i < 75; i = i + 15) {
			listInit.addAll(threadApprovedNewList.getApprovedThreads(i));
		}
		listInit = listInit.stream().collect(Collectors.toMap((threadId) -> forumMessageQueryService
				.getThread(threadId), threadId -> threadId, (e1, e2) -> e1,
				() -> new ConcurrentSkipListMap<ForumThread, Long>(new HomePageComparator(approvedListSpec, threadViewCounterJob))))
				.values();

		return listInit;		
	}


	

}
