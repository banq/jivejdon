package com.jdon.jivejdon.model.message.weibo;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.util.EmojiRegexUtil;
import com.jdon.util.Debug;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * escape 😃  these utf-16, not fit for utf-8 mysql
 */
public class EscapeUTFInFIlter implements MessageRenderSpecification {
	private final static String module = EscapeUTFInFIlter.class.getName();

	@Override
	public ForumMessage render(ForumMessage forumMessage) {
		try {
			MessageVO messageVO = forumMessage.getMessageVO();
			messageVO.setBody(esacpeUtf(messageVO.getBody()));
			messageVO.setSubject(esacpeUtf(messageVO.getSubject()));
			forumMessage.setMessageVO(messageVO);
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return forumMessage;
	}

	public String esacpeUtf(String input) {
		Pattern unicodeOutliers = Pattern.compile(EmojiRegexUtil.getFullEmojiRegex(),
				Pattern.UNICODE_CASE | Pattern.CANON_EQ
						| Pattern.CASE_INSENSITIVE);
		Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(input);
		return unicodeOutlierMatcher.replaceAll(" ");
	}
}
