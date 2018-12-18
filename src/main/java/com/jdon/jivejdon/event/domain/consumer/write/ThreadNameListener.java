package com.jdon.jivejdon.event.domain.consumer.write;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.event.ThreadNameSavedEvent;
import com.jdon.jivejdon.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.repository.builder.MessageRepositoryDao;
import com.jdon.jivejdon.repository.builder.ThreadRepositoryDao;

import java.util.Optional;

@Consumer("saveName")
public class ThreadNameListener implements DomainEventHandler {

	private final ThreadRepositoryDao threadRepositoryDao;

	private final MessageRepositoryDao messageRepositoryDao;

	private final ForumAbstractFactory forumAbstractFactory;

	public ThreadNameListener(ThreadRepositoryDao threadRepositoryDao, MessageRepositoryDao
			messageRepositoryDao,
							  ForumAbstractFactory forumAbstractFactory) {
		super();
		this.threadRepositoryDao = threadRepositoryDao;
		this.messageRepositoryDao = messageRepositoryDao;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {

		ThreadNameSavedEvent es = (ThreadNameSavedEvent) event.getDomainMessage().getEventSource();
		Long threadId = es.getThreadId();
		Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread(threadId);
		try {
			threadRepositoryDao.updateThreadName(es.getName(), forumThreadOptional.get());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
