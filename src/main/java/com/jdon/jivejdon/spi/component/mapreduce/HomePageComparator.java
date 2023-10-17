package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Home page Mixer: approve/rank threads Comparator
 * Mixer Ranking algorithm:
 * https://www.jdon.com/61442
 * https://herman.bearblog.dev/a-better-ranking-algorithm/
 */
public class HomePageComparator implements Comparator<ForumThread> {
    private final ApprovedListSpec approvedListSpec;

	public HomePageComparator(ApprovedListSpec approvedListSpec){
        this.approvedListSpec = approvedListSpec;
	}

	@Override
	public int compare(final ForumThread thread1, final ForumThread thread2) {

		if (thread1.getThreadId().longValue() == thread2.getThreadId().longValue())
			return 0;

		final double countRs1 = algorithm(thread1, thread2);
		final double countRs2 = algorithm(thread2, thread1);

		return compareResult(countRs1, countRs2, thread1, thread2);
	}

	private int compareResult(final double countRs1, final double countRs2, final ForumThread thread1, final ForumThread thread2) {
		if (countRs1 > countRs2)
			return -1; // returning the first object
		else if (countRs1 < countRs2)
			return 1;
		else if (countRs1 == countRs2) {
			if (thread1.getThreadId() > thread2.getThreadId())
				return -1;
			else if (thread1.getThreadId() < thread2.getThreadId())
				return 1;
		}
		return 0;
	}

	private double algorithm(final ForumThread thread, final ForumThread threadPrev) {
		return approvedListSpec.sortedLeaderboard(thread, threadPrev);
	}
}
