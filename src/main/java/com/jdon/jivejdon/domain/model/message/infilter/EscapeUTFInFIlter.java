package com.jdon.jivejdon.domain.model.message.infilter;

import java.util.Stack;
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
		if (isNested(input))
		     return  input.replaceAll("\\[|\\]", "");
		return input.replaceAll("[^\\u0000-\\uFFFF]", "");
		// Pattern unicodeOutliers = Pattern.compile(EmojiRegexUtil.getFullEmojiRegex(),
		// 		Pattern.UNICODE_CASE | Pattern.CANON_EQ
		// 				| Pattern.CASE_INSENSITIVE);
		// Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(input);
		// return unicodeOutlierMatcher.replaceAll(" ");
	}

	//from chatGPT 
	private boolean isNested(String text) {
		Stack<Character> stack = new Stack<>();

		for (char c : text.toCharArray()) {
			if (c == '[') {
				stack.push(c);
			} else if (c == ']') {
				if (stack.isEmpty()) {
					return false; // Closing bracket without a matching opening bracket
				}
				stack.pop();
			}
		}
        //chatgpt NOT know here have "!", he say: stack.isEmpty()
		return !stack.isEmpty(); // If the stack is empty, brackets are properly nested
	}

	public static void main(String[] args) {
		EscapeUTFInFIlter escapeUTFInFIlter = new EscapeUTFInFIlter();
		String in = "dasdfsdf[sfsa[b]df[/b]sad";
		if (escapeUTFInFIlter.isNested("dasdfsdf[sfsa[b]df[/b]sad"))
		     System.out.println("===" + in.replaceAll("\\[|\\]", "ss"));
	}
}
