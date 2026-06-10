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
package com.jdon.jivejdon.spi.pubsub.subscriber.sm;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.event.ATUserNotifiedEvent;
import com.jdon.jivejdon.spi.component.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

@Component
public class ShortMessageHandler  implements Startable {

	protected final ShortMessageFactory factory;
	private ScheduledExecutorUtil scheduledExecutorUtil;

	public ShortMessageHandler(ShortMessageFactory factory, ScheduledExecutorUtil scheduledExecutorUtil) {
		super();
		this.factory = factory;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
		
	}

	@OnEvent("atUserNotifiedEvent")
	public void notifyATUser(ATUserNotifiedEvent event) throws Exception {
		EventBus eventBus = new AsyncEventBus(scheduledExecutorUtil.getScheduExec());
		eventBus.register(this);
		eventBus.post(event);

	}

	@Subscribe
	public void notifyATUserAction(ATUserNotifiedEvent event) throws Exception {
		factory.createWeiBoShortMessage(event);

	}

	public void start() {
		        System.out.println("ShortMessageHandler started");
	}
	public void stop() {
		scheduledExecutorUtil = null;
	}

}
