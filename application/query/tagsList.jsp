<%@ page session="false" %>
<%@page isELIgnored="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<bean:define id="title"  value="编程教程全系列" />
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
   <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>	
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="Description" content="教程，免费在线教程，编码教程和面试问题">
   <meta name="Keywords" content="Java教程,SpringBoot教程,程序,算法,模式,数据库,计算机科学,面试技巧,程序员职场,幽默,meme,Python,Java,JavaScript,安卓,SQL,数据科学,机器学习,Web开发,系统设计,技术博客,面试,HTML,CSS,golang,Rust">
  <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
  <link rel="alternate" type="application/rss+xml" title="极道订阅" href="https://www.jdon.com/rss">
 
<%if(pagestartInt != 0 ) {%> 
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <link rel="prev" href="/tags/<%=(pagestartInt-pagecountInt)%>"/>
    <%}else{%>
        <link rel="prev" href="/tags/"/>
     <%}%>
 <%}%>
<%if (currentPageNo > 1) {%>
       <link rel="canonical" href="/tags/<%=pagestartInt%>"/>      
   <% }else{%>
         <link rel="canonical" href="/tags/"/>  
   <% }%>
          
 <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <link rel="next" href="/tags/<%=pagestartInt+pagecountInt%>"/>
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

<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-ev-1p-5j-ot+26n"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3378777426"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>

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
       <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" title="<bean:write name="threadTag" property="title" />">         
        <img id="home-thumbnai" src="/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(49)%>.jpg" border="0" class="thumbnail" style="width: 100%" loading="lazy"/>                  
       </a>
      <div style="position: absolute;bottom: 0px;">
       <div class="tagcloud">
        <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" class="tag-cloud-link">
		    <bean:write name="threadTag" property="title" /></a>
	      <a href="/tag-<bean:write name="threadTag" property="tagID"/>/rss"><i class="fa fa-feed"></i></a>
       </div>
       </div> 
      </div>
	<div id='ajax_<bean:write name="threadTag" property="tagID"/>' >
  <br><br><br><br><br><br>     
  </div>  
  <div class="lazyload" >
	    <!-- 
        <script>
         $('#ajax_<bean:write name="threadTag" property="tagID"/>').load("/query/tt/${threadTag.tagID}");        
        </script>
        -->
	  
  </div>  
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
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/tags/<%=(pagestartInt-pagecountInt)%>" rel="prev">上页</a>
    <%}else{%>
        <a href="/tags/" rel="prev">上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>
<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/tags/<%=pagestartInt+pagecountInt%>" rel="next">下页</a>
    <%}%>
</span>

</div>
</div>
</div>

    </div>
  </div>
</div>

</main>

<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-ev-1p-5j-ot+26n"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3378777426"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>

</logic:greaterThan>
</logic:present>

</div>
</div>
</div>

<%@include file="../common/IncludeBottomBody.jsp"%>

</body>
</html>
