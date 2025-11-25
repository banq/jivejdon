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
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
  <link rel="preconnect" href="https://pagead2.googlesyndication.com" crossorigin>
  <link rel="preconnect" href="https://tpc.googlesyndication.com" crossorigin>
  <link rel="preconnect" href="https://googleads.g.doubleclick.net" crossorigin>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>极道Jdon:一语道破科技玄机</title>
  <meta name="Description" content="极道Jdon带你探索科技世界，深析最新科技动态，道破科技玄机。">
  <meta name="Keywords" content="极道,Jdon,IT,科技,AI,AGI,智能,哲学,复杂性,思维,生物,极客,长寿,冻龄,语言,DDD,软件,架构,硬件,芯片,vibe,编程,上下文,逻辑,解道,J道">
  <style> 
     button,h2,h3,h4,input{color:inherit;font-family:inherit}h2,h3,ul{margin-bottom:10px}.btn,img{vertical-align:middle}.link a,a{text-decoration:none}html{font-family:sans-serif;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}aside,header,main,nav,section{display:block}a{background:0;color:#41872d}img{border:0;height:auto;max-width:100%}button,input{color:inherit;font:inherit;margin:0}button::-moz-focus-inner,input::-moz-focus-inner{border:0;padding:0}input{line-height:normal}*,:after,:before{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}html{font-size:62.5%;-webkit-text-size-adjust:none}button,input{font-size:inherit;line-height:inherit}h2,h3,h4{font-weight:500;line-height:1.1}.btn,body{font-weight:400}h2,h3{margin-top:20px}h2{font-size:2rem}h3{font-size:1.8rem}h4{font-size:1.6rem}ul{margin-top:0}.list-inline{padding-left:0;list-style:none;margin-left:-5px}.list-inline>li{display:inline-block;padding-left:5px;padding-right:5px}.form-control,.nav>li,.nav>li>a{display:block}.container{margin-right:auto;margin-left:auto;padding-left:15px;padding-right:15px}.container>.navbar-collapse,.container>.navbar-header,.row{margin-right:-15px;margin-left:-15px}@media (min-width:768px){.container{width:750px}}.col-lg-12,.col-lg-4,.col-lg-8,.col-md-6{position:relative;min-height:1px;padding-left:15px;padding-right:15px}.btn,.form-control{padding:6px 12px;line-height:1.42857143;font-size:14px;background-image:none}@media (min-width:992px){.container{width:970px}.col-md-6{float:left;width:50%}}@media (min-width:1200px){.container{width:128rem}.col-lg-12,.col-lg-4,.col-lg-8{float:left}.col-lg-12{width:100%}.col-lg-8{width:66.66666667%}.col-lg-4{width:33.33333333%}}.form-control{width:100%;height:34px;color:#555;background-color:#fff;border:1px solid #ccc;border-radius:12px;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075)}.form-control::-moz-placeholder{color:#999;opacity:1}.form-control:-ms-input-placeholder{color:#999}.form-control::-webkit-input-placeholder{color:#999}.btn{display:inline-block;margin-bottom:0;text-align:center;border:1px solid transparent;white-space:nowrap;border-radius:4px}.collapse{display:none}.dropdown{position:relative}.dropdown-menu{position:absolute;top:100%;left:0;z-index:1000;display:none;float:left;min-width:160px;padding:5px 0;margin:2px 0 0;list-style:none;font-size:14px;background-color:#fff;border:1px solid rgba(0,0,0,.15);border-radius:4px;-webkit-box-shadow:0 6px 12px rgba(0,0,0,.175);box-shadow:0 6px 12px rgba(0,0,0,.175);background-clip:padding-box}.nav>li,.nav>li>a,.navbar,.navbar-toggle{position:relative}.nav{margin-bottom:0;padding-left:0;list-style:none}.nav>li>a{padding:10px 15px}.navbar{min-height:50px;margin-bottom:20px;border:1px solid transparent}.navbar-collapse{max-height:340px;overflow-x:visible;padding-right:15px;padding-left:15px;border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1);-webkit-overflow-scrolling:touch}.linkblock,.widget{overflow:hidden;text-overflow:ellipsis;word-break:break-all}.navbar-brand{float:left;padding:15px;font-size:20px;line-height:20px;height:50px;color:#fff;font-weight:700}.navbar-toggle{float:right;margin-right:15px;padding:9px 10px;margin-top:8px;margin-bottom:8px;background-color:transparent;background-image:none;border:1px solid transparent;border-radius:4px}.navbar-nav{margin:7.5px -15px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px;line-height:20px}@media (min-width:768px){.navbar{border-radius:4px}.navbar-header,.navbar-nav>li{float:left}.navbar-collapse{width:auto;border-top:0;box-shadow:none}.navbar-collapse.collapse{display:block!important;height:auto!important;padding-bottom:0;overflow:visible!important}.container>.navbar-collapse,.container>.navbar-header{margin-right:0;margin-left:0}.navbar-toggle{display:none}.navbar-nav{float:left;margin:0}.navbar-nav>li>a{padding-top:15px;padding-bottom:15px}.navbar-right{float:right!important}}.navbar-nav>li>.dropdown-menu{margin-top:0;border-top-right-radius:0;border-top-left-radius:0}.navbar-inverse{background-color:#222;border-color:#080808}#top,.linkblock,.thumbn,.widget{background:#fff}.thumbnail{display:block;background-color:#fff}.container:after,.container:before,.nav:after,.nav:before,.navbar-collapse:after,.navbar-collapse:before,.navbar-header:after,.navbar-header:before,.navbar:after,.navbar:before,.row:after,.row:before{content:" ";display:table}.container:after,.nav:after,.navbar-collapse:after,.navbar-header:after,.navbar:after,.row:after{clear:both}.pull-right{float:right!important}@-ms-viewport{width:device-width}font-weight:400;font-style:normal;font-display:block}[class*=" fa-"]{font-family:icomoon;display:inline-block;font-size:inherit;font-style:normal;text-rendering:auto;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}.fa-star-half-full:before{content:"\f006"}.fa-home:before{content:"\f015"}.fa-feed:before{content:"\f09e"}.fa-weibo:before{content:"\f18a"}.fa-qq:before{content:"\f1d6"}.fa-weixin:before{content:"\f1d7"}.fa-arrow-circle-o-down:before{content:"\f01a"}.fa-bars:before{content:"\f039"}.fa-eye:before{content:"\f06e"}.fa-calendar:before{content:"\f073"}@font-face {font-family: "ChineseFont";src: local("Microsoft YaHei"), local("微软雅黑");unicode-range: U+4E00-9FFF; }@font-face {font-family: "EnglishFont";src: local("Segoe UI"), local(sans-serif);unicode-range: U+00-7F; }body {font-family: "ChineseFont", "EnglishFont", sans-serif;font-size:1.6rem;color:#333;background-color:#E6E6E6;line-height:2.2rem}#top,#top a,.vid-name a,h2{color:#000}.link a,.vid-name a,h2{font-weight:400}body,html{margin:0;padding:0;width:100%;word-wrap:break-word}h2{letter-spacing:.1rem}p{margin:0 0 20px}.box:after,.box:before,.widget:after,.widget:before,section:after,section:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:"\0020"}#top{padding:10px 0 0;border-bottom:1px solid #ddd}#menu,#menu .navbar-brand,#menu .navbar-nav{background-color:#000}.link{text-align:right}.link a{display:inline-block;letter-spacing:1px;font-size:12px}#menu{border-radius:8px}#menu .navbar-brand{margin:0;padding:14px 28px;height:auto;text-transform:uppercase}#menu .navbar-collapse,#page-content{padding:0}#menu .dropdown-menu{border:0;background-color:#fff}#menu .dropdown-inner{display:table;background-color:#fff}#menu ul.nav li a{padding:14px 19px;border-radius:5px;color:#fff;font-weight:400}#menu ul.nav li.dropdown a{border-radius:5px 5px 0 0}#menu .top-social{padding:5px;background-color:#000;text-align:center;font-size:20px}#menu ul.top-social,.box h2,.vid-name{margin:0}#menu ul.top-social li{width:38px;height:38px}#menu ul.top-social a i{width:38px;height:38px;border-radius:50%;background-color:#41872d;color:#fff;line-height:1.9}#menu .btn-navbar{float:right;padding:5px 15px;border:3px solid #41872d;color:#41872d;font-size:20px}@media (max-width:991px){.navbar-header,.navbar-nav>li{float:none}.navbar-nav,.navbar-right{float:none!important}.navbar-toggle{display:block}.navbar-collapse{border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1)}.navbar-collapse.collapse{display:none!important}.navbar-nav{margin-top:7.5px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px}}#menu div.dropdown-menu{margin-left:0!important;padding-bottom:10px;background-color:rgba(0,0,0,.1)}#page-content.single-page{margin-top:20px}.box{padding:1rem;background-color:#fff}@media (max-width:768px){.box{padding:1rem}}.box .info{margin:5px 0 10px}.info{margin:0;font-size:14px}.info i{margin-right:8px}.info span,.thumbn{margin-right:10px}.widget{padding:10px 20px}.thumbn{float:left;display:block}button{overflow:visible;text-transform:none;-webkit-appearance:button;background-color:#41872d;color:#fff;border:0}.article{font-size:1.8rem;letter-spacing: 0.05rem;line-height:3.2rem;color:#222;padding:2rem}.bige20{font-size:2.7rem}
  </style>  
  <link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl%>/sitemap.xml">
  <link rel="canonical" href="<%=domainUrl%>/">
  <link rel="preload" href="/js/jdon.css" as="style" onload="this.onload=null;this.rel='stylesheet'">
  <link rel="preload" href="/js/fonts/icomoon.woff" as="font" type="font/woff" crossorigin>
  <link rel="preload" href="/js/jquery-bootstrap2.js" as="script">
  <script defer src="/js/jquery-bootstrap2.js"></script> 
</head>
<body style="background-color:#FFF">
<%@ include file="./common/body_header.jsp" %>

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
  
  <jsp:include page="/query/threadApprovedNewList.shtml" flush="true"></jsp:include>
	<div class="row">
  <main>	  	 
	  <div id="main-content" class="col-lg-8 custom-col-left">
      
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
  <div id="sidebar" class="col-lg-4 custom-col-right" style="padding-left:0px">
      <div class="box">
                <!---- Start Widget ---->
                <div class="widget">
                    <div class="content">
                        <ul class="list-inline">
              <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
                                <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
                            </form>
                        </ul>						
                    </div>
                </div>

                <div class="box">
                    <div class="wid-vid">
                      <div id="newList"><jsp:include page="/query/threadNewList.shtml?count=10" flush="true"></jsp:include></div>   
                    </div>
                </div>

                <div class="box">
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
    </div>
</aside>
    
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
