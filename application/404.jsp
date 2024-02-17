<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page isELIgnored="false" %>
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF,com.jdon.jivejdon.spi.component.email.EmailHelper" %>
<%@ page import="com.jdon.jivejdon.spi.component.email.EmailVO" %>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
if (errorBlocker.checkRate(request.getRemoteAddr(), 10)){
    return;
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta http-equiv="refresh" content="15;url=https://www.jdon.com" />
    
    <title>该页无法找到404 ERROR</title>
    <link rel="stylesheet" type="text/css" href="/common/style/cmstop-error.css" media="all">
</head>
<body class="body-bg">

<center>
<div class="main">
    <p class="title">404错误！<br>
    <br>
    <br>在Jdon.com无法找到. 去除多余非法字符后再试试看
 <p>
 <form  method="post" action="/query/threadViewQuery.shtml">
		查找：<input type="text"  value="" name="query" size="40">
</form>

    <a href="<%=request.getContextPath() %>/" class="btn">Jdon.com首页</a>
</div>
</center>

</body></html>
