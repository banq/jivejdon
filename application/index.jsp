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
  <link rel="stylesheet" href="/js/jdon.css" type="text/css"> 
  <script defer src="/js/jquery-bootstrap2.js"></script>
  <link rel="dns-prefetch" href="//googleads.g.doubleclick.net">
  <link rel="dns-prefetch" href="//tpc.googlesyndication.com">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>极道 - 技术前沿极客</title>
  <meta name="Description" content="实时追踪全球最前沿技术思想">
  <meta name="Keywords" content="认知,逻辑,教程,资料,指南,企业架构,大语言模型,编程,规则引擎,流程,领域驱动,微服务,数据工程,平台,人工智能,程序,算法,模式,Java,Go,Python,Rust">
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

    <jsp:include page="/query/threadApprovedNewList3.shtml?count=30" flush="true"></jsp:include>        
	
    <div class="box"> 
	    <div class="tres center">        
           追踪前沿最新技术 <a href="/tags/"><b>更多</b></a>	   
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
					
                              <div id="newList"><jsp:include page="/query/threadNewList.shtml?count=15" flush="true"></jsp:include></div>   
							   
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
