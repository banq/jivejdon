<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%          
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCacheForum(1 * 30 * 60, this.getServletContext(), request, response)) {
    return ;
}	
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
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">    
  <title>极道Jdon:一语道破科技玄机</title>
  <meta name="Description" content="极道Jdon带你探索科技世界，深析最新科技动态，道破科技玄机。">
  <meta name="Keywords" content="极道,Jdon,IT,科技,AI,AGI,智能,哲学,复杂性,思维,生物,极客">
  <link rel="stylesheet" href="/js/jdon.css">
  <style>
    html { font-size: 62.5%; -webkit-text-size-adjust: 100%; }
    body { margin: 0; padding: 0; font-family: "Microsoft YaHei", sans-serif; font-size: 1.6rem; color: #333; background-color: #E6E6E6; line-height: 2.2rem; }
    .container { margin: 0 auto; padding: 0 15px; }
    .row { margin: 0 -15px; }
    .row:after, .container:after { content: " "; display: table; clear: both; }
    .col-lg-12, .col-lg-8, .col-lg-4 { position: relative; min-height: 1px; padding: 0 15px; }
    @media (min-width: 768px) { .container { width: 750px; } }
    @media (min-width: 992px) { .container { width: 970px; } }
    @media (min-width: 1200px) {
        .container { width: 128rem; }
        .col-lg-8 { float: left; width: 66.66666667%; }
        .col-lg-4 { float: left; width: 33.33333333%; }
        .col-lg-12 { float: left; width: 100%; }
    }
    .navbar { min-height: 50px; margin-bottom: 20px; background-color: #000; }
  </style>
  <link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl%>/sitemap.xml">
  <link rel="canonical" href="<%=domainUrl%>/">
  <link rel="preload" href="/js/fonts/icomoon.woff" as="font" type="font/woff" crossorigin>
  <link rel="preload" href="/js/jquery-bootstrap2.js" as="script">
  <script defer src="/js/jquery-bootstrap2.js"></script> 
</head>
<body>
<%@ include file="./common/body_header.jsp" %>

<div id="page-content" class="single-page container">

<div style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);background-color: #fff;" class="box">
  
  <jsp:include page="/query/threadApprovedNewList.shtml" flush="true"></jsp:include>
	<div class="row">
  <main>	  	 
	  <div id="main-content" class="col-lg-8">

      <jsp:include page="/query/threadApprovedNewList2.shtml?offset=1&count=7" flush="true"></jsp:include>
      <jsp:include page="/query/threadApprovedNewList3.shtml?offset=0&count=1" flush="true"></jsp:include>        
	
      <jsp:include page="/query/threadApprovedNewList2.shtml?offset=7&count=13" flush="true"></jsp:include>
      <jsp:include page="/query/threadApprovedNewList3.shtml?offset=1&count=1" flush="true"></jsp:include>        
	
      <jsp:include page="/query/threadApprovedNewList2.shtml?offset=13&count=19" flush="true"></jsp:include>
      <jsp:include page="/query/threadApprovedNewList3.shtml?offset=2&count=1" flush="true"></jsp:include>        
	
      <jsp:include page="/query/threadApprovedNewList2.shtml?offset=19&count=25" flush="true"></jsp:include>
  
      <div id="loadMoreBox"></div>

      <div class="box"> 
	      <div class="tres center">        
          一语道破科技玄机：<a href="<%=domainUrl%>/approval/"><b>更多</b></a>	   
		    </div>
      </div>
		  
    </div>   
  </main> 

  <aside>  			
  <div id="sidebar" class="col-lg-4" style="padding-left:0px">
                <!---- Start Widget ---->
                <div class="widget">
                    <div class="box">
                        <ul class="list-inline">
              <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
                                <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
                            </form>
                        </ul>						
                    </div>

                    <div class="wid-vid">
                      <div id="newList"><jsp:include page="/query/threadNewList.shtml?count=10" flush="true"></jsp:include></div>   
                    </div>

                    <div class="wid-vid">
                      <div id="digList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
                      <script defer>
                        document.addEventListener("DOMContentLoaded", function(event) { 
                          $(document).ready(function() {      
                            $('#digList').load("/query/threadDigList");                                
                          });            
                        });  
                      </script>           
                    </div>   
      </div>
    </div>
</aside>
    
  </div>
</div>
</div>
</div>
	<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >



<%@ include file="./common/IncludeBottomBody.jsp" %> 
<script>
document.addEventListener("DOMContentLoaded", function(event) {   
$(function() {
  var loading = false;
  var done = false;

  var offset = 25; // 初始 offset
  var count = 27;  // 初始 count
  var scrollTimer = null;

  function loadMore() {
    if (loading || done) return;
    loading = true;

    var url = '/query/threadApprovedNewList2.shtml?offset=' + offset + '&count=' + count;

    $.get(url, function(html) {
      html = $.trim(html);
      if (html) {
        $('#loadMoreBox').before(html);

        // 每次加载后更新 offset 和 count
        offset += 2;
        count += 2;
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
</script>

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
