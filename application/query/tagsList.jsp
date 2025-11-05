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
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<%
java.util.List nums = new java.util.ArrayList();
int[] randomArr = new int[5];
int idx = 0;
while (idx < 5) {
    nums.add(idx);
    idx++;
}
java.util.Collections.shuffle(nums);
idx = 0;
while (idx < 5) {
    randomArr[idx] = ((Integer)nums.get(idx)).intValue();
    idx++;
}
int randomIdx = 0;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
   <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script> 	
   <%@include file="../common/IncludeTopHead.jsp"%>
 
   
  <link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl%>/sitemap.xml">
  <link rel="canonical" href="<%=domainUrl%>/tag/"/>  
 
<%if(pagestartInt != 0 ) {%> 
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <link rel="prev" href="<%=domainUrl%>/tag/page/<%=(pagestartInt-pagecountInt)%>"/>
    <%}else{%>
        <link rel="prev" href="<%=domainUrl%>/tag/"/>
     <%}%>
 <%}%>          
 <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <link rel="next" href="<%=domainUrl%>/tag/page/<%=pagestartInt+pagecountInt%>"/>
 <%}%>

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
		<div id="main-content" class="col-md-12">
      <div style="border-radius:12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)" class="box">
      
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

<script>
  const ids = []; // 初始化一个空数组
</script>
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
        <img id="home-thumbnai" src="/simgs/thumb2/<%=(randomIdx < 5) ? randomArr[randomIdx++] : (int)(Math.random()*5)%>.jpg" border="0" class="img-thumbnail" style="width: 100%" loading="lazy"/>                  
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
  
  <script>
    ids.push(<bean:write name="threadTag" property="tagID"/>);
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

 <script>
  async function fetchData() {
    for (const id of ids) {
            try {
                const response = await fetch(`/query/tt/\${id}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const html = await response.text();
                document.getElementById(`ajax_\${id}`).innerHTML = html;
            } catch (error) {
                console.error('Fetch error:', error);
            }
    }
  }

  fetchData();    

</script>

<div class="box">
<div class="row">
<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl%>/tag/page/<%=(pagestartInt-pagecountInt)%>" rel="prev nofollow" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl%>/tag/" rel="prev" class="btn-page">上页</a>
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
    <a href="<%=domainUrl%>/tag/page/<%=pagestartInt+pagecountInt%>" rel="next" rel="next nofollow" class="btn-page">下页</a>
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
