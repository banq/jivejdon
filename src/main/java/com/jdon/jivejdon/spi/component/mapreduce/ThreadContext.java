package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.infrastructure.repository.dao.TagDao;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;

@Component("threadContext")
public class ThreadContext {
    private final MessageQueryDao messageQueryDao;
    private final TagDao tagDao;
    private final ForumMessageQueryService forumMessageQueryService;
    private final ThreadViewCounterJob threadViewCounterJob ;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public ThreadContext(MessageQueryDao messageQueryDao, TagDao tagDao, ForumMessageQueryService forumMessageQueryService,  ThreadViewCounterJob threadViewCounterJob) {
        this.messageQueryDao = messageQueryDao;
        this.tagDao = tagDao;
        this.forumMessageQueryService = forumMessageQueryService;
        this.threadViewCounterJob = threadViewCounterJob;
    }

    public List<Long> getPrevNextInTag(ForumThread thread) {
        List<Long> resultIds = new ArrayList<>();
        List<Long> threadListInContext = getThreadListInContext2(thread).stream().collect(Collectors.toList());
        int index = threadListInContext.indexOf(thread.getThreadId());
        if (index >= 1) {
            Long prevThreadId = (Long) threadListInContext.get(index - 1);
            resultIds.add(prevThreadId);
        }
        if (index < (threadListInContext.size() - 1)) {
            Long nextThreadId = (Long) threadListInContext.get(index + 1);
            resultIds.add(nextThreadId);
        }
        return resultIds;
    }

    public Set<Long> getThreadListInContext2(ForumThread thread) {
        Set<Long> threadIds = createSortedSet();
        for (ThreadTag tag : thread.getTags()) {
            threadIds.addAll(tagDao.getThreadsPrevNextInTag(tag.getTagID(), thread.getThreadId()));
            break;
        }
        if (threadIds.isEmpty()) {
            threadIds.addAll(messageQueryDao.getThreadsPrevNext(thread.getForum().getForumId(), thread.getThreadId()));
        }
        if (!threadIds.contains(thread.getThreadId()))
            threadIds.add(thread.getThreadId());// new item not be inconsistency with DB
        return threadIds;
    }

    public Set<Long> getThreadListInContext(ForumThread thread) {
        Set<Long> threadIds = createSortedSet();
        for (ThreadTag tag : thread.getTags()) {
            PageIterator pi = tagDao.getTaggedThread(tag.getTagID(), 0, 2);
            List list = Arrays.asList(pi.getKeys());
            threadIds.addAll(new HashSet<>(list));
        }
        if (threadIds.isEmpty()) {
            PageIterator pi = messageQueryDao.getThreads(thread.getForum().getForumId(),  0, 6, new ResultSort());
            List list = Arrays.asList(pi.getKeys());
             threadIds.addAll(new HashSet<>(list));
        }
        return threadIds;
    }

    private SortedSet<Long> createSortedSet() {
        return new TreeSet<Long>(new Comparator<Long>() {
            public int compare(Long thread1, Long thread2) {
                if (thread1.longValue() > thread2.longValue())
                    return -1;
                else if (thread1.longValue() == thread2.longValue())
                    return 0;
                else
                    return 1;
            }
        });
    }

    public List<ForumThread> createsThreadLinks(String threadId) {
        ForumThread thread = forumMessageQueryService.getThread(Long.parseLong(threadId));
        final List<ForumThread> threadLinks = loadReblog(thread);
        if (threadLinks.isEmpty()) {
            threadLinks.addAll(transform(thread));
        }
        return threadLinks;
    }

    private List<ForumThread> transform(ForumThread thread) {
        return getPrevNextInTag(thread).stream()
                .map(e -> forumMessageQueryService.getThread(e))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<ForumThread> loadReblog(ForumThread thread) {
		final List<ForumThread> threadLinks = new ArrayList<>();
		ReBlogVO reBlogVO = thread.getReBlogVO();
		if (reBlogVO == null)
			return threadLinks;

		if (reBlogVO.getThreadFroms() == null && reBlogVO.getThreadTos() == null)
			return threadLinks;

		for (ForumThread threadLink : reBlogVO.getThreadFroms()) {
			threadLinks.add(threadLink);
		}
		for (ForumThread threadLink : reBlogVO.getThreadTos()) {
			threadLinks.add(threadLink);
		}
		if (threadLinks.size() == 0) {

		}
		return threadLinks;
	}
    
    public List<ForumThread> prepareThreadOthers(Long threadId, String ipAddress) {
        // 这里是异步任务的逻辑
        ForumThread forumThread = forumMessageQueryService.getThread(threadId);
        // 1.prepare reBlog
        forumThread.getReBlogVO().loadAscResult();

        // // 2. page view counting
        executorService.execute(() -> {
            try {
                threadViewCounterJob.saveViewCounter(forumThread.addViewCount(ipAddress));
            } catch (Exception e) { }         
        });
        

        // 3.prepare relation posts
        return createsThreadLinks(threadId.toString());

    }

}
