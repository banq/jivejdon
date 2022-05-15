package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

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
		double thread1Count = thread1.getRootMessage().getDigCount()>0?thread1.getRootMessage().getDigCount() * thread1.getViewCount():thread1.getViewCount();
		double thread2Count = thread2.getRootMessage().getDigCount()>0?thread2.getRootMessage().getDigCount() * thread2.getViewCount():thread2.getViewCount();

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
