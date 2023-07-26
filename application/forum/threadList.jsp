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
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="alternate" type="application/rss+xml" title="<bean:write name="title" /> " href="/rss" /> 
<meta http-equiv="refresh" content="3600">
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>

<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-12">
				<div class="box">	
<ul class="nav nav-tabs">        
  <li class="active"><a href="#">最新</a></li>  
  <li><a href="<%=request.getContextPath()%>/approval">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" >搜索</a></li>  
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
                  

<%@ include file="threadListCore.jsp" %>


   
  <div id="nextPageContent"></div>
  <ul class="nav nav-tabs">
  <li class="active"><a href="#">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" >搜索</a></li>                 
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
  <div class="row">
    <div class="col-sm-12">
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
    </div>         
 </div>

				</div>
            </div>	
	</div>
</div>

</main>
<%@ include file="../common/IncludeBottomBody.jsp" %> 

</body>
</html>

