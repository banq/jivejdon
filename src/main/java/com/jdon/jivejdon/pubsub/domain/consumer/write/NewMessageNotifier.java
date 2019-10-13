/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.event.domain.consumer.write;

import com.google.common.eventbus.AsyncEventBus;
import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.component.pingrpc.BlogPingClient;
import com.jdon.jivejdon.model.realtime.Lobby;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

@Consumer("newForumMessageNotifier")
public class NewMessageNotifier implements DomainEventHandler {

	private final Lobby lobby;
	private final AsyncEventBus eventBus;

	public NewMessageNotifier(Lobby lobby, BlogPingClient blogPingClient, ScheduledExecutorUtil scheduledExecutorUtil) {
		super();
		this.lobby = lobby;
		eventBus = new AsyncEventBus(scheduledExecutorUtil.getScheduExec());
		eventBus.register(blogPingClient);
	}

	@Override
	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		Notification notification = (Notification) event.getDomainMessage().getEventSource();
		lobby.addNotification(notification);
		eventBus.post(notification);
	}

}
