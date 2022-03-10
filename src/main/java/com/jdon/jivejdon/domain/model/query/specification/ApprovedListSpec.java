package com.jdon.jivejdon.domain.model.query.specification;

import com.jdon.jivejdon.domain.model.account.Account;

import org.apache.commons.io.filefilter.TrueFileFilter;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;

import java.util.Collection;

public class ApprovedListSpec extends ThreadListSpec {

	//this value is display count on one page
	private final int needCount = 15;
	private final int needViewcount = 300;
	private long currentIndicator = 0;
	private int currentStartBlock = 0;
	private int currentStartPage = 0;

	public ApprovedListSpec() {
		sorttableName = "creationDate";
	}

	public boolean isApproved(ForumThread thread, Account account, ForumThread threadPrev) {
		if (isGoodBlog(thread, account) || isExcelledDiscuss(thread) || isGreaterThanPrev(threadPrev, threadPrev)) {
			return true;
		} else
			return false;
	}

	public boolean isApprovedToBest(ForumThread thread, Account account, int count, ForumThread threadPrev){		
		return isApproved(thread, account, threadPrev) && count < getNeedCount();
	}

	public boolean isGreaterThanPrev(ForumThread thread, ForumThread threadPrev){
		if (threadPrev == null || threadPrev.getViewCount() == 0)
			return false;
		if (thread.getViewCount() > threadPrev.getViewCount()){
			return (thread.getViewCount() >= threadPrev.getViewCount()*3/2)?true:false;
		}
		return false;
	}

	protected boolean isGoodBlog(ForumThread thread, Account account) {
		return (hasTags(thread, 1) && isGoodAuthor(account, 2) && isDigged(
				thread, 1));
	}

	protected boolean isExcelledDiscuss(ForumThread thread) {
		return (hasTags(thread, 1) && hasReply(thread, 2));

	}

	protected boolean isLargeViewCount(ForumThread thread) {
		return thread.getViewCount()>needViewcount;
	}

	private boolean hasTags(ForumThread thread, int throttle) {
		Collection tags = thread.getTags();
		if (tags != null && tags.size() >= throttle)
			return true;
		else
			return false;
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

//	public void setNeedCount(int needCount) {
//		this.needCount = needCount;
//	}

	public long getCurrentIndicator() {
		return currentIndicator;
	}

	public void setCurrentIndicator(long currentIndicator) {
		this.currentIndicator = currentIndicator;
	}

	public int getCurrentStartBlock() {
		return currentStartBlock;
	}

	public void setCurrentStartBlock(int currentStartBlock) {
		this.currentStartBlock = currentStartBlock;
	}

	public int getCurrentStartPage() {
		return currentStartPage;
	}

	public void setCurrentStartPage(int currentStartPage) {
		this.currentStartPage = currentStartPage;
	}

}
