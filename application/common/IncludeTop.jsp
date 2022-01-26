<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8" />
    <link rel="preconnect" href="https://adservice.google.com/">
    <link rel="preconnect" href="https://googleads.g.doubleclick.net/">
    <link rel="preconnect" href="https://www.googletagservices.com/">
    <link rel="preconnect" href="https://tpc.googlesyndication.com/">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty></title>
    <script src="//static.jdon.com/common/login2.js"></script>
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://static.jdon.com/js/bootstrap.min.css"  type="text/css">
    <!-- Custom Fonts -->
    <link rel="stylesheet" href="https://static.jdon.com/js/font-awesome.min.css"  type="text/css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="https://static.jdon.com/common/js/styles/style.css">
            
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://static.jdon.com/js/html5shiv.min.js></script>
        <script src="https://static.jdon.com/js/respond.min.js"></script>
    <![endif]-->    
</head>
<body>
<%@ include file="./body_header.jsp" %>
<div id="page-content" class="single-page container">
	<div class="row">
		<!-- /////////////////左边 -->
		<div id="main-content" class="col-md-12">
<%@ include file="./header_errors.jsp" %>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >