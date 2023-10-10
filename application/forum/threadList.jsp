<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheader" name="noheader"  value=""/>

<bean:define id="threadList" name="threadListForm" property="list" />
<logic:empty name="threadListForm" property="oneModel">
  <% 
  response.sendError(404);  
  %>
</logic:empty>
<bean:define id="forum" name="threadListForm" property="oneModel"/>
<logic:empty name="forum" property="name">
<bean:define id="title" value="最新列表"/>
</logic:empty>
<logic:notEmpty name="forum" property="name">
<bean:define id="title" name="forum" property="name"/>
</logic:notEmpty>
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="lastModifiedDate" name="forum" property="modifiedDate2"/>
<%

int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
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
   <logic:empty name="forum" property="forumId">	
       <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/threads/<%=pagestartInt%>"> 
       <% }else{%>
          <link rel="canonical" href="/threads"> 
       <% }%>   
   </logic:empty>
   <logic:notEmpty name="forum" property="forumId">		
        <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt%>"> 
       <% }else{%>
          <link rel="canonical" href="/forum/<bean:write name="forum" property="forumId"/>/"> 
       <% }%>   
   </logic:notEmpty>         
<link rel="alternate" type="application/rss+xml" title="<bean:write name="title" /> " href="/rss" /> 
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
		<div id="main-content" class="col-md-12">

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-8">
				<div class="box">	
<ul class="nav nav-tabs">        
  <li class="active"><a href="#">最新</a></li>  
  <li><a href="<%=request.getContextPath()%>/approval">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/random/threadRandomList.shtml" >
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>
</ul>           
 <ul class="pagination pull-right">
  		 <logic:empty name="forum" property="forumId">						        
          <MultiPagesREST:pager actionFormName="threadListForm" page="/threads" >
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="8" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>          
        </logic:empty>
        <logic:notEmpty name="forum" property="forumId">			    
          <MultiPagesREST:pager actionFormName="threadListForm" page="/forum" paramId="forum" paramName="forum" paramProperty="forumId">
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="8" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>
           有<b><bean:write name="threadListForm" property="allCount"/></b>贴          
        </logic:notEmpty>           
  </ul> 
                  


<div class="list-group">

 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">

    <%@ include file="threadListCore.jsp" %>

 </logic:iterate>

</div>


   
  <div id="nextPageContent"></div>
  <ul class="nav nav-tabs">
  <li class="active"><a href="#">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/random/threadRandomList.shtml" >
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>                
  </ul>   
  <ul class="pagination pull-right">
  		 <logic:empty name="forum" property="forumId">						        
          <MultiPagesREST:pager actionFormName="threadListForm" page="/threads" >
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="8" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>          
        </logic:empty>
        <logic:notEmpty name="forum" property="forumId">			    
          <MultiPagesREST:pager actionFormName="threadListForm" page="/forum" paramId="forum" paramName="forum" paramProperty="forumId">
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="8" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>
           有<b><bean:write name="threadListForm" property="allCount"/></b>贴          
        </logic:notEmpty>           
  </ul> 
  

				</div>
            </div>	
      <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-md-4">
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
						
							  <div id="tagHotList"><br><br><br><br><br><br><br><br><br><br></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>

							</div>
				</div>

        <!---- Start Widget ---->
      <div class="widget">
        <div class="wid-vid">
							    <div id="digList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>

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

</body>
</html>

