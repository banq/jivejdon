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
package com.jdon.jivejdon.infrastructure.component.email;

import com.jdon.annotation.Service;
import com.jdon.container.pico.Startable;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.domain.model.feed.Feed;
import com.jdon.jivejdon.util.EmailTask;

@Service("feedEmailSender")
public class FeedEmailSenderImp implements FeedEmailSender, Startable {

	private FeedbackEmailParams feedbackEmailParams;

	private EmailHelper emailHelper;

	public FeedEmailSenderImp(FeedbackEmailParams feedbackEmailParams, EmailHelper emailHelper) {
		super();
		this.feedbackEmailParams = feedbackEmailParams;
		this.emailHelper = emailHelper;
	}

	public void send(EventModel em) {
		Feed feed = (Feed) em.getModelIF();
		String subject = feed.getSubject();
		String body = feed.getBody();
		String toEmail = feedbackEmailParams.getToEmail();
		String toName = feedbackEmailParams.getToName();
		String fromEmail = feed.getEmail();

		EmailVO emailVO = new EmailVO(toName, toEmail, "", fromEmail, subject, body + " from:" + feed.getEmail(), EmailTask.NOHTML_FORMAT);
		emailHelper.send(emailVO);

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		emailHelper.stop();

	}

}
