<%@ page contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> 
</title>

</head>
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="<html:rewrite page="/shortmessage/shortmsg_css.jsp"/>"	type="text/css">
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css"/>
<script language="javascript" src="/common/js/prototype.js"></script>

<body >