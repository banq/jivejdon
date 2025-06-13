package com.jdon.jivejdon.presentation.action.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.spi.component.mapreduce.HomePageComparator;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.strutsutil.ModelListAction;

public class ThreadApprovedHomeListAction extends ModelListAction {
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;
	private final static ApprovedListSpec approvedListSpec  = new ApprovedListSpec();

	private ThreadViewCounterJob threadViewCounterJob;

	public ThreadViewCounterJob getThreadViewCounterJob() {
		if (threadViewCounterJob == null)
			threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil
					.getComponentInstance("threadViewCounterJob", this.servlet.getServletContext());
		return threadViewCounterJob;
	}

	
	

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext
							());

		return threadApprovedNewList;
	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start >= 30 || start % count != 0)
			return new PageIterator();

//		Collection<Long> list = getThreadApprovedNewList().getApprovedThreads(start);
		Collection<Long> list = getList( start,  count);
		if (list != null)
			return new PageIterator(list.size(), list.toArray(new Long[0]));
		else
			return new PageIterator();
	}

	public Collection<Long> getList(int start, int count) {
		List<Long> all = initList();
		int end = Math.min(start + count, all.size());
		if (start > end)
			return new ArrayList<>();
		return all.subList(start, end);
	}

	private List<Long> initList() {
		// 直接并发获取第一页的 ForumThread
		List<ForumThread> threads = getThreadApprovedNewList().getApprovedThreads(0,100).stream()
				.map(id -> getForumMessageQueryService().getThread(id))
				.filter(thread -> thread != null)
				.collect(Collectors.toList());
		threads.sort(new HomePageComparator(approvedListSpec, getThreadViewCounterJob()));
		return threads.stream().map(ForumThread::getThreadId).collect(Collectors.toList());
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		try {
			return getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
