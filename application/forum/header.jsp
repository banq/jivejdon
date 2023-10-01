<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<html lang="zh-CN">
<html>
<head>
    <link rel="stylesheet" href="/js/jdon.css"  type="text/css">  
    <script defer src="/js/jquery-bootstrap2.js"></script>
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>	
    <link rel="preconnect dns-prefetch" href="//adservice.google.com" crossorigin>
    <link rel="preconnect dns-prefetch" href="//googleads.g.doubleclick.net" crossorigin>
    <link rel="preconnect dns-prefetch" href="//tpc.googlesyndication.com" crossorigin>
    <link rel="preconnect dns-prefetch" href="//analytics.google.com" crossorigin>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="refresh" content="3600">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty> - 极道</title>
    <meta property="og:type" content="article">
    <meta property="og:url" content="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html">
    <meta property="og:release_date" content="<bean:write name="forumThread" property="creationDate" />">
    <meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate>">
    <meta name="description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[140]" /> ">
    <meta name="og:title" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>">
    <meta name="og:description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[140]" /> ">
    <script defer src="/common/messageList12.js"></script> 
    <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml" >
    <link rel="alternate" type="application/rss+xml" title="极道订阅" href="/rss" >
    <link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html">  
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-FTT1M21HE8"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());
      gtag('config', 'G-FTT1M21HE8');
    </script>  
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../common/header_errors.jsp" %>

