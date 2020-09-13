/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.domain.model.message.output.html;

import com.jdon.jivejdon.domain.model.message.MessageVO;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A ForumMessageFilter that replaces to <img src=url></img>
 */

public class ImageFilter implements Function<MessageVO, MessageVO> {
	private final static String module = ImageFilter.class.getName();

	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(messageVO.getSubject()).body(convertTags
				(messageVO)).build();
	}


	private String convertTags(MessageVO messageVO) {
		String str = messageVO.getBody();
		if (str == null || str.length() == 0) {
			return str;
		}
		String patt = "(\\[img\\])([^\\[]+)(\\[/img\\])";
		Pattern p = Pattern.compile(patt);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		String url = "";
		while (result) {
			url = m.group(2);
			m.appendReplacement(sb, "<img src=\"" + url + "\" border='0' loading='lazy' >");
			result = m.find();
		}
//		messageVO.setThumbnailUrl(url);
		m.appendTail(sb);
		return sb.toString();
	}

}
