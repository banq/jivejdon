<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
	<title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty></title>
	 <meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate>"/>
   <meta name="description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" /> "/>
	<link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap" />
<link rel="alternate" type="application/rss+xml" title="解道订阅" href="/rss" />
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://cdn.jdon.com/js/bootstrap.min.css"  type="text/css">
	<!-- Custom Fonts -->
    <link rel="stylesheet" href="https://cdn.jdon.com/js/font-awesome-4.4.0/css/font-awesome.min.css"  type="text/css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="https://cdn.jdon.com/js/style.css">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="/js/html5shiv.min.js"></script>
        <script src="/js/respond.min.js"></script>
    <![endif]-->
	<%
String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
%>
	<%@ include file="../common/security.jsp" %>
<%@ include file="../common/loginAccount.jsp" %>
<link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />" />
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({
          google_ad_client: "ca-pub-7573657117119544",
          enable_page_level_ads: true
     });
</script>
</head>

<%@ include file="../common/body_header.jsp" %>

 <%@ include file="../common/header_errors.jsp" %>
 