package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;

/**
 * remove
 * [color=#][/color]
 */
public class FontStyle implements MessageRenderSpecification {
	private final static String module = FontStyle.class.getName();

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
		String parentTagRegx = "\\[[\\/=#! \\w]*color[=#! \\w]*\\](.*?)";
		String parentHtml = "";

		String childTagRex = "";
		String childHtml = "";
		input = ToolsUtil.convertTags(input, parentTagRegx, parentHtml, childTagRex, childHtml);
		return input;
	}
}
