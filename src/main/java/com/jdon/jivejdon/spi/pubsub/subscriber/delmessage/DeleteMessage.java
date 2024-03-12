package com.jdon.jivejdon.spi.pubsub.subscriber.delmessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.domain.command.MessageRemoveCommand;
import com.jdon.jivejdon.infrastructure.MessageCRUDService;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;

@Consumer("deleteMessage")
public class DeleteMessage implements DomainEventHandler {
    private final static Logger logger = LogManager.getLogger(DeleteMessage.class);

	protected final MessageCRUDService messageCRUDService;
	protected final ForumFactory forumAbstractFactory;

	public DeleteMessage(MessageCRUDService messageCRUDService, ForumFactory forumAbstractFactory) {
		this.messageCRUDService = messageCRUDService;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		MessageRemoveCommand messageRemoveCommand = (MessageRemoveCommand) event.getDomainMessage().getEventSource();
		try {
			messageCRUDService.deleteMessage(messageRemoveCommand);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
