<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" %>
<%
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Page Not Found</title>
    <meta http-equiv="refresh" content="5;url=https://www.jdon.com" />
</head>
<body>
    <h1>404 - 页面无法发现</h1>
    <p>在Jdon.com无法找到. </p>
</body>
</html>

