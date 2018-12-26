package com.jdon.jivejdon.event.domain.consumer.write.postThread;

import com.google.common.eventbus.AsyncEventBus;
import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;


@Consumer("postThread")
public class ZCDNRefreshListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(ZCDNRefreshListener.class);

	private final CDNRefreshUrls cdnRefreshUrls;
	private final ForumFactory forumFactory;
	private final AsyncEventBus eventBus;

	public ZCDNRefreshListener(CDNRefreshUrls cdnRefreshUrls, ForumFactory forumFactory,
							   CDNRefreshSubsciber cdnRefreshSubsciber, ScheduledExecutorUtil
									   scheduledExecutorUtil) {
		this.cdnRefreshUrls = cdnRefreshUrls;
		this.forumFactory = forumFactory;
		eventBus = new AsyncEventBus(scheduledExecutorUtil.getScheduExec());
		eventBus.register(cdnRefreshSubsciber);
	}

	@Override
	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
//		String clientIp = InetAddress.getLocalHost().getHostAddress();
//		if (clientIp.indexOf("192.168") != -1 || clientIp.indexOf("127.0.0.1") != -1) {
//			logger.error(clientIp + " is not server, so not send cdn refresh");
//			return;
//		}
		Arrays.stream(cdnRefreshUrls.getUrls()).forEach(eventBus::post);

		DomainMessage lastStepMessage = (DomainMessage) event.getDomainMessage().getEventSource();
		Object lastStepOk = lastStepMessage.getBlockEventResult();
		if (lastStepOk != null) {
			// the forumMessage is input DTO
			ForumMessage forumMessage = (ForumMessage) lastStepOk;
			forumMessage = forumFactory.getMessage(forumMessage.getMessageId());
			forumMessage.getForumThread().getTags().stream().forEach(threadTag -> {
				eventBus.post("query/tt/" + threadTag.getTagID());
				eventBus.post("query/tagThreads/" + threadTag.getTagID());
				eventBus.post("rss/tag/" + threadTag.getTagID());
			});

		}
	}

}
