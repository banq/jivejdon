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
<logic:empty name="threadListForm" property="list">
<% 
  response.setHeader("X-Robots-Tag", "noindex");  // 防止被搜索引擎索引
     response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 使用 410 表示该页面已被永久删除
  %>
</logic:empty>
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
   <link rel="preconnect" href="https://pagead2.googlesyndication.com" crossorigin>
  <link rel="dns-prefetch" href="https://tpc.googlesyndication.com" >
  <link rel="dns-prefetch" href="https://googleads.g.doubleclick.net" >
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>     
   <%@include file="../common/IncludeTopHead.jsp"%>

   <meta name="Description" content="有关<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>系列">
<meta name="Keywords" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>">
<link rel="canonical" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/"/>  
<% if (request.getParameter("r") == null){ %>  
<%if(pagestartInt != 0 ) {%> 
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <link rel="prev" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=(pagestartInt-pagecountInt)%>"/>
    <%}else{%>
        <link rel="prev" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/"/>
     <%}%>
 <%}%>
 <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <link rel="next" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=pagestartInt+pagecountInt%>"/>
 <%}%>
<% } %>  

<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>

<%
String start = "0";
if (request.getParameter("start")!=null){
   start = request.getParameter("start");
}
String offset = "0";
if (request.getParameter("offset")!=null){
   offset = request.getParameter("offset");
}
String count = "5";
if (request.getParameter("count")!=null){
   count = request.getParameter("count");
}
%>
<script>
// JavaScript初始化随机数组
var randomArr = [0, 1, 2, 3, 4];
for (var i = randomArr.length - 1; i > 0; i--) {
    var j = Math.floor(Math.random() * (i + 1));
    var temp = randomArr[i];
    randomArr[i] = randomArr[j];
    randomArr[j] = temp;
}
var randomIdx = 0;

function getRandomThumb() {
    return (randomIdx < 5) ? randomArr[randomIdx++] : Math.floor(Math.random() * 5);
}
</script>



<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
 


<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-lg-12">
        <div style="border-radius:12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)" class="box">
        <div class="box"> 
        


  

<main>
  <div class="row">	
      <div class="col-md-4">
       <div style="position: relative;padding-top:20px">           
       <a href='<%=request.getContextPath() %>/tag/<bean:write name="threadTag" property="tagID"/>/'>
        <img id="home-thumbnai" src="" border="0" class="img-thumbnail" style="width: 100%" loading="lazy"/>
       </a>
       <script>
document.getElementById('home-thumbnai').src = '/simgs/thumb2/' + getRandomThumb() + '.jpg';
</script>
      <div style="position: absolute;top: 0px;">
       <div class="tagcloud">
        <h1 class="tagcloud bige20"><a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/" class="tag-cloud-link"><bean:write  name='TITLE'/></a></h1>
  
       </div>
       </div> 
      </div>
      </div>
      <div class="col-md-8">

        
<logic:iterate  id="forumThread" name="threadListForm" property="list" length="2" >
  
      
 <ul style="list-style-type:none;padding:0px" itemscope itemtype="https://schema.org/ItemList">
 <div class="row">	

 <div class="col-sm-12" style="padding:0px">
<li class="box" itemprop="itemListElement" itemscope itemtype="https://schema.org/ListItem">	
     <div>	             
         <div class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
          <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" class="hover-preload" itemprop="url"><h3 itemprop="name"><bean:write name="forumThread" property="name"/></h3></a>
         </div>
         <div style="color: #554646; display:flex; align-items:flex-start; justify-content:space-between; margin-top: 10px">              
            <span style="flex:1; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical;overflow:hidden">           
              <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[150]" />
             </span>  
             
            </div>
  </div>	
</li>  
            </div>

          

  </div>
  </ul>

</logic:iterate>

      </div>
  </div>

<ul style="list-style-type:none;padding:0px">

<jsp:include page="/query/taggedThreadList2.shtml" flush="true">
    <jsp:param name="offset" value="2"/>
    <jsp:param name="count" value="<%=count%>"/>
</jsp:include>

<div id="loadMoreBox"></div>

 </ul>

</main>

<%-- 
<script>
document.addEventListener("DOMContentLoaded", function(event) {   
$(function() {
  var loading = false;
  var done = false;

  var start = <%=start%> +  <%=count%>; 
  var scrollTimer = null;

  function loadMore() {
    if (loading || done) return;
    loading = true;

    var url = '/query/taggedThreadList2.shtml?tagID=<bean:write name="tagID"/>&count=2&start=' + start;

    $.get(url, function(html) {
      html = $.trim(html);
      if (html) {
        $('#loadMoreBox').before(html);

        // 每次加载后更新 offset 和 count
        start += 2;
      } else {
        done = true; // 没数据了
      }
      loading = false;
    }).fail(function() {
      loading = false;
    });
  }

  // 滚动检测 + 节流
  $(window).on('scroll', function() {
    if (scrollTimer) clearTimeout(scrollTimer);

    scrollTimer = setTimeout(function() {
      if (loading || done) return;

      var box = $('#loadMoreBox');
      if (!box.length) return;

      var boxTop = box.offset().top;
      var scrollBottom = $(window).scrollTop() + $(window).height();

      if (scrollBottom + 100 >= boxTop) {
        loadMore();
      }
    }, 200); // 200ms 节流
  });
});
});
</script> --%>

        </div>


<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=(pagestartInt-pagecountInt)%>" rel="prev nofollow" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/" rel="prev" class="btn-page">上页</a>
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
    <a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=pagestartInt+pagecountInt%>" rel="next nofollow" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>
</div>

</div>
</div>

      </div>  

  </div>
</div>  
</div>  


<%@ include file="../common/IncludeBottomBody.jsp" %> 


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