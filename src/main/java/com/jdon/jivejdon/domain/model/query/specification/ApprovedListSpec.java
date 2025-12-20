package com.jdon.jivejdon.domain.model.query.specification;

import java.util.concurrent.TimeUnit;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;

public class ApprovedListSpec extends ThreadListSpec {

	// this value is display count on one page
	private final int needCount = 100;
	private final int needViewcount = 5;

	private int getNeedViewcount() {
		return needViewcount;
	}

	public ApprovedListSpec() {
		sorttableName = "threadID";
	}

	
	public boolean isApprovedToBest(ForumThread thread) {
		return contentQualityScore(thread)>100 && (thread.getViewCount() > getNeedViewcount() || thread.getRootMessage().getDigCount()>=1);
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
		int digCount = thread.getRootMessage().getDigCount();

		// 48小时内放大，48小时到5天不放大不缩小，5天后缩小
		double newThreadBonus;
		if (diffHours < 2 * 24) {
			newThreadBonus = 5.0; // 48小时内放大
		}else if (diffHours < 5 * 24) {
			newThreadBonus = 3.0; // 
		} else if (diffHours < 7 * 24) {
			newThreadBonus = 1.0; // 48小时到5天不变
		} else {
			// 5天后递减，最低0.2
			long extraDays = (diffHours - 5 * 24) / 24;
			newThreadBonus = 1.0 / (1.0 + extraDays);
			if (newThreadBonus < 0.2)
				newThreadBonus = 0.2;
		}

		double digBonus;
		if (diffHours < 2 * 24) {
			digBonus = 1.0 + digCount * 5.0; // 48小时内放大
		} else if (diffHours < 5 * 24) {	
			digBonus = 1.0 + digCount * 3.0;
		} else if (diffHours < 7 * 24) {
			digBonus = 1.0 + digCount * 1.0; // 48小时到5天不变
		} else {
			long extraDays = (diffHours - 5 * 24) / 24;
			double digWeight = 1.0 / (1.0 + extraDays);
			if (digWeight < 0.2)
				digWeight = 0.2;
			digBonus = 1.0 + digCount * digWeight;
		}

		return hourViewCount * newThreadBonus * digBonus;
	}
	/**
	 * recommend：ThreadDigComparator
	 */
	public double approvedCompare(ForumThread thread) {
		if(thread == null) return 0;
		// 基础热度分
		double baseScore = calculateWeightedScore(thread);

		// 内容质量分
		double qualityScore = contentQualityScore(thread);

		// 质量分大于0时加权加分（如加20%）
		if (qualityScore > 0) {
			baseScore += qualityScore * 0.2;
		}

		// 添加时间权重，越近权重越高
		double timeWeight = calculateTimeWeight(thread);
		baseScore *= timeWeight;

		return baseScore;
	}

	/**
	 * 计算帖子质量分
	 * 
	 * @param thread 论坛帖子
	 * @return 质量分
	 */
	public double contentQualityScore(ForumThread thread) {
		if (thread == null || thread.getRootMessage() == null || thread.getTags() == null) {
            return 0;
        }
		int digCount = thread.getRootMessage().getDigCount();
		double tagsCount = thread.getTags().stream()
				.map(threadTag -> threadTag.getAssonum())
				.reduce(0, Integer::sum);
		ReBlogVO reBlogVO = thread.getReBlogVO();
	    int linkCount = 0;
        if (reBlogVO != null) {
            linkCount = reBlogVO.getThreadFroms().size() + reBlogVO.getThreadTos().size();
        }

		// 三个都必须 > 0，否则返回 0
		if (digCount <= 0 || tagsCount <= 0 || linkCount <= 0) {
			return 0;
		}

		double digWeight = 100.0;
		double tagWeight = 50.0;
		double linkWeight = 50.0;

		return (digCount * digWeight) + (tagsCount * tagWeight) + (linkCount * linkWeight);

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

	/**
	 * 计算时间权重，越近的时间权重越高
	 * 
	 * @param thread 论坛帖子
	 * @return 时间权重
	 */
	private double calculateTimeWeight(ForumThread thread) {
		if (thread == null || thread.getRootMessage() == null) {
			return 1.0; // 默认权重
		}
		
		long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getRootMessage().getModifiedDate2());
		long diffHours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);
		
		// 48小时内权重最高，之后随时间递减
		if (diffHours < 2 * 24) {
			return 1.5; // 48小时内权重为1.5
		} else if (diffHours < 5 * 24) {
			return 1.2; // 2-5天内权重为1.2
		} else if (diffHours < 7 * 24) {
			return 1.0; // 5-7天内权重为1.0
		} else {
			// 7天后递减，最低0.5
			long extraDays = (diffHours - 7 * 24) / 24;
			double weight = 1.0 / (1.0 + extraDays * 0.1);
			if (weight < 0.5)
				weight = 0.5;
			return weight;
		}
	}

	public int getNeedCount() {
		return needCount;
	}

}
