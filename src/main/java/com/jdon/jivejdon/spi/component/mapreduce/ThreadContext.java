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

    public Set<Long> getThreadListInContext2(ForumThread currentThread) {
        Set<Long> threadIds = createSortedSet();
        // for (ThreadTag tag : thread.getTags()) {
        // threadIds.addAll(tagDao.getThreadsPrevNextInTag(tag.getTagID(),
        // thread.getThreadId()));
        // break;
        // }
        List resultIds = messageQueryDao.getThreadsPrevNext(currentThread.getForum().getForumId(),
        currentThread.getThreadId());
        int index = resultIds.indexOf(currentThread.getThreadId());
        if (index == -1)
            return threadIds;

        int prevIndex = index - 1;
        int nextIndex = index + 1;

        // Add the previous 2 threads if available
        if (prevIndex >= 1 && prevIndex - 1 >= 0 && prevIndex < resultIds.size()) {
            Long prevThreadId1 = (Long) resultIds.get(prevIndex - 1);
            Long prevThreadId2 = (Long) resultIds.get(prevIndex);
            if (prevThreadId1 != null) {
                threadIds.add(prevThreadId1); // Only add if not null
            }
            if (prevThreadId2 != null) {
                threadIds.add(prevThreadId2); // Only add if not null
            }
        }

        threadIds.add(currentThread.getThreadId());

        // Add the next 2 threads if available
        if (nextIndex < resultIds.size() - 1 && nextIndex + 1 < resultIds.size() - 1) {

            Long nextThreadId1 = (Long) resultIds.get(nextIndex);
            Long nextThreadId2 = (Long) resultIds.get(nextIndex + 1);
            if (nextThreadId1 != null) {
                threadIds.add(nextThreadId1); // Only add if not null
            }
            if (nextThreadId2 != null) {
                threadIds.add(nextThreadId2); // Only add if not null
            }
        }

        return threadIds;
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
