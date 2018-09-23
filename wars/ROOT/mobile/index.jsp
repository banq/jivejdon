<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page contentType="text/html; charset=UTF-8" %>
 
<%
response.setContentType("text/html");
response.setDateHeader("Expires", 0);
response.setHeader("Location", request.getContextPath()+"/mobile/new");
response.setStatus(301); 
%>
