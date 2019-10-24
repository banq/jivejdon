package com.jdon.jivejdon.domain.model.message.output.quote;

import com.jdon.jivejdon.domain.model.message.MessageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Qutote filter is responsible for replacing the [quoto][/quote] tags in the
 * body of ForumMessage
 * 
 * @author oojdon
 * 
 */
public class QuoteRegexFilter implements Function<MessageVO, MessageVO> {
	private List<RegexFilter> filters = new ArrayList<RegexFilter>();

	public QuoteRegexFilter() {
		filters.add(new SummarytFilter());
		filters.add(new FromFilter());
		filters.add(new AuthorDateFilter());
	}

	public MessageVO apply(MessageVO messageVO) {
		String body = messageVO.getBody();
		for (RegexFilter filter : filters) {
			body = filter.doFilter(body);
		}
		body = body.replace("[/quote]", "</div>");
		return messageVO.builder().subject(messageVO.getSubject()).body(body)
				.build();
	}


}
