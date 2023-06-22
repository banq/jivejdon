<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>


<bean:define id="threadList" name="threadListForm" property="list" />
<logic:empty name="threadListForm" property="oneModel">
  <% 
  response.sendError(404);  
  %>
</logic:empty>
<bean:define id="forum" name="threadListForm" property="oneModel"/>
<bean:define id="title" name="forum" property="name" />
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
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script> 
<script language="javascript" defer="defer" src="<html:rewrite page="/forum/js/threadList.js"/>"></script>
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-12">
				<div class="box">	
<ul class="nav nav-tabs">
  <li ><a href="<%=request.getContextPath()%>/threads">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval">新佳</a></li>
  <li class="active"><a href="#">最佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" rel="nofollow">搜索</a></li>
</ul>          
<ul class="pagination pull-right">
        <logic:empty name="forum" property="forumId">
          <MultiPagesREST:pager actionFormName="threadListForm" page="/forum/threadDigSortedList" >
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="8" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>
        </logic:empty>
      
         有<b>
        <bean:write name="threadListForm" property="allCount"/>
        </b>贴
       
</ul>
                 
  
<%@ include file="threadListCore.jsp" %>


<ul class="pagination pull-right">
        <logic:empty name="forum" property="forumId">
          <MultiPagesREST:pager actionFormName="threadListForm" page="/forum/threadDigSortedList" >
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="8" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>
        </logic:empty>
      
         有<b>
        <bean:write name="threadListForm" property="allCount"/>
        </b>贴
       
</ul>
  <!-- 自适应主广告 -->
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

<%@ include file="../common/IncludeBottomBody.jsp" %> 


</body>
</html>

