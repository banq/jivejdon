<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%          
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCacheForum(1 * 30 * 60, this.getServletContext(), request, response)) {
    return ;
}	
%>

<bean:parameter id="noheader" name="noheader"  value=""/>

<logic:empty name="threadListForm" property="oneModel">
  <% 
  response.sendError(404);  
  %>
</logic:empty>
<logic:notEmpty name="threadListForm" property="oneModel">

<logic:empty name="threadListForm" property="list">
<% 
  response.setHeader("X-Robots-Tag", "noindex");  // 防止被搜索引擎索引
     response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 使用 410 表示该页面已被永久删除
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
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>	   
  <link rel="preconnect" href="https://pagead2.googlesyndication.com" crossorigin>
  <link rel="preconnect" href="https://tpc.googlesyndication.com" crossorigin>
  <link rel="preconnect" href="https://googleads.g.doubleclick.net" crossorigin>      
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="Description" content="最新列表">
   <link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl%>/sitemap.xml">

   <logic:empty name="forum" property="forumId">	
       <link rel="canonical" href="<%=domainUrl%>/threads/"/>    
       <%if(pagestartInt != 0 ) {%> 
        <%if(pagestartInt-pagecountInt>0 ) {%>  
            <link rel="prev" href="<%=domainUrl%>/threads/<%=(pagestartInt-pagecountInt)%>"/>
        <%}else{%>
            <link rel="prev" href="<%=domainUrl%>/threads/"/>
         <%}%>
        <%}%>

         <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
            <link rel="next" href="<%=domainUrl%>/threads/<%=pagestartInt+pagecountInt%>"/>
         <%}%>
   </logic:empty>
   <logic:notEmpty name="forum" property="forumId">		
       <link rel="canonical" href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>/"/>    
       <%if(pagestartInt != 0 ) {%> 
        <%if(pagestartInt-pagecountInt>0 ) {%>  
            <link rel="prev" href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>/<%=(pagestartInt-pagecountInt)%>"/>
        <%}else{%>
            <link rel="prev" href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>"/>
         <%}%>
        <%}%>
       
         <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
            <link rel="next" href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt+pagecountInt%>"/>
         <%}%>       
   </logic:notEmpty>         
 
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
<div id="ad-container" style="text-align: center; margin: 0 auto;">	
<!-- 728X90横幅 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="2308336581"
     data-ad-format="auto"
     data-full-width-responsive="true"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
</div>

<div id="page-content" class="single-page container">
	<div class="row">
		<!-- /////////////////左边 -->
		<div id="main-content" class="col-lg-12">

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-8 custom-col-left">
        <logic:notEmpty name="forum" property="name">
        <div class="box">
          <h1 style="font-size: 2.2rem"><bean:write name="title"/></h1>
        </div>
        </logic:notEmpty>
				<div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)">	
<ul class="nav nav-tabs">        
  <li class="active"><a href="#">最新</a></li>  
  <li><a href="<%=domainUrl%>/approval/">新佳</a></li>
  <li><a href="<%=domainUrl%>/threadDigSortedList/">最佳</a></li>            
  <li><a href="<%=domainUrl%>/maxPopThreads/">精华</a></li>
  <li><a href="<%=domainUrl%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>
</ul>           





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
  <li><a href="<%=domainUrl%>/approval/">新佳</a></li>
  <li><a href="<%=domainUrl%>/threadDigSortedList/">最佳</a></li>            
  <li><a href="<%=domainUrl%>/maxPopThreads/">精华</a></li>
  <li><a href="<%=domainUrl%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>                
  </ul>   
 
 
<logic:empty name="forum" property="forumId">	
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl%>/threads/<%=(pagestartInt-pagecountInt)%>" rel="prev" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl%>/threads/" rel="prev" class="btn-page">上页</a>
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
    <a href="<%=domainUrl%>/threads/<%=pagestartInt+pagecountInt%>" rel="next" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>
</div>

</div>
</div>
 </logic:empty>
<logic:notEmpty name="forum" property="forumId">		
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>/<%=(pagestartInt-pagecountInt)%>" rel="prev" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>/" rel="prev" class="btn-page">上页</a>
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
    <a href="<%=domainUrl%>/forum/<bean:write name="forum" property="forumId"/>/<%=pagestartInt+pagecountInt%>" rel="next" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>
</div>

</div>
</div>
</logic:notEmpty>  

  

				</div>
            </div>	
      <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-lg-4 custom-col-right">
<div class="scrolldiv"><div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1); background-color: white; overflow: hidden; padding-left: 0; padding-right: 0">
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
        </div>
 
    </div>

</div></aside>         
	</div>
</div>

</main>

<%@ include file="../common/IncludeBottomBody.jsp" %> 

</div>
</div>
</div>


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

</logic:notEmpty>