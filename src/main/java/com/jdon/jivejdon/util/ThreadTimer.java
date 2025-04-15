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
package com.jdon.jivejdon.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import com.jdon.container.pico.Startable;
import com.jdon.util.UtilValidate;

public class ThreadTimer implements Startable {
	private final Queue<Thread> queue = new ConcurrentLinkedQueue();

	private int delay = 300; // 60s

	private int fixedSize = 10;

	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadTimer(ScheduledExecutorUtil scheduledExecutorUtil) {
		this.scheduledExecutorUtil = scheduledExecutorUtil;

	}

	public void offer(Thread thread) {
		if (queue.size() >= fixedSize) {
			queue.remove();
		}
		queue.offer(thread);
	}

	@Override
	public void start() {
		final Runnable sender = new Runnable() {
			public void run() {
				if (queue.size() == 0)
					return;
				try {
					Thread thread = queue.poll();
					if (thread != null)
						thread.run();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(sender, 30, delay, TimeUnit.SECONDS);

	}

	@Override
	public void stop() {

		queue.clear();

	}

}
