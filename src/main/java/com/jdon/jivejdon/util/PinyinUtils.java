package com.jdon.jivejdon.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
    public static String toPinyin(String chinese) {
        if (chinese == null || chinese.isEmpty()) {
            return "";
        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();
        boolean lastWasSpace = false;

        for (char c : chinese.toCharArray()) {
            if (Character.toString(c).matches("[\\u4E00-\\u9FA5]")) {
                // 处理中文字符
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyinArray != null) {
                        sb.append(pinyinArray[0]);
                        lastWasSpace = false;
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else if (c == ' ') {
                // 处理空格，多个连续空格只转换为一个连字符
                if (!lastWasSpace && sb.length() > 0) {
                    sb.append("-");
                    lastWasSpace = true;
                }
            } else if (Character.isLetterOrDigit(c) && c <= 127) {
                // 处理ASCII范围内的字母和数字
                if (lastWasSpace && sb.length() > 0 && !sb.toString().endsWith("-")) {
                    sb.append("-");
                }
                sb.append(c);
                lastWasSpace = false;
            }
            // 其他字符（包括全角符号＋等）直接跳过，不做任何处理
        }

        // 如果最后一个字符是"-"，则去除它
        String result = sb.toString();
        if (result.endsWith("-")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(toPinyin("你好 世界  people＋  chinese 12389 ")); // 输出: nihaoshijie
    }
}
