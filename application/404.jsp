<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" %>
<%
    response.setHeader("X-Robots-Tag", "noindex"); 
    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); // 301
    response.setHeader("Location", "https://www.jdon.com/");
%>


