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
  <%
    int pagestartInt = ((Integer) pageContext.getAttribute("pagestart")).intValue();
    int pagecountInt = ((Integer) pageContext.getAttribute("pagecount")).intValue();
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
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="/js/jdon.css"  type="text/css">  
    <script defer src="/js/jquery-bootstrap2.js"></script>
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>	
    <link rel="dns-prefetch" href="//googleads.g.doubleclick.net">
    <link rel="dns-prefetch" href="//partner.googleadservices.com">
    <link rel="dns-prefetch" href="//analytics.google.com">
    <link rel="dns-prefetch" href="//tpc.googlesyndication.com">
    <link rel="dns-prefetch" href="//www.google-analytics.com">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
      <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/post/<bean:write name="forumThread" property="threadId" />/<%=pagestartInt%>"> 
       <% }else{%>
          <link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" />.html">  
       <% }%>   
    
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

<main>
<div id="page-content" class="single-page container">
  <div class="row">
    <!-- /////////////////左边 -->
    <div id="main-content" class="col-md-8">
      <div class="box">

        <!--  内容-->
        <div id="messageListBody">
 
          <%@include file="messageListBody.jsp" %>
                  
        </div>

        <div id="pageEnd"></div>
  
  <!-- 导航区  -->
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
              <div class="box">
                <ul class="pagination pull-right">
                  有<b><bean:write name="messageListForm" property="numPages"/></b>页 
                  <MultiPagesREST:pager actionFormName="messageListForm" page="/post" paramId="thread" paramName="forumThread" paramProperty="threadId">
                    <MultiPagesREST:prev name=" 上一页 "/>
                    <MultiPagesREST:index displayCount="6"/>
                    <MultiPagesREST:next name=" 下一页 "/>
                  </MultiPagesREST:pager>
                </ul>
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
    <div id="sidebar" class="col-md-4">
      <!-- Start Widget -->
      <div class="widget wid-follow">
        <div class="content">
          <ul class="list-inline">
            <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
              <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
            </form>
          </ul>
        </div>
      </div>
			<div class="widget">
        <div class="wid-vid">
              
                <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
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
        
                 <logic:notEmpty name="forumThread" property="tags">          
                  <div id="threadTagList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>             
                   <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/forum/threadTagList.shtml?othread=<bean:write name="forumThread" property="threadId"/>&threadId=<bean:write name="forumThread" property="threadId"/>', function (xhr) {				
  	                                  document.getElementById("threadTagList").innerHTML = xhr.responseText;
			                     });
							     </script> -->
							   </div>	
                 </logic:notEmpty>		
                 <logic:empty name="forumThread" property="tags">    
                  <div id="digList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
							     <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							      </script> -->
							     </div>
                </logic:empty>   				   
          
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

</main>

<%@include file="../common/IncludeBottomBody.jsp"%>

 
  </body>
  </html>
