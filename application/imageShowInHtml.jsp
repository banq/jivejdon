<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
if (errorBlocker.checkCount(request.getRemoteAddr(), 5)){
	response.sendError(404);
    return;
}
  if (request.getParameter("oid") == null && request.getParameter("id") == null) {
    response.sendError(404);
    return;
  }
  if (!request.getParameter("id").matches("(\\w+|\\d+)")) {
	response.sendError(404);
    return;	
}
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title></title>
</head>
<body>
<% if (request.getParameter("oid") != null && request.getParameter("oid").matches("(\\w+|\\d+)")) { %>
<%--  <img src="<%=request.getContextPath() %>/img/<%=request.getParameter("id") %>/<%=request.getParameter("oid") %>"  border='0' /> --%>
 <img src="/img/<%=request.getParameter("id") %>/<%=request.getParameter("oid") %>"  border='0' />
<% }else if (request.getParameter("type") != null && request.getParameter("type").matches("(\\w+|\\d+)")) {   %> 
 <%-- <img src="<%=request.getContextPath() %>/img/uploadShowAction.shtml?id=<%=request.getParameter("id") %>&type=<%=request.getParameter("type") %>"  border='0' /> --%>
 <img src="/img/<%=request.getParameter("id") %>/<%=request.getParameter("type") %>"  border='0' />
<%} %>
</body>
</html>