package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;

@Component("threadContext")
public class ThreadContext {
    private final MessageQueryDao messageQueryDao;

    public ThreadContext(MessageQueryDao messageQueryDao) {
        this.messageQueryDao = messageQueryDao;
    }

    public List<Long> getPrevNextInTag(ForumThread thread) {
        List<Long> resultIds = new ArrayList<>();
        List<Long> threadListInContext = getThreadListInContext(thread).stream().collect(Collectors.toList());
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

    private Set<Long> getThreadListInContext(ForumThread thread) {
        Set<Long> threadIds = createSortedSet();
        for (ThreadTag tag : thread.getTags()) {
            threadIds.addAll(messageQueryDao.getThreadsPrevNextInTag(tag.getTagID(), thread.getThreadId()));
            break;
        }
        if (threadIds.isEmpty()) {
            threadIds.addAll(messageQueryDao.getThreadsPrevNext(thread.getForum().getForumId(), thread.getThreadId()));
        }
        if (!threadIds.contains(thread.getThreadId()))
            threadIds.add(thread.getThreadId());// new item not be inconsistency with DB
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

}
