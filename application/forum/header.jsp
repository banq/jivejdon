<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="dns-prefetch" href="https://cdn.jdon.com/"/>
    <link rel="dns-prefetch" href="https://static.jdon.com/"/>
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>	
    <meta charset="utf-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta http-equiv="" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty></title>
    <meta property="og:type" content="article"/>
    <meta property="og:url" content="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html"/>
    <meta property="og:release_date" content="<bean:write name="forumThread" property="creationDate" />"/>
    <meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate>"/>
    <meta name="description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[140]" /> "/>
    <meta name="og:title" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>"/>
    <meta name="og:description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[140]" /> "/>
    <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap" />
    <link rel="alternate" type="application/rss+xml" title="解道订阅" href="/rss" />
    <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 256 256%22><text y=%22203%22 font-size=%22224%22>☯</text></svg>"/>
	<%-- <%@ include file="../common/security.jsp" %>
    <%@ include file="../common/loginAccount.jsp" %> --%>
    <link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html" />    
    <link rel="stylesheet" href="https://www.jdon.com/js/jdon.css"  type="text/css">  
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://cdn.jdon.com/js/html5shiv.min.js"></script>
        <script src="https://cdn.jdon.com/js/respond.min.js"></script>
    <![endif]-->
    <script defer src="https://static.jdon.com/js/jquery-2.1.1.min.js"></script>
    <script defer src="https://static.jdon.com/js/bootstrap.min.js"></script>
    <script defer src="https://static.jdon.com/common/js/jquery.lazyload-any.js"></script>
    <script defer src="https://static.jdon.com/common/login2.js"></script>
    <script defer src="https://static.jdon.com/common/messageList9.js"></script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../common/header_errors.jsp" %>
 