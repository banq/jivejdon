<%@ page session="false" %>
<%@page isELIgnored="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<bean:define id="title"  value="话题系列" />
<bean:define id="pagestart" name="tagsListForm" property="start" />
<bean:define id="pagecount" name="tagsListForm" property="count" />
<bean:define id="pageallCount" name="tagsListForm" property="allCount" />
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
   	
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="robots" content="noindex">
   <meta name="Description" content="编程 软件架构 教程">
   <meta name="Keywords" content="Java教程,SpringBoot教程,程序,算法,模式,数据库,计算机科学,面试技巧,程序员职场,幽默,meme,Python,Java,JavaScript,安卓,SQL,数据科学,机器学习,Web开发,系统设计,技术博客,面试,HTML,CSS,golang,Rust">
  <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
  <link rel="canonical" href="https://www.jdon.com/tag/"/>  
 
<%if(pagestartInt != 0 ) {%> 
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <link rel="prev" href="/tag/page/<%=(pagestartInt-pagecountInt)%>"/>
    <%}else{%>
        <link rel="prev" href="/tag/"/>
     <%}%>
 <%}%>          
 <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <link rel="next" href="/tag/page/<%=pagestartInt+pagecountInt%>"/>
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
		<div id="main-content" class="col-md-12">

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<bean:parameter name="queryType" id="queryType" value=""/>

<logic:present name="tagsListForm">
<logic:greaterThan name="tagsListForm" property="allCount" value="0">


<main>
<div class="row">
  <div class="col-md-12">
    <div class="box"> 



<%
int i = 3;
int h = 0 ;
%>

<logic:iterate id="threadTag" name="tagsListForm" property="list" >
 <%
  if(i % 3==0){ 
 %>
 <div class="row">	
 <%}%>

 <div class="col-md-4">
 <div class="box">	
  <div class="linkblock">
                
     <div style="position: relative;">           
       <a href='<%=request.getContextPath() %>/tag/<bean:write name="threadTag" property="tagID"/>/' title="<bean:write name="threadTag" property="title" />">         
        <img id="home-thumbnai" src="/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(5)%>.jpg" border="0" class="img-thumbnail" style="width: 100%" loading="lazy"/>                  
       </a>
      <div style="position: absolute;bottom: 0px;">
       <div class="tagcloud">
        <a href='<%=request.getContextPath() %>/tag/<bean:write name="threadTag" property="tagID"/>/' class="tag-cloud-link">
		    <bean:write name="threadTag" property="title" /></a>
       </div>
       </div> 
      </div>
	<div id='ajax_<bean:write name="threadTag" property="tagID"/>' >
  <br><br><br><br><br><br>     
  </div>  
  

        <script defer>
          document.addEventListener("DOMContentLoaded", function(event) { 
              fetch('/query/tt/${threadTag.tagID}')
              .then(response => {
                if (!response.ok) {
                  throw new Error('Network response was not ok');
                }
                return  response.text();
              })
              .then(html => {
                 document.getElementById('ajax_<bean:write name="threadTag" property="tagID"/>').innerHTML = html;
              })
              .catch(error => {
                console.error('Fetch error:', error);
              });
          });    
        </script>

	  

	</div>	
</div>  
</div>

 
<% i = i+1;%>
<%
  if(i % 3==0){ 
 %>
  </div>
 <%}%>

</logic:iterate>

<%
  if(i % 3 !=0){ 
 %>
  </div>
 <%}%>


<div class="box">
<div class="row">
<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/tag/page/<%=(pagestartInt-pagecountInt)%>" rel="prev" class="btn-page">上页</a>
    <%}else{%>
        <a href="/tag/" rel="prev" class="btn-page">上页</a>
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
    <a href="/tag/page/<%=pagestartInt+pagecountInt%>" rel="next" rel="next" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>

</div>
</div>
</div>

    </div>
  </div>
</div>

</main>


</logic:greaterThan>
</logic:present>

</div>
</div>
</div>

<%@include file="../common/IncludeBottomBody.jsp"%>

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
