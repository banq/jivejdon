package com.jdon.jivejdon.domain.model.query.specification;

import java.util.concurrent.TimeUnit;

import com.jdon.jivejdon.domain.model.ForumThread;

public class ApprovedListSpec extends ThreadListSpec {

	// this value is display count on one page
	private final int needCount = 100;
	private final int needViewcount = 10;

	private int getNeedViewcount() {
		return needViewcount;
	}

	public ApprovedListSpec() {
		sorttableName = "threadID";
	}

	
	public boolean isApprovedToBest(ForumThread thread) {
		return  (thread.getViewCount() > getNeedViewcount() || thread.getRootMessage().getDigCount()>=1);
	}

	
	/**
	 * approval：HomePageComparator
	 * grok3 编写的代码
	 * GPT4.1 vscode
	 * @param thread
	 * @param threadPrev
	 * @return
	 */
	public double sortedLeaderboard(final ForumThread thread, final ForumThread threadPrev) {
		long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getRootMessage().getModifiedDate2());
		long diffHours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        double hourViewCount = (double) thread.getViewCount() / (diffHours == 0 ? 1 : diffHours);
		
		double newThreadBonus;
		if (diffHours < 2 * 24) {
			newThreadBonus = 5.0; // 48小时内放大
		}else if (diffHours < 5 * 24) {
			newThreadBonus = 3.0; // 
		} else
			newThreadBonus = 1.0; 

		return hourViewCount  * newThreadBonus * (1 + thread.getRootMessage().getDigCount());
	}
	/**
	 * recommend：ThreadDigComparator
	 */
	public double approvedCompare(ForumThread thread) {
		if(thread == null) return 0;
		// 基础热度分
		return calculateWeightedScore(thread);

	}


	private double calculateWeightedScore(ForumThread thread) {
		if(thread == null) return 0;
		long viewCount = thread.getViewCount();
		int digCount = 0;
        if (thread.getRootMessage() != null) {
            digCount = thread.getRootMessage().getDigCount();
        }
		return viewCount * (1 + digCount) ;
	}

	public int getNeedCount() {
		return needCount;
	}

}
