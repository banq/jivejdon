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
  <link rel="stylesheet" href="/js/jdon.css" type="text/css"> 
  <script defer src="/js/jquery-bootstrap2.js"></script>
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script> 
  <link rel="preconnect dns-prefetch" href="//adservice.google.com" crossorigin>
  <link rel="preconnect dns-prefetch" href="//googleads.g.doubleclick.net" crossorigin>
  <link rel="preconnect dns-prefetch" href="//tpc.googlesyndication.com" crossorigin>
  <link rel="preconnect dns-prefetch" href="//analytics.google.com" crossorigin> 
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>极道 - 技术前沿极客</title>
  <meta name="Description" content="实时追踪全球最前沿技术思想">
  <meta name="Keywords" content="科普,领域语言,编程语言,符号逻辑,形式逻辑,系统,IT,架构,科技,认知,涌现,LLM,大语言模型,规则引擎,业务流程,DDD,微服务,SOA,大数据,人工智能">
  <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">
  <link rel="alternate" type="application/rss+xml" title="极道订阅" href="https://www.jdon.com/rss">
  <link rel="canonical" href="https://www.jdon.com/">
  <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 256 256%22><text y=%22203%22 font-size=%22224%22>☯</text></svg>">   
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
<body>
<%@ include file="./common/body_header.jsp" %>

	
<main>	
	<div id="page-content" class="single-page container">
		<div class="row">
			<div id="main-content" class="col-md-8">
			  
				<div class="box">


       <jsp:include page="/query/threadApprovedNewList2.shtml?count=6" flush="true"></jsp:include>

	
<div class="box"> 
  
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

    <jsp:include page="/query/threadApprovedNewList3.shtml?count=30" flush="true"></jsp:include>        
	
    <div class="box"> 
	    <div class="tres center">        
           追踪前沿最新技术 <a href="/tags/"><b>更多</b></a>	   
		</div>
    </div>
		  
    </div>
	

</div>

<aside>  			
		   	<div id="sidebar" class="col-md-4">
				<!---- Start Widget ---->
				<div class="widget wid-follow">
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
							  <div id="tagHotList"><br><br><br><br><br><br><br><br><br><br></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>
							</div>
				</div>

                <div class="widget">
					    <div class="wid-vid">
					
                              <div id="newList"><jsp:include page="/query/threadNewList.shtml?count=15" flush="true"></jsp:include></div>   
							   
							</div>
				</div>

				<div class="widget">
					    <div class="wid-vid">
							 <div id="digList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
							 <div class="lazyload" >
							    <!-- 
							     <script>
							  	  load('/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							    </script> -->
							  </div>
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


</main> 

<%@ include file="./common/IncludeBottomBody.jsp" %> 

 
</body>
</html>
