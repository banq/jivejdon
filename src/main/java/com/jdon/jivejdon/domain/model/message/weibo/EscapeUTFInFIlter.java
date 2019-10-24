package com.jdon.jivejdon.domain.model.message.weibo;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.util.EmojiRegexUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Pattern unicodeOutliers = Pattern.compile(EmojiRegexUtil.getFullEmojiRegex(),
				Pattern.UNICODE_CASE | Pattern.CANON_EQ
						| Pattern.CASE_INSENSITIVE);
		Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(input);
		return unicodeOutlierMatcher.replaceAll(" ");
	}
}
