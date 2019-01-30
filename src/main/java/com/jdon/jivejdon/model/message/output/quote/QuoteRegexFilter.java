package com.jdon.jivejdon.model.message.output.quote;

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Qutote filter is responsible for replacing the [quoto][/quote] tags in the
 * body of ForumMessage
 * 
 * @author oojdon
 * 
 */
public class QuoteRegexFilter implements MessageRenderSpecification {
	private List<RegexFilter> filters = new ArrayList<RegexFilter>();

	public QuoteRegexFilter() {
		filters.add(new SummarytFilter());
		filters.add(new FromFilter());
		filters.add(new AuthorDateFilter());
	}

	public MessageVO render(MessageVO messageVO) {
		String body = messageVO.getBody();
		for (RegexFilter filter : filters) {
			body = filter.doFilter(body);
		}
		body = body.replace("[/quote]", "</div>");
		return MessageVO.builder().subject(messageVO.getSubject()).body(body).message(messageVO
				.getForumMessage())
				.build();
	}


}
