<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<!-- Google Tag Manager -->
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-PFPM5XC');</script>
<!-- End Google Tag Manager -->
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
    <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 256 256%22><text y=%22203%22 font-size=%22224%22>🐶</text></svg>"/>
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
    <style>
       body{margin:0}header,nav{display:block}h1{font-size:2em;margin:.67em 0}img{border:0}button,input{color:inherit;font:inherit;margin:0}button{overflow:visible}button{text-transform:none}button{-webkit-appearance:button}button::-moz-focus-inner,input::-moz-focus-inner{border:0;padding:0}input{line-height:normal}*{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}:before,:after{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}html{font-size:62.5%}body{font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;color:#666;background-color:#fff}input,button{font-family:inherit;font-size:inherit;line-height:inherit}img{vertical-align:middle}h1{font-family:inherit;font-weight:500;line-height:1.1;color:inherit}h1{margin-top:20px;margin-bottom:10px}p{margin:0 0 10px}ul{margin-top:0;margin-bottom:10px}ul ul{margin-bottom:0}.list-unstyled{padding-left:0;list-style:none}.list-inline{padding-left:0;list-style:none;margin-left:-5px}.list-inline>li{display:inline-block;padding-left:5px;padding-right:5px}.container{margin-right:auto;margin-left:auto;padding-left:15px;padding-right:15px}@media (min-width:768px){.container{width:750px}}@media (min-width:992px){.container{width:970px}}@media (min-width:1200px){.container{width:1170px}}.row{margin-left:-15px;margin-right:-15px}.col-md-4,.col-md-8,.col-md-12{position:relative;min-height:1px;padding-left:15px;padding-right:15px}@media (min-width:992px){.col-md-4,.col-md-8,.col-md-12{float:left}.col-md-12{width:100%}.col-md-8{width:66.66666667%}.col-md-4{width:33.33333333%}}.form-control{display:block;width:100%;height:34px;padding:6px 12px;font-size:14px;line-height:1.42857143;color:#555;background-color:#fff;background-image:none;border:1px solid #ccc;border-radius:4px;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075)}.form-control::-moz-placeholder{color:#999;opacity:1}.form-control:-ms-input-placeholder{color:#999}.form-control::-webkit-input-placeholder{color:#999}.btn{display:inline-block;margin-bottom:0;font-weight:400;text-align:center;vertical-align:middle;background-image:none;border:1px solid transparent;white-space:nowrap;padding:6px 12px;font-size:14px;line-height:1.42857143;border-radius:4px}.collapse{display:none}.dropdown{position:relative}.dropdown-menu{position:absolute;top:100%;left:0;z-index:1000;display:none;float:left;min-width:160px;padding:5px 0;margin:2px 0 0;list-style:none;font-size:14px;background-color:#fff;border:1px solid #ccc;border:1px solid rgba(0,0,0,.15);border-radius:4px;-webkit-box-shadow:0 6px 12px rgba(0,0,0,.175);box-shadow:0 6px 12px rgba(0,0,0,.175);background-clip:padding-box}.nav{margin-bottom:0;padding-left:0;list-style:none}.nav>li{position:relative;display:block}.nav>li>a{position:relative;display:block;padding:10px 15px}.navbar{position:relative;min-height:50px;margin-bottom:20px;border:1px solid transparent}@media (min-width:768px){.navbar{border-radius:4px}}@media (min-width:768px){.navbar-header{float:left}}.navbar-collapse{max-height:340px;overflow-x:visible;padding-right:15px;padding-left:15px;border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1);-webkit-overflow-scrolling:touch}@media (min-width:768px){.navbar-collapse{width:auto;border-top:0;box-shadow:none}.navbar-collapse.collapse{display:block!important;height:auto!important;padding-bottom:0;overflow:visible!important}}.container>.navbar-header,.container>.navbar-collapse{margin-right:-15px;margin-left:-15px}@media (min-width:768px){.container>.navbar-header,.container>.navbar-collapse{margin-right:0;margin-left:0}}.navbar-brand{float:left;padding:15px;font-size:18px;line-height:20px;height:50px}.navbar-toggle{position:relative;float:right;margin-right:15px;padding:9px 10px;margin-top:8px;margin-bottom:8px;background-color:transparent;background-image:none;border:1px solid transparent;border-radius:4px}@media (min-width:768px){.navbar-toggle{display:none}}.navbar-nav{margin:7.5px -15px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px;line-height:20px}@media (min-width:768px){.navbar-nav{float:left;margin:0}.navbar-nav>li{float:left}.navbar-nav>li>a{padding-top:15px;padding-bottom:15px}}@media (min-width:768px){.navbar-right{float:right!important}}.navbar-nav>li>.dropdown-menu{margin-top:0;border-top-right-radius:0;border-top-left-radius:0}.navbar-inverse{background-color:#222;border-color:#080808}.container:before,.container:after,.row:before,.row:after,.nav:before,.nav:after,.navbar:before,.navbar:after,.navbar-header:before,.navbar-header:after,.navbar-collapse:before,.navbar-collapse:after{content:" ";display:table}.container:after,.row:after,.nav:after,.navbar:after,.navbar-header:after,.navbar-collapse:after{clear:both}@-ms-viewport{width:device-width}body{font-weight:400;font-size:15px;line-height:1.6em}body,html{margin:0;padding:0;width:100%;word-wrap:break-word}html{聽-webkit-text-size-adjust:none}img{width:auto\9;height:auto;max-width:100%}h1{color:#111;font-weight:600;font-family:Roboto,sans-serif}p{margin:0 0 20px}a{color:#41872d;text-decoration:none}.bige20{font-size:24px}.post:after,.post:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:'\0020'}#top{padding:10px 0 0;border-bottom:1px solid #ddd;background:#fff;color:#000}#top a{color:#000}.link{text-align:right}.link a{display:inline-block;text-decoration:none;letter-spacing:1px;font-weight:700;font-size:12px}#menu{border-radius:0;background-color:#444}#menu .navbar-brand{margin:0;padding:14px 18px;height:auto;background-color:#000;text-transform:uppercase}#menu .navbar-collapse{padding:0}#menu .dropdown-menu{border:none;background-color:#fff}#menu ul.nav .dropdown-menu li a{border-radius:5px!important;color:#000;font-weight:700}#menu .dropdown-inner{display:table;background-color:#fff}#menu .dropdown-inner ul{display:table-cell}#menu .dropdown-inner a{clear:both;display:block;margin:0 5px;padding:3px 20px;min-width:160px;color:#000;font-size:14px;line-height:20px}#menu ul.nav li a{padding:14px 19px;border-radius:5px;color:#fff;font-weight:700}#menu ul.nav li.dropdown a{border-top-right-radius:5px;border-bottom-right-radius:0;border-bottom-left-radius:0;border-top-left-radius:5px}#menu .top-social{padding:5px;background-color:#505052;text-align:center;font-size:20px}#menu ul.top-social{margin:0}#menu ul.top-social li{width:38px;height:38px}#menu ul.top-social a i{width:38px;height:38px;border-radius:50%;background-color:#41872d;color:#fff;line-height:1.9}#menu .btn-navbar{float:right;padding:5px 15px;border:3px solid #41872d;color:#41872d;font-size:20px}@media (max-width:768px){#menu{border-radius:0;background-color:#000}}@media (min-width:768px){#menu.navbar{margin-top:20px;padding:0;height:auto}}@media (max-width:767px){#menu .navbar-brand{display:block;color:#fff}#menu div.dropdown-inner>ul.list-unstyled{display:block}#menu .dropdown-inner a{width:100%;color:#fff}#menu div.dropdown-menu{margin-left:0!important;padding-bottom:10px;background-color:rgba(0,0,0,.1)}}#page-content{padding:0}#page-content.single-page{margin-top:20px}.box{padding:15px 20px 10px;background-color:#fff}@media (max-width:768px){.box{padding:15px 10px 5px}}.box:after,.box:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:'\0020'}.widget{overflow:hidden;text-overflow:ellipsis;word-break:break-all;padding:10px 20px;background:#fff}.widget:after,.widget:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:'\0020'}.widget ul{margin:0;padding:0;list-style:none}.post{margin-bottom:15px}.post:after,.post:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:'\0020'}.post:last-child{margin-bottom:0}.post:after,.post:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:'\0020'}.article{font-family:'Segoe UI',SegoeUI,'Microsoft YaHei',微软雅黑;font-weight:300;font-size:18px;line-height: 2;color: #282828;}.fa{display:inline-block;font:normal normal normal 14px/1 FontAwesome;font-size:inherit;text-rendering:auto;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}.fa-home:before{content:"\f015"}.fa-arrow-circle-o-down:before{content:"\f01a"}.fa-feed:before{content:"\f09e"}.fa-arrow-circle-up:before{content:"\f0aa"}.fa-bars:before{content:"\f0c9"}.fa-list-ul:before{content:"\f0ca"}.fa-star-half-full:before{content:"\f123"}.fa-weibo:before{content:"\f18a"}.fa-qq:before{content:"\f1d6"}.fa-weixin:before{content:"\f1d7"}
    </style>            
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://cdn.jdon.com/js/html5shiv.min.js"></script>
        <script src="https://cdn.jdon.com/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-PFPM5XC"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../common/header_errors.jsp" %>
 