<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="/js/jdon.css" type="text/css"> 
  <script src="/js/jquery-bootstrap2.js"></script>
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script> 
  <meta charset="utf-8"/>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
  <meta http-equiv="" content="IE=edge,chrome=1"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>解道jdon - 传道解惑的架构师博客</title>
  <meta name="Description" content="有关领域驱动设计DDD、微服务、设计模式、软件架构、编程设计等主题，开拓软件开发思路，介绍各种编程工具库包"/>
  <meta name="Keywords" content="微服务,SOA,DDD,体系结构,架构,Gof设计模式,Java框架,大数据,云原生,面向对象,函数编程,SpringBoot,EDA,java,JavaEE,CQRS,eventsourcing" />
  <link rel="dns-prefetch" href="//static.jdon.com">
  <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap" />
  <link rel="alternate" type="application/rss+xml" title="解道订阅" href="https://www.jdon.com/rss"/>
  <link rel="canonical" href="https://www.jdon.com/" />
  <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 256 256%22><text y=%22203%22 font-size=%22224%22>☯</text></svg>"/>   
  <meta http-equiv="refresh" content="3600">
  <meta name="domain_verify" content="pmrgi33nmfuw4ir2ejvgi33ofzrw63jcfqrgo5ljmqrduirtmu2wczlcgvrtomzyha2dcylfhfqtmntemmydezbvme4gmmrugarcyitunfwwku3bozsseorrgq2dmmjrgu4teobrgqzh2">
</head>
<body>
<%@ include file="./common/body_header.jsp" %>

	<!-- /////////////////////////////////////////Content -->
	
	<div id="page-content" class="single-page container">
		<div class="row">
			<div id="main-content" class="col-md-8">
				<div class="box">

          <jsp:include page="/query/threadApprovedNewList2.shtml?count=1" flush="true"></jsp:include>

<div class="box"> 
  <div class="linkblock">
    <div class="row">
        <div class="col-sm-12">
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

    <div class="box"> 
	    <div class="tres center">        
           每日更新 <html:link page="/forum/threadDigSortedList"><b>更多最佳文章</b></html:link>		   
		</div>
    </div>

    <div id="approvedItem1"><br><br><br><br><br><br></div>          
	<div>
         
		  <script>
		  load('/query/threadApprovedNewList3.shtml?offset=1&start=0&count=10', function (xhr) {
               document.getElementById("approvedItem1").innerHTML = xhr.responseText;
            }); 
		  </script>
          
    </div>  
	<div id="approvedItem2"><br><br><br><br><br><br></div>          
	<div class="lazyload" >
          <!--
		  <script>
		  load('/query/threadApprovedNewList3.shtml?start=10&count=10', function (xhr) {
               document.getElementById("approvedItem2").innerHTML = xhr.responseText;
            }); 
		  </script>
          -->
    </div>  
	<div id="approvedItem3"><br><br><br><br><br><br></div>          
	<div class="lazyload" >
          <!--
		  <script>
		  load('/query/threadApprovedNewList3.shtml?start=20&count=10', function (xhr) {
               document.getElementById("approvedItem3").innerHTML = xhr.responseText;
            }); 
		  </script>
          -->
    </div>  
	<div id="approvedItem4"><br><br><br><br><br><br></div>          
	<div class="lazyload" >
          <!--
		  <script>
		  load('/query/threadApprovedNewList3.shtml?start=30&count=10', function (xhr) {
               document.getElementById("approvedItem4").innerHTML = xhr.responseText;
            }); 
		  </script>
          -->
    </div>  
	<div id="approvedItem5"><br><br><br><br><br><br></div>          
	<div class="lazyload" >
          <!--
		  <script>
		  load('/query/threadApprovedNewList3.shtml?start=40&count=10', function (xhr) {
               document.getElementById("approvedItem5").innerHTML = xhr.responseText;
            }); 
		  </script>
          -->
    </div>  
    <div id="approvedItem6"><br><br><br><br><br><br></div>     	  
	<div class="lazyload" >
          <!--
		  <script>
	      load('/query/threadApprovedNewList4.shtml?count=50&wSize=720', function (xhr) {
               document.getElementById("approvedItem6").innerHTML = xhr.responseText;
            }); 
		  </script>
          -->
      </div>

		  
        </div>
        <div class="box">
					<div class="box-header header-natural">
					</div>
					<div class="box-content">
						<div class="row">
							
						</div>
					</div>
				</div>
			</div>
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
							<ul>
							  <div id="tagHotList"></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>

							</ul>
							</div>
				</div>


				<div class="widget">
					    <div class="wid-vid">
							<ul>
							 <div id="digList"></div>   
							 <div class="lazyload" >
							    <!-- 
							     <script>
							  	  load('/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							    </script> -->
							  </div>
							</ul>
						</div>
				</div>		

				<div class="widget">
					    <div class="wid-vid">
							<ul>
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
							</ul>
							</div>
				</div>
		
			
			</div>
		</div>
	</div>
	<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

	<%-- <%@ include file="../account/loginAJAX.jsp" %> --%>
  
   <%@ include file="./common/IncludeBottomBody.jsp" %> 

 
</body>
</html>
