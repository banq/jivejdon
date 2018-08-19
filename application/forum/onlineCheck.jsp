<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.jdon.jivejdon.presentation.listener.UserCounterListener,java.util.List,java.util.Iterator"%>
<%
List userList = (List)application.getAttribute(UserCounterListener.OnLineUser_KEY);
String renderText = "";
if(userList.contains(request.getParameter("username")))
	renderText = "<font color=blue>我在线上</font>";
else
	renderText = "当前离线";
%>
<%=renderText%>