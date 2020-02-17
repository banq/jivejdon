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

		double countRs1 = algorithm(thread1);
		double countRs2 = algorithm(thread2);

		return compareResult(countRs1, countRs2, thread1, thread2);
	}


	private int compareResult(double countRs1, double countRs2, ForumThread thread1, ForumThread
			thread2) {
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

	private double algorithm(ForumThread thread) {
		int tagsCount = 0;
		tagsCount = tagsCount + thread.getTags().stream().mapToInt(tag -> tag.getAssonum()).sum();

		if (thread.getTags().size() > 3) {
			tagsCount = tagsCount * 3;
		}
		double messageCount = Math.pow(thread.getState()
				.getMessageCount() + 1, thread.getState()
				.getMessageCount() + 1);
		double digCount = Math.pow(thread.getRootMessage().getDigCount() + 1,thread.getRootMessage().getDigCount()+1);
		double p = tagsCount * thread.getViewCount() * messageCount * digCount;

		double t = System.currentTimeMillis() - thread.getState().getModifiedDate2() + 1000;

		double G = 1.5;
		double tg = Math.pow(t, G);
		return p / (tg + 1);
	}
}
