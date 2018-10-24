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
package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;

/**
 * A ForumMessageFilter that converts newline characters into HTML &lt;br&gt;
 * tags. This filter should only be run after any HTML stripping filters. This
 * filter should be run before the Java code filter, as "[" and "]" are escaped
 * to work around the TextStyle filter.
 */
public class Newline implements MessageRenderSpecification {
	private final static String module = Newline.class.getName();
	private static final char[] P_TAG = "<p>".toCharArray();
	private static final char[] BR_TAG = "<p class=\"indent\">".toCharArray();

	/**
	 * Finds out whether a given index position resides within any boundaries of
	 * a set of index boundaries.
	 *
	 * @param index      the index position to be tested.
	 * @param boundaries the table containing a set of boundaries.
	 * @return true if index resides within at least one boundary, false
	 * otherwise.
	 */
	private static boolean notInCodeSection(int index, int[][] boundaries) {
		if (boundaries == null) {
			return true;
		} else {
			boolean notInCodeSection = true;
			int i = 0;
			while (i < boundaries.length) {
				// if in a code section
				if (index >= boundaries[i][0] && index <= boundaries[i][1]) {
					return false;
				} else {
					i++;
					continue;
				}
			}
			return notInCodeSection;
		}
	}

	/**
	 * Records the index positions of [code][/code] sections
	 *
	 * @param input the text to be filtered.
	 * @return a table containing the index positions of code sections
	 */
	private static int[][] recordCodeIndeces(String input) {
		int[][] codeIndeces;
		int count;
		int oldend;
		int i, j;

		count = 0;
		oldend = 0;
		i = j = 0;

		// first figure out how many pairs of [code][/code] there are
		while (((i = input.indexOf("[code]", oldend)) >= 0) && ((j = input.indexOf("[/code]", i +
				6)) >= 0)) {
			count++;
			oldend = j + 7;
		}

		oldend = 0;
		codeIndeces = new int[count][2];
		i = j = 0;
		int x = 0;
		// int y = 0;

		// record code index positions
		while (((i = input.indexOf("[code]", oldend)) >= 0) && ((j = input.indexOf("[/code]", i +
				6)) >= 0)) {
			codeIndeces[x][0] = i;
			// y++;
			codeIndeces[x][1] = j;
			x++;
			// y--;
			oldend = j + 7;
		}
		return codeIndeces;
	}

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 * @param message
	 *            the ForumMessage to wrap the new filter around.
	 */
	public ForumMessage render(ForumMessage message) {
		try {
			MessageVO messageVO = message.getMessageVO();
			if (!messageVO.isFiltered()) {
				String s = messageVO.getBody();
				s = ToolsUtil.convertTags(s, "\n\\[", new String(P_TAG) + "\\[");
				messageVO.setBody(convertNewlinesAroundCode(s));
			}
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

	/**
	 * Replaces newline characters with the HTML equivalent. This method works
	 * around the code filter, allowing copy and paste actions to be
	 * successfully performed.
	 *
	 * @param input
	 *            the text to be converted.
	 * @return the input string with newline characters replaced with HTML
	 *         newline tags..
	 */
	private String convertNewlinesAroundCode(String input) {
		char[] chars = input.toCharArray();
		int[][] table;
		int cur = 0;
		int len = chars.length;
		StringBuilder buf = new StringBuilder(len);

		table = recordCodeIndeces(input);

		if (table.length > 0) { // there are some code sections
			// while there are more characters to filter
			for (int i = 0; i < len; i++) {
				// If we've found a Unix newline, add BR tag.
				// filter only positions outside of code sections
				if (chars[i] == '\n' && notInCodeSection(i, table)) {
					buf.append(chars, cur, i - cur).append(BR_TAG);
					cur = i + 1;
				}
				// If we've found a Windows newline, add BR tag.
				// filter only positions outside of code sections
				else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n' && notInCodeSection(i, table)) {
					buf.append(chars, cur, i - cur).append(BR_TAG);
					i++;
					cur = i + 1;
				}
			}
		} else { // there are no code sections
			// filter normally
			for (int i = 0; i < len; i++) {
				// If we've found a Unix newline, add BR tag.
				if (chars[i] == '\n') {
					buf.append(chars, cur, i - cur).append(BR_TAG);
					cur = i + 1;
				}
				// If we've found a Windows newline, add BR tag.
				else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n') {
					buf.append(chars, cur, i - cur).append(BR_TAG);
					i++;
					cur = i + 1;
				}
			}
		}
		// Add whatever chars are left to buffer.
		buf.append(chars, cur, len - cur);
		return buf.toString().intern();
	}
}
