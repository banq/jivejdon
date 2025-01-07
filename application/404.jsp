<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" %>
<%
     response.setHeader("X-Robots-Tag", "noindex");  // 防止被搜索引擎索引
     response.setStatus(HttpServletResponse.SC_GONE);  // 使用 410 表示该页面已被永久删除

%>


