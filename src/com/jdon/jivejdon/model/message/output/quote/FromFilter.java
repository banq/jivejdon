package com.jdon.jivejdon.model.message.output.quote;


/**
 * replace [quote from=xxxx][/quote]
 * @author oojdon
 *
 */
public class FromFilter implements RegexFilter {

	@Override
	public String doFilter(String source) {
		String regex =  "\\[quote from=(.[^\\[]*)\\]";
		String rpstr = "<div class=quote_title>摘录自\"$1\"</div><div class=quote_body>";
		return new RegexUtil(regex,rpstr).doFilter(source);

	}

}
