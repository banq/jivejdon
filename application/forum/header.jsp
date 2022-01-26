<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
%>
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
    <meta property="og:type" content="article"/>
    <meta property="og:url" content="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />"/>
    <meta property="og:release_date" content="<bean:write name="forumThread" property="creationDate" />"/>
    <meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate>"/>
    <meta name="description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[140]" /> "/>
    <meta name="og:title" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>"/>
    <meta name="og:description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[140]" /> "/>
    <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap" />
    <link rel="alternate" type="application/rss+xml" title="解道订阅" href="/rss" />
	<%-- <%@ include file="../common/security.jsp" %>
    <%@ include file="../common/loginAccount.jsp" %> --%>
    <link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />" />
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
    <script>
        (adsbygoogle = window.adsbygoogle || []).push({
            google_ad_client: "ca-pub-7573657117119544",
            enable_page_level_ads: true
        });
    </script>
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-32868073-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-32868073-1');
    </script>
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://static.jdon.com/js/bootstrap.min.css"  type="text/css">
    <!-- Custom Fonts -->
    <link rel="stylesheet" href="https://static.jdon.com/js/font-awesome.min.css"  type="text/css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="https://static.jdon.com/common/js/styles/style.css">  
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://static.jdon.com/js/html5shiv.min.js"></script>
        <script src="https://static.jdon.com/js/respond.min.js"></script>
    <![endif]-->
</head>
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../common/header_errors.jsp" %>
 