package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;

@Component("threadContext")
public class ThreadContext {
    private final MessageQueryDao messageQueryDao;

    private final Map<Long,List<Long>> cachePrevNexts;
    
    public ThreadContext(MessageQueryDao messageQueryDao) {
        this.messageQueryDao = messageQueryDao;
        this.cachePrevNexts= new ConcurrentHashMap<>();
    }


    public List<Long> getPrevNextInTag(ForumThread thread){
        return cachePrevNexts.computeIfAbsent(thread.getThreadId(), k ->getForumThreadsInTag(thread));
    }

	private List<Long> getForumThreadsInTag(ForumThread thread) {
        List<Long> threadIds = new ArrayList<>();
		for (ThreadTag tag : thread.getTags()) {
				threadIds.addAll(messageQueryDao.getThreadsPrevNextInTag(tag.getTagID(),thread.getThreadId()));
		}
        if(threadIds.isEmpty()){
            threadIds.addAll(messageQueryDao.getThreadsPrevNext(thread.getForum().getForumId(),thread.getThreadId()));
        }
        threadIds.remove(thread.getThreadId());
        return threadIds;
	}

    
}
