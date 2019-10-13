package com.jdon.jivejdon.pubsub.domain.consumer.write.updatemessage;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.pubsub.bus.cqrs.query.CacheQueryRefresher;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.event.ThreadNameSavedEvent;
import com.jdon.jivejdon.repository.query.MessagePageIteratorSolver;
import com.jdon.jivejdon.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.repository.builder.MessageRepositoryDao;
import com.jdon.jivejdon.repository.builder.ThreadRepositoryDao;
import com.jdon.jivejdon.util.ContainerUtil;

import java.util.Optional;

@Consumer("saveName")
public class ThreadNameListener implements DomainEventHandler {

	private final ThreadRepositoryDao threadRepositoryDao;

	private final MessageRepositoryDao messageRepositoryDao;

	private final ForumAbstractFactory forumAbstractFactory;

	private final CacheQueryRefresher cacheQueryRefresher;

	protected final ContainerUtil containerUtil;

	public ThreadNameListener(ThreadRepositoryDao threadRepositoryDao, MessageRepositoryDao
			messageRepositoryDao, ForumAbstractFactory forumAbstractFactory, MessagePageIteratorSolver messagePageIteratorSolver, ContainerUtil containerUtil) {
		super();
		this.threadRepositoryDao = threadRepositoryDao;
		this.messageRepositoryDao = messageRepositoryDao;
		this.forumAbstractFactory = forumAbstractFactory;
		this.containerUtil = containerUtil;
		this.cacheQueryRefresher = new CacheQueryRefresher( forumAbstractFactory, messagePageIteratorSolver);
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {

		ThreadNameSavedEvent es = (ThreadNameSavedEvent) event.getDomainMessage().getEventSource();
		Long threadId = es.getThreadId();
		Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread(threadId);
		try {
			threadRepositoryDao.updateThreadName(es.getName(), forumThreadOptional.get());
			containerUtil.clearCache(forumThreadOptional.get().getRootMessage().getMessageId());
			cacheQueryRefresher.refresh(forumThreadOptional.get().getRootMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
