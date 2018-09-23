package com.jdon.jivejdon.model.message.output.quote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	private String regex = "";
	private String rpStr = "";
	private Matcher matcher = null;
	private Pattern pattern ;

    public RegexUtil(String regex, String rpStr) {
        this.regex = regex;
        this.rpStr = rpStr;
        pattern = Pattern.compile(regex,Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    }

    public String doFilter(String source) {
        matcher = pattern.matcher(source);
        return matcher.replaceAll(rpStr);
    }
    
    public boolean matches(){
    	return matcher.matches();
    }
    
    public String getGroup(int i){
    	return matcher.group(i);
    }

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRpStr() {
		return rpStr;
	}

	public void setRpStr(String rpStr) {
		this.rpStr = rpStr;
	}
}
