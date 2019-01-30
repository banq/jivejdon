/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.model.message.output.codeviewer;

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * A ForumMessageFilter that syntax highlights Java code appearing between
 * [code][/code] tags in the body of ForumMessage.
 */
public class JavaCodeHighlighter implements MessageRenderSpecification {
	private final static String module = JavaCodeHighlighter.class.getName();

	private String commentStart;
	private String commentEnd;
	private String stringStart;
	private String stringEnd;
	private String reservedWordStart;
	private String reservedWordEnd;
	private String methodStart;
	private String methodEnd;
	private String characterStart;
	private String characterEnd;
	private String bracketStart;
	private String bracketEnd;
	private String numberStart;
	private String numberEnd;
	private boolean filterMethod;
	private boolean filterNumber;

	// for formatting code with an html table and printing line numbers
	private boolean applyTableSurround; // surround code with an html table
	private boolean showLineCount; // show line numbers
	// private int alignPosition;
	private String tableBorderColor;
	private String tableLinePanelBackgroundColor;
	private String tableCodePanelBackgroundColor;
	private String lineNumberColor;

	/**
	 * Much of the work of this filter is done by a JavaCodeViewer object.
	 */
	private JavaCodeViewer viewer;

	/**
	 * Construct a new JavaCodeHighlighter filter.
	 */
	public JavaCodeHighlighter() {
		viewer = new JavaCodeViewer();
		commentStart = viewer.getCommentStart();
		commentEnd = viewer.getCommentEnd();
		stringStart = viewer.getStringStart();
		stringEnd = viewer.getStringEnd();
		reservedWordStart = viewer.getReservedWordStart();
		reservedWordEnd = viewer.getReservedWordEnd();
		methodStart = viewer.getMethodStart();
		methodEnd = viewer.getMethodEnd();
		characterStart = viewer.getCharacterStart();
		characterEnd = viewer.getCharacterEnd();
		bracketStart = viewer.getBracketStart();
		bracketEnd = viewer.getBracketEnd();
		numberStart = viewer.getNumberStart();
		numberEnd = viewer.getNumberEnd();
		filterMethod = viewer.getFilterMethod();
		filterNumber = viewer.getFilterNumber();
		applyTableSurround = false; // default
		showLineCount = false; // default
		tableBorderColor = "#999999"; // default
		tableLinePanelBackgroundColor = "#dddddd"; // default
		tableCodePanelBackgroundColor = "#ffffff"; // default
		lineNumberColor = "#555555"; // default
	}

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 * 
	 */
	public MessageVO render(MessageVO messageVO) {
		return MessageVO.builder().subject(messageVO.getSubject()).body
				(highlightCode(messageVO.getBody(), applyTableSurround)).message(messageVO
				.getForumMessage())
				.build();
	}

	// FROM THE FORUMMESSAGE INTERFACE//

	/**
	 * create an outter table that surrounds code. A benefit of this is that the
	 * table background colors can be specified according to whatever maybe
	 * easier on the eyes, especially if the existing page's background color
	 * does not display well with formatted code. Another useful feature of the
	 * code table is the ability to conveniently output line numbers.
	 * 
	 * @param text
	 *            code already formatted with html makeup
	 * @param numLines
	 *            number of lines in text
	 * @return code surrounded with a table, possibly displaying line numbers
	 */
	private String surroundWithTable(String text, int numLines) {
		// kludgey, non-optimized code, as the table
		// code can be shared across all calls to the filter
		StringBuilder buffer = new StringBuilder();
		buffer.append("<table border=\"0\" cellspacing=\"1\" cellpadding=\"0\" width=\"100%\" bgcolor=\"" + tableBorderColor + "\">").append("\n");
		buffer.append("<tr>");
		// if line counts should be displayed
		// a cool feature to implement would be to be able
		// to specify left or right sides
		if (showLineCount) {
			buffer.append("<td width=\"1%\" align=\"left\" bgcolor=\"" + tableLinePanelBackgroundColor + "\">\n");
			buffer.append("<font color=\"" + lineNumberColor + "\">");
			buffer.append("<pre>\n");
			buffer.append(makeLines(numLines));
			buffer.append("</pre>\n");
			buffer.append("</font>");
			buffer.append("</td>");
		}
		buffer.append("<td width=\"99%\" align=\"left\" bgcolor=\"" + tableCodePanelBackgroundColor + "\">\n");
		buffer.append("<pre>\n");
		buffer.append(text);
		buffer.append("</pre>\n");
		buffer.append("</td>").append("</tr>\n");
		buffer.append("</table>\n");
		return buffer.toString().intern();
	}

	/**
	 * create line numbers the effect is similar to an ide's line number display
	 */
	private String makeLines(int numLines) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 1; i <= numLines; i++) {
			/*
			 * // saving bandwidth at the cost of computation! if(i==numLines) {
			 * buffer.append(i+" \n"); } else { buffer.append(i+"\n"); }
			 */
			buffer.append(" " + i + " \n");
		}
		return buffer.toString().intern();
	}

	// OTHER METHODS//

	public boolean getApplyTableSurround() {
		return applyTableSurround;
	}

	public void setApplyTableSurround(boolean applyTableSurround) {
		this.applyTableSurround = applyTableSurround;
	}

	public boolean getShowLineCount() {
		return showLineCount;
	}

	public void setShowLineCount(boolean showLineCount) {
		this.showLineCount = showLineCount;
	}

	/**
	 * Returns the code table's border color. For example, it could be
	 * <code>red</code> or <code>#123456</code>
	 * 
	 * @return the code table's border color.
	 */
	public String getTableBorderColor() {
		return tableBorderColor;
	}

	/**
	 * Sets the code table's border color. For example, it could be
	 * <code>red</code> or <code>#123456</code>
	 * 
	 * @param tableBorderColor
	 *            the code table's border color.
	 */
	public void setTableBorderColor(String tableBorderColor) {
		this.tableBorderColor = tableBorderColor;
	}

	/**
	 * Returns the line-number panel's background color. For example, it could
	 * be <code>white</code> or <code>#123456</code>
	 * 
	 * @return the line panel's background color.
	 */
	public String getTableLinePanelBackgroundColor() {
		return tableLinePanelBackgroundColor;
	}

	/**
	 * Sets the line-number panel's background color. For example, it could be
	 * <code>white</code> or <code>#123456</code>
	 * 
	 * @param tableLinePanelBackgroundColor
	 *            the line panel's background color.
	 */
	public void setTableLinePanelBackgroundColor(String tableLinePanelBackgroundColor) {
		this.tableLinePanelBackgroundColor = tableLinePanelBackgroundColor;
	}

	/**
	 * Returns the code panel's background color. For example, it could be
	 * <code>blue</code> or <code>#123456</code>
	 * 
	 * @return the code panel's background color.
	 */
	public String getTableCodePanelBackgroundColor() {
		return tableCodePanelBackgroundColor;
	}

	/**
	 * Sets the code panel's background color. For example, it could be
	 * <code>blue</code> or <code>#123456</code>
	 * 
	 * @param tableCodePanelBackgroundColor
	 *            the code panel's background color.
	 */
	public void setTableCodePanelBackgroundColor(String tableCodePanelBackgroundColor) {
		this.tableCodePanelBackgroundColor = tableCodePanelBackgroundColor;
	}

	public String getLineNumberColor() {
		return lineNumberColor;
	}

	public void setLineNumberColor(String lineNumberColor) {
		this.lineNumberColor = lineNumberColor;
	}

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

	/**
	 * Returns the HTML tag that starts string blocks. For example, it could be
	 * <code>&lt;font color=&quot;red&quot;&gt;</code>.
	 * 
	 * @return the HTML tag to start string blocks.
	 */
	public String getStringStart() {
		return stringStart;
	}

	/**
	 * Sets the HTML tag that starts string blocks. For example, it could be
	 * <code>&lt;font color=&quot;red&quot;&gt;</code>.
	 * 
	 * @param stringStart
	 *            the HTML tag to start string blocks.
	 */
	public void setStringStart(String stringStart) {
		this.stringStart = stringStart;
		viewer.setStringStart(stringStart);
	}

	/**
	 * Returns the HTML tag that ends string blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. The tag should correspond to the string start
	 * tag.
	 * 
	 * @return the HTML tag to end string blocks.
	 */
	public String getStringEnd() {
		return stringEnd;
	}

	/**
	 * Sets the HTML tag that ends string blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. The tag should correspond to the string end
	 * tag.
	 * 
	 * @param stringEnd
	 *            the HTML tag to end string blocks.
	 */
	public void setStringEnd(String stringEnd) {
		this.stringEnd = stringEnd;
		viewer.setStringEnd(stringEnd);
	}

	/**
	 * Returns the HTML tag that starts keyword blocks. For example, it could be
	 * <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @return the HTML tag to start keyword blocks.
	 */
	public String getReservedWordStart() {
		return reservedWordStart;
	}

	/**
	 * Sets the HTML tag that starts reserved word blocks. For example, it could
	 * be <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @param reservedWordStart
	 *            the HTML tag to start keyword blocks.
	 */
	public void setReservedWordStart(String reservedWordStart) {
		this.reservedWordStart = reservedWordStart;
		viewer.setReservedWordStart(reservedWordStart);
	}

	/**
	 * Returns the HTML tag that ends keyword blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * keyword blocks.
	 * 
	 * @return the HTML tag to end keyword blocks.
	 */
	public String getReservedWordEnd() {
		return reservedWordEnd;
	}

	/**
	 * Sets the HTML tag that ends keyword blocks. For example, it could be
	 * <code>&lt;font color=&quot;navy&quot;&gt;</code>. This should correspond
	 * to the start tag for keyword blocks.
	 * 
	 * @param reservedWordEnd
	 *            the HTML tag to end keyword blocks.
	 */
	public void setReservedWordEnd(String reservedWordEnd) {
		this.reservedWordEnd = reservedWordEnd;
		viewer.setReservedWordEnd(reservedWordEnd);
	}

	/**
	 * Returns the HTML tag that starts method blocks. For example, it could be
	 * <code>&lt;font color=&quot;brown&quot;&gt;</code>.
	 * 
	 * @return the HTML tag to start method blocks.
	 */
	public String getMethodStart() {
		return methodStart;
	}

	/**
	 * Sets the HTML tag that starts method blocks. For example, it could be
	 * <code>&lt;font color=&quot;brown&quot;&gt;</code>.
	 * 
	 * @param methodStart
	 *            the HTML tag to start method blocks.
	 */
	public void setMethodStart(String methodStart) {
		this.methodStart = methodStart;
		viewer.setMethodStart(methodStart);
	}

	/**
	 * Returns the HTML tag that ends method blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * method blocks.
	 * 
	 * @return the HTML tag to end method blocks.
	 */
	public String getMethodEnd() {
		return methodEnd;
	}

	/**
	 * Sets the HTML tag that ends method blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * method blocks.
	 * 
	 * @param methodEnd
	 *            the HTML tag to end method blocks.
	 */
	public void setMethodEnd(String methodEnd) {
		this.methodEnd = methodEnd;
		viewer.setMethodEnd(methodEnd);
	}

	/**
	 * Returns the HTML tag that starts character blocks. For example, it could
	 * be <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @return the HTML tag to start method blocks.
	 */
	public String getCharacterStart() {
		return characterStart;
	}

	/**
	 * Sets the HTML tag that starts character blocks. For example, it could be
	 * <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @param characterStart
	 *            the HTML tag to start method blocks.
	 */
	public void setCharacterStart(String characterStart) {
		this.characterStart = characterStart;
		viewer.setCharacterStart(characterStart);
	}

	/**
	 * Returns the HTML tag that ends character blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * character blocks.
	 * 
	 * @return the HTML tag to end method blocks.
	 */
	public String getCharacterEnd() {
		return characterEnd;
	}

	/**
	 * Sets the HTML tag that ends character blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * character blocks.
	 * 
	 * @param characterEnd
	 *            the HTML tag to end character blocks.
	 */
	public void setCharacterEnd(String characterEnd) {
		this.characterEnd = characterEnd;
		viewer.setCharacterEnd(characterEnd);
	}

	/**
	 * Returns the HTML tag that starts bracket blocks. For example, it could be
	 * <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @return the HTML tag to start bracket blocks.
	 */
	public String getBracketStart() {
		return bracketStart;
	}

	/**
	 * Sets the HTML tag that starts character blocks. For example, it could be
	 * <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @param bracketStart
	 *            the HTML tag to start bracket blocks.
	 */
	public void setBracketStart(String bracketStart) {
		this.bracketStart = bracketStart;
		viewer.setBracketStart(bracketStart);
	}

	/**
	 * Returns the HTML tag that ends character blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * bracket blocks.
	 * 
	 * @return the HTML tag to end bracket blocks.
	 */
	public String getBracketEnd() {
		return bracketEnd;
	}

	/**
	 * Sets the HTML tag that ends character blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * bracket blocks.
	 * 
	 * @param bracketEnd
	 *            the HTML tag to end bracket blocks.
	 */
	public void setBracketEnd(String bracketEnd) {
		this.bracketEnd = bracketEnd;
		viewer.setBracketEnd(bracketEnd);
	}

	/**
	 * Gets the string value of the string escape character
	 * 
	 * @return the string value of the string escape character
	 */
	public String getNumberStart() {
		return numberStart;
	}

	/**
	 * Sets the HTML tag that starts character blocks. For example, it could be
	 * <code>&lt;font color=&quot;navy&quot;&gt;</code>.
	 * 
	 * @param numberStart
	 *            the HTML tag to start bracket blocks.
	 */
	public void setNumberStart(String numberStart) {
		this.numberStart = numberStart;
		viewer.setNumberStart(numberStart);
	}

	/**
	 * Returns the HTML tag that ends character blocks. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * bracket blocks.
	 * 
	 * @return the HTML tag to end bracket blocks.
	 */
	public String getNumberEnd() {
		return numberEnd;
	}

	/**
	 * Sets the HTML tag that ends number literals. For example, it could be
	 * <code>&lt;/font&gt;</code>. This should correspond to the start tag for
	 * number literals.
	 * 
	 * @param numberEnd
	 *            the HTML tag to end number literals.
	 */
	public void setNumberEnd(String numberEnd) {
		this.numberEnd = numberEnd;
		viewer.setNumberEnd(numberEnd);
	}

	/**
	 * See if method filtering is enabled.
	 */
	public boolean getFilterMethod() {
		return filterMethod;
	}

	/**
	 * Enables or disables method filtering.
	 */
	public void setFilterMethod(boolean filterMethod) {
		this.filterMethod = filterMethod;
		viewer.setFilterMethod(filterMethod);
	}

	/**
	 * See if number filtering is enabled.
	 */
	public boolean getFilterNumber() {
		return filterNumber;
	}

	/**
	 * Enables or disables number filtering.
	 */
	public void setFilterNumber(boolean filterNumber) {
		this.filterNumber = filterNumber;
		viewer.setFilterNumber(filterNumber);
	}

	/**
	 * Syntax highlights Java code that appears inside [code][/code] tags
	 * 
	 * @param input
	 *            the input string that possibly contains Java code
	 * @return the input string, syntax highlighted with HTML
	 */
	private String highlightCode(String input, boolean doTable) {
		// Check if the string is null or zero length -- if so, return what was
		// sent in.
		if (input == null || input.length() == 0) {
			return input;
		} else {
			StringBuilder codeBuf = new StringBuilder(input.length());
			int i = 0;
			int j = 0;
			int oldend = 0;
			boolean doFilter = true;
			boolean noClosingTag = false;

			// This processes all [code][/code] tags inside an input string,
			// iteratively.
			// Each iteration advances the i and j pointers to point to the
			// positions of
			// the next pair of [code][/code] tags in the input, until all are
			// processed
			while ((i = input.indexOf("[code]", oldend)) >= 0) { // get the
				// first
				// unprocessed
				// start
				// position
				// Check to see where the ending code tag is and store this
				// position in j
				if ((j = input.indexOf("[/code]", i + 6)) < 0) { // get the
					// first
					// unprocessed
					// end
					// position
					if (input.length() > "[code]".length()) {
						// End at end of input if no closing tag is given
						// which is the first character after [code]
						// [code]1afadsfasdfafasfasd
						j = input.length() - 7;
						doFilter = true;
						noClosingTag = true;
					} else {
						// nothing to filter
						i = -1;
						j = i + 6;
						// j = input.length()-6;
						doFilter = false;
						noClosingTag = true;
					}
				}
				// ++++//
				if (doFilter) {
					// ++++//
					// Take the string up to the code, append the string
					// returned by JavaCodeViewer
					codeBuf.append(input.substring(oldend, i));

					// if we don't want a code table <pre> must be appended
					if (!doTable) {
						codeBuf.append("<pre>");
					}

					String temp = codeBuf.toString();
					codeBuf = new StringBuilder();

					// start looking after the initial [code] tag
					// [code]123[/code]
					String edit = "";
					// String edit = input.substring(i+6,j);
					if (noClosingTag) {
						edit = input.substring(i + 6, j + 7);
					} else {
						edit = input.substring(i + 6, j);
					}
					int editLength = edit.length();
					boolean endInNewline = true;
					boolean startWithNewline = true;

					if (editLength > 0) {
						// if the last character is not a carriage return nor a
						// newline
						if (edit.charAt(editLength - 1) != '\r' && edit.charAt(editLength - 1) != '\n') {
							endInNewline = false;
						}
						// if the first character is not a carriage return nor a
						// newline
						if (edit.charAt(0) != '\r' && edit.charAt(0) != '\n') {
							startWithNewline = false;
						}

						String currSection = "";
						if (endInNewline) {
							if (startWithNewline) {
								// get rid of initial newline and carriage
								// return
								// [code]\n\r(tokens)\n\r[/code]
								currSection = deleteCarriages(edit.substring(2));
								// codeBuf.append(temp).append(viewer.javaCodeFilter(deleteCarriages(edit.substring(2))));
								if (doTable) {
									codeBuf.append(temp).append(surroundWithTable(viewer.javaCodeFilter(currSection), countLines(currSection))); // what
									// a
									// hack
								} else {
									codeBuf.append(temp).append(viewer.javaCodeFilter(currSection));
								}
							} else {
								// filter normally
								// [code](tokens)\n\r[/code]
								currSection = deleteCarriages(edit);
								if (doTable) {
									codeBuf.append(temp).append(surroundWithTable(viewer.javaCodeFilter(currSection), countLines(currSection)));
								} else {
									codeBuf.append(temp).append(viewer.javaCodeFilter(currSection));
								}
								// codeBuf.append(temp).append(viewer.javaCodeFilter(deleteCarriages(edit)));
							}
						} else {
							if (startWithNewline) {
								// get rid of initial newline and carriage
								// return, then append an extra newline to
								// compensate
								// [code]\n\r(tokens)[/code]
								currSection = deleteCarriages(edit.substring(2) + "\n");
								if (doTable) {
									codeBuf.append(temp).append(surroundWithTable(viewer.javaCodeFilter(currSection), countLines(currSection)));
								} else {
									codeBuf.append(temp).append(viewer.javaCodeFilter(currSection));
								}
								// codeBuf.append(temp).append(viewer.javaCodeFilter(deleteCarriages(edit.substring(2)+"\n")));
							} else {
								// append an extra newline to compensate
								// [code](tokens)[/code]
								currSection = deleteCarriages(edit + "\n");
								if (doTable) {
									codeBuf.append(temp).append(surroundWithTable(viewer.javaCodeFilter(currSection), countLines(currSection)));
								} else {
									codeBuf.append(temp).append(viewer.javaCodeFilter(currSection));
								}
								// codeBuf.append(temp).append(viewer.javaCodeFilter(deleteCarriages(edit+"\n")));
							}
						}
					} else {
						codeBuf.append(temp);
					}

					// escape braces to workaround TextStyle filter
					String code = codeBuf.toString().intern();
					code = StringUtil.replace(code, "[", "&#91;");
					code = StringUtil.replace(code, "]", "&#93;");

					codeBuf = new StringBuilder().append(code);

					// if we don't want a code table <pre> must be appended
					if (!doTable) {
						codeBuf.append("</pre>");
					}

					// Next time, start looking after the ending [/code] tag
					oldend = j + 7;
					// the newline filter which is applied before this one
					// substitutes <BR> for \n\r or \n, so check to see
					// if a <BR> is immediately after [/code]
					if (input.length() >= oldend + 3) {
						// found one, so advance pointer ahead to ignore the
						// <BR>
						if (input.charAt(oldend) == '<' && input.charAt(oldend + 1) == 'B' && input.charAt(oldend + 2) == 'R'
								&& input.charAt(oldend + 3) == '>') {
							// input =
							// input.substring(0,oldend)+StringUtils.replace(input.substring(oldend,oldend+1),
							// "\n", " ")+input.substring(oldend+1);
							// if(input.charAt(oldend+1)=='\n' ||
							// input.charAt(oldend+1)=='\r') {
							// }
							oldend = oldend + 4;
						}
					}
				}
				oldend = j + 7;
			}
			if (doFilter) {
				codeBuf.append(input.substring(oldend, input.length()));
			}
			return codeBuf.toString().intern();
		}
	}

	private final int countLines(String text) {
		int lineCount = 0;
		BufferedReader reader = new BufferedReader(new StringReader(text));
		try {
			while (reader.readLine() != null) {
				lineCount++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return lineCount;
	}

	private final String deleteCarriages(String line) {
		char curr_char;
		int i = 0;

		// lineCount = 0; // reset the line count
		StringBuilder buf = new StringBuilder();
		while (i < line.length()) {
			curr_char = line.charAt(i);
			if (curr_char == '\r') { // waste some cpu cycles
				;
			}
			/*
			 * // for printing line numbers else if(curr_char == '\n') {
			 * buf.append(curr_char); lineCount++; }
			 */
			else {
				buf.append(curr_char);
			}
			i++;
		}
		return buf.toString().intern();
	}

	public JavaCodeViewer getViewer() {
		return viewer;
	}

	public void setViewer(JavaCodeViewer viewer) {
		this.viewer = viewer;
	}

}
