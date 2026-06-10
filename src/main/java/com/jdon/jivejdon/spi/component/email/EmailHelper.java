package com.jdon.jivejdon.spi.component.email;

import com.google.common.eventbus.AsyncEventBus;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

public class EmailHelper  implements Startable {

	private ScheduledExecutorUtil scheduledExecutorUtil;
	private EmailSender emailSender;

	public EmailHelper(EmailSender emailSender, ScheduledExecutorUtil scheduledExecutorUtil) {
		this.emailSender = emailSender;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void send(EmailVO emailVO) {
		AsyncEventBus eventBus = new AsyncEventBus(scheduledExecutorUtil.getScheduExec());
		eventBus.register(emailSender);
		eventBus.post(emailVO);
	}

	public void start() {
		        System.out.println("EmailHelper started");
	}
	public void stop() {
		emailSender = null;
		scheduledExecutorUtil = null;
	}
}
