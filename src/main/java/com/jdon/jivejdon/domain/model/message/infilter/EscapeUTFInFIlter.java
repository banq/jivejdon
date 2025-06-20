package com.jdon.jivejdon.domain.model.message.infilter;

import java.util.Stack;
import java.util.function.Function;

import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * escape 😃  these utf-16, not fit for utf-8 mysql
 */
public class EscapeUTFInFIlter implements Function<MessageVO, MessageVO> {
	private final static String module = EscapeUTFInFIlter.class.getName();


    public MessageVO apply(MessageVO messageVO) {
        return messageVO.builder()
                .subject(escapeUtf(messageVO.getSubject()))
                .body(escapeUtf(messageVO.getBody()))
                .build();
    }
     /**
     * 去除不支持的 emoji 字符或不规范的嵌套标签
     */
    public String escapeUtf(String input) {
        if (input == null) return null;
        if (isNested(input)) {
            // 用 StringBuilder 替换 replaceAll，效率更高
            StringBuilder sb = new StringBuilder(input.length());
            for (char c : input.toCharArray()) {
                if (c != '[' && c != ']') {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        // 用 codePoints 过滤超出 BMP 的字符，效率高于正则
        StringBuilder sb = new StringBuilder(input.length());
        input.codePoints().forEach(cp -> {
            if (cp <= 0xFFFF) {
                sb.append((char) cp);
            }
        });
        return sb.toString();
    }

    // 检查是否存在未闭合的 [ 标签
    private boolean isNested(String text) {
        if (text == null) return false;
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == '[') count++;
            else if (c == ']') {
                if (count == 0) return false;
                count--;
            }
        }
        return count > 0;
    }

    public static void main(String[] args) {
        EscapeUTFInFIlter escapeUTFInFIlter = new EscapeUTFInFIlter();
        String in = "dasdfsdf[sfsa[b]df[/b]sad";
        if (escapeUTFInFIlter.isNested(in))
            System.out.println("===" + escapeUTFInFIlter.escapeUtf(in));
        String emoji = "abc😃def";
        System.out.println("no emoji: " + escapeUTFInFIlter.escapeUtf(emoji));
    }
}
