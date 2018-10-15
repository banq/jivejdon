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
		String parentTagRegx = "\\[list\\](.*?)\\[\\/list\\]";
		String parentHtml = "ul";
		String childHtml = "li";
		String childTagRex = "\\[\\*\\](.*?)";
		input = ToolsUtil.convertTags(input, parentTagRegx, parentHtml, childTagRex, childHtml);

		parentTagRegx = "\\[list=1\\](.*?)\\[\\/list\\]";
		childTagRex = "\\[\\*\\](.*?)";
		parentHtml = "ol";
		input = ToolsUtil.convertTags(input, parentTagRegx, parentHtml, childTagRex, childHtml);
		return input;
	}
}
