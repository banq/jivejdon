package com.jdon.jivejdon.domain.model.message.output.linkurl;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.domain.model.message.MessageUrlVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * Quote link
 */
public class LinkUrlExtractor implements Function<MessageVO, MessageVO> {
	private final static Logger logger = LogManager.getLogger(LinkUrlExtractor.class);
	private final static Pattern httpURLEscape = Pattern.compile("^(https?|ftp|file)" +
			"://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");


	@Override
	public MessageVO apply(MessageVO messageVO) {
		String linkUrl = "";
		String newbody = messageVO.getBody();
		if (!newbody.contains("http")) return messageVO;
		Matcher matcher = httpURLEscape.matcher(newbody);
		if (matcher.find()) {
			linkUrl = matcher.group();
			newbody = matcher.replaceAll("");
		} 

		messageVO.getForumMessage().setMessageUrlVO(new MessageUrlVO(linkUrl, messageVO
				.getForumMessage().getMessageUrlVO().getThumbnailUrl(), messageVO
				.getForumMessage().getMessageUrlVO().getImageUrl()));
		return messageVO.builder().subject(messageVO.getSubject()).body(newbody).build();
	}
}
