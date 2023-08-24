package com.jdon.jivejdon.domain.model.message.infilter;

import java.util.function.Function;

import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * escape 😃  these utf-16, not fit for utf-8 mysql
 */
public class EscapeUTFInFIlter implements Function<MessageVO, MessageVO> {
	private final static String module = EscapeUTFInFIlter.class.getName();


	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(esacpeUtf(messageVO
				.getSubject())).body(esacpeUtf(messageVO.getBody()))
				.build();

	}

	public String esacpeUtf(String input) {
		return input.replaceAll("[^\\u0000-\\uFFFF]", "");
		// Pattern unicodeOutliers = Pattern.compile(EmojiRegexUtil.getFullEmojiRegex(),
		// 		Pattern.UNICODE_CASE | Pattern.CANON_EQ
		// 				| Pattern.CASE_INSENSITIVE);
		// Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(input);
		// return unicodeOutlierMatcher.replaceAll(" ");
	}
}
