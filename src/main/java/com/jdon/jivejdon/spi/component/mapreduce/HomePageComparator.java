package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.jivejdon.domain.model.ForumThread;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Home page Mixer: approve/rank threads Comparator
 * Mixer Ranking algorithm:
 * https://www.jdon.com/61442
 * https://herman.bearblog.dev/a-better-ranking-algorithm/
 */
public class HomePageComparator implements Comparator<ForumThread> {
	@Override
	public int compare(ForumThread thread1, ForumThread thread2) {

		if (thread1.getThreadId().longValue() == thread2.getThreadId().longValue())
			return 0;

		double countRs1 = algorithm(thread1, thread2);
		double countRs2 = algorithm(thread2, thread1);

		return compareResult(countRs1, countRs2, thread1, thread2);
	}

	private int compareResult(double countRs1, double countRs2, ForumThread thread1, ForumThread thread2) {
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

	private double algorithm(ForumThread thread, ForumThread threadPrev) {
		double p = thread.getViewCount();
		long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getState().getModifiedDate2());
		long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
		if (diff >= 3)
			p = p / diff;
		long diff2 = thread.getViewCount() - thread.getViewCounter().getLastSavedCount() + 1;
		p = (thread.getRootMessage().getDigCount() + 1) * p * diff2;
		return p;
	}
}
