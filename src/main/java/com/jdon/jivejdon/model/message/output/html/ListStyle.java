package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;

/**
 * [list] [/list]  ===> [ul][/ul]
 */
public class ListStyle implements MessageRenderSpecification {
	private final static String module = ListStyle.class.getName();


	@Override
	public ForumMessage render(ForumMessage forumMessage) {
		try {
			MessageVO messageVO = forumMessage.getMessageVO();
			if (!messageVO.isFiltered()) {
				messageVO.setBody(convertTags(messageVO.getBody()));
			}
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return forumMessage;
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
