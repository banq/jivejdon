<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">

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
	int expire =5 * 24 * 60 *60;
  long modelLastModifiedDate = com.jdon.jivejdon.presentation.action.util.ForumUtil.getForumsLastModifiedDate(this.getServletContext());
	if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
		return ;
	}

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
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 自动调整尺寸 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="9040920314"
     data-ad-format="auto"></ins>
<script>
    (adsbygoogle = window.adsbygoogle || []).push({});
</script>
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-12">
				<div class="box">	
			 <logic:empty name="forum" property="forumId">
					<ul class="nav nav-tabs">
  <li class="active"><a href="#">时间</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">回复</a></li>
            <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">点赞</a></li>
            <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" rel="nofollow">搜索</a></li>
	               <div class="tres" style="float: right;">
     
          <MultiPagesREST:pager actionFormName="threadListForm" page="/threads" >
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="3" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>
        </logic:empty>
        <logic:notEmpty name="forum" property="forumId">
			<div class="tres" style="float: right;">
          <MultiPagesREST:pager actionFormName="threadListForm" page="/forum" paramId="forum" paramName="forum" paramProperty="forumId">
            <MultiPagesREST:prev name=" 上一页 " />
            <MultiPagesREST:index displayCount="3" />
            <MultiPagesREST:next  name=" 下一页 " />
          </MultiPagesREST:pager>
        </logic:notEmpty>
         有<b>
        <bean:write name="threadListForm" property="allCount"/>
        </b>贴
     
      </div>
	</ul>              
</logic:notEqual>    
   <%@ include file="threadListCore.jsp" %>
<logic:notEqual name="noheader" value="on">       
  
  <bean:define id="pagestart" name="threadListForm" property="start" />
  <bean:define id="pagecount" name="threadListForm" property="count" />
  <bean:define id="pageallCount" name="threadListForm" property="allCount" />
  <%  
    int pageStartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
    int pageCountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int pageNo = (pageAllcountInt / pageCountInt);
    if(pageAllcountInt % pageCountInt !=0){ 
        pageNo = pageNo + 1;
    }
    
  %>
  <div id="nextPageContent"></div>
  <div id="nextPage"></div> 
	<div class="tres">
                    <logic:empty name="forum" property="forumId">
                      <MultiPagesREST:pager actionFormName="threadListForm" page="/threads" >
                        <MultiPagesREST:prev name=" 上一页 " />
                        <MultiPagesREST:index displayCount="3" />
                        <MultiPagesREST:next  name=" 下一页 " />
                      </MultiPagesREST:pager>
                    </logic:empty>
                    <logic:notEmpty name="forum" property="forumId">
                      <MultiPagesREST:pager actionFormName="threadListForm" page="/forum" paramId="forum" paramName="forum" paramProperty="forumId">
                        <MultiPagesREST:prev name=" 上一页 " />
                        <MultiPagesREST:index displayCount="3" />
                        <MultiPagesREST:next  name=" 下一页 " />
                      </MultiPagesREST:pager>
                    </logic:notEmpty>
                  </div>
				</div>
            </div>	
	</div>
</div>
<%@ include file="../common/IncludeBottomBody.jsp" %> 
<script>
  document.getElementById("nextPage").value = "<%=pageStartInt+pageCountInt%>";
  $(window).scroll(function() {
    var hT = $('#nextPage').offset().top,
       hH = $('#nextPage').outerHeight(),
       wH = $(window).height(),
       wS = $(this).scrollTop();
    if (wS > (hT+hH-wH)){
           var start = document.getElementById("nextPage").value ;           
           load('/forum/threadList.shtml?start='+ start +'&count=<%=pageCountInt%>&noheader=on', function (xhr) {
               document.getElementById("nextPageContent").innerHTML = document.getElementById("nextPageContent").innerHTML + xhr.responseText;
               document.getElementById("nextPage").value = document.getElementById("nextPage").value/1 + <%=pageCountInt%>;                              
            }); 
    }
   });
  </script>   
</body>
</html>
</logic:notEqual>    
