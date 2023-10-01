<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="/js/jdon.css"  type="text/css">      
    <script defer src="/js/jquery-bootstrap2.js"></script>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty> - 极道</title>
</head>
<body>
<%@ include file="./body_header.jsp" %>
<div id="page-content" class="single-page container">
	<div class="row">
		<!-- /////////////////左边 -->
		<div id="main-content" class="col-md-12">
<%@ include file="./header_errors.jsp" %>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >