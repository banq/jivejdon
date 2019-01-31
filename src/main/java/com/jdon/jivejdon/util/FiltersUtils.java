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
package com.jdon.jivejdon.util;

import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 *
 */
public class FiltersUtils {
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	public static String getPropertyHTML(Function<MessageVO, MessageVO> filter, PropertyDescriptor
			descriptor) {
		// HTML of the customizer for this property
		StringBuilder html = new StringBuilder(50);
		// Get the name of the property (this becomes the name of the form
		// element)
		String propName = descriptor.getName();
		// Get the current value of the property
		Object propValue = null;
		try {
			Method methodc = descriptor.getReadMethod();
			// Decode the property value using the property type and
			// encoded String value.
			Object[] args = null;
			propValue = methodc.invoke(filter, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get the classname of this property
		String className = descriptor.getPropertyType().getName();

		// HTML form elements for number values (rendered as small textfields)
		if ("int".equals(className) || "double".equals(className) || "long".equals(className)) {
			html.append("<input type=\"text\" name=\"").append(propName).append("\" size=\"6\" maxlength=\"10\"");
			if (propValue != null) {
				html.append(" value=\"").append(propValue.toString()).append("\"");
			}
			html.append(">");
		}
		// HTML form elements for boolean values (rendered as Yes/No radio
		// buttons)
		else if ("boolean".equals(className)) {
			boolean value = false;
			if ("true".equals(propValue.toString())) {
				value = true;
			}
			html.append("<input type=\"radio\" name=\"").append(propName).append("\" id=\"rb").append(propName).append("1\" ");
			html.append("value=\"true\"");
			html.append((value) ? " checked" : "");
			html.append("> <label for=\"rb").append(propName).append("1\">Yes</label> ");
			html.append("<input type=\"radio\" name=\"").append(propName).append("\" id=\"rb").append(propName).append("2\" ");
			html.append("value=\"false\"");
			html.append((!value) ? " checked" : "");
			html.append("> <label for=\"rb").append(propName).append("2\">No</label> ");
		}
		// HTML elements for a String (rendered as a wide textfield)
		else if ("java.lang.String".equals(className)) {
			if (propName.toLowerCase().equals("password")) {
				html.append("<input type=\"password\"");
			} else {
				html.append("<input type=\"text\"");
			}
			html.append(" name=\"").append(propName).append("\" size=\"30\" maxlength=\"150\"");
			if (propValue != null) {
				html.append(" value=\"").append(escapeHTML(propValue.toString())).append("\"");
			}
			html.append(">");
		}
		if (html.length() == 0) {
			html.append("&nbsp;");
		}
		return html.toString();
	}

	private static String escapeHTML(String html) {
		html = StringUtil.replace(html, "\"", "&quot;");
		return FiltersUtils.escapeHTMLTags(html);
	}

	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuilder out = new StringBuilder((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}
}
