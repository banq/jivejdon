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

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;

/**
 * A ForumMessageFilter that replaces [b][/b] and [i][/i] tags with their HTML
 * tag equivalents. This filter should only be run after any HTML stripping
 * filters are.
 */
public class TextStyle implements MessageRenderSpecification {
	private final static String module = TextStyle.class.getName();

	private boolean boldEnabled = true;
	private boolean italicEnabled = true;
	private boolean underlineEnabled = true;
	private boolean preformatEnabled = true;
	private boolean filteringSubject = false;
	private boolean filteringBody = true;

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case. The count
	 * paramater is set to the number of replaces performed.
	 *
	 * @param line      the String to search to perform replacements on
	 * @param oldString the String that should be replaced by newString
	 * @param newString the String that will replace all instances of oldString
	 * @param count     a value that will be updated with the number of replaces
	 *                  performed.
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString,
												 int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString().intern();
		}
		return line;
	}

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 */
	public MessageVO render(MessageVO messageVO) {
		return MessageVO.builder().subject(convertTags(messageVO.getSubject())).body
				(convertTags(messageVO.getBody())).message(messageVO.getForumMessage())
				.build();
	}


	/**
	 * Returns true if translation of [b][/b] tags to the HTML bold tag is
	 * enabled.
	 *
	 * @return true if translation of [b][/b] tags is enabled.
	 */
	public boolean getBoldEnabled() {
		return boldEnabled;
	}

	/**
	 * Toggles translation of [b][/b] tags to the HTML bold tag.
	 *
	 * @param boldEnabled
	 *            toggles translation of [b][/b] tags.
	 */
	public void setBoldEnabled(boolean boldEnabled) {
		this.boldEnabled = boldEnabled;
	}

	/**
	 * Returns true if translation of [i][/i] tags to the HTML italic tag is
	 * enabled.
	 *
	 * @return true if translation of [i][/i] tags is enabled.
	 */
	public boolean getItalicEnabled() {
		return italicEnabled;
	}

	/**
	 * Toggles translation of [i][/i] tags to the HTML italic tag.
	 *
	 * @param italicEnabled
	 *            toggles translation of [i][/i] tags.
	 */
	public void setItalicEnabled(boolean italicEnabled) {
		this.italicEnabled = italicEnabled;
	}

	/**
	 * Returns true if translation of [u][/u] tags to the HTML underline tag is
	 * enabled.
	 *
	 * @return true if translation of [i][/i] tags is enabled.
	 */
	public boolean getUnderlineEnabled() {
		return underlineEnabled;
	}

	/**
	 * Toggles translation of [u][/u] tags to the HTML underline tag.
	 *
	 * @param underlineEnabled
	 *            toggles translation of [u][/u] tags.
	 */
	public void setUnderlineEnabled(boolean underlineEnabled) {
		this.underlineEnabled = underlineEnabled;
	}

	/**
	 * Returns true if translation of [pre][/pre] tags to the HTML preformat tag
	 * is enabled.
	 *
	 * @return true if translation of [pre][/pre] tags is enabled.
	 */
	public boolean getPreformatEnabled() {
		return preformatEnabled;
	}

	/**
	 * Toggles translation of [pre][/pre] tags to the HTML underline tag.
	 *
	 * @param preformatEnabled
	 *            toggles translation of [pre][/pre] tags.
	 */
	public void setPreformatEnabled(boolean preformatEnabled) {
		this.preformatEnabled = preformatEnabled;
	}

	/**
	 * Returns true if filtering on the subject is enabled.
	 *
	 * @return true if filtering on the subject is enabled.
	 */
	public boolean isFilteringSubject() {
		return filteringSubject;
	}

	/**
	 * Enables or disables filtering on the subject.
	 *
	 * @param filteringSubject
	 *            toggle value for filtering on subject.
	 */
	public void setFilteringSubject(boolean filteringSubject) {
		this.filteringSubject = filteringSubject;
	}

	/**
	 * Returns true if filtering on the body is enabled.
	 *
	 * @return true if filtering on the body is enabled.
	 */
	public boolean isFilteringBody() {
		return filteringBody;
	}

	/**
	 * Enables or disables filtering on the body.
	 *
	 * @param filteringBody
	 *            toggle value for filtering on body.
	 */
	public void setFilteringBody(boolean filteringBody) {
		this.filteringBody = filteringBody;
	}

	/**
	 * This method takes a string which may contain [b][/b] and [i][/i] tags and
	 * converts them to their HTML equivalents.
	 *
	 * @param input
	 *            the text to be converted.
	 * @return the input string with the [b][/b] and [i][/i] tags converted to
	 *         their HTML equivalents.
	 */
	private String convertTags(String input) {
		// Check if the string is null or zero length -- if so, return what was
		// sent in.
		if (input == null || input.length() == 0) {
			return input;
		} else {
			// To figure out how many times we've made text replacements, we
			// need to pass around Integer count objects.
			int[] boldStartCount = new int[1];
			int[] italicsStartCount = new int[1];
			int[] boldEndCount = new int[1];
			int[] italicsEndCount = new int[1];
			int[] underlineStartCount = new int[1];
			int[] underlineEndCount = new int[1];
			int[] preformatStartCount = new int[1];
			int[] preformatEndCount = new int[1];
			if (boldEnabled) {
				input = replaceIgnoreCase(input, "[b]", "<b>", boldStartCount);
				input = replaceIgnoreCase(input, "[/b]", "</b>", boldEndCount);
				int bStartCount = boldStartCount[0];
				int bEndCount = boldEndCount[0];
				while (bStartCount > bEndCount) {
					input = input.concat("</b>");
					bEndCount++;
				}
			}
			if (italicEnabled) {
				input = replaceIgnoreCase(input, "[i]", "<i>", italicsStartCount);
				input = replaceIgnoreCase(input, "[/i]", "</i>", italicsEndCount);
				int iStartCount = italicsStartCount[0];
				int iEndCount = italicsEndCount[0];
				while (iStartCount > iEndCount) {
					input = input.concat("</i>");
					iEndCount++;
				}
			}
			if (underlineEnabled) {
				input = replaceIgnoreCase(input, "[u]", "<u>", underlineStartCount);
				input = replaceIgnoreCase(input, "[/u]", "</u>", underlineEndCount);
				int uStartCount = underlineStartCount[0];
				int uEndCount = underlineEndCount[0];
				while (uStartCount > uEndCount) {
					input = input.concat("</u>");
					uEndCount++;
				}
			}
			if (preformatEnabled) {
				input = replaceIgnoreCase(input, "[pre]", "<pre>", preformatStartCount);
				input = replaceIgnoreCase(input, "[/pre]", "</pre>", preformatEndCount);
				int preStartCount = preformatStartCount[0];
				int preEndCount = preformatEndCount[0];
				while (preStartCount > preEndCount) {
					input = input.concat("</pre>");
					preEndCount++;
				}
			}
		}
		return input;
	}

}
