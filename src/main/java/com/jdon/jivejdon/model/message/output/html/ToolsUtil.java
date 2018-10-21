package com.jdon.jivejdon.model.message.output.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolsUtil {


	/**
	 * replace String [list] [/ist]  or [XX] [/XX] or XXX YYY
	 *
	 * @param input
	 * @param parentTagRegx [list] [/ist]  or [XX] [/XX] or XXX YYY
	 * @param parentHtml
	 * @param childTagRex
	 * @param childHtml
	 * @return
	 */
	public static String convertTags(String input, String parentTagRegx, String parentHtml, String
			childTagRex, String childHtml) {
		// Check if the string is null or zero length -- if so, return what was
		// sent in.
		if (input == null || input.length() == 0)
			return input;

		Matcher matcher = Pattern.compile(parentTagRegx, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
				.matcher(input);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			input = convertTagsFirst(input, parentTagRegx, parentHtml, childTagRex, childHtml);
		}

		return input;
	}

	public static String convertTagsFirst(String input, String parentTagRegx, String parentHtml,
										  String
												  childTagRex, String childHtml) {
		// Check if the string is null or zero length -- if so, return what was
		// sent in.
		if (input == null || input.length() == 0)
			return input;

		Matcher matcher = Pattern.compile(parentTagRegx, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
				.matcher(input);
		StringBuilder sb = new StringBuilder();
		if (matcher.find()) {
			//remove \n ...
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(matcher.group(1));
			String cleanS = m.replaceAll("");

			if (parentHtml.length() != 0)
				sb.append("<" + parentHtml + ">");
			String childhtmlBegin = "";
			String childhtmlEnd = "";
			if (childHtml.length() != 0) {
				childhtmlBegin = "<" + childHtml + ">";
				childhtmlEnd = "</" + childHtml + ">";
			}
			String[] sss = cleanS.split(childTagRex);
			for (String s : sss) {
				if (s != null && s.length() != 0) {
					sb.append(childhtmlBegin).append(s).append(childhtmlEnd);
				}
			}
			if (parentHtml.length() != 0)
				sb.append("</" + parentHtml + ">");

		}

		input = matcher.replaceFirst(sb.toString());

		return input;
	}

	public static String convertTags(String input, String parentTagRegx, String phtml) {
		// Check if the string is null or zero length -- if so, return what was
		// sent in.
		if (input == null || input.length() == 0)
			return input;

		Matcher matcher = Pattern.compile(parentTagRegx, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
				.matcher(input);
		input = matcher.replaceAll(phtml);

		return input;
	}
}
