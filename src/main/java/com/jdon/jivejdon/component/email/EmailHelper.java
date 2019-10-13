package com.jdon.jivejdon.component.email;

import com.google.common.eventbus.AsyncEventBus;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

public class EmailHelper {

	private AsyncEventBus eventBus;

	public EmailHelper(EmailSender emailSender, ScheduledExecutorUtil scheduledExecutorUtil) {
		eventBus = new AsyncEventBus(scheduledExecutorUtil.getScheduExec());
		eventBus.register(emailSender);
	}

	public void send(EmailVO emailVO) {
		eventBus.post(emailVO);
	}

	public void stop() {
		eventBus = null;
	}
}
