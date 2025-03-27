<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>


  <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
  <bean:define id="forum" name="forumThread" property="forum"/>
  <bean:define id="title" name="forumThread" property="name"/>
  <bean:define id="pagestart" name="messageListForm" property="start"/>
  <bean:define id="pagecount" name="messageListForm" property="count"/>
  <bean:define id="pageallCount" name="messageListForm" property="allCount" />
  <%
    int pagestartInt = ((Integer) pageContext.getAttribute("pagestart")).intValue();
    int pagecountInt = ((Integer) pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int currentPageNo = 1;
    if (pagecountInt > 0)
      currentPageNo = (pagestartInt / pagecountInt) + 1;
    String titleStr = (String) pageContext.getAttribute("title");
    if (titleStr == null)
      response.sendError(404);
    if (currentPageNo > 1)
      titleStr = titleStr + "  - 第" + currentPageNo + "页";
    pageContext.setAttribute("title", titleStr);
    pageContext.setAttribute("currentPageNo", currentPageNo);
  %>

<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link rel="preconnect" href="https://static.jdon.com/" crossorigin> 
    <link rel="preconnect" href="https://pagead2.googlesyndication.com/">
    <link rel="preconnect" href="https://www.googletagmanager.com/">    
    <link rel="preconnect" href="https://googleads.g.doubleclick.net/">
    <link rel="preconnect" href="https://tpc.googlesyndication.com/">
    <link rel="preconnect" href="https://www.googletagservices.com/">
    <link rel="preconnect" href="https://static.googleadsserving.cn/">
    <link rel="preconnect" href="https://www.gstatic.com/">
    <link rel="preconnect" href="https://fonts.gstatic.com/">
    <link rel="preconnect" href="https://www.gstatic.cn/">
    <link rel="preconnect" href="https://fonts.googleapis.com/">
    <link rel="preconnect" href="https://cm.g.doubleclick.net/">
    <link rel="preconnect" href="https://ad.doubleclick.net/">
    <link rel="preconnect" href="https://fundingchoicesmessages.google.com/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty> - 极道</title> 
    <meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate><bean:write name="forumThread" property="token" />">
    <meta name="description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" /> ">  
    <meta name="og:title" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>">
    <meta name="og:description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" /> ">       
    <meta property="og:url" content="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html"/>
    <meta property="og:release_date" content="<bean:write name="forumThread" property="creationDate" />"/>
    <meta property="og:image" content="<%=domainUrl %>/simgs/jdon100.png"/> 
    <style> 
      button,h2,h3,h4,input{color:inherit;font-family:inherit}h2,h3,ul{margin-bottom:10px}.btn,img{vertical-align:middle}.link a,a{text-decoration:none}html{font-family:sans-serif;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}aside,header,main,nav,section{display:block}a{background:0;color:#41872d}img{border:0;height:auto;max-width:100%}button,input{color:inherit;font:inherit;margin:0}button::-moz-focus-inner,input::-moz-focus-inner{border:0;padding:0}input{line-height:normal}*,:after,:before{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}html{font-size:62.5%;-webkit-text-size-adjust:none}button,input{font-size:inherit;line-height:inherit}h2,h3,h4{font-weight:500;line-height:1.1}.btn,body{font-weight:400}h2,h3{margin-top:20px}h2{font-size:2rem}h3{font-size:1.75rem}h4{font-size:1.6rem}ul{margin-top:0}.list-inline{padding-left:0;list-style:none;margin-left:-5px}.list-inline>li{display:inline-block;padding-left:5px;padding-right:5px}.form-control,.nav>li,.nav>li>a{display:block}.container{margin-right:auto;margin-left:auto;padding-left:15px;padding-right:15px}.container>.navbar-collapse,.container>.navbar-header,.row{margin-right:-15px;margin-left:-15px}@media (min-width:768px){.container{width:750px}}.col-lg-12,.col-lg-4,.col-lg-8,.col-md-6{position:relative;min-height:1px;padding-left:15px;padding-right:15px}.btn,.form-control{padding:6px 12px;line-height:1.42857143;font-size:14px;background-image:none}@media (min-width:992px){.container{width:970px}.col-md-6{float:left;width:50%}}@media (min-width:1200px){.container{width:128rem}.col-lg-12,.col-lg-4,.col-lg-8{float:left}.col-lg-12{width:100%}.col-lg-8{width:66.66666667%}.col-lg-4{width:33.33333333%}}.form-control{width:100%;height:34px;color:#555;background-color:#fff;border:1px solid #ccc;border-radius:4px;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075)}.form-control::-moz-placeholder{color:#999;opacity:1}.form-control:-ms-input-placeholder{color:#999}.form-control::-webkit-input-placeholder{color:#999}.btn{display:inline-block;margin-bottom:0;text-align:center;border:1px solid transparent;white-space:nowrap;border-radius:4px}.collapse{display:none}.dropdown{position:relative}.dropdown-menu{position:absolute;top:100%;left:0;z-index:1000;display:none;float:left;min-width:160px;padding:5px 0;margin:2px 0 0;list-style:none;font-size:14px;background-color:#fff;border:1px solid rgba(0,0,0,.15);border-radius:4px;-webkit-box-shadow:0 6px 12px rgba(0,0,0,.175);box-shadow:0 6px 12px rgba(0,0,0,.175);background-clip:padding-box}.nav>li,.nav>li>a,.navbar,.navbar-toggle{position:relative}.nav{margin-bottom:0;padding-left:0;list-style:none}.nav>li>a{padding:10px 15px}.navbar{min-height:50px;margin-bottom:20px;border:1px solid transparent}.navbar-collapse{max-height:340px;overflow-x:visible;padding-right:15px;padding-left:15px;border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1);-webkit-overflow-scrolling:touch}.linkblock,.widget{overflow:hidden;text-overflow:ellipsis;word-break:break-all}.navbar-brand{float:left;padding:15px;font-size:20px;line-height:20px;height:50px;color:#fff;font-weight:700}.navbar-toggle{float:right;margin-right:15px;padding:9px 10px;margin-top:8px;margin-bottom:8px;background-color:transparent;background-image:none;border:1px solid transparent;border-radius:4px}.navbar-nav{margin:7.5px -15px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px;line-height:20px}@media (min-width:768px){.navbar{border-radius:4px}.navbar-header,.navbar-nav>li{float:left}.navbar-collapse{width:auto;border-top:0;box-shadow:none}.navbar-collapse.collapse{display:block!important;height:auto!important;padding-bottom:0;overflow:visible!important}.container>.navbar-collapse,.container>.navbar-header{margin-right:0;margin-left:0}.navbar-toggle{display:none}.navbar-nav{float:left;margin:0}.navbar-nav>li>a{padding-top:15px;padding-bottom:15px}.navbar-right{float:right!important}}.navbar-nav>li>.dropdown-menu{margin-top:0;border-top-right-radius:0;border-top-left-radius:0}.navbar-inverse{background-color:#222;border-color:#080808}#top,.linkblock,.thumbn,.widget{background:#fff}.thumbnail{display:block;background-color:#fff}.container:after,.container:before,.nav:after,.nav:before,.navbar-collapse:after,.navbar-collapse:before,.navbar-header:after,.navbar-header:before,.navbar:after,.navbar:before,.row:after,.row:before{content:" ";display:table}.container:after,.nav:after,.navbar-collapse:after,.navbar-header:after,.navbar:after,.row:after{clear:both}.pull-right{float:right!important}@-ms-viewport{width:device-width}@font-face{font-family:icomoon;src:url('/js/fonts/icomoon.eot');src:url('/js/fonts/icomoon.eot') format('embedded-opentype'),url('/js/fonts/icomoon.ttf') format('truetype'),url('/js/fonts/icomoon.woff') format('woff'),url('/js/fonts/icomoon.svg') format('svg');font-weight:400;font-style:normal;font-display:block}[class*=" fa-"]{font-family:icomoon;display:inline-block;font-size:inherit;font-style:normal;text-rendering:auto;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}.fa-star-half-full:before{content:"\f006"}.fa-home:before{content:"\f015"}.fa-feed:before{content:"\f09e"}.fa-weibo:before{content:"\f18a"}.fa-qq:before{content:"\f1d6"}.fa-weixin:before{content:"\f1d7"}.fa-arrow-circle-o-down:before{content:"\f01a"}.fa-bars:before{content:"\f039"}.fa-eye:before{content:"\f06e"}.fa-calendar:before{content:"\f073"}body{font-family:Arial,sans-serif,'Microsoft YaHei',微软雅黑;font-size:14px;color:#333;background-color:#E6E6E6;line-height:2.2rem}#top,#top a,.vid-name a,h2{color:#000}.link a,.vid-name a,h2{font-weight:400}body,html{margin:0;padding:0;width:100%;word-wrap:break-word}h2{letter-spacing:.1rem}p{margin:0 0 20px}.box:after,.box:before,.widget:after,.widget:before,section:after,section:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:"\0020"}#top{padding:10px 0 0;border-bottom:1px solid #ddd}#menu,#menu .navbar-brand,#menu .navbar-nav{background-color:#000}.link{text-align:right}.link a{display:inline-block;letter-spacing:1px;font-size:12px}#menu{border-radius:0}#menu .navbar-brand{margin:0;padding:14px 28px;height:auto;text-transform:uppercase}#menu .navbar-collapse,#page-content{padding:0}#menu .dropdown-menu{border:0;background-color:#fff}#menu .dropdown-inner{display:table;background-color:#fff}#menu ul.nav li a{padding:14px 19px;border-radius:5px;color:#fff;font-weight:700}#menu ul.nav li.dropdown a{border-radius:5px 5px 0 0}#menu .top-social{padding:5px;background-color:#000;text-align:center;font-size:20px}#menu ul.top-social,.box h2,.vid-name{margin:0}#menu ul.top-social li{width:38px;height:38px}#menu ul.top-social a i{width:38px;height:38px;border-radius:50%;background-color:#41872d;color:#fff;line-height:1.9}#menu .btn-navbar{float:right;padding:5px 15px;border:3px solid #41872d;color:#41872d;font-size:20px}@media (max-width:991px){.navbar-header,.navbar-nav>li{float:none}.navbar-nav,.navbar-right{float:none!important}.navbar-toggle{display:block}.navbar-collapse{border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1)}.navbar-collapse.collapse{display:none!important}.navbar-nav{margin-top:7.5px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px}}#menu div.dropdown-menu{margin-left:0!important;padding-bottom:10px;background-color:rgba(0,0,0,.1)}#page-content.single-page{margin-top:20px}.box{padding:1rem;background-color:#fff}@media (max-width:768px){.box{padding:1rem}}.box .info{margin:5px 0 10px}.info{margin:0;font-size:14px}.info i{margin-right:8px}.info span,.thumbn{margin-right:10px}.widget{padding:10px 20px}.linkblock{white-space:normal;padding:5px;border:1px solid #ddd;border-radius:3px;box-shadow:0 15px 10px -15px #e5e5e5}.thumbn{float:left;display:block}button{overflow:visible;text-transform:none;-webkit-appearance:button;background-color:#41872d;color:#fff;border:0}.article{font-family:Arial,sans-serif,'Microsoft YaHei',微软雅黑;font-size:1.6rem;line-height:3.2rem;color:#222;padding:2rem 5rem 2rem 5rem}.bige20{font-size:2.2rem}ins.adsbygoogle[data-ad-status='unfilled']{display:none!important}@media(max-width:768px){.box .article{padding:0.3rem 0.3rem;}}
    </style> 
    <link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl %>/sitemap.xml">
    <link rel="apple-touch-icon" href="<%=domainUrl %>/simgs/jdon100.png" />
    <link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html">  
    <%if(pagestartInt != 0 ) {%> 
      <%if(pagestartInt-pagecountInt>0 ) {%>  
          <link rel="prev" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />/page/<%=(pagestartInt-pagecountInt)%>"/>
      <%}else{%>
          <link rel="prev" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html"/>
       <%}%>
  <%}%> 
  <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
          <link rel="next" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />/page/<%=pagestartInt+pagecountInt%>"/>
  <%}%>
    <link rel="stylesheet" type="text/css" href="<%=domainUrl %>/js/jdon.css" media="print" onload="this.media='all'">
    <script defer src="<%=domainUrl %>/js/jquery-bootstrap2.js"></script>   
    <script defer src="<%=domainUrl %>/common/messageList12.js"></script>  
    <script>
      var _hmt = _hmt || [];
      (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?beb8727358ee1695cf3da14e61adb422";
        var s = document.getElementsByTagName("script")[0]; 
        s.parentNode.insertBefore(hm, s);
      })();
      </script>
      
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../common/header_errors.jsp" %>


<div id="page-content" class="single-page container">
  <div class="row">
    <!-- /////////////////左边 -->
    <div id="main-content" class="col-lg-8">
      <div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)">	
        <!--  内容-->       
        <div id="messageListBody">
          <main>
            <%@include file="messageListBody.jsp" %>
          </main>        
        </div>

        <div id="pageEnd"></div>
  
  <!-- 导航区  -->
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />/page/<%=(pagestartInt-pagecountInt)%>" rel="prev" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html" rel="prev" class="btn-page">上页</a>
     <%}%>
    </li>  
  </ul>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
  <ul class="pagination pull-right"> 
    <li>
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />/page/<%=pagestartInt+pagecountInt%>" rel="next" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>
</div>

</div>
</div>              
              
              </logic:greaterThan>
      </div>
      <logic:present name="principal"> 
       <div class="box">
        <jsp:include page="../message/messagePostReply2.jsp" flush="true">
          <jsp:param name="forumId" value="${forumThread.forum.forumId}"/>
          <jsp:param name="pmessageId" value="${forumThread.rootMessage.messageId}"/>
          <jsp:param name="parentMessageSubject" value="${forumThread.name}"/>
        </jsp:include>
       </div>
      </logic:present>
    </div>

<aside>
    <!-- /////////////////右边 -->
    <div id="sidebar" class="col-lg-4">
      <!-- Start Widget -->
      <div class="widget wid-follow">
        <div class="content">
          <ul class="list-inline">
            <form role="form" class="form-horizontal" method="post" action="<%=domainUrl %>/query/threadViewQuery.shtml">
              <input type="text" value="<bean:write name="forumThread" property="token" />" name="query" id="v_search" class="form-control">
            </form>
          </ul>
        </div>
      </div>
			<div class="widget">
        <div class="wid-vid">
              
                
<!-- 内容右侧悬浮广告 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="5184711902"
     data-ad-format="auto"
     data-full-width-responsive="true"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>

        </div>				
			</div>

     <div class="widget">
        <div class="wid-vid">
               <div id="searchResult">                		
                
                  <div id="threadPreNextList" class="linkblock">
                    <logic:notEmpty name="threadPreNextList" >   
                      <ul style="list-style-type:none;padding:0">
                      <logic:iterate id="forumThreadPreNext" name="threadPreNextList" length="5">
                        <li class="box">
                          <section style="display: flex; align-items: center;"> 
                          <div class="wrap-vid">              
                                 <logic:notEmpty name="forumThreadPreNext" property="rootMessage.messageUrlVO.imageUrl">                  
                                   <div class="thumbn">
                                        <img src="<bean:write name="forumThreadPreNext" property="rootMessage.messageUrlVO.imageUrl"/>" border='0' class="img-thumbnail" loading="lazy" width="45" height="45" onerror="this.src='/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(3)%>.jpg'"/>    
                                   </div>
                                 </logic:notEmpty>
                                  
                                 <logic:notEmpty name="forumThreadPreNext" property="rootMessage.messageUrlVO.thumbnailUrl">                  
                                   <div class="thumbn">
                                     <img src="<bean:write name="forumThreadPreNext" property="rootMessage.messageUrlVO.thumbnailUrl"/>" border='0' class="img-thumbnail" loading="lazy" width="45" height="45"/>                  
                                   </div>  
                                 </logic:notEmpty>              
                           </div>
                          <div class="vid-name" style="margin-left: 2px; flex: 1;"><a href="<%=domainUrl %>/<bean:write name="forumThreadPreNext" property="threadId"/>.html" class="hover-preload"><bean:write name="forumThreadPreNext" property="name"/></a></div>
                       
    
                 
                           </section>
                          </li>
                      </logic:iterate>  
                    </ul>
                    </logic:notEmpty>

                  </div>   
                 
							                           
                
              </div> 
        </div>
      </div>     

      <div class="widget">
        <div class="content">
      <h4>本文话题：</h4>
          <ul class="list-inline">
        
            <logic:iterate id="threadTag" name="forumThread" property="tags" >
              <li class="tagcloud">
                <a href="<%=domainUrl %>/tag/<bean:write name="threadTag" property="tagID"/>/" target="_blank" class="tag-cloud-link" class="hover-preload">
                  <bean:write name="threadTag" property="title" /></a>
              </li>
           
            </logic:iterate>

          </ul>
        </div>
      </div>

      <!-- Start Widget -->
     <div class="widget">
        <div class="wid-vid">
          
                <div class="scrolldiv">
							   <div style="width: 300px"> 
<!-- 自适应主广告 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="5184711902"
     data-ad-format="auto"
     data-full-width-responsive="true"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>		                
                 </div> 
							 </div>   
         
        </div>
      </div>

    </div>
</aside>

</div>
</div>

  <div id="reply" style="display:none">
    <input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
    <input type="hidden" id="replySubject" name="replySubject" value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>">
  </div>



<%@include file="../common/IncludeBottomBody.jsp"%>

<script defer>  
document.addEventListener("DOMContentLoaded", function(event) { 
$(document).ready(function() {
  $(document).one('mousemove touchstart', function() {
    $.post("/forum/viewThread.shtml?threadId=<bean:write name="forumThread" property="threadId"/>");
  });
 });
});
</script>
 
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>

<script type="speculationrules">
  {
    "prerender": [{
      "source": "document",
      "where": {
        "and": [
          { "selector_matches": ".hover-preload" },
          { "not": { "selector_matches": ".do-not-prerender" } }
        ]
      },
      "eagerness": "moderate"
    }]
  }
  </script>
</body>
</html>
