package com.jdon.jivejdon.model.message.output.quote;

/**
 * replace [quote][/quote]
 * @author oojdon
 *
 */
public class SummarytFilter implements RegexFilter {

	@Override
	public String doFilter(String source) {
		String regex =  "\\[quote\\]";
		String rpstr = "<div class=quote_title>摘录</div><div class=quote_body>";

		return new RegexUtil(regex,rpstr).doFilter(source);

	}

}
