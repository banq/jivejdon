<%@ page session="false" %>
<%@page import="com.jdon.jivejdon.util.TitleExtractor,java.util.regex.*"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="application/json; charset=UTF-8" %>

<%
if (request.getParameter("url") == null)
	return;
String url = request.getParameter("url");
Pattern httpURLEscape = Pattern.compile("^(http://|https://){1}[\\w\\.\\-/:]+");
Matcher matcher = httpURLEscape.matcher(url);
if (!matcher.find())
	return;

String title = "";
try {
	title = TitleExtractor.getPageTitle(url);	
} catch (Exception e) {
	e.printStackTrace();
}
%>  

[{ "value": "<%=title%>" }]
