package com.jdon.jivejdon.domain.model.query.specification;

import java.util.concurrent.TimeUnit;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;

public class ApprovedListSpec extends ThreadListSpec {
	private final int initViewCount = 500;

	//this value is display count on one page
	private final int needCount = 15;
	private final int needViewcount = 300;
	

	public ApprovedListSpec() {
		sorttableName = "creationDate";
	}

	public boolean isApproved(ForumThread thread, Account account, ForumThread threadPrev) {
			try {
				if (thread.getViewCount() > initViewCount ||
						isDigged(thread, 1) ||
						isExcelledDiscuss(thread) ||
						isGreaterThanPrev(thread, threadPrev,0.6) ||
						isLongText(thread, 15) ||
						thread.getRootMessage().hasImage()||
						(isTagged(thread,3) && isLinked(thread,3))){
					return true;
				}
			} finally {
			}
			return false;
	}

	public double sortedLeaderboard(final ForumThread thread, final ForumThread threadPrev) {
		double p = 0;
		try {
			final ReBlogVO reBlogVO = thread.getReBlogVO();
			final double linkCount = reBlogVO.getThreadFroms().size() + reBlogVO.getThreadTos().size() + 1;
			final double tagsCOunt = thread.getTags().stream().map(threadTag -> threadTag.getAssonum()).reduce(0, (subtotal, element) -> subtotal + element);
			p = ( linkCount + tagsCOunt + (thread.getRootMessage().hasImage() ? 1 : 0) + 1)
					* (thread.getRootMessage().getDigCount() + 1) * thread.getViewCount();
			final long diff2 = thread.getViewCount() - thread.getViewCounter().getLastSavedCount() + 1;
			final long diffInMillis = Math.abs(System.currentTimeMillis() - thread.getCreationDate2());
			final long diffDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
			if (diffDays >= 5)
				p = diff2 * p / (diffDays * 100);
			else if (diffDays >= 3)
				p = diff2 * p / (diffDays * 10);
			else {
				p = Math.pow(p, diff2);
			}
		} finally {
		}
		return p;
	}

	protected boolean isTagged(ForumThread thread, int count) {
		final double tagsCOunt = thread.getTags().stream().map(threadTag -> threadTag.getAssonum()).reduce(0, (subtotal, element) -> subtotal + element);
        return tagsCOunt>count?true:false;
	}

	protected boolean isLinked(ForumThread thread, int count) {
		final ReBlogVO reBlogVO = thread.getReBlogVO();
        return (reBlogVO.getThreadFroms().size() + reBlogVO.getThreadTos().size())>count?true:false;
	}
		

	public boolean isApprovedToBest(ForumThread thread, Account account, int count, ForumThread threadPrev){		
		return isApproved(thread, account, threadPrev) && count < getNeedCount();
	}

	public boolean isGreaterThanPrev(ForumThread thread, ForumThread threadPrev, double rate){
		if (threadPrev == null || threadPrev.getViewCount() == 0)
			return false;
		if (thread.getViewCount() > threadPrev.getViewCount()){
			if (thread.getCreationDate().substring(2, 11).equals(threadPrev.getCreationDate().substring(2, 11)))
				return (thread.getViewCount() * rate > threadPrev.getViewCount()) ? true : false;
		}
		return false;
	}

	protected boolean isGoodBlog(ForumThread thread, Account account) {
		return (isTagged(thread,1) && isGoodAuthor(account, 2) && isDigged(
				thread, 1));
	}

	protected boolean isLongText(ForumThread thread, int count){
		int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
		if (bodylength<=0) return false;

		if (bodylength / 1024 > count)
			return true;
		else
			return false;
	}

	protected boolean isExcelledDiscuss(ForumThread thread) {
		return (isTagged(thread,2) && hasReply(thread, 2));

	}

	protected boolean isLargeViewCount(ForumThread thread) {
		return thread.getViewCount()>needViewcount;
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
