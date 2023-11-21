<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
   <%@include file="./IncludeTopHead.jsp"%>
</head>
<body>
<%@ include file="./body_header.jsp" %>
<div id="page-content" class="single-page container">
	<div class="row">
		<!-- /////////////////左边 -->
		<div id="main-content" class="col-lg-12">
<%@ include file="./header_errors.jsp" %>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >