package com.jdon.jivejdon.manager.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageQueryService;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;

@Component("homepageListSolver")
public class HomepageListSolver {

	private final ThreadApprovedNewList threadApprovedNewList;
	private final ForumMessageQueryService forumMessageQueryService;

	public HomepageListSolver(ThreadApprovedNewList threadApprovedNewList,
							  ForumMessageQueryService forumMessageQueryService) {
		this.threadApprovedNewList = threadApprovedNewList;
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public Collection<Long> getList() {
		TreeMap<ForumThread, Long> sorted_map = createHomeTreeMap();
		Collection<Long> list = threadApprovedNewList.getApprovedThreads(0);
		for (Long threadId : list) {
			ForumThread thread = forumMessageQueryService.getThread(threadId);
			sorted_map.put(thread, thread.getThreadId());
		}
		return sorted_map.values();
	}


	private TreeMap<ForumThread, Long> createHomeTreeMap() {
		return new TreeMap(new Comparator() {
			public int compare(Object num1, Object num2) {
				if (num1 == num2)
					return 0;
				ForumThread thread1 = (ForumThread) num1;
				ForumThread thread2 = (ForumThread) num2;

				double countRs1 = algorithm(thread1);
				double countRs2 = algorithm(thread2);

				return compareResult(countRs1, countRs2, thread1, thread2);
			}

		});
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
		double p = tagsCount * 0.5 + thread.getState().getMessageCount() * 0.3 +
				thread.getRootMessage().getDigCount() * 0.1 + thread.getViewCount() * 0.1;

		double t = System.currentTimeMillis() - thread.getState().getModifiedDate2() + 1000;

		double G = 10;
		double tg = Math.pow(t, G);
		return p / (tg + 1);
	}

}
