package com.jdon.jivejdon.domain.model.message.output.html;

import java.util.function.Function;

import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * [list] [/list]  ===> [ul][/ul]
 */
public class ListStyle implements Function<MessageVO, MessageVO> {
	private final static String module = ListStyle.class.getName();


	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(messageVO.getSubject()).body(convertTags
				(messageVO.getBody())).build();
	}

	private String convertTags(String input) {
		if (!input.contains("list")) return input;
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
