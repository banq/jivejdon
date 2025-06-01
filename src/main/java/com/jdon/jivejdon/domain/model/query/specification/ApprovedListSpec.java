package com.jdon.jivejdon.domain.model.query.specification;

import java.util.concurrent.TimeUnit;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;

public class ApprovedListSpec extends ThreadListSpec {

	// this value is display count on one page
	private final int needCount = 30;
	private final int needViewcount = 300;

	public ApprovedListSpec() {
		sorttableName = "creationDate";
	}

	/**
	 * recommend
	 * 
	 * @param thread
	 * @param threadPrev
	 * @param threadPrev2
	 * @return
	 */
	public boolean isApproved(ForumThread thread, ForumThread threadPrev, ForumThread threadPrev2) {
		return calculateWeightedScore(thread) > 10
				|| isDigged(thread, 1)
				|| isDailyViewCountAboveThreshold(thread, 5)
				|| isGreaterThanPrev(thread, threadPrev, threadPrev2, 0.5)
				|| thread.getRootMessage().hasImage()
				|| isTutorial(thread);
	}

	/**
	 * 检查帖子每日浏览量是否超过指定阈值
	 * 
	 * @param thread    论坛帖子
	 * @param threshold 每日浏览量阈值
	 * @return 如果每日浏览量超过阈值返回true，否则返回false
	 */
	private boolean isDailyViewCountAboveThreshold(ForumThread thread, double threshold) {
		long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getCreationDate2());
		long diffDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

		// 计算平均每天浏览量
		double dailyViewCount = (double) thread.getViewCount() / (diffDays == 0 ? 1 : diffDays);

		return dailyViewCount > threshold;
	}

	public boolean isTutorial(ForumThread thread) {
		return isLongText(thread, 4) && isTagged(thread, 3)
				&& isLinked(thread, 4) && thread.getViewCount() > 50;
	}

	/**
	 * approval：HomePageComparator
	 * grok3 编写的代码
	 * 
	 * @param thread
	 * @param threadPrev
	 * @return
	 */
	public double sortedLeaderboard(final ForumThread thread, final ForumThread threadPrev) {
		double p = 0;
		try {

			// 时间差计算
			long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getCreationDate2());
			long diffDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

			if (diffDays <= 3) {
				// 计算平均每天浏览量
				double dailyViewCount = (double) thread.getViewCount() / (diffDays == 0 ? 1 : diffDays);
				int digCount = thread.getRootMessage().getDigCount();
				if (dailyViewCount > 5 || digCount > 0) {
					p = p + (dailyViewCount * 100); // 点赞和浏览量高的帖子获得额外加分
				}

			} else {
				// // 时间衰减
				p = p / (diffDays * 100);
			}

		} finally {
		}
		return p;
	}

	/**
	 * recommend：ThreadDigComparator
	 */
	public double approvedCompare(ForumThread thread) {
		// 只按 dig、标签、链接数加权，不考虑浏览量和时间
		int digCount = thread.getRootMessage().getDigCount();
		double digWeight = 100.0; // dig权重

		// 标签权重
		double tagsCount = thread.getTags().stream()
				.map(threadTag -> threadTag.getAssonum())
				.reduce(0, Integer::sum);
		double tagWeight = 10.0;

		// 链接权重
		ReBlogVO reBlogVO = thread.getReBlogVO();
		int linkCount = reBlogVO.getThreadFroms().size() + reBlogVO.getThreadTos().size();
		double linkWeight = 5.0;

		return (digCount * digWeight) + (tagsCount * tagWeight) + (linkCount * linkWeight);

	}

	public double calculateApprovedScore(ForumThread thread) {
		double weightedScore = calculateWeightedScore(thread);
		long daysSinceCreation = getDaysSinceCreation(thread.getRootMessage().getModifiedDate2());
		return weightedScore / (daysSinceCreation + 1);
	}

	private double calculateWeightedScore(ForumThread thread) {
		// Extract metrics
		long viewCount = thread.getViewCount();
		int digCount = thread.getRootMessage().getDigCount();

		// Define weights
		double viewWeight = getViewWeight(thread.getRootMessage().getModifiedDate2());
		double digWeight = 0.2;

		// Calculate weighted score
		return (viewCount * viewWeight) + (digCount * digWeight);
	}

	private double getViewWeight(long creationDate) {
		long daysSinceCreation = getDaysSinceCreation(creationDate);
		return (daysSinceCreation <= 5) ? 10.0 : 0.8;
	}

	private long getDaysSinceCreation(long creationDate) {
		long currentTime = System.currentTimeMillis();
		long timeDifferenceMillis = Math.abs(currentTime - creationDate);
		return TimeUnit.DAYS.convert(timeDifferenceMillis, TimeUnit.MILLISECONDS);
	}

	public boolean isTagged(ForumThread thread, int count) {
		final double tagsCOunt = thread.getTags().stream().map(threadTag -> threadTag.getAssonum()).reduce(0,
				(subtotal, element) -> subtotal + element);
		return tagsCOunt >= count ? true : false;
	}

	public boolean isLinked(ForumThread thread, int count) {
		final ReBlogVO reBlogVO = thread.getReBlogVO();
		return (reBlogVO.getThreadFroms().size() + reBlogVO.getThreadTos().size()) >= count ? true : false;
	}

	public boolean isApprovedToBest(ForumThread thread, int count, ForumThread threadPrev, ForumThread threadPrev2) {
		return isApproved(thread, threadPrev, threadPrev2) && count < getNeedCount();
	}

	public boolean isGreaterThanPrev(ForumThread thread, ForumThread threadPrev, ForumThread threadPrev2, double rate) {
		if (threadPrev == null || threadPrev2 == null || thread.getViewCount() < 10)
			return false;
		return (thread.getViewCount() * rate > Math.min(threadPrev.getViewCount(), threadPrev2.getViewCount())) ? true
				: false;
	}

	public boolean isGoodBlog(ForumThread thread) {
		return (isTagged(thread, 1) && isGoodAuthor(thread.getRootMessage().getAccount(), 2) && isDigged(
				thread, 1));
	}

	public boolean isLongText(ForumThread thread, int count) {
		int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
		if (bodylength <= 0)
			return false;

		if (bodylength / 1024 > count)
			return true;
		else
			return false;
	}

	public boolean isExcelledDiscuss(ForumThread thread, int count) {
		return (isTagged(thread, 2) && hasReply(thread, count));

	}

	protected boolean isLargeViewCount(ForumThread thread) {
		return thread.getViewCount() > needViewcount;
	}

	private boolean isGoodAuthor(Account account, int throttle) {
		if (account.getMessageCount() >= throttle)
			return true;
		else
			return false;
	}

	public boolean isDigged(ForumThread thread, int digcount) {
		ForumMessage message = thread.getRootMessage();
		if (message.getDigCount() >= digcount)
			return true;
		else
			return false;
	}

	private boolean hasReply(ForumThread thread, int throttle) {
		if (thread.getState().getMessageCount() >= throttle)
			return true;
		else
			return false;
	}

	public int getNeedCount() {
		return needCount;
	}

}
