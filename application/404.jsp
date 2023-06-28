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
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta http-equiv="refresh" content="3;url=https://www.jdon.com" />
    
    <title>该页无法找到404 ERROR</title>
    <link rel="stylesheet" type="text/css" href="/common/style/cmstop-error.css" media="all">
</head>
<body class="body-bg">

<center>
<div class="main">
    <p class="title">404错误！<br> 非常抱歉，你访问的网址
    <br>
    <br>在Jdon.com无法找到. 去除多余非法字符后再试试看
 <br>可进入<a href="/query/threadViewQuery.shtml">查询页面</a>查找
    <a href="<%=request.getContextPath() %>/" class="btn">Jdon.com首页</a>
</div>
</center>

</body></html>
