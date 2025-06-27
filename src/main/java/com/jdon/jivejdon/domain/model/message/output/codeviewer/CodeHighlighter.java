/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.domain.model.message.output.codeviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.StringUtil;

/**
 * A ForumMessageFilter that syntax highlights Java code appearing between
 * [code][/code] tags in the body of ForumMessage.
 */
public class CodeHighlighter implements Function<MessageVO, MessageVO> {
	private final static Logger logger = LogManager.getLogger(CodeHighlighter.class);

	private static final String NEW_LINE = System.getProperty("line.separator");

	private String commentStart;
	private String commentEnd;
	private String stringStart;
	private String stringEnd;
	private String reservedWordStart;
	private String reservedWordEnd;

	/**
	 * Much of the work of this filter is done by a CodeViewer object.
	 */
	private CodeViewer viewer;

	/**
	 * Construct a new CodeHighlighter filter.
	 */
	public CodeHighlighter() {
		viewer = new CodeViewer();
		commentStart = viewer.getCommentStart();
		commentEnd = viewer.getCommentEnd();
		stringStart = viewer.getStringStart();
		stringEnd = viewer.getStringEnd();
		reservedWordStart = viewer.getReservedWordStart();
		reservedWordEnd = viewer.getReservedWordEnd();
	}

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 *            the ForumMessage to wrap the new filter around.
	 */
	public MessageVO apply(MessageVO messageVO) {
		return messageVO.getForumMessage().messageVOBuilder().subject(messageVO.getSubject()).body
				(highlightCode(messageVO.getBody()))
				.build();
	}

	// OTHER METHODS//

	/**
	 * Returns the HTML tag that starts comment blocks. For example, it could be
	 * <code>&lt;i&gt;</code>.
	 * 
	 * @return the HTML tag to start comment blocks.
	 */
	public String getCommentStart() {
		return commentStart;
	}

	/**
	 * Sets the HTML tag that starts comment blocks. For example, it could be
	 * <code>&lt;i&gt;</code>.
	 * 
	 * @param commentStart
	 *            the HTML tag to start comment blocks.
	 */
	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart;
		viewer.setCommentStart(commentStart);
	}

	/**
	 * Returns the HTML tag that ends comment blocks. For example, it could be
	 * <code>&lt;/i&gt;</code>. The tag should correspond to the comment start
	 * tag.
	 * 
	 * @return the HTML tag to end comment blocks.
	 */
	public String getCommentEnd() {
		return commentEnd;
	}

	/**
	 * Sets the HTML tag that ends comment blocks. For example, it could be
	 * <code>&lt;/i&gt;</code>. The tag should correspond to the comment start
	 * tag.
	 * 
	 * @param commentEnd
	 *            the HTML tag to end comment blocks.
	 */
	public void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd;
		viewer.setCommentEnd(commentEnd);
	}

	public String getStringStart() {
		return stringStart;
	}

	/**
	 * Sets the HTML tag that starts string blocks. For example, it could be
	 * <code>&lt;font color=&quot;green&quot;&gt;</code>.
	 * 
	 * @param stringStart
	 *            the HTML tag to start string blocks.
	 */
	public void setStringStart(String stringStart) {
		this.stringStart = stringStart;
		viewer.setStringStart(stringStart);
	}

	public String getStringEnd() {
		return stringEnd;
	}

	public void setStringEnd(String stringEnd) {
		this.stringEnd = stringEnd;
		viewer.setStringEnd(stringEnd);
	}

	public String getReservedWordStart() {
		return reservedWordStart;
	}

	/**
	 * Sets the HTML tag that starts reserved word blocks. For example, it could
	 * be <code>&lt;b&gt;</code>.
	 * 
	 * @param reservedWordStart
	 *            the HTML tag to start reserved word blocks.
	 */
	public void setReservedWordStart(String reservedWordStart) {
		this.reservedWordStart = reservedWordStart;
		viewer.setReservedWordStart(reservedWordStart);
	}

	public String getReservedWordEnd() {
		return reservedWordEnd;
	}

	public void setReservedWordEnd(String reservedWordEnd) {
		this.reservedWordEnd = reservedWordEnd;
		viewer.setReservedWordEnd(reservedWordEnd);
	}

	/**
	 * This method takes a string which may contain Java code. The Java code
	 * will be highlighted.
	 * 
	 * @param input
	 *            The text to be converted.
	 * @return The input string with any Java code highlighted.
	 */
	private String highlightCode(String input) {
		// Check if the string is null or zero length -- if so, return what was
		// sent in.
		if (input == null || input.length() == 0) {
			return input;
		}
		if (!input.contains("[code]"))
			return input;
		StringBuilder buf = new StringBuilder(input.length() + 50);
		int i = 0, j = 0, oldend = 0;

		while ((i = input.indexOf("[code]", oldend)) >= 0) {
			// Check to see where the ending code tag is and store than in j
			if ((j = input.indexOf("[/code]", i + 6)) < 0) {
				// End at end of input if no closing tag is given
				j = input.length() - 7;
			}
			// Take the string up to the code, append the string returned by
			// CodeViewer
			buf.append(input.substring(oldend, i));
			buf.append(
					"<TABLE width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><TBODY><TR><TD class=\"code-outline\"><PRE class=\"displaycode\">");
			// Read line by line and filter accordingly.

			StringBuilder codeBuf = new StringBuilder(input.length() / 2);

			// Advance i past one initial carriage return until the code,
			// if a carriage return exists
			if (i + 6 + 1 < input.length() && (input.charAt(i + 6 + 1) == '\n')) {
				i++;
				if (i + 6 + 1 < input.length() && (input.charAt(i + 6 + 1) == '\r')) {
					i++;
				}
			}

			BufferedReader reader = new BufferedReader(new StringReader(input.substring(i + 6, j)));
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					// unescape html > and < to workaround the HTMLFilter
					line = StringUtil.replace(line, "&lt;", "<");
					line = StringUtil.replace(line, "&gt;", ">");
					codeBuf.append(viewer.syntaxHighlight(line));
					codeBuf.append(NEW_LINE);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			String code = codeBuf.toString().intern();
			// escape braces to workaround TextStyle filter
			code = StringUtil.replace(code, "[", "&#91;");
			code = StringUtil.replace(code, "]", "&#93;");

			buf.append(code);
			buf.append("</PRE></TD></TR></TBODY></TABLE>");
			// Next time, start looking after the ending [/code] tag
			oldend = j + 7;
		}
		buf.append(input.substring(oldend, input.length()));
		return buf.toString().intern();

	}


}
