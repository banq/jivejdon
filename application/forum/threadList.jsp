<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheader" name="noheader"  value=""/>

<logic:empty name="threadListForm" property="oneModel">
  <% 
  response.sendError(404);  
  %>
</logic:empty>
<logic:notEmpty name="threadListForm" property="oneModel">

<logic:empty name="threadListForm" property="list">
<% 
  response.sendError(204);  
  %>
</logic:empty>


<bean:define id="forum" name="threadListForm" property="oneModel"/>
<logic:empty name="forum" property="name">
<bean:define id="title" value="最新趋势与教程"/>
</logic:empty>
<logic:notEmpty name="forum" property="name">
<bean:define id="title" name="forum" property="name"/>
</logic:notEmpty>
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
<bean:define id="lastModifiedDate" name="forum" property="modifiedDate2"/>
<%

int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
int currentPageNo = 1;

if (pagecountInt > 0) {
	currentPageNo = (pagestartInt / pagecountInt) + 1;
}
String titleStr = (String)pageContext.getAttribute("title");
if (currentPageNo > 1){
	titleStr = titleStr + "  - 第"+ currentPageNo + "页";
}
pageContext.setAttribute("title", titleStr);
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
   
   
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
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="Description" content="最新免费在线编程教程">
<meta name="Keywords" content="最新技术,前沿技术,编码前沿,编程动态,Java21,Java22,JVM,最新Java,Java动态,Java版本发布,DDD最新,DDD源码,DDD实战,定义,数据分析,科技新闻,Java新闻,SpringBoot新闻,Java发布,SpringBoot发布,Java版本,SpringBoot版本,微服务,认知负担">
<link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
   
   <logic:empty name="forum" property="forumId">	
     
       <%if(pagestartInt != 0 ) {%> 
        <%if(pagestartInt-pagecountInt>0 ) {%>  
            <link rel="prev" href="/threads/<%=(pagestartInt-pagecountInt)%>"/>
        <%}else{%>
            <link rel="prev" href="/threads/"/>
         <%}%>
        <%}%>

        <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/threads/<%=pagestartInt%>"/> 
       <% }else{%>
          <link rel="canonical" href="/threads/"/> 
       <% }%>   

         <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
            <link rel="next" href="/threads/<%=pagestartInt+pagecountInt%>"/>
         <%}%>
   </logic:empty>
   <logic:notEmpty name="forum" property="forumId">		
      
       <%if(pagestartInt != 0 ) {%> 
        <%if(pagestartInt-pagecountInt>0 ) {%>  
            <link rel="prev" href="/forum/<bean:write name="forum" property="forumId"/>/<%=(pagestartInt-pagecountInt)%>"/>
        <%}else{%>
            <link rel="prev" href="/forum/<bean:write name="forum" property="forumId"/>"/>
         <%}%>
        <%}%>
        <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt%>"/> 
       <% }else{%>
          <link rel="canonical" href="/forum/<bean:write name="forum" property="forumId"/>"/> 
       <% }%>   

         <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
            <link rel="next" href="/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt+pagecountInt%>"/>
         <%}%>       
   </logic:notEmpty>         
 
<meta http-equiv="refresh" content="3600">
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
<div id="page-content" class="single-page container">
	<div class="row">
		<!-- /////////////////左边 -->
		<div id="main-content" class="col-lg-12">

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-8">
				<div class="box">	
<ul class="nav nav-tabs">        
  <li class="active"><a href="#">最新</a></li>  
  <li><a href="<%=request.getContextPath()%>/approval/">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/threadDigSortedList/">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/maxPopThreads/">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/random/threadRandomList.shtml" >
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>
</ul>           


 
<logic:empty name="forum" property="forumId">	
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/threads/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/threads/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/threads/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>
</div>

</div>
</div>
 </logic:empty>
<logic:notEmpty name="forum" property="forumId">		
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/forum/<bean:write name="forum" property="forumId"/>/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/forum/<bean:write name="forum" property="forumId"/>/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>
</div>

</div>
</div>
</logic:notEmpty>  



<ul class="list-group" style="list-style-type:none;padding:0">

 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">
 <logic:equal name="i" value="2">

<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-ev-1p-5j-ot+26n"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3378777426"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
 </logic:equal>
    <%@ include file="threadListCore.jsp" %>

 </logic:iterate>

</ul>


   
  <div id="nextPageContent"></div>
  <ul class="nav nav-tabs">
  <li class="active"><a href="#">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval/">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/threadDigSortedList/">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/maxPopThreads/">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/random/threadRandomList.shtml" >
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>                
  </ul>   
 
 
<logic:empty name="forum" property="forumId">	
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/threads/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/threads/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/threads/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>
</div>

</div>
</div>
 </logic:empty>
<logic:notEmpty name="forum" property="forumId">		
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/forum/<bean:write name="forum" property="forumId"/>/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/forum/<bean:write name="forum" property="forumId"/>/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>
</div>

</div>
</div>
</logic:notEmpty>  

  

				</div>
            </div>	
      <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-lg-4">
        <!---- Start Widget ---->
        <div class="widget wid-follow">
          <div class="content">
            <ul class="list-inline">
              <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
						 <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
					 </form>
            </ul>           
          </div>
        </div>
        <!---- Start tags ---->
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

        <!---- Start Widget ---->
      <div class="widget">
        <div class="wid-vid">
				<div id="digList" class="linkblock"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
				 <script defer>
					document.addEventListener("DOMContentLoaded", function(event) { 
                                 $(document).ready(function() {      
                                    $('#digList').load("/query/threadDigList");                                
                                 });            
                         });  
                  </script>           
          </div>
        </div>


        <!---- Start Widget ---->
       <div class="widget">
           <div class="wid-vid">
							    <div class="scrolldiv">
							     <div style="width: 300px"> 

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
          </div>
        </div>
 
    </div>

</aside>         
	</div>
</div>

</main>

<%@ include file="../common/IncludeBottomBody.jsp" %> 

</div>
</div>
</div>
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>

</body>
</html>

</logic:notEmpty>