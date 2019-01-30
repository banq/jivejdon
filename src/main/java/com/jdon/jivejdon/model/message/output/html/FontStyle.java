package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;

/**
 * remove
 * [color=#][/color]
 */
public class FontStyle implements MessageRenderSpecification {
	private final static String module = FontStyle.class.getName();

	public MessageVO render(MessageVO messageVO) {
		return MessageVO.builder().subject(messageVO.getSubject()).body(convertTags
				(messageVO.getBody())).message(messageVO.getForumMessage())
				.build();
	}


	private String convertTags(String input) {
		String parentTagRegx = "\\[[\\/=#! \\w]*color[=#! \\w]*\\](.*?)";
		String parentHtml = "";

		String childTagRex = "";
		String childHtml = "";
		input = ToolsUtil.convertTags(input, parentTagRegx, parentHtml, childTagRex, childHtml);
		return input;
	}
}
