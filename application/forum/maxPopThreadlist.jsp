<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheader" name="noheader"  value=""/>


<logic:empty name="threadListForm" property="list">
<% 
  response.setHeader("X-Robots-Tag", "noindex");  // 防止被搜索引擎索引
  response.setStatus(HttpServletResponse.SC_GONE);  // 使用 410 表示该页面已被永久删除
  %>
</logic:empty>


<bean:define id="title"  value=" 精华教程 " />
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
   <meta name="Description" content="编程精华历史与网友编程实践精彩观点">
<meta name="Keywords" content="Java心得,实战经验,Java最佳实践,Java模式,Java实战,数据库,ORM,struts,j2se,JDK,java6,hibernate,EJB,j2ee,Javaee,it,设计模式,weblogic,spring,工作流,权限,ioc,依赖注射">
<link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl%>/sitemap.xml">
<link rel="canonical" href="<%=domainUrl%>/maxPopThreads/">  
  

   <%if(pagestartInt != 0 ) {%> 
        <%if(pagestartInt-pagecountInt>0 ) {%>  
            <link rel="prev" href="<%=domainUrl%>/maxPopThreads/<%=(pagestartInt-pagecountInt)%>"/>
        <%}else{%>
            <link rel="prev" href="<%=domainUrl%>/maxPopThreads/"/>
         <%}%>
        <%}%>
 
         <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
            <link rel="next" href="<%=domainUrl%>/maxPopThreads/<%=pagestartInt+pagecountInt%>"/>
         <%}%>
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
				<div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)">	
<ul class="nav nav-tabs">
  <li ><a href="<%=domainUrl%>/threads/">最新</a></li>
  <li><a href="<%=domainUrl%>/approval/">新佳</a></li>
  <li><a href="<%=domainUrl%>/threadDigSortedList/">最佳</a></li>	
  <li class="active"><a href="#">精华</a></li>
  <li><a href="<%=domainUrl%>/random/threadRandomList.shtml" rel="nofollow">
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
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
 
 

<div class="box">
  <div class="row">
  
  <div class="col-lg-4">
   <%if(pagestartInt != 0 ) {%> 
   <ul class="pagination pull-left">
      <li>
      <%if(pagestartInt-pagecountInt>0 ) {%>  
          <a href="<%=domainUrl%>/maxPopThreads/<%=(pagestartInt-pagecountInt)%>" rel="prev nofollow" class="btn-page">上页</a>
      <%}else{%>
          <a href="<%=domainUrl%>/maxPopThreads/" rel="prev" class="btn-page">上页</a>
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
      <a href="<%=domainUrl%>/maxPopThreads/<%=pagestartInt+pagecountInt%>" rel="next nofollow" class="btn-page">下页</a>
      <%}%>
    </li>  
  </ul>
  </div>
  
  </div>
  </div>

      
	    	         
				</div>
    </div>	

  <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-lg-4"><div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1); background-color: white; overflow: hidden; padding-left: 0; padding-right: 0">
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

