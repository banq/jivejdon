package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.infrastructure.repository.dao.TagDao;

@Component("threadContext")
public class ThreadContext {
    private final MessageQueryDao messageQueryDao;
    private final TagDao tagDao;
    private final ForumFactory forumFactory;
  

    public ThreadContext(MessageQueryDao messageQueryDao, TagDao tagDao, ForumFactory forumFactory) {
        this.messageQueryDao = messageQueryDao;
        this.tagDao = tagDao;
        this.forumFactory = forumFactory;
    }

    public List<Long> getPrevNextInTag(ForumThread thread) {
        List<Long> threadListInContext = new ArrayList<>(getThreadListInContext2(thread));
        int index = threadListInContext.indexOf(thread.getThreadId());
        List<Long> resultIds = new ArrayList<>();
        // 先放后一个，再放前一个
        if (index < threadListInContext.size() - 1) {
            resultIds.add(threadListInContext.get(index + 1)); // 后一个
        }
        if (index > 0) {
            resultIds.add(threadListInContext.get(index - 1)); // 前一个
        }
        return resultIds;
    }

    public Set<Long> getThreadListInContext2(ForumThread currentThread) {
        List<Long> resultIds = messageQueryDao.getThreadsPrevNext(
            currentThread.getForum().getForumId(),
            currentThread.getThreadId()
        );
        // 直接返回有序且去重的 threadId 集合，顺序与 getThreadsPrevNext 保持一致
        return new LinkedHashSet<>(resultIds);
    }

    public List<ForumThread> getThreadListInContext(ForumThread thread) {
        Set<Long> threadIds = createSortedSet();
        // for (ThreadTag tag : thread.getTags()) {
        //     PageIterator pi = tagDao.getTaggedThread(tag.getTagID(), 0, 2);
        //     List list = Arrays.asList(pi.getKeys());
        //     threadIds.addAll(new HashSet<>(list));
        // }
        // if (threadIds.isEmpty()) {
        //     PageIterator pi = messageQueryDao.getThreads(thread.getForum().getForumId(),  0, 6, new ResultSort());
        //     List list = Arrays.asList(pi.getKeys());
        //      threadIds.addAll(new HashSet<>(list));
        // }
        threadIds.addAll(getThreadListInContext2(thread));
        return threadIds.stream().map(e -> forumFactory.getThread(e).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
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

    public List<ForumThread> createsThreadLinks(ForumThread thread) {
        final List<ForumThread> threadLinks = loadReblog(thread);
        if (threadLinks.isEmpty()) {
            threadLinks.addAll(transform(thread));
        }
        return threadLinks;
    }

    private List<ForumThread> transform(ForumThread thread) {
        return getPrevNextInTag(thread).stream()
                .map(e -> forumFactory.getThread(e).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<ForumThread> loadReblog(ForumThread thread) {
		final List<ForumThread> threadLinks = new ArrayList<>();
		ReBlogVO reBlogVO = thread.getReBlogVO();
		if (reBlogVO == null)
			return threadLinks;

		if ( reBlogVO.getThreadTos() == null)
			return threadLinks;

		// for (ForumThread threadLink : reBlogVO.getThreadFroms()) {
		// 	threadLinks.add(threadLink);
		// }
		for (ForumThread threadLink : reBlogVO.getThreadTos()) {
			threadLinks.add(threadLink);
		}
		// if (threadLinks.size() == 0) {

		// }
		return threadLinks;
	}
    
  
}
