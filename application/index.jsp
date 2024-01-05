<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script> 
  <style> 
    button,h2,h3,h4,input{color:inherit;font-family:inherit}h2,h3,ul{margin-bottom:10px}.btn,img{vertical-align:middle}.link a,a{text-decoration:none}html{font-family:sans-serif;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}aside,header,main,nav,section{display:block}a{background:0;color:#41872d}img{border:0;height:auto;max-width:100%}button,input{color:inherit;font:inherit;margin:0}button::-moz-focus-inner,input::-moz-focus-inner{border:0;padding:0}input{line-height:normal}*,:after,:before{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}html{font-size:62.5%;-webkit-text-size-adjust:none}button,input{font-size:inherit;line-height:inherit}h2,h3,h4{font-weight:500;line-height:1.1}.btn,body{font-weight:400}h2,h3{margin-top:20px}h2{font-size:2rem}h3{font-size:1.75rem}h4{font-size:1.6rem}ul{margin-top:0}.list-inline{padding-left:0;list-style:none;margin-left:-5px}.list-inline>li{display:inline-block;padding-left:5px;padding-right:5px}.form-control,.nav>li,.nav>li>a{display:block}.container{margin-right:auto;margin-left:auto;padding-left:15px;padding-right:15px}.container>.navbar-collapse,.container>.navbar-header,.row{margin-right:-15px;margin-left:-15px}@media (min-width:768px){.container{width:750px}}.col-lg-12,.col-lg-4,.col-lg-8,.col-md-6{position:relative;min-height:1px;padding-left:15px;padding-right:15px}.btn,.form-control{padding:6px 12px;line-height:1.42857143;font-size:14px;background-image:none}@media (min-width:992px){.container{width:970px}.col-md-6{float:left;width:50%}}@media (min-width:1200px){.container{width:128rem}.col-lg-12,.col-lg-4,.col-lg-8{float:left}.col-lg-12{width:100%}.col-lg-8{width:66.66666667%}.col-lg-4{width:33.33333333%}}.form-control{width:100%;height:34px;color:#555;background-color:#fff;border:1px solid #ccc;border-radius:4px;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075)}.form-control::-moz-placeholder{color:#999;opacity:1}.form-control:-ms-input-placeholder{color:#999}.form-control::-webkit-input-placeholder{color:#999}.btn{display:inline-block;margin-bottom:0;text-align:center;border:1px solid transparent;white-space:nowrap;border-radius:4px}.collapse{display:none}.dropdown{position:relative}.dropdown-menu{position:absolute;top:100%;left:0;z-index:1000;display:none;float:left;min-width:160px;padding:5px 0;margin:2px 0 0;list-style:none;font-size:14px;background-color:#fff;border:1px solid rgba(0,0,0,.15);border-radius:4px;-webkit-box-shadow:0 6px 12px rgba(0,0,0,.175);box-shadow:0 6px 12px rgba(0,0,0,.175);background-clip:padding-box}.nav>li,.nav>li>a,.navbar,.navbar-toggle{position:relative}.nav{margin-bottom:0;padding-left:0;list-style:none}.nav>li>a{padding:10px 15px}.navbar{min-height:50px;margin-bottom:20px;border:1px solid transparent}.navbar-collapse{max-height:340px;overflow-x:visible;padding-right:15px;padding-left:15px;border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1);-webkit-overflow-scrolling:touch}.linkblock,.widget{overflow:hidden;text-overflow:ellipsis;word-break:break-all}.navbar-brand{float:left;padding:15px;font-size:20px;line-height:20px;height:50px;color:#fff;font-weight:700}.navbar-toggle{float:right;margin-right:15px;padding:9px 10px;margin-top:8px;margin-bottom:8px;background-color:transparent;background-image:none;border:1px solid transparent;border-radius:4px}.navbar-nav{margin:7.5px -15px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px;line-height:20px}@media (min-width:768px){.navbar{border-radius:4px}.navbar-header,.navbar-nav>li{float:left}.navbar-collapse{width:auto;border-top:0;box-shadow:none}.navbar-collapse.collapse{display:block!important;height:auto!important;padding-bottom:0;overflow:visible!important}.container>.navbar-collapse,.container>.navbar-header{margin-right:0;margin-left:0}.navbar-toggle{display:none}.navbar-nav{float:left;margin:0}.navbar-nav>li>a{padding-top:15px;padding-bottom:15px}.navbar-right{float:right!important}}.navbar-nav>li>.dropdown-menu{margin-top:0;border-top-right-radius:0;border-top-left-radius:0}.navbar-inverse{background-color:#222;border-color:#080808}#top,.linkblock,.thumbn,.widget{background:#fff}.thumbnail{display:block;padding:4px;line-height:1.42857143;background-color:#fff}.container:after,.container:before,.nav:after,.nav:before,.navbar-collapse:after,.navbar-collapse:before,.navbar-header:after,.navbar-header:before,.navbar:after,.navbar:before,.row:after,.row:before{content:" ";display:table}.container:after,.nav:after,.navbar-collapse:after,.navbar-header:after,.navbar:after,.row:after{clear:both}.pull-right{float:right!important}@-ms-viewport{width:device-width}@font-face{font-family:icomoon;src:url('//cdn.jdon.com/js/fonts/icomoon.eot?or1tgc');src:url('//cdn.jdon.com/js/fonts/icomoon.eot?or1tgc#iefix') format('embedded-opentype'),url('//cdn.jdon.com/js/fonts/icomoon.ttf?or1tgc') format('truetype'),url('//cdn.jdon.com/js/fonts/icomoon.woff?or1tgc') format('woff'),url('//cdn.jdon.com/js/fonts/icomoon.svg?or1tgc#icomoon') format('svg');font-weight:400;font-style:normal;font-display:block}[class*=" fa-"]{font-family:icomoon;display:inline-block;font-size:inherit;font-style:normal;text-rendering:auto;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}.fa-star-half-full:before{content:"\f006"}.fa-home:before{content:"\f015"}.fa-feed:before{content:"\f09e"}.fa-weibo:before{content:"\f18a"}.fa-qq:before{content:"\f1d6"}.fa-weixin:before{content:"\f1d7"}.fa-arrow-circle-o-down:before{content:"\f01a"}.fa-bars:before{content:"\f039"}.fa-eye:before{content:"\f06e"}.fa-calendar:before{content:"\f073"}body{background-color:#e6e6e6;color:#666;font-size:15px;font-family:"Ek Mukta",sans-serif;line-height:1.6em}#top,#top a,.vid-name a,h2{color:#000}.link a,.vid-name a,h2{font-weight:700}body,html{margin:0;padding:0;width:100%;word-wrap:break-word}h2{letter-spacing:.1rem}p{margin:0 0 20px}.box:after,.box:before,.widget:after,.widget:before,section:after,section:before{clear:both;display:block;visibility:hidden;width:0;height:0;content:"\0020"}#top{padding:10px 0 0;border-bottom:1px solid #ddd}#menu,#menu .navbar-brand,#menu .navbar-nav{background-color:#000}.link{text-align:right}.link a{display:inline-block;letter-spacing:1px;font-size:12px}#menu{border-radius:0}#menu .navbar-brand{margin:0;padding:14px 28px;height:auto;text-transform:uppercase}#menu .navbar-collapse,#page-content{padding:0}#menu .dropdown-menu{border:0;background-color:#fff}#menu .dropdown-inner{display:table;background-color:#fff}#menu ul.nav li a{padding:14px 19px;border-radius:5px;color:#fff;font-weight:700}#menu ul.nav li.dropdown a{border-radius:5px 5px 0 0}#menu .top-social{padding:5px;background-color:#000;text-align:center;font-size:20px}#menu ul.top-social,.box h2,.vid-name{margin:0}#menu ul.top-social li{width:38px;height:38px}#menu ul.top-social a i{width:38px;height:38px;border-radius:50%;background-color:#41872d;color:#fff;line-height:1.9}#menu .btn-navbar{float:right;padding:5px 15px;border:3px solid #41872d;color:#41872d;font-size:20px}@media (max-width:991px){.navbar-header,.navbar-nav>li{float:none}.navbar-nav,.navbar-right{float:none!important}.navbar-toggle{display:block}.navbar-collapse{border-top:1px solid transparent;box-shadow:inset 0 1px 0 rgba(255,255,255,.1)}.navbar-collapse.collapse{display:none!important}.navbar-nav{margin-top:7.5px}.navbar-nav>li>a{padding-top:10px;padding-bottom:10px}}#menu div.dropdown-menu{margin-left:0!important;padding-bottom:10px;background-color:rgba(0,0,0,.1)}#page-content.single-page{margin-top:20px}.box{padding:1rem;background-color:#fff}@media (max-width:768px){.box{padding:1rem}}.box .info{margin:5px 0 10px}.info{margin:0;font-size:14px}.info i{margin-right:8px}.info span,.thumbn{margin-right:10px}.widget{padding:10px 20px}.linkblock{white-space:normal;padding:5px;border:1px solid #ddd;border-radius:3px;box-shadow:0 15px 10px -15px #e5e5e5}.thumbn{float:left;display:block}button{overflow:visible;text-transform:none;-webkit-appearance:button;background-color:#41872d;color:#fff;border:0}
  </style> 
  <link rel="stylesheet" href="//cdn.jdon.com/js/jdon.css"  media="print" onload="this.media='all'">   
  <script defer src="//cdn.jdon.com/js/jquery-bootstrap2.js"></script>
  <link rel="dns-prefetch" href="//googleads.g.doubleclick.net">
  <link rel="dns-prefetch" href="//tpc.googlesyndication.com">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>极道 - 技术前沿极客</title>
  <meta name="Description" content="教程，免费在线教程，为初学者和专业人士提供所有编码与架构设计的教程和面试问题">
  <meta name="Keywords" content="认知,逻辑,编程,Java教程,SpringBoot教程,软件架构,企业架构,大语言模型,规则引擎,流程,领域驱动,微服务,数据工程,平台,人工智能,程序,算法,模式,数据库,计算机科学,Python,Java,JavaScript,安卓,SQL,数据科学,机器学习,Web开发,系统设计,技术博客,面试,HTML,CSS,golang,Rust">
  <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
  <link rel="alternate" type="application/rss+xml" title="极道订阅" href="https://www.jdon.com/rss">
  <link rel="canonical" href="https://www.jdon.com/">
  <meta http-equiv="refresh" content="3600">
   <!-- Google tag (gtag.js) -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=G-FTT1M21HE8"></script>
  <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());
      gtag('config', 'G-FTT1M21HE8');
  </script>
</head>
<body style="background-color:#FFF">
<%@ include file="./common/body_header.jsp" %>

	

<div id="page-content" class="single-page container">
	<div class="row">
       <main>	  
	  <div id="main-content" class="col-lg-8">

       <jsp:include page="/query/threadApprovedNewList2.shtml?count=7" flush="true"></jsp:include>

	
<div class="box"> 
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

    <jsp:include page="/query/threadApprovedNewList3.shtml?count=15" flush="true"></jsp:include>        
	
    <div class="box"> 
	    <div class="tres center">        
           追踪前沿最新技术 <a href="/approval/"><b>更多</b></a>	   
		</div>
    </div>
		  
   
</div>
</main> 
<aside>  			
    <div id="sidebar" class="col-lg-4">
				<!---- Start Widget ---->
				<div>
					<div class="content">
						<ul class="list-inline">
              <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
								<input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
							</form>
						</ul>						
					</div>
				</div>

				<div class="widget">
					    <div class="wid-vid">
							   <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<!-- 内容右侧悬浮广告 -->
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

                <div>
					    <div>
					
                              <div id="newList"><jsp:include page="/query/threadNewList.shtml?count=10" flush="true"></jsp:include></div>   
							   
							</div>
				</div>

				<div>
					    <div >
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

				<div class="widget">
					    <div class="wid-vid">
							 <div class="scrolldiv">
							   <div style="width: 300px"> 
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
		
			
			</div>
    </aside>
    
  </div>
</div>
	<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >




<%@ include file="./common/IncludeBottomBody.jsp" %> 

 
</body>
</html>
