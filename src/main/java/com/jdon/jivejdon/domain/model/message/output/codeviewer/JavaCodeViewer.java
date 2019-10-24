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
package com.jdon.jivejdon.domain.model.message.output.codeviewer;

import java.util.HashMap;

/**
 * Never used
 * 
 * A class that syntax highlights Java code into html.
 * <p>
 * A CodeViewer object is used to syntax highlight Java code. To make use of
 * this class, first set up how tokens should be highlighted by invoking the
 * getXXX and setXXX methods, then pass in "chunks" of relevent input to be
 * filtered. Typically this will mean the section(s) of a form input that has
 * been tagged with [code][/code] tags.
 */
public class JavaCodeViewer {
	private static HashMap reservedWords = new HashMap(150); // >= Java2 only
																// (also, not
																// thread-safe)
	private char[] backgroundColor = "#ffffff".toCharArray();
	private char[] commentStart = "<font color=\"darkgreen\">".toCharArray();
	private char[] commentEnd = "</font>".toCharArray();
	private char[] stringStart = "<font color=\"red\">".toCharArray();
	private char[] stringEnd = "</font>".toCharArray();
	private char[] reservedWordStart = "<font color=\"navy\"><b>".toCharArray();
	private char[] reservedWordEnd = "</b></font>".toCharArray();
	private char[] methodStart = "<font color=\"brown\">".toCharArray();
	private char[] methodEnd = "</font>".toCharArray();
	private char[] characterStart = "<font color=\"navy\">".toCharArray();
	private char[] characterEnd = "</font>".toCharArray();
	private char[] bracketStart = "<font color=\"navy\">".toCharArray();
	private char[] bracketEnd = "</font>".toCharArray();
	private char[] numberStart = "<font color=\"red\">".toCharArray();
	private char[] numberEnd = "</font>".toCharArray();
	private char[] nbsp = "&nbsp;".toCharArray();
	private char stringEscape = '\\';
	private char characterEscape = '\\';
	private boolean filterMethod = false;
	private boolean filterNumber = false;

	/**
	 * Load all keywords at class loading time.
	 */
	static {
		loadKeywords();
	}

	/**
	 * Gets the html for the start of a comment block.
	 */
	public String getCommentStart() {
		return String.valueOf(commentStart);
	}

	/**
	 * Sets the html for the start of a comment block.
	 */
	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a comment block.
	 */
	public String getCommentEnd() {
		return String.valueOf(commentEnd);
	}

	/**
	 * Sets the html for the end of a comment block.
	 */
	public void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a String.
	 */
	public String getStringStart() {
		return String.valueOf(stringStart);
	}

	/**
	 * Sets the html for the start of a String.
	 */
	public void setStringStart(String stringStart) {
		this.stringStart = stringStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a String.
	 */
	public String getStringEnd() {
		return String.valueOf(stringEnd);
	}

	/**
	 * Sets the html for the end of a String.
	 */
	public void setStringEnd(String stringEnd) {
		this.stringEnd = stringEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a reserved word.
	 */
	public String getReservedWordStart() {
		return String.valueOf(reservedWordStart);
	}

	/**
	 * Sets the html for the start of a reserved word.
	 */
	public void setReservedWordStart(String reservedWordStart) {
		this.reservedWordStart = reservedWordStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a reserved word.
	 */
	public String getReservedWordEnd() {
		return String.valueOf(reservedWordEnd);
	}

	/**
	 * Sets the html for the end of a reserved word.
	 */
	public void setReservedWordEnd(String reservedWordEnd) {
		this.reservedWordEnd = reservedWordEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a method.
	 */
	public String getMethodStart() {
		return String.valueOf(methodStart);
	}

	/**
	 * Sets the html for the start of a method.
	 */
	public void setMethodStart(String methodStart) {
		this.methodStart = methodStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a method.
	 */
	public String getMethodEnd() {
		return String.valueOf(methodEnd);
	}

	/**
	 * Sets the html for the end of a method.
	 */
	public void setMethodEnd(String methodEnd) {
		this.methodEnd = methodEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a character.
	 */
	public String getCharacterStart() {
		return String.valueOf(characterStart);
	}

	/**
	 * Sets the html for the start of a character.
	 */
	public void setCharacterStart(String characterStart) {
		this.characterStart = characterStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a character.
	 */
	public String getCharacterEnd() {
		return String.valueOf(characterEnd);
	}

	/**
	 * Sets the html for the end of a character.
	 */
	public void setCharacterEnd(String characterEnd) {
		this.characterEnd = characterEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a bracket.
	 */
	public String getBracketStart() {
		return String.valueOf(bracketStart);
	}

	/**
	 * Sets the html for the start of a bracket.
	 */
	public void setBracketStart(String bracketStart) {
		this.bracketStart = bracketStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a bracket.
	 */
	public String getBracketEnd() {
		return String.valueOf(bracketEnd);
	}

	/**
	 * Sets the html for the end of a bracket.
	 */
	public void setBracketEnd(String bracketEnd) {
		this.bracketEnd = bracketEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a number
	 */
	public String getNumberStart() {
		return String.valueOf(numberStart);
	}

	/**
	 * Sets the html for the start of a number.
	 */
	public void setNumberStart(String numberStart) {
		this.numberStart = numberStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a number.
	 */
	public String getNumberEnd() {
		return String.valueOf(numberEnd);
	}

	/**
	 * Sets the html for the end of a number.
	 */
	public void setNumberEnd(String numberEnd) {
		this.numberEnd = numberEnd.toCharArray();
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
	}

	/**
	 * Syntax highlights any java code in the input. In working with this method
	 * it is helpful to know how lexers work in general.
	 * <p>
	 * The overall strategy is as follows: The input is processed a character at
	 * a time, accompanied by a state update. When a valid Java token is
	 * detected, such as a keyword, a string, a comment block, etc, said token
	 * gets "wrapped" by htmlStart and htmlEnd tags. Not everything is
	 * implemented according to the Java Language Specifications. For example,
	 * the length of a valid number is left unchecked.
	 * <p>
	 * 
	 * @param input
	 *            string possibly containing Java code
	 * @return the output string containing html formatted Java code
	 */
	public final String javaCodeFilter(String line) {
		final int ENTRY = 0;
		final int INTERIM = 1;
		final int ACCEPT = 2;
		final int IGNORE_BEGIN = 3;
		final int INLINE_IGNORE = 4;
		final int MULTILINE_IGNORE = 5;
		final int MULTILINE_EXIT = 6;
		final int STRING_ENTRY = 7;
		final int CHARACTER_ENTRY = 8;
		final int NEWLINE_ENTRY = 9;
		final int NUMBER_BIN_INT_FLOAT_OCTAL = 10;
		final int NUMBER_HEX_BEGIN = 11;
		final int NUMBER_HEX_REST = 12;
		int state = ENTRY;
		char[] char_line;
		char curr_char;
		String token;
		StringBuilder buffer;

		if (line == null || line.equals("")) {
			return "";
		} else {
			char_line = line.toCharArray();
			buffer = new StringBuilder(char_line.length);
		}

		int i = 0;
		int head_idx = 0;
		int tail_idx = 0;

		while (i < char_line.length) {
			curr_char = char_line[i];
			switch (state) {
			case ENTRY:
				if (Character.isJavaIdentifierPart(curr_char)) { // keyword, id,
																	// number
																	// literal
					head_idx = i;
					if (Character.isDigit(curr_char)) {
						if (curr_char == '0') {
							state = NUMBER_HEX_BEGIN;
						} else {
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
						}
					} else {
						state = INTERIM;
					}
				} else if (curr_char == '+' || curr_char == '-') { // number
																	// literals
					if (i < char_line.length - 1) {
						if (!Character.isDigit(char_line[i + 1])) { // +0x43 <--
																	// this
																	// cannot be
																	// hex
							buffer.append(curr_char);
							state = ENTRY;
						} else {
							head_idx = i;
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
						}
					} else {
						buffer.append(curr_char);
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					}
				} else if (curr_char == '/') { // comment
					head_idx = i;
					state = IGNORE_BEGIN;
				} else if (curr_char == '\"') { // string
					head_idx = i;
					state = STRING_ENTRY;
				} else if (curr_char == '\'') { // character
					head_idx = i;
					state = CHARACTER_ENTRY;
				} else if (curr_char == '{' || curr_char == '}') { // scope
																	// bracket
					buffer.append(bracketStart);
					buffer.append(curr_char);
					buffer.append(bracketEnd);
					state = ACCEPT;
				} else if (curr_char == '\n') { // in case of multiple newlines
					buffer.append(curr_char);
					state = NEWLINE_ENTRY;
				} else { // space, =, >, <, \t, white_space, etc
					buffer.append(curr_char);
					state = ENTRY;
				}
				break;
			case NUMBER_BIN_INT_FLOAT_OCTAL:
				if (Character.isJavaIdentifierPart(curr_char)) {
					// 1f 1xf 67.6 1. -1 +2 0l
					if (Character.isDigit(curr_char)) {
						// 87 10 12 00-0L
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					} else if (curr_char == 'l' || curr_char == 'L') { // cheating...
																		// +l +L
						tail_idx = i;
						if (filterNumber) {
							buffer.append(numberStart);
						}
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						if (filterNumber) {
							buffer.append(numberEnd);
						}
						state = ACCEPT;
					} else {
						// -E +f 5e 1x 34234x 7979/7897 79+897 890-7989
						if (char_line[i - 1] == '-' || char_line[i - 1] == '+') {
							// -E +f -l -0l
							tail_idx = i;
							buffer.append(char_line, head_idx, (tail_idx - head_idx));
							state = ACCEPT;
						} else { // must be numbers infront
							// 5e 1x 34234x 2342static 243} 243;
							tail_idx = i;
							if (filterNumber) {
								buffer.append(numberStart);
							}
							buffer.append(char_line, head_idx, (tail_idx - head_idx));
							if (filterNumber) {
								buffer.append(numberEnd);
							}
							state = ACCEPT;
							i--;
						}
					}
				} else {
					// 12324\ 23423\n 23432. 2432\r 2423\t 2421{ 423: 2342~ 242&
					// 6868- 768+
					tail_idx = i;
					if (filterNumber) {
						buffer.append(numberStart);
					}
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					if (filterNumber) {
						buffer.append(numberEnd);
					}
					state = ACCEPT;
					i--;
				}
				break;
			case NUMBER_HEX_BEGIN:
				if (Character.isJavaIdentifierPart(curr_char)) {
					// 0X 0B 05 0i 0x 00x3 0x0x0 0x00x 0x0005fg
					if (curr_char == 'x') { // this accounts for proper case
						// 0xn 0xf 0xl
						state = NUMBER_HEX_REST;
					} else if (Character.isDigit(curr_char)) {
						// 0000.0 05 078799
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					} else { // upper case letters, lower case minus 'x'
						// 0X 0b 0static 0case
						tail_idx = i;
						if (filterNumber) {
							buffer.append(numberStart);
						}
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						if (filterNumber) {
							buffer.append(numberEnd);
						}
						state = ACCEPT;
						i--;
					}
				} else {
					// 0. 0\n 0\t 0\r 0' '
					tail_idx = i;
					if (filterNumber) {
						buffer.append(numberStart);
					}
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					if (filterNumber) {
						buffer.append(numberEnd);
					}
					state = ACCEPT;
					i--;
				}
				break;
			case NUMBER_HEX_REST:
				if (Character.isDigit(curr_char)) {
					state = NUMBER_HEX_REST;
				} else if (((int) curr_char >= 97 && (int) curr_char <= 102) || ((int) curr_char >= 65 && (int) curr_char <= 70)) {
					// else if (curr_char == 'a' || curr_char == 'b' ||
					// curr_char == 'c' || curr_char == 'd' || curr_char == 'e'
					// || curr_char == 'f' || curr_char == 'A' || curr_char ==
					// 'B' || curr_char == 'C' || curr_char == 'D' || curr_char
					// == 'E' || curr_char == 'F') {
					// 0x0 -> 0xf 0xFEef
					state = NUMBER_HEX_REST;
				} else if (curr_char == 'l' || curr_char == 'L') {
					tail_idx = i;
					if (filterNumber) {
						buffer.append(numberStart);
					}
					buffer.append(char_line, head_idx, (tail_idx - head_idx + 1));
					if (filterNumber) {
						buffer.append(numberEnd);
					}
					state = ACCEPT;
				} else {
					// 0xg 0x~ 0x\n 0x\t 0x{
					tail_idx = i;
					if (filterNumber) {
						buffer.append(numberStart);
					}
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					if (filterNumber) {
						buffer.append(numberEnd);
					}
					state = ACCEPT;
					i--;
				}
				break;
			// this is only for iterating through a sequence of \n's until we
			// read something else
			case NEWLINE_ENTRY: // to be in here, we've got to have read at
								// least 1 newline already
				if (curr_char == '\n') { // definitely have at least 2
											// consecutive newlines now
					// just keep on adding &nbsp;'s until we read something else
					buffer.append(nbsp);
					buffer.append(curr_char);
					state = NEWLINE_ENTRY;
				} else { // anything else we process next time
					state = ACCEPT;
					i--;
				}
				break;
			case STRING_ENTRY: // tabs are caught also
				if (curr_char != '\"' && curr_char != '\n' && curr_char != '\r') {
					state = STRING_ENTRY; // still reading a string
				} else if (curr_char == '\"') {
					if (char_line[i - 1] != stringEscape) {
						tail_idx = i;
						buffer.append(stringStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx + 1));
						buffer.append(stringEnd);
						state = ACCEPT;
					} else { // escape this character's usual meaning
						state = STRING_ENTRY;
					}
				} else if (curr_char == '\n') { // chop it off at previous char
					tail_idx = i;
					buffer.append(stringStart);
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					buffer.append(stringEnd);
					state = ACCEPT;
					i--;
				} else if (curr_char == '\r') { // shouldn't happen, as carriage
												// returns were erased
					// but ignore it just in case
					tail_idx = i;
					buffer.append(stringStart);
					buffer.append(char_line, head_idx, (tail_idx - head_idx + 1));
					buffer.append(stringEnd);
					state = ACCEPT;
					i--;
				} else { // what did we miss
					;
				}
				break;
			case CHARACTER_ENTRY:
				if (curr_char != '\'' && curr_char != '\n' && curr_char != '\r') { // tabs
																					// are
																					// caught
																					// also
					state = CHARACTER_ENTRY; // still reading a string
				} else if (curr_char == '\'') {
					if (char_line[i - 1] != characterEscape) {
						tail_idx = i;
						buffer.append(characterStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx + 1));
						buffer.append(characterEnd);
						state = ACCEPT;
					} else { // escape this character's usual meaning
						state = CHARACTER_ENTRY;
					}
				} else if (curr_char == '\n') {
					tail_idx = i;
					buffer.append(characterStart);
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					buffer.append(characterEnd);
					state = ACCEPT;
					i--;
				} else if (curr_char == '\r') { // shouldn't happen, as carriage
												// returns were erased
					tail_idx = i;
					buffer.append(characterStart);
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					buffer.append(characterEnd);
					state = ACCEPT;
					i--;
				} else { // what did we miss
					; // stay in character_entry
				}
				break;
			case INTERIM:
				if (Character.isJavaIdentifierPart(curr_char) || curr_char == '.') {
					state = INTERIM;
				} else if (curr_char == '(') {
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) { // keyword
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
					} else { // method
						if (filterMethod) {
							buffer.append(methodStart);
						}
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						if (filterMethod) {
							buffer.append(methodEnd);
						}
					}
					buffer.append(curr_char);
					state = ACCEPT;
				} else if (curr_char == ')') {
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) { // keyword
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						buffer.append(curr_char);
						state = ACCEPT;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(curr_char);
						state = ENTRY;
					}
				} else if (curr_char == ':') {
					// eg: default: etc
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						buffer.append(curr_char);
						state = ACCEPT;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(curr_char);
						state = ENTRY;
					}
				} else if (curr_char == ';') {
					// eg: break; return; etc
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						buffer.append(curr_char);
						state = ACCEPT;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(curr_char);
						state = ENTRY;
					}
				} else if (curr_char == '[' || curr_char == ']') {
					// eg: char[], etc
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						buffer.append(curr_char);
						state = ACCEPT;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(curr_char);
						state = ENTRY;
					}
				} else if (curr_char == '{' || curr_char == '}') {
					// eg: static{, etc
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						state = ACCEPT;
						i--;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						state = ENTRY;
						i--;
					}
				} else if (curr_char == '/') { // should check for keyword
					tail_idx = i;
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					state = ENTRY;
					i--;
				} else if (curr_char == '\"') { // should check for keyword
					tail_idx = i;
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					state = ENTRY;
					i--;
					// head_idx = i;
					// state = string_entry; // should go to accept and not
					// assign head_idx
				} else if (curr_char == '\'') { // should check for keyword
					tail_idx = i;
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					state = ENTRY;
					i--;
					// head_idx = i;
					// state = character_entry; // should go to accept and not
					// assign head_idx
				} else if (curr_char == ' ') {
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						state = ACCEPT;
						i--;
					} else { // can also do some highlighing in here, seeing as
								// we have an identifier
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						state = ENTRY;
						i--;
					}
				} else if (curr_char == '\n') { // possible multiple newlines
					// check if a keyword is matched
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						state = ACCEPT;
						i--;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						state = ACCEPT;
						i--;
					}
				} else if (curr_char == '\t') { // this recognizes keyword\t
					// eg: else\t...
					tail_idx = i;
					token = new String(char_line, head_idx, (tail_idx - head_idx));
					if (reservedWords.containsKey(token)) {
						buffer.append(reservedWordStart);
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						buffer.append(reservedWordEnd);
						state = ACCEPT;
						i--;
					} else {
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						state = ACCEPT;
						i--;
					}
				} else { // so int+, int;, public+, etc, are ignored :)
					tail_idx = i;
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					state = ENTRY;
					i--;
				}
				break;
			case ACCEPT:
				if (Character.isJavaIdentifierPart(curr_char)) { // keyword, id,
																	// num
																	// literal
					head_idx = i;
					if (Character.isDigit(curr_char)) {
						if (curr_char == '0') {
							state = NUMBER_HEX_BEGIN;
						} else {
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
						}
					} else {
						state = INTERIM;
					}
				} else if (curr_char == '+' || curr_char == '-') { // number
																	// literals
					if (i < char_line.length - 1) {
						if (!Character.isDigit(char_line[i + 1])) { // +0x43 <--
																	// this
																	// cannot be
																	// hex
							buffer.append(curr_char);
							state = ENTRY;
						} else {
							head_idx = i;
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
						}
					} else {
						buffer.append(curr_char);
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					}
				} else if (curr_char == '/') {
					head_idx = i;
					state = IGNORE_BEGIN;
				} else if (curr_char == '\"') {
					head_idx = i;
					state = STRING_ENTRY;
				} else if (curr_char == '{' || curr_char == '}') {
					buffer.append(bracketStart);
					buffer.append(curr_char);
					buffer.append(bracketEnd);
					state = ACCEPT;
				} else {
					state = ENTRY;
					i--;
				}
				break;
			case IGNORE_BEGIN:
				if (curr_char == '/') {
					state = INLINE_IGNORE;
				} else if (curr_char == '*') {
					state = MULTILINE_IGNORE;
				} else if (Character.isJavaIdentifierPart(curr_char)) {
					head_idx = i;
					if (Character.isDigit(curr_char)) {
						buffer.append('/');
						if (curr_char == '0') {
							state = NUMBER_HEX_BEGIN;
							i--;
						} else {
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
							i--;
						}
					} else {
						buffer.append('/');
						state = ENTRY;
						i--;
					}
				} else if (curr_char == '\n') {
					buffer.append('/');
					state = NEWLINE_ENTRY;
					i--;
				} else { // space, \t, etc
					buffer.append('/');
					state = ENTRY;
					i--;
				}
				break;
			case INLINE_IGNORE:
				if (curr_char != '\n') {
					state = INLINE_IGNORE;
				} else if (curr_char == '\n') {
					tail_idx = i;
					buffer.append(commentStart);
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					buffer.append(commentEnd);
					i--;
					state = ACCEPT;
				} else { // what did we miss
					;
				}
				break;
			case MULTILINE_IGNORE:
				if (curr_char == '*') {
					state = MULTILINE_EXIT;
				} else {
					state = MULTILINE_IGNORE;
				}
				break;
			case MULTILINE_EXIT:
				if (curr_char == '/') {
					tail_idx = i;
					buffer.append(commentStart);
					buffer.append(char_line, head_idx, (tail_idx - head_idx + 1));
					buffer.append(commentEnd);
					state = ACCEPT;
				} else if (curr_char == '*') {
					state = MULTILINE_EXIT;
				} else {
					state = MULTILINE_IGNORE;
				}
				break;
			default:
				break;
			}
			i++;
		}
		return buffer.toString().intern();
	}

	public static HashMap getReservedWords() {
		return reservedWords;
	}

	public static void setReservedWords(HashMap reservedWords) {
		JavaCodeViewer.reservedWords = reservedWords;
	}

	public char[] getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(char[] backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public char getCharacterEscape() {
		return characterEscape;
	}

	public void setCharacterEscape(char characterEscape) {
		this.characterEscape = characterEscape;
	}

	public char[] getNbsp() {
		return nbsp;
	}

	public void setNbsp(char[] nbsp) {
		this.nbsp = nbsp;
	}

	public char getStringEscape() {
		return stringEscape;
	}

	public void setStringEscape(char stringEscape) {
		this.stringEscape = stringEscape;
	}

	public void setBracketEnd(char[] bracketEnd) {
		this.bracketEnd = bracketEnd;
	}

	public void setBracketStart(char[] bracketStart) {
		this.bracketStart = bracketStart;
	}

	public void setCharacterEnd(char[] characterEnd) {
		this.characterEnd = characterEnd;
	}

	public void setCharacterStart(char[] characterStart) {
		this.characterStart = characterStart;
	}

	public void setCommentEnd(char[] commentEnd) {
		this.commentEnd = commentEnd;
	}

	public void setCommentStart(char[] commentStart) {
		this.commentStart = commentStart;
	}

	public void setMethodEnd(char[] methodEnd) {
		this.methodEnd = methodEnd;
	}

	public void setMethodStart(char[] methodStart) {
		this.methodStart = methodStart;
	}

	public void setNumberEnd(char[] numberEnd) {
		this.numberEnd = numberEnd;
	}

	public void setNumberStart(char[] numberStart) {
		this.numberStart = numberStart;
	}

	public void setReservedWordEnd(char[] reservedWordEnd) {
		this.reservedWordEnd = reservedWordEnd;
	}

	public void setReservedWordStart(char[] reservedWordStart) {
		this.reservedWordStart = reservedWordStart;
	}

	public void setStringEnd(char[] stringEnd) {
		this.stringEnd = stringEnd;
	}

	public void setStringStart(char[] stringStart) {
		this.stringStart = stringStart;
	}

	/*
	 * Load Hashtable (or HashMap) with Java reserved words. Improved list in
	 * version 1.1 taken directly from Java language spec.
	 */
	private static void loadKeywords() {
		reservedWords.put("abstract", "abstract");
		reservedWords.put("boolean", "boolean");
		reservedWords.put("break", "break");
		reservedWords.put("byte", "byte");
		reservedWords.put("case", "case");
		reservedWords.put("catch", "catch");
		reservedWords.put("char", "char");
		reservedWords.put("class", "class");
		reservedWords.put("const", "const");
		reservedWords.put("continue", "continue");
		reservedWords.put("default", "default");
		reservedWords.put("do", "do");
		reservedWords.put("double", "double");
		reservedWords.put("else", "else");
		reservedWords.put("extends", "extends");
		reservedWords.put("false", "false");
		reservedWords.put("final", "final");
		reservedWords.put("finally", "finally");
		reservedWords.put("float", "float");
		reservedWords.put("for", "for");
		reservedWords.put("goto", "goto");
		reservedWords.put("if", "if");
		reservedWords.put("implements", "implements");
		reservedWords.put("import", "import");
		reservedWords.put("instanceof", "instanceof");
		reservedWords.put("int", "int");
		reservedWords.put("interface", "interface");
		reservedWords.put("long", "long");
		reservedWords.put("native", "native");
		reservedWords.put("new", "new");
		reservedWords.put("package", "package");
		reservedWords.put("private", "private");
		reservedWords.put("protected", "protected");
		reservedWords.put("public", "public");
		reservedWords.put("return", "return");
		reservedWords.put("short", "short");
		reservedWords.put("static", "static");
		reservedWords.put("strictfp", "strictfp");
		reservedWords.put("super", "super");
		reservedWords.put("switch", "switch");
		reservedWords.put("synchronized", "synchronized");
		reservedWords.put("this", "this");
		reservedWords.put("throw", "throw");
		reservedWords.put("throws", "throws");
		reservedWords.put("transient", "transient");
		reservedWords.put("true", "true");
		reservedWords.put("try", "try");
		reservedWords.put("void", "void");
		reservedWords.put("volatile", "volatile");
		reservedWords.put("while", "while");
	}
}
