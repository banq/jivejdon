<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>
<logic:empty  name="TITLE">
<%response.sendError(404);%>
</logic:empty>   
<logic:notEmpty  name="TITLE">
<bean:define id="title" name="TITLE" />
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
   <meta name="Description" content="有关<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>系列文章">
<meta name="Keywords" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>,最佳实践,教材,论文,文章,技巧,模式,编程心得,面试,设计">
<link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
  
<% if (request.getParameter("r") == null){ %>  
<%if(pagestartInt != 0 ) {%> 
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <link rel="prev" href="/tag-<bean:write name="tagID"/>/<%=(pagestartInt-pagecountInt)%>"/>
    <%}else{%>
        <link rel="prev" href="/tag-<bean:write name="tagID"/>/"/>
     <%}%>
 <%}%>
 
   <%if (currentPageNo > 1) {%>
         <link rel="canonical" href="/tag-<bean:write name="tagID"/>/<%=pagestartInt%>"/> 
   <% }else{%>
          <link rel="canonical" href="/tag-<bean:write name="tagID"/>/"/>  
   <% }%>    

 <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <link rel="next" href="/tag-<bean:write name="tagID"/>/<%=pagestartInt+pagecountInt%>"/>
 <%}%>
<% } %>  

<link rel="alternate" type="application/rss+xml" title="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>" href="/tag-<bean:write name="tagID"/>/rss"/>		

<meta http-equiv="refresh" content="3600">
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
 
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
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-lg-12">
        <div class="box"> 
       <center>
        
        <h2 class="tagcloud"><a href="/tag-<bean:write name="tagID"/>/" class="tag-cloud-link"><bean:write  name='TITLE'/></a></h2>
  
      <div>
       
             <%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
           
                 <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  ><i class="fa fa-heart"></i></a>    
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本标签" border="0" /></a>                                                         
            <%
            }
        %>
      
        
       &nbsp;&nbsp;       
		   <a href="/tag-<bean:write name="tagID"/>/rss"><i class="fa fa-feed"></i></a>
       &nbsp;&nbsp;  
       <a href="/random/taggedThreadList.shtml?tagID=<bean:write name="tagID"/>&count=15&r=1">
       <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
       </a>

       </div>

    
		  
      </center>


<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
  <%@ include file="threadListCore.jsp" %>
</logic:iterate>


        </div>


 

<% if (request.getParameter("r") == null){ %>  

<div class="box">
<div class="row">
<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
 <span class="pull-left">
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="/tag-<bean:write name="tagID"/>/<%=(pagestartInt-pagecountInt)%>" >上页</a>
    <%}else{%>
        <a href="/tag-<bean:write name="tagID"/>/" >上页</a>
     <%}%>
 </span>
 <%}%>
</div>
<div class="col-lg-4"></div>
<div class="col-lg-4">
<span class="pull-right"> 
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="/tag-<bean:write name="tagID"/>/<%=pagestartInt+pagecountInt%>" >下页</a>
    <%}%>
</span>

</div>
</div>
</div>
<% } %>  

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


<%@ include file="../common/IncludeBottomBody.jsp" %> 


</body>
</html>

</logic:notEmpty>