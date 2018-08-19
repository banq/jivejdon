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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;
import com.jdon.util.StringUtil;

/**
 * A ForumMessageFilter that highlights quoted messages based upon the level of
 * indentation - very similar to how groups.google.com highlights quotes.
 * 
 * @author Bruce Ritchie
 */
public class QuoteFilter implements MessageRenderSpecification {
	private final static String module = QuoteFilter.class.getName();

	private ArrayList quoteColors;
	private char quoteChar;
	private boolean reformatTextEnabled;

	public QuoteFilter() {
		quoteColors = new ArrayList();

		quoteColors.add("#660066");
		quoteColors.add("#007777");
		quoteColors.add("#3377ff");
		quoteColors.add("#669966");
		quoteColors.add("#660000");

		quoteChar = '>';
		reformatTextEnabled = false;
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
			if (!messageVO.isFiltered())
				messageVO.setBody(parseQuotes(messageVO.getBody()));
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

	// OTHER METHODS//

	public String getQuoteColors() {
		StringBuilder sb = new StringBuilder();

		for (int x = 0; x < quoteColors.size(); x++) {
			sb.append(quoteColors.get(x).toString());
			if (x != quoteColors.size() - 1) {
				sb.append(",");
			}
		}

		return sb.toString().intern();
	}

	public void setQuoteColors(String prefix) {
		if (prefix != null) {
			quoteColors = new ArrayList();
			StringTokenizer st = new StringTokenizer(prefix, ",");

			while (st.hasMoreTokens()) {
				quoteColors.add(st.nextToken());
			}
		}
	}

	public boolean isReformatTextEnabled() {
		return reformatTextEnabled;
	}

	public void setReformatTextEnabled(boolean enabled) {
		reformatTextEnabled = enabled;
	}

	public String getQuoteChar() {
		return new Character(quoteChar).toString();
	}

	public void setQuoteChar(String c) {
		if (c != null) {
			quoteChar = c.trim().charAt(0);
		}
	}

	/**
	 * This method takes a string which may contain quotes lines and quotes the
	 * lines accordingly. It does this by adding the prefix line postfix
	 * according to the appropriate quote level. An arbitrary level of quotes
	 * are available to be set, and once those are used up it restarts from the
	 * beginning of the list
	 * 
	 * @param input
	 *            the text to be converted.
	 * @returns the input string with the quoted lines handled accordingly.
	 */
	private String parseQuotes(String input) {
		// Check if the string is null or zero length -- if so, return
		// what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}

		StringBuilder sb = new StringBuilder(input.length() + 512);
		BufferedReader br = new BufferedReader(new StringReader(input), 1024);
		ArrayList lines = new ArrayList();
		String line;
		char chars[];

		try {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException e) { /* ignore */
		}

		// correct text formatting if requested
		if (reformatTextEnabled) {
			reformatText(lines);
		}

		for (int c = 0; c < lines.size(); c++) {
			line = (String) lines.get(c);
			chars = StringUtil.replace(line, "&gt;", ">").toCharArray();

			if (isQuoted(chars)) {
				int count = countQuotes(chars) - 1;
				sb.append("<font color=\"");
				sb.append(quoteColors.get(count % quoteColors.size()).toString());
				sb.append("\">");
				sb.append(line);

				// for all following lines that have the same quote level
				// just append them to the buffer, we'll append the quotePostfix
				// at the end. This saves on needless html being generated and
				// reduces the size of the final html page. (Every little bit
				// counts)
				for (int g = c + 1; g < lines.size(); g++) {
					line = (String) lines.get(g);
					chars = StringUtil.replace(line, "&gt;", ">").toCharArray();

					if (isQuoted(chars) && (countQuotes(chars) - 1 == count)) {
						sb.append("\n").append(line);
						c = g;
					} else {
						break;
					}
				}
				sb.append("</font>").append("\n");
			} else {
				sb.append(line).append("\n");
			}
		}

		return sb.toString().intern();
	}

	/**
	 * This method takes a list of strings (one per line) and through some
	 * heuristics attempts to correct for badly wrapped lines.
	 * <p>
	 * 
	 * This is allows for:
	 * <p>
	 * 
	 * <ul>
	 * <li>Making the text easier to read</li>
	 * <li>Restoring the text format as close as possible to the original
	 * formatting of the text prior to being reformatted by a MUA such as
	 * Microsoft Outlook. MUA's often wrap lines of length > 76 characters
	 * regardless of whether they are quoted or not.</li>
	 * </ul>
	 * 
	 * @param lines
	 *            an arraylist of strings, one line/arraylist entry
	 */
	private void reformatText(ArrayList lines) {
		char chars[];
		int count;
		int readAhead;
		boolean isHTMLFilterActive = false;

		for (int c = 0; c < lines.size(); c++) {
			chars = getLineAsChar(lines, c);

			// if this isn't a quoted line, skip it
			if (!isQuoted(chars)) {
				continue;
			}

			// check to see if we substituted any &gt; text
			if (chars.length < ((String) lines.get(c)).length()) {
				isHTMLFilterActive = true;
			}

			count = countQuotes(chars);
			readAhead = (lines.size() <= c + count + 1) ? lines.size() : c + count + 1;
			LinkedList l = new LinkedList();

			// For each quoted line, read ahead (n-1 quotes) lines to see
			// if the following lines are actually wrapped text from the
			// current quoted line.
			//
			// Notes: - If any of the following lines do not have < n quotes
			// (no quote acceptable), they are not wrapped text. However
			// if prior following lines fulfill all the other criteria,
			// they are wrapped text
			// - If any of the following lines have more than 2 words,
			// they are not wrapped text
			// - If any consecutive following lines have the same number
			// of quotes, they are not wrapped text
			// - If any of the following lines have less quotes than the
			// previous following lines, they are not wrapped text
			// - If the following line has no characters, they are not
			// wrapped text
			for (int g = c + 1; g < readAhead; g++) {
				chars = getLineAsChar(lines, g);
				l.addLast(chars);

				// rule 1
				if (isQuoted(chars) && countQuotes(chars) > count) {
					// System.err.println("Too many quotes - " + new
					// String(chars));
					l.removeLast();
					break;
				}
				// rule 2
				if (countWords(chars) > 2) {
					// System.err.println("Too many words - " + new
					// String(chars));
					l.removeLast();
					break;

				}
				// rule 3
				if (countQuotes(chars) == count) {
					// System.err.println("same number of quotes - " + new
					// String(chars));
					l.removeLast();
					break;
				}
				// rule 4
				if (!checkQuoteConsistency(l)) {
					// System.err.println("Quote Inconsistent - " + new
					// String(chars));
					l.removeLast();
					break;
				}
				// rule 5
				if (new String(chars).trim().length() < 1) {
					// System.err.println("Empty line - " + new String(chars));
					l.removeLast();
					break;
				}
			}

			// we have wrapped text
			if (l.size() > 0) {
				Iterator iter = l.iterator();
				StringBuilder sb = new StringBuilder(l.size() + 1 * 80);
				String line = (String) lines.get(c);

				if (isHTMLFilterActive) {
					line = StringUtil.replace(line, ">", "&gt;");
				}

				sb.append(line);

				while (iter.hasNext()) {
					chars = (char[]) iter.next();
					sb.append(" ");
					sb.append(stripQuotes(chars));
					lines.remove(c + 1);
				}

				lines.set(c, sb.toString().intern());
			}
		}
	}

	private char[] getLineAsChar(ArrayList lines, int number) {
		String line = (String) lines.get(number);
		return StringUtil.replace(line, "&gt;", ">").toCharArray();
	}

	private int countWords(char[] chars) {
		String line = stripQuotes(chars).trim();
		int count = 0;

		for (int x = 0; x < line.length(); x++) {
			if (line.charAt(x) == ' ') {
				count++;
			}
		}

		return count + 1;
	}

	private boolean checkQuoteConsistency(LinkedList l) {

		for (int c = l.size() - 1; c > 0; c--) {
			for (int g = 0; g < c; g++) {
				if (countQuotes((char[]) l.get(c)) < countQuotes((char[]) l.get(g))) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isQuoted(char[] chars) {
		for (int c = 0; c < chars.length; c++) {
			if (chars[c] == quoteChar) {
				return true;
			} else if (chars[c] != ' ') {
				return false;
			}
		}

		return false;
	}

	private int countQuotes(char[] chars) {
		int quoteCount = 0;

		for (int c = 0; c < chars.length; c++) {
			if (chars[c] == quoteChar) {
				quoteCount++;
			} else if (chars[c] == ' ') {
				continue;
			} else {
				return quoteCount;
			}
		}

		return quoteCount;
	}

	private String stripQuotes(char[] line) {
		StringBuilder sb = new StringBuilder(new String(line));
		boolean start = false;

		for (int c = 0; c < sb.length(); c++) {
			if (!start && sb.charAt(c) == quoteChar) {
				sb.deleteCharAt(c);
				if (c > 0) {
					c--;
				}
			} else if (!start && sb.charAt(c) != ' ') {
				start = true;
			}
		}

		return sb.toString().trim().intern();
	}
}
