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
	int expire =5 * 60;
  long modelLastModifiedDate = com.jdon.jivejdon.presentation.action.util.ForumUtil.getForumsLastModifiedDate(this.getServletContext());
	if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
		return ;
	}

}
pageContext.setAttribute("title", titleStr);
%>
<%@ include file="../common/IncludeTop.jsp" %>
    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
    <script>
        (adsbygoogle = window.adsbygoogle || []).push({
            google_ad_client: "ca-pub-7573657117119544",
            enable_page_level_ads: true
        });
    </script>
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-32868073-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-32868073-1');
    </script>
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
<ul class="nav nav-tabs">        
  <li class="active"><a href="#">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval">最佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">最热</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最赞</a></li>            
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" rel="nofollow">搜索</a></li>  
  <div class="tres" style="float: right;">       
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
           有<b><bean:write name="threadListForm" property="allCount"/></b>贴          
        </logic:notEmpty>           
  </div>                                  
</ul>        
</logic:notEqual>    
   <%@ include file="threadListCore.jsp" %>
<logic:notEqual name="noheader" value="on">       
  <div id="nextPageContent"></div>
  <ul class="nav nav-tabs">
  <li class="active"><a href="#">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval">最佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">最热</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最赞</a></li>            
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" rel="nofollow">搜索</a></li>  
	  <div class="tres" style="float: right;">
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
  </ul>   

				</div>
            </div>	
	</div>
</div>
<%@ include file="../common/IncludeBottomBody.jsp" %> 
  
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
<%-- <%  
    int pageStartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
    int pageCountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int pageNo = (pageAllcountInt / pageCountInt);
    if(pageAllcountInt % pageCountInt !=0){ 
        pageNo = pageNo + 1;
    }    
%>
<script>
function scrollLoader(url){
  var start = "<%=pageStartInt+pageCountInt%>";
  var loading = false;
  $(window).scroll(function() {
    var hT = $('#nextPageContent').offset().top,
       hH = $('#nextPageContent').outerHeight(),
       wH = $(window).height(),
       wS = $(this).scrollTop();       
    if (wS > (hT+hH-wH) && !loading){           
         loading = true;          
         if (start <= <%=pageAllcountInt%> ){                  
           surl = (url.indexOf("?")==-1)?(url+"?"):(url+"&");           
           load(surl +'start=' + start +'&count=<%=pageCountInt%>&noheader=on', function (xhr) {
               document.getElementById("nextPageContent").innerHTML = document.getElementById("nextPageContent").innerHTML + xhr.responseText;               
               start = start/1 + <%=pageCountInt%>;                              
               loading = false;
           });          
         }   
    }
   });
}
<logic:notEmpty name="forum" property="name">
  scrollLoader('/forum/threadList.shtml?forumId=<bean:write name="forum" property="forumId"/>');      
</logic:notEmpty>
<logic:empty name="forum" property="name">
  scrollLoader('/forum/threadList.shtml');   
</logic:empty>
</script>    --%>
</body>
</html>
</logic:notEqual>    
