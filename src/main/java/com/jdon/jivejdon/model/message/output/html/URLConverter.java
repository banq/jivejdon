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

import com.jdon.jivejdon.model.message.MessageVO;

import java.util.function.Function;

/**
 * A ForumMessageFilter that converts URL's to working HTML web links.
 * <p>
 * The patterns recognized are <code>ftp://path-of-url</code>,
 * <code>http://path-of-url</code>, <code>https://path-of-url</code>
 * <code>[url path-of-url]descriptive text[/url]</code> and
 * <code>[url=path-of-url]descriptive text[/url]</code> the <code>[url]</code>
 * allows any path to be defined as link.
 */
public class URLConverter implements Function<MessageVO, MessageVO> {
	private final static String module = URLConverter.class.getName();

	private boolean filteringSubject;
	private boolean filteringBody;
	private boolean newWindowEnabled;

	/**
	 * Creates a new filter not associated with a message. This is generally
	 * only useful for defining a template filter that other fitlers will be
	 * cloned from.
	 */
	public URLConverter() {
		filteringSubject = false;
		filteringBody = true;
		newWindowEnabled = false;
	}

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 */
	public MessageVO apply(MessageVO messageVO) {
		String s = convertURL(messageVO.getBody());
		s = convertUrlNull(s);
		return MessageVO.builder().subject(messageVO.getSubject()).body(s).message(messageVO
				.getForumMessage())
				.build();
	}


	private String convertUrlNull(String s) {
		String parentTagRegx = "\\[url\\](.*?)\\[\\/url\\]";
		String parentHtml = "a";

		String childTagRex = "";
		String childHtml = "";
		s = ToolsUtil.convertTags(s, parentTagRegx, parentHtml, childTagRex, childHtml);
		return s;
	}

	// OTHER METHODS//

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
	 * Returns true if URL clicks will open in a new window.
	 * 
	 * @return true if new window mode is enabled.
	 */
	public boolean isNewWindowEnabled() {
		return newWindowEnabled;
	}

	/**
	 * Enables or disables the new window mode. When active, URL clicks will
	 * open in a new window.
	 * 
	 * @param enabled
	 *            true if new window mode should be enabled.
	 */
	public void setNewWindowEnabled(boolean enabled) {
		this.newWindowEnabled = enabled;
	}

	/**
	 * This method takes a string which may contain URLs and replaces them with
	 * working links. It does this by adding the html tags &lt;a href&gt; and
	 * &lt;/a&gt;.
	 * 
	 * don't need http or https auto convert, such as img src="http://" modified
	 * by banq
	 * <p>
	 * The patterns recognized are <code>ftp://path-of-url</code>,
	 * <code>http://path-of-url</code>, <code>https://path-of-url</code>
	 * <code>[url path-of-url]descriptive text[/url]</code> and
	 * <code>[url=path-of-url]descriptive text[/url]</code> the
	 * <code>[url]</code> allows any path to be defined as link.
	 * 
	 * @param input
	 *            the text to be converted.
	 * @return the input string with the URLs replaced with links.
	 */
	private String convertURL(String input) {
		// Check if the string is null or zero length
		// -- if so, return what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}

		// Build the response in a buffer
		StringBuilder buf = new StringBuilder(input.length() + 25);
		char[] chars = input.toCharArray();
		int len = input.length();

		int index = -1, i = 0;
		int j = 0, oldend = 0;
		int u1, u2;
		char cur;

		// Handle the occurences of the ftp:// , http:// , https:// patterns
		// and the [url] pattern.
		while (++index < len) {
			cur = chars[i = index];

			// save valuable cpu resources by expanding the tests here instead
			// of calling String.indexOf()
			j = -1;
			// if ((cur == 'f' && index < len-6 && chars[++i] == 't' &&
			// chars[++i] == 'p') || (cur == 'h' && (i = index) < len-7 &&
			// chars[++i] == 't' && chars[++i] == 't' && chars[++i] == 'p' &&
			// (chars[++i] == 's' || chars[--i] == 'p')))
			// {
			// if (i < len-4 && chars[++i] == ':' && chars[++i] == '/' &&
			// chars[++i] == '/') {
			// j = ++i;
			// }
			// }

			// did we find http or ftp?
			if (j > 0) {
				// check context, dont handle patterns preceded by any of '"<=
				// if (index == 0 || ((cur=chars[index-1]) != '\'' && cur != '"'
				// && cur != '<' && cur != '='))
				// {
				// cur = chars[j];
				// // now collect url pattern upto next " <\n\r\t"
				// while (j < len) {
				// // Is a space?
				// if (cur == ' ') break;
				// if (cur == '\t') break;
				// // Is a quote?
				// if (cur == '\'') break;
				// if (cur == '\"') break;
				// // Is html?
				// if (cur == '<') break;
				// if (cur == '[') break;
				// // Is a Unix newline?
				// if (cur == '\n') break;
				// // Is Win32 newline?
				// if (cur == '\r' && j < len-1 && chars[j+1] == '\n')
				// break;

				// j++;
				// if (j < len) {
				// cur = chars[j];
				// }
				// }
				// Check the ending character of the URL. If it's a ".,)]"
				// then we'll remove that part from the URL.
				// cur = chars[j-1];
				// if (cur == '.' || cur == ',' || cur == ')' || cur == ']')
				// {
				// j--;
				// }
				// buf.append(chars, oldend, index - oldend);
				// buf.append("<a href=\"");
				// buf.append(chars, index, j - index);
				// buf.append('\"');
				// if (newWindowEnabled) {
				// buf.append(" target=\"_blank\"");
				// }
				// buf.append('>');
				//
				// buf.append(chars, index, j - index);
				// buf.append("</a>");
				// }
				// else {
				// buf.append(chars, oldend, j - oldend);
				// }
				// oldend = index = j;
				// }
				// else if (cur == '[' && index < len - 6 &&
			}
			if (cur == '[' && index < len - 6 && chars[i = index + 1] == 'u' && chars[++i] == 'r' && chars[++i] == 'l'
					&& (chars[++i] == '=' || chars[i] == ' ')) {
				j = ++i;
				u1 = u2 = input.indexOf("]", j);
				if (u1 > 0) {
					u2 = input.indexOf("[/url]", u1 + 1);
				}
				if (u2 < 0) {
					buf.append(chars, oldend, j - oldend);
					oldend = j;
				} else {
					buf.append(chars, oldend, index - oldend);
					buf.append("<a href =\"");
					buf.append(input.substring(j, u1).trim());
					if (newWindowEnabled) {
						buf.append("\" target=\"_blank");
					}
					buf.append("\"  class='body_href' >");
					buf.append(input.substring(u1 + 1, u2).trim());
					buf.append("</a>");
					oldend = u2 + 6;
				}
				index = oldend;
			}
		}
		if (oldend < len) {
			buf.append(chars, oldend, len - oldend);
		}
		return buf.toString().intern();
	}
}
