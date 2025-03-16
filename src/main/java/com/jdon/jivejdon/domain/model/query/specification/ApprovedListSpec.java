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
		if (isDigged(thread, 1) || isDailyViewCountAboveThreshold(thread,15) ||  isGreaterThanPrev(thread, threadPrev, threadPrev2, 0.5)  || thread.getRootMessage().hasImage()  || isTutorial(thread))
			return true;
		else
			return false;
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
		return isLongText(thread, 4)  && isTagged(thread, 3)
				&& isLinked(thread, 4) &&  thread.getViewCount()>50;
	}

	/**
	 * approval：HomePageComparator
	 * grok3 编写的代码
	 * @param thread
	 * @param threadPrev
	 * @return
	 */
	public double sortedLeaderboard(final ForumThread thread, final ForumThread threadPrev) {
		double p = 0;
		try {
			p = approvedCompare(thread);
	
			// // 在线人数影响
			// int onlineCount = thread.getViewCounter().getLastSavedCount();
			// long diff2 = onlineCount > 1 ? (onlineCount + 1) : 1;
			// p =  p + (diff2 * 10) ;
	
			// 时间差计算
			long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getCreationDate2());
			long diffDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
			
			// // 计算平均每天浏览量
			// double dailyViewCount = (double) thread.getViewCount() / (diffDays == 0 ? 1 : diffDays);
			// if (dailyViewCount > 5) {
			// 	p = p + (dailyViewCount * 10); // 每天浏览量高的帖子获得额外加分
			// }
	
			 // 新增逻辑：一天内发布的帖子，基于浏览量和点赞数加权
			 if (diffDays <= 3) {
                double weightedScore = calculateWeightedScore(thread);
                p = p + weightedScore; // 将加权分数加到 p 上，影响排序
            }
			// 时间衰减
			p = p + (diffDays == 0 ? 0 : p / (diffDays * 100));
	
		} finally {
		}
		return p;
	}

	 // 新增方法：计算一天内帖子的加权分数
	 private double calculateWeightedScore(ForumThread thread) {
        long viewCount = thread.getViewCount();
        int digCount = thread.getRootMessage().getDigCount();

        // 浏览量和点赞的权重
        double viewWeight = 0.7; // 浏览量权重更高
        double digWeight = 0.3;  // 点赞权重

        // 加权分数
        return (viewCount * viewWeight) + (digCount * digWeight);
    }
	
	

	/**
	 * recommend：ThreadDigComparator
	 * 
	 * @param thread
	 * @return
	 */
	public double approvedCompare(ForumThread thread) {
		double threadCount = thread.getRootMessage().getDigCount() > 0
				? thread.getViewCount() * (thread.getRootMessage().getDigCount() + 1)
				: thread.getViewCount();
		long diff = TimeUnit.DAYS.convert(Math.abs(System.currentTimeMillis() - thread.getCreationDate2()),
				TimeUnit.MILLISECONDS);
		return threadCount / (diff + 1);
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
		if (threadPrev == null || threadPrev.getViewCount() < 50 || threadPrev2 == null
				|| threadPrev2.getViewCount() < 50 || thread.getViewCount() < 100)
			return false;

		// if (thread.getCreationDate().substring(2,
		// 11).equals(threadPrev.getCreationDate().substring(2, 11)))
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
