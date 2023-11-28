package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;

@Component("threadContext")
public class ThreadContext {
    private final MessageQueryDao messageQueryDao;

    private final Map<Long,Set<Long>> cachePrevNexts;
    
    public ThreadContext(MessageQueryDao messageQueryDao) {
        this.messageQueryDao = messageQueryDao;
        this.cachePrevNexts= new ConcurrentHashMap<>();
    }

    public Set<Long> getThreadListInContext(ForumThread thread){
        return cachePrevNexts.computeIfAbsent(thread.getThreadId(), k ->getForumThreadsInTag(thread));
    }

     public List<Long> getPrevNextInTag(ForumThread thread){
       List<Long>  resultIds = new ArrayList<>();
       List<Long> threadListInContext = getThreadListInContext(thread).stream().collect(Collectors.toList());
       int index =  threadListInContext.indexOf(thread.getThreadId());
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

	private Set<Long> getForumThreadsInTag(ForumThread thread) {
        Set<Long> threadIds = new HashSet<>();
        for (ThreadTag tag : thread.getTags()) {
            threadIds.addAll(messageQueryDao.getThreadsPrevNextInTag(tag.getTagID(), thread.getThreadId()));
        }
        if (threadIds.isEmpty()) {
            threadIds.addAll(messageQueryDao.getThreadsPrevNext(thread.getForum().getForumId(), thread.getThreadId()));
        }
        return threadIds;
    }

    
}
