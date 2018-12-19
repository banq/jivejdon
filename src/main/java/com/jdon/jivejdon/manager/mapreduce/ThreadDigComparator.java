package com.jdon.jivejdon.manager.mapreduce;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageQueryService;

import java.util.Comparator;

/**
 * Like or Give the thumbs-up Comparator
 */
public class ThreadDigComparator implements Comparator<Long> {
	private final ForumMessageQueryService forumMessageQueryService;

	public ThreadDigComparator(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
	}

	@Override
	public int compare(Long threadId1, Long threadId2) {
		if (threadId1.longValue() == threadId2.longValue())
			return 0;
		ForumThread thread1 = forumMessageQueryService.getThread(threadId1);
		ForumThread thread2 = forumMessageQueryService.getThread(threadId2);
		int thread1Count = thread1.getRootMessage().getDigCount();
		int thread2Count = thread2.getRootMessage().getDigCount();

		if (thread1Count > thread2Count)
			return -1; // returning the first object
		else if (thread1Count < thread2Count)
			return 1;
		else {
			if (threadId1.longValue() > threadId2.longValue())
				return -1;
			else
				return 1;
		}
	}
}
