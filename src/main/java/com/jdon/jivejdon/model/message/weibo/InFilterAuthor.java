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
package com.jdon.jivejdon.model.message.weibo;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.event.ATUserNotifiedEvent;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.output.weibo.AuthorNameFormat;
import com.jdon.util.Debug;

import java.util.Collection;

/**
 * pick up
 * 
 * @banq , then send a short message to banq
 * 
 * @author banq
 * @see AuthorNameFormat
 * 
 */

public class InFilterAuthor implements MessageRenderSpecification {
	private final static String module = InFilterAuthor.class.getName();

	public ForumMessage render(ForumMessage message) {
		try {
			AuthorNameFilter authorNameFilter = new AuthorNameFilter();
			Collection<String> usernames = authorNameFilter.getUsernameFromBody(message);
			if (usernames == null || usernames.isEmpty())
				return message;

			message.shortMPublisherRole.notifyATUser(new ATUserNotifiedEvent(message.getAccount(), usernames, message.getMessageVO().getSubject(),
					message.getMessageId()));
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

}
