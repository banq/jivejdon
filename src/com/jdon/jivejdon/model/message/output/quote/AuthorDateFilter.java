package com.jdon.jivejdon.model.message.output.quote;

/**
 * replace [quote author=username date=yyyy/mm/dd][/quote]
 * 
 * @author oojdon
 * 
 */
public class AuthorDateFilter implements RegexFilter {

	@Override
	public String doFilter(String source) {
		String regex = "\\[quote author=(.[^\\[]*) date=(.[^\\[]*)\\]";
		String rpstr = "<div class=quote_title>$2 \"$1\"的内容</div><div class=quote_body>";
		return new RegexUtil(regex, rpstr).doFilter(source);

	}

}
