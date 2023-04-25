<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="dns-prefetch" href="https://cdn.jdon.com/"/>
    <link rel="dns-prefetch" href="https://static.jdon.com/"/>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta http-equiv="" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 256 256%22><text y=%22203%22 font-size=%22224%22>☯</text></svg>"/>
    <title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty></title>
    <link rel="stylesheet" href="https://cdn.jdon.com/js/bootstrap.min.css"  type="text/css">
    <link rel="stylesheet" href="https://cdn.jdon.com/common/js/styles/style.css" type="text/css">           
	<script defer src="https://static.jdon.com/js/jquery-2.1.1.min.js"></script>	
    <script defer src="https://static.jdon.com/js/bootstrap.min.js"></script>
    <script defer src="https://static.jdon.com/common/js/jquery.lazyload-any.js"></script>  
    <script defer src="https://static.jdon.com/common/login2.js"></script>  
</head>
<body>
<%@ include file="./body_header.jsp" %>
<div id="page-content" class="single-page container">
	<div class="row">
		<!-- /////////////////左边 -->
		<div id="main-content" class="col-md-12">
<%@ include file="./header_errors.jsp" %>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >