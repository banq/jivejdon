package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;

/**
 * [list] [/list]  ===> [ul][/ul]
 */
public class ListStyle implements MessageRenderSpecification {
	private final static String module = ListStyle.class.getName();


	public MessageVO render(MessageVO messageVO) {
		return MessageVO.builder().subject(messageVO.getSubject()).body(convertTags
				(messageVO.getBody())).message(messageVO.getForumMessage())
				.build();
	}

	private String convertTags(String input) {
		input = convertOLTag(input);
		input = convertULTag(input);
		return input;
	}

	private String convertULTag(String s) {
		String parentTagRegx = "\\[list\\]([\n]*)";
		String parentHtml = "<ul>";
		s = ToolsUtil.convertTags(s, parentTagRegx, parentHtml);

		parentTagRegx = "([\n]*)\\[\\*\\](.*?)([\n]*)";
		parentHtml = "<li>";
		s = ToolsUtil.convertTags(s, parentTagRegx, parentHtml);

		parentTagRegx = "([\n]*)\\[\\/list\\]";
		parentHtml = "</ul>";
		s = ToolsUtil.convertTags(s, parentTagRegx, parentHtml);
		return s;
	}

	private String convertOLTag(String s) {
		String parentTagRegx = "\\[list=1\\](.*?)\\[\\/list\\]([\n]*)";
		String parentHtml = "ol";
		String childTagRex = "\\[\\*\\](.*?)";
		String childHtml = "li";
		s = ToolsUtil.convertTags(s, parentTagRegx, parentHtml, childTagRex, childHtml);
		return s;
	}
}
