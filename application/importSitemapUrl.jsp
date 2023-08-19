<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@page import="com.jdon.jivejdon.util.ToolsUtil"%>
<%@page import="com.jdon.jivejdon.presentation.form.MessageForm"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
    
<title>导入Sitemap</title>
<%@ include file="./common/headerBody.jsp" %>
</head>
<body>
<%
String subject = "";
if (request.getParameter("subject") != null){
	subject = request.getParameter("subject");
    subject = subject.replaceAll("- 极道", "");	
    subject = ToolsUtil.replaceBlank(subject, "\t|\r|\n|\\s+");
    if (subject.length() >= MessageForm.subjectMaxLength)
       subject = subject.substring(0, MessageForm.subjectMaxLength);
}

String url = "";
if (request.getParameter("url") != null)
	url = request.getParameter("url");

%>

<center>
<form name="urlForm" method="post" action="/message/urlSaveAction.shtml" >
<input type="hidden" name="action" value="create">
<input type="hidden" name="urlId" value="0">

网址:<input type="text" name="ioc" value="<%=url%>">
<br>
标题:<input type="text" name="name" value="<%=subject%>">
<br>
<input type="submit" name="submit" value="保存">
</form>
</center>
</body>
</html>