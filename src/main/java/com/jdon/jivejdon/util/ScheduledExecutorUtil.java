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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;

@Component("scheduledExecutorUtil")
public class ScheduledExecutorUtil implements Startable {

	public final ScheduledExecutorService scheduExec;

	public static ScheduledExecutorService scheduExecStatic = Executors.newScheduledThreadPool(1);

	public ScheduledExecutorUtil() {
		scheduExec = Executors.newScheduledThreadPool(1);
	}

	public void start() {
	}

	// when container down or undeploy, active this method.
	public void stop() {
		scheduExec.shutdown();
		scheduExecStatic.shutdown();
	}

	public ScheduledExecutorService getScheduExec() {
		return scheduExec;
	}

}
