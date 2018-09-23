<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@page import="com.jdon.jivejdon.presentation.listener.UserCounterListener,java.util.List,java.util.Iterator"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>
在线登录用户状态
</title>
<style>
body,table,td,select,input,div,form{font-size: 12px;}
A{text-decoration:none;color:#036;}a:visited{text-decoration:none;color: #666;font-weight:lighter;}A:hover{text-decoration:underline;color:#eab30d;}
</style>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="300" align='center'>
<tr>
<td>
<%
int count = 1;
List userList = (List)application.getAttribute(UserCounterListener.OnLineUser_KEY);
StringBuffer sb = new StringBuffer();
Iterator iter = userList.iterator();
while(iter.hasNext()){
	String username = (String)iter.next();
	sb.append("<a href=\""+request.getContextPath()+"/blog/"+username+"\" target=\"_blank\">"+username+"</a>&nbsp;");
	if ((count = count % 6) == 0)
		sb.append("<br>");
	count = count + 1;
}
out.print(sb.toString());
%>
</td>
</tr>
</table>
</body></html>
