/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.domain.model.message.output.topics;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.Debug;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * removing replace #topic with search key
 * 
 * @author banq
 * 
 */
public class TopicsFilter implements Function<MessageVO, MessageVO> {
	private final static String module = TopicsFilter.class.getName();

	private final String regEx = "[a-zA-Z\u4e00-\u9fa5]+";
	private String topicUrl = "/query/searchAction.shtml?query=";

	//
	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(messageVO.getSubject()).body(applyFilteredBody(messageVO.getBody())).build();
	}

	/**
	 * space + keyword + space will be replaced
	 */
	public String applyFilteredBody(String body) {
		try {
			Pattern topicRegEx = Pattern.compile("#" + regEx);
			Matcher matcher = topicRegEx.matcher(body);
			while (matcher.find()) {
				String topocStr = matcher.group();
				if (topocStr.length() < 15)
					body = Pattern.compile(topocStr).matcher(body)
							.replaceAll(getKeyUrlStr(topocStr.replaceAll("#", "")));
			}
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return body;
	}

	public static void main(String[] args) throws Exception {
		TopicsFilter topicsFilter = new TopicsFilter();
		String body = topicsFilter.applyFilteredBody("asdasd#dddddd www");
		System.out.println(body);
	}

	private String getKeyUrlStr(String topic) {
		StringBuilder bf = new StringBuilder();
		bf.append("<a href='");
		bf.append(topicUrl);
		bf.append(topic);
		bf.append("'><b>");
		bf.append(topic);
		bf.append("</b></a>");
		return bf.toString();
	}

	/**
	 * @return String return the topicUrl
	 */
	public String getTopicUrl() {
		return topicUrl;
	}

	/**
	 * @param topicUrl the topicUrl to set
	 */
	public void setTopicUrl(String topicUrl) {
		this.topicUrl = topicUrl;
	}

}
