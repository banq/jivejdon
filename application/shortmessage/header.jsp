<%@ page contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> 
</title>

</head>
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="<html:rewrite page="/shortmessage/shortmsg_css.jsp"/>"	type="text/css">
<link rel="stylesheet" href="//cdn.jdon.com/common/jivejdon5.css" type="text/css"/>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<script language="javascript" src="//cdn.jdon.com/common/js/prototype.js"></script>

<body >