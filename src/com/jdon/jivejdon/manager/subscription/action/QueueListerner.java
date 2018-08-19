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
package com.jdon.jivejdon.manager.subscription.action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.manager.subscription.SubscriptionAction;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.util.UtilValidate;

public class QueueListerner implements Startable {

	private final Queue<SubscriptionAction> queue = new ConcurrentLinkedQueue();

	private int delay = 100; // 60s

	private int fixedSize = 10;

	public QueueListerner(String delay, String size) {
		if (!UtilValidate.isEmpty(delay))
			this.delay = Integer.parseInt(delay);

		if (!UtilValidate.isEmpty(size))
			this.fixedSize = Integer.parseInt(size);

	}

	public void offer(SubscriptionAction subscriptionAction) {
		if (queue.size() >= fixedSize) {
			return;
			// queue.remove();
		}
		queue.offer(subscriptionAction);
	}

	public boolean contains(SubscriptionAction subscriptionAction) {
		return queue.contains(subscriptionAction);
	}

	@Override
	public void start() {
		final Runnable sender = new Runnable() {
			public void run() {
				if (queue.isEmpty())
					return;
				try {
					SubscriptionAction subscriptionAction = queue.poll();
					if (subscriptionAction != null)
						subscriptionAction.exec();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(sender, 30, delay, TimeUnit.SECONDS);

	}

	@Override
	public void stop() {

		ScheduledExecutorUtil.scheduExecStatic.shutdownNow();
		queue.clear();

	}

}
