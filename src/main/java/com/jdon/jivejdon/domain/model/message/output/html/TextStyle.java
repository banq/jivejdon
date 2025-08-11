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
package com.jdon.jivejdon.domain.model.message.output.html;

import java.util.function.Function;

import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * A ForumMessageFilter that replaces [b][/b] and [i][/i] tags with their HTML
 * tag equivalents. This filter should only be run after any HTML stripping
 * filters are.
 */
public class TextStyle implements Function<MessageVO, MessageVO> {
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
	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(convertTags(messageVO.getSubject())).body(convertTags(messageVO.getBody()))
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
	 *                    toggles translation of [b][/b] tags.
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
	 *                      toggles translation of [i][/i] tags.
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
	 *                         toggles translation of [u][/u] tags.
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
	 *                         toggles translation of [pre][/pre] tags.
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
	 *                         toggle value for filtering on subject.
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
	 *                      toggle value for filtering on body.
	 */
	public void setFilteringBody(boolean filteringBody) {
		this.filteringBody = filteringBody;
	}

	/**
	 * This method takes a string which may contain [b][/b] and [i][/i] tags and
	 * converts them to their HTML equivalents.
	 *
	 * @param input
	 *              the text to be converted.
	 * @return the input string with the [b][/b] and [i][/i] tags converted to
	 *         their HTML equivalents.
	 */
	private String convertTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        // 先处理 HTML 包裹的 Markdown 列表块
		input = wrapMarkdownListWithUl(input);
        // 其余内容不再全局替换 <p class="indent"> 和 <br>
        input = input.replaceAll("^\\s*\\n+", "");
        input = input.replaceAll("\\n+$", "");

        // Markdown 分隔线 ---，前面有 >，后面有 < 或空格，转为 <hr>
		input = input.replaceAll("(>\\s*)-{3,}(?=\\s|<)", "$1<hr>");

        // Markdown 标题 #、##、### 转为 <h1>、<h2>、<h3>
		// 匹配1~3个#，后跟一个空格，标题内容（不允许空格），内容后面必须是一个空格或"<"（即HTML标签起始）
		input = input.replaceAll("(#{1,6})(?:\\s|\\u00A0|&nbsp;)+([^\\s<]+)(?= |<|$)", "<strong>$2</strong>");
        input = input.replaceAll("\\*\\*([\\s\\S]+?)\\*\\*", "<strong>$1</strong>");
        input = input.replaceAll("__(.+?)__", "<strong>$1</strong>");
      
        // Markdown 删除线 ~~XXX~~ 转为 <del>XXX</del>
        input = input.replaceAll("~~(.+?)~~", "<del>$1</del>");
        
		// Markdown 行内代码块 ```XXX``` 转为 <code>XXX</code>
		input = input.replaceAll("```([^`]+?)```", "<pre class=\"displaycode\"><code>$1</code></pre>");
		// Markdown 行内代码 `XXX` 转为 <code>XXX</code>
		input = input.replaceAll("`([^`]+?)`", "<code>$1</code>");

        // Markdown 链接 [text](url)
        input = input.replaceAll("\\[(.+?)\\]\\((.+?)\\)", "<a href=\"$2\">$1</a>");
        // Markdown 无序列表 - xxx 或 * xxx
        input = input.replaceAll("(?m)^[\\*-] (.+)$", "<li>$1</li>");
        // Markdown 有序列表 1. xxx
       // input = input.replaceAll("(?m)^\\d+\\. (.+)$", "<li>$1</li>");
        // Markdown 块引用 > xxx
        //input = input.replaceAll("(?m)^> (.+)$", "<blockquote>$1</blockquote>");

        // BBCode 处理
        int[] boldStartCount = new int[1];
        int[] italicsStartCount = new int[1];
        int[] boldEndCount = new int[1];
        int[] italicsEndCount = new int[1];
        int[] underlineStartCount = new int[1];
        int[] underlineEndCount = new int[1];
        int[] preformatStartCount = new int[1];
        int[] preformatEndCount = new int[1];
        if (boldEnabled) {
            input = replaceIgnoreCase(input, "[b]", "<strong>", boldStartCount);
            input = replaceIgnoreCase(input, "[/b]", "</strong>", boldEndCount);
            int bStartCount = boldStartCount[0];
            int bEndCount = boldEndCount[0];
            while (bStartCount > bEndCount) {
                input = input.concat("</strong>");
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
        return input;
    }

    /**
     * 只处理 <p class="indent">* ...</p> 这种块，将其转为 <ul><li>...</li></ul>
     */
    private String processHtmlWrappedMarkdownList(String input) {
        if (input == null || input.length() == 0) return input;
        StringBuffer sb = new StringBuffer();
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
            "<p class=\\\"indent\\\">([\\s\\S]*?)</p>", java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher m = p.matcher(input);
        int lastEnd = 0;
        while (m.find()) {
            sb.append(input.substring(lastEnd, m.start()));
            String inner = m.group(1);
          
            String[] lines = inner.split("\n");
            boolean allList = true;
            int listCount = 0;
            for (String line : lines) {
                String t = line.trim();
                if (t.startsWith("* ") || t.startsWith("- ")) {
                    listCount++;
                } else if (!t.isEmpty()) {
                    allList = false;
                    break;
                }
            }
            // 至少一项且全部为列表才包ul，否则原样输出
            if (allList && listCount >= 1) {
                StringBuilder ul = new StringBuilder("<ul>");
                for (String line : lines) {
                    String t = line.trim();
                    if (t.startsWith("* ") || t.startsWith("- ")) {
                        ul.append("<li>").append(t.substring(2).trim()).append("</li>");
                    }
                }
                ul.append("</ul>");
                sb.append(ul.toString());
            } else {
                // 非列表块，原样输出
                sb.append("<p class=\"indent\">").append(inner).append("</p>");
            }
            lastEnd = m.end();
        }
        sb.append(input.substring(lastEnd));
        return sb.toString();
    }

    /**
     * 检查并将连续的 Markdown 列表行（* 或 - 开头）包裹为 <ul>...</ul>
     */
    private String wrapMarkdownListWithUl(String input) {
		String[] lines = input.split("\\r?\\n");
		StringBuilder sb = new StringBuilder();
		boolean inUl = false;
		for (String line : lines) {
			// 支持以 * 或 - 开头，或以 <br>- 开头的行
			String trimmed = line.trim();
			boolean isList = trimmed.matches("^[\\*-] .+")
					|| trimmed.matches("^<br>[\\*-] .+");
			if (isList) {
				if (!inUl) {
					sb.append("<ul>\n");
					inUl = true;
				}
			
				sb.append(trimmed).append("\n");
			} else {
				if (inUl) {
					sb.append("</ul>\n");
					inUl = false;
				}
				sb.append(line).append("\n");
			}
		}
		if (inUl) {
			sb.append("</ul>\n");
		}
		return sb.toString().trim();
	}

    // 测试主函数
    public static void main(String[] args) {
        TextStyle textStyle = new TextStyle();
        String test1 = "[b]Hello[/b] World!";
        String test2 = "\r\n" + //
						"> **OWL 想让机器“理解世界”，却把自己困在逻辑迷宫；  \r\n" + //
						"> 而 SHACL + SPARQL + RDF-Star + 命名图，让机器“管理世界”，在混乱中建立秩序。**  ";
        String test3 = "[b]Mix **Markdown** and BBCode[/b]";
        String test4 = "[pre]Preformatted[/pre] and normal";
        String test5 = "###	OWL是什么：";
        String test6 = "<br>- 列表项1 <br>- 列表项2<p></p>1. 有序项";
        String test7 = "> 引用\n~~删除线~~\n`代码`";
        String test8 = "[链接](https://example.com)";
		String test9 = "<p class=\"indent\">---</p>";
 		String test10 = "<br>---</br>";
		System.out.println("Test1: " + textStyle.convertTags(test1));
		System.out.println("Test2: " + textStyle.convertTags(test2));
		System.out.println("Test3: " + textStyle.convertTags(test3));
		System.out.println("Test4: " + textStyle.convertTags(test4));
		System.out.println("Test5: " + textStyle.convertTags(test5));
		System.out.println("Test6: " + textStyle.convertTags(test6));
		System.out.println("Test7: " + textStyle.convertTags(test7));
		System.out.println("Test8: " + textStyle.convertTags(test8));
		System.out.println("Test9: " + textStyle.convertTags(test9));
		System.out.println("Test10: " + textStyle.convertTags(test10));
      
// ...existing code...
      
    }

}
