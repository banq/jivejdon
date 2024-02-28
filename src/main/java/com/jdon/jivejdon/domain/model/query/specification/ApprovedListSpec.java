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
		try {
			if (isDigged(thread, 1) ||
					isExcelledDiscuss(thread) ||
					isGreaterThanPrev(thread, threadPrev, threadPrev2, 0.6) ||
					isLongText(thread, 15) ||
					thread.getRootMessage().hasImage() ||
					(isTagged(thread, 3) && isLinked(thread, 3))) {
				return true;
			}
		} finally {
		}
		return false;
	}

	/**
	 * recommend：HomePageComparator
	 * 
	 * @param thread
	 * @param threadPrev
	 * @return
	 */
	public double sortedLeaderboard(final ForumThread thread, final ForumThread threadPrev) {
		double p = 0;
		try {
			p = approvedCompare(thread);

			int onlineCount = thread.getViewCounter().getLastSavedCount();
			long diff2 = onlineCount > 1 ? (thread.getViewCount() - onlineCount + 1) : 1;
			p = diff2 + p;

		} finally {
		}
		return p;
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
		if (threadPrev == null || threadPrev.getViewCount() == 0 || threadPrev2 == null
				|| threadPrev2.getViewCount() == 0)
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

	protected boolean isExcelledDiscuss(ForumThread thread) {
		return (isTagged(thread, 2) && hasReply(thread, 2));

	}

	protected boolean isLargeViewCount(ForumThread thread) {
		return thread.getViewCount() > needViewcount;
	}

	private boolean isGoodAuthor(Account account, int throttle) {
		if (account.getMessageCountNow() >= throttle)
			return true;
		else
			return false;
	}

	private boolean isDigged(ForumThread thread, int digcount) {
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
