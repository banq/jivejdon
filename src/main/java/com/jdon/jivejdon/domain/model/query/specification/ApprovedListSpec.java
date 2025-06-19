package com.jdon.jivejdon.domain.model.query.specification;

import java.util.concurrent.TimeUnit;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;

public class ApprovedListSpec extends ThreadListSpec {

	// this value is display count on one page
	private final int needCount = 30;
	private final int needViewcount = 5;

	private int getNeedViewcount() {
		return needViewcount;
	}

	public ApprovedListSpec() {
		sorttableName = "creationDate";
	}

	
	public boolean isApprovedToBest(ForumThread thread) {
		return isApproved(thread) && isLargeViewCount(thread, getNeedViewcount());
	}

	/**
	 * recommend
	 * 
	 * @param thread
	 * @param threadPrev
	 * @param threadPrev2
	 * @return
	 */
	public boolean isApproved(ForumThread thread) {
		return calculateWeightedScore(thread) > 100
				|| isDigged(thread, 1)
				|| isDailyViewCountAboveThreshold(thread, 10)
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
		long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getRootMessage().getModifiedDate2());
		long diffDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

		// 计算平均每天浏览量
		double dailyViewCount = (double) thread.getViewCount() / (diffDays == 0 ? 1 : diffDays);

		return dailyViewCount > threshold;
	}

	public boolean isTutorial(ForumThread thread) {
		return isTagged(thread, 3)
				&& isLinked(thread, 4) && thread.getViewCount() > 50 && isLongText(thread, 20);
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
		long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getRootMessage().getModifiedDate2());
		long diffHours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);

		double hourViewCount = (double) thread.getViewCount() / (diffHours == 0 ? 1 : diffHours);
		int digCount = thread.getRootMessage().getDigCount();

		if (diffHours <= 3 * 24) {
			p = (Math.log(hourViewCount + 1) * 100) + 200;
			if (digCount > 0) {
				p += digCount  * 200;
			}
		} else {
			p = (Math.log(hourViewCount + 1) * 100) / (diffHours * 2);
		}
		return p;
	}
	/**
	 * recommend：ThreadDigComparator
	 */
	public double approvedCompare(ForumThread thread) {
		// 基础热度分
		double baseScore = popularityScore(thread);

		// 内容质量分
		double qualityScore = contentQualityScore(thread);

		// 质量分大于0时加权加分（如加20%）
		if (qualityScore > 0) {
			baseScore += qualityScore * 0.2;
		}

		return baseScore;
	}

	/**
	 * 计算帖子质量分
	 * 
	 * @param thread 论坛帖子
	 * @return 质量分
	 */
	public double contentQualityScore(ForumThread thread) {
		int digCount = thread.getRootMessage().getDigCount();
		double tagsCount = thread.getTags().stream()
				.map(threadTag -> threadTag.getAssonum())
				.reduce(0, Integer::sum);
		ReBlogVO reBlogVO = thread.getReBlogVO();
		int linkCount = reBlogVO.getThreadFroms().size() + reBlogVO.getThreadTos().size();

		// 三个都必须 > 0，否则返回 0
		if (digCount <= 0 || tagsCount <= 0 || linkCount <= 0) {
			return 0;
		}

		double digWeight = 100.0;
		double tagWeight = 10.0;
		double linkWeight = 5.0;

		return (digCount * digWeight) + (tagsCount * tagWeight) + (linkCount * linkWeight);

	}

	/**
	 * 计算帖子热度分
	 * 
	 * @param thread 论坛帖子
	 * @return 热度分
	 */
	private double popularityScore(ForumThread thread) {
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


	private boolean isGreaterThanPrev(ForumThread thread, ForumThread threadPrev, ForumThread threadPrev2, double rate) {
		if (threadPrev == null || threadPrev2 == null || thread.getViewCount() < 10)
			return false;
		return (thread.getViewCount() * rate > Math.min(threadPrev.getViewCount(), threadPrev2.getViewCount())) ? true
				: false;
	}

	private boolean isGoodBlog(ForumThread thread) {
		return (isTagged(thread, 1) && isGoodAuthor(thread.getRootMessage().getAccount(), 2) && isDigged(
				thread, 1));
	}

	private boolean isLongText(ForumThread thread, int count) {
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

	private boolean isLargeViewCount(ForumThread thread, int needViewcount) {
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
