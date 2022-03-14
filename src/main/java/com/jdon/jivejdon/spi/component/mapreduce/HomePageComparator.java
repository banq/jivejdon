package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.jivejdon.domain.model.ForumThread;

import java.util.Comparator;

/**
 * Home page approve threads Comparator
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
		int tagsCount = thread.getTags().stream().mapToInt(tag -> tag.getAssonum()).sum();
		if (thread.getTags().size() > 3) {
			tagsCount = tagsCount * 3;
		}
		double messageCount = thread.getState().getMessageCount() + 1;
		double digCount = thread.getRootMessage().getDigCount() + 1;
		double viscount = thread.getViewCount() * Math.ceil(thread.getViewCount() / 100) + 1;
		double t = System.currentTimeMillis() - thread.getState().getModifiedDate2() + 5000;

		double p = Math.pow((messageCount * viscount * tagsCount)/ (t/1000000000 + 1), digCount) ;
		p = 10000 * p;
		if (thread.getViewCount() > thread.getViewCounter().getLastSavedCount()) {
			p = p * (thread.getViewCount() - thread.getViewCounter().getLastSavedCount() + 1);
		}
		return p;
	}
}
