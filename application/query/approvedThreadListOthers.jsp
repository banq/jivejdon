<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<logic:empty name="threadListForm" property="list">
<% 
  response.sendError(204);  
  %>
</logic:empty>

<bean:define id="title"  value=" 最新最热的趋势与教程 " />
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
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
   <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>	
   <%@include file="../common/IncludeTopHead.jsp"%>
 <meta name="Description" content="近期最新又最佳的热点趋势与编程教程">
<meta name="Keywords" content="编码经典,又新又好教程,技术热点,IT新闻,产品经理,产品新闻,科技新闻,前沿大技术,未来大技术,架构动态,SpringBoot,微服务,软件架构,企业架构,系统设计,架构设计,企业IT,IT设计,IT平台,平台工程,Devops,运维,维护工程,敏捷,软件工程,程序工艺,编码手艺,码农">
<link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
<link rel="alternate" type="application/rss+xml" title="汲道订阅" href="/rss">     
 

   <%if(pagestartInt != 0 ) {%> 
        <%if(pagestartInt-pagecountInt>0 ) {%>  
            <link rel="prev" href="/approval/<%=(pagestartInt-pagecountInt)%>"/>
        <%}else{%>
            <link rel="prev" href="/approval/"/>
         <%}%>
        <%}%>

   <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/approval/<%=pagestartInt%>"> 
   <% }else{%>
          <link rel="canonical" href="/approval/">  
   <% }%>      

         <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
            <link rel="next" href="/approval/<%=pagestartInt+pagecountInt%>"/>
         <%}%>

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
  <li><a href="<%=request.getContextPath()%>/threads/">最新</a></li>
  <li class="active"><a href="#">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/threadDigSortedList/">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/maxPopThreads/">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/random/threadRandomList.shtml" >
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>

</ul>   


<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/approval/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/approval/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/approval/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>
</div>

</div>
</div>


<div class="list-group">

 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">
  <%@ include file="../forum/threadListCore.jsp" %>
 </logic:iterate>

</div>



<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/approval/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/approval/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/approval/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>
</div>

</div>
</div>


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
</body>
</html>
