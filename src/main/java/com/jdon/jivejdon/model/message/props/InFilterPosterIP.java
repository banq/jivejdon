/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.model.message.props;

import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * Manage the properties of a ForumMessage
 * 
 * @author banq(http://www.jdon.com)
 */
public class InFilterPosterIP implements Function<MessageVO, MessageVO> {
	private final static String module = InFilterPosterIP.class.getName();

	private DateFormat dateTime_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public MessageVO apply(MessageVO messageVO) {
		try {
			String newBody = applyFilteredBody(messageVO);
			messageVO = messageVO.builder().subject(messageVO
					.getSubject()).body(newBody).build();
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return messageVO;
	}

	public String applyFilteredBody(MessageVO messageVO) {
		StringBuilder buffer = new StringBuilder(messageVO.getBody());
//		buffer.append("\n");
		long now = System.currentTimeMillis();
		String displayDateTime = dateTime_formatter.format(new Date(now));

//		buffer.append("[该贴被").append(modifier.getUsername());
//		buffer.append("于").append(displayDateTime).append("修改过]");
		return buffer.toString().intern();
	}

	public DateFormat getDateTime_formatter() {
		return dateTime_formatter;
	}

	public void setDateTime_formatter(DateFormat dateTime_formatter) {
		this.dateTime_formatter = dateTime_formatter;
	}

}
