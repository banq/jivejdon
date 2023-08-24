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
package com.jdon.jivejdon.domain.model.message.infilter;

import com.jdon.jivejdon.domain.event.ATUserNotifiedEvent;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.message.output.weibo.AuthorNameFormat;
import com.jdon.util.Debug;

import java.util.Collection;
import java.util.function.Function;

/**
 * pick up
 * 
 * @banq , then send a short message to banq
 * 
 * @author banq
 * @see AuthorNameFormat
 * 
 */

public class InFilterAuthor implements Function<MessageVO, MessageVO> {
	private final static String module = InFilterAuthor.class.getName();


	public MessageVO apply(MessageVO messageVO) {
		try {
			AuthorNameFilter authorNameFilter = new AuthorNameFilter();
			Collection<String> usernames = authorNameFilter.getUsernameFromBody(messageVO);
			if (usernames == null || usernames.isEmpty())
				return messageVO;

			messageVO.getForumMessage().shortMPublisherRole.notifyATUser(new ATUserNotifiedEvent
					(messageVO.getForumMessage().getAccount(),
							usernames, messageVO.getSubject(),
							messageVO.getForumMessage().getMessageId()));
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return messageVO;
	}

}
