<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(30 * 60, request, response);
	
%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <link rel="preconnect" href="https://adservice.google.com/">
  <link rel="preconnect" href="https://googleads.g.doubleclick.net/">
  <link rel="preconnect" href="https://www.googletagservices.com/">
  <link rel="preconnect" href="https://tpc.googlesyndication.com/">
  <link rel="prefetch" href="//static.jdon.com/common/login2.js" />
  <link rel="prefetch" href="/approvedNewList3" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>解道jdon - 传道解惑的架构师博客</title>
  <meta name="Description" content="领域驱动设计DDD,微服务,<jsp:include page="/query/tagHotList2.shtml" flush="true"></jsp:include>"/>
  <meta name="Keywords" content="微服务,SOA,DDD,体系结构,架构,Gof设计模式,Java框架,大数据,云原生,面向对象,函数编程,SpringBoot,EDA,java,JavaEE,CQRS,eventsourcing" />
  <meta name="domain_verify" content="pmrgi33nmfuw4ir2ejvgi33ofzrw63jcfqrgo5ljmqrduirtmu2wczlcgvrtomzyha2dcylfhfqtmntemmydezbvme4gmmrugarcyitunfwwku3bozsseorrgq2dmmjrgu4teobrgqzh2">
  <meta http-equiv="refresh" content="3600">
  <link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap" />
  <link rel="alternate" type="application/rss+xml" title="解道订阅" href="https://www.jdon.com/rss"/>
  <link rel="canonical" href="https://www.jdon.com/" />
  <script data-ad-client="ca-pub-7573657117119544" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
  <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.1.1/css/bootstrap.min.css"  type="text/css">
  <!-- Custom Fonts -->
  <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css"  type="text/css">
  <!-- Custom CSS -->
  <link rel="stylesheet" href="https://static.jdon.com/common/js/styles/style.css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
      <script src="https://static.jdon.com/js/html5shiv.min.js"></script>
      <script src="https://static.jdon.com/js/respond.min.js"></script>
  <![endif]-->	
</head>
<body>
<%@ include file="./common/body_header.jsp" %>

	<!-- /////////////////////////////////////////Content -->
	
	<div id="page-content" class="single-page container">
		<div class="row">
			<div id="main-content" class="col-md-8">
				<div class="box">
          <jsp:include page="/query/threadApprovedNewList2.shtml?count=1" flush="true"></jsp:include>
<%
    String imagesize = "10";
		if (request.getParameter("imagesize") != null)
		    imagesize = request.getParameter("imagesize");
    String mythreadId = "";
    if (application.getAttribute("thumbthreadId") != null) {
        mythreadId = ((Long) application.getAttribute("thumbthreadId")).toString();
    }
    Integer homethumbnai = (Integer) application.getAttribute(mythreadId);
    if (homethumbnai == null) {
        homethumbnai = 1 + (int) (Math.random() * Integer.parseInt(imagesize));
        application.setAttribute(mythreadId, homethumbnai);
    }	
%>
<div class="lazyload" >
<!-- 
<script> 
 if(document.getElementById("home-thumbnai") != null)
	          if(document.getElementById("home-thumbnai").getAttribute("data-src") != null)
				   //document.getElementById("home-thumbnai").src = document.getElementById("home-thumbnai").getAttribute("data-src") ;
				   $("#home-thumbnai").attr('src',$("#home-thumbnai").attr('data-src'));
	          else
                   //document.getElementById("home-thumbnai").src = "https://static.jdon.com/simgs/thumb2/<%=homethumbnai%>.jpg";
                   $("#home-thumbnai").attr('src','https://static.jdon.com/simgs/thumb2/<%=homethumbnai%>.jpg');
</script>
-->
</div>


<div class="box"> 
  <div class="linkblock">
    <div class="row">
        <div class="col-sm-12">
			<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			<ins class="adsbygoogle"
				 style="display:block; text-align:center;"
				 data-ad-layout="in-article"
				 data-ad-format="fluid"
				 data-ad-client="ca-pub-7573657117119544"
				 data-ad-slot="6913243852"></ins>
			<script>
                (adsbygoogle = window.adsbygoogle || []).push({});
			</script>
        </div>
    </div>
  </div>
</div>
          <span id="threadApprovedNewListOthers"></span>
 		  <div class="lazyload" >
          <!--
		  <script>
		  load('/approvedNewList3', function (xhr) {
               document.getElementById("threadApprovedNewListOthers").innerHTML = xhr.responseText;
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
							<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
							<ins class="adsbygoogle"
								 style="display:block"
								 data-ad-format="fluid"
								 data-ad-layout-key="-e3+4a-2h-5p+v6"
								 data-ad-client="ca-pub-7573657117119544"
								 data-ad-slot="4250528285"></ins>
							<script>
                                (adsbygoogle = window.adsbygoogle || []).push({});
							</script>
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
				<div class="widget wid-post">
                    <div class="content">
                       <div class="wrap-vid">
			               <div class="thumbn"><img src="https://static.jdon.com/simgs/forum/ddd-book.png" class="thumbnail" loading="lazy"></div> 
						      <p><br>本站原创<br><a href="/54881" target="_blank">《复杂软件设计之道：领域驱动设计全面解析与实战》</a></p>
                        </div>
                    </div>
                </div>
               	<!---- Start tags ---->
				<div class="widget wid-post">
                    <div class="info">
                      <jsp:include page="/query/tagHotList.shtml" flush="true"></jsp:include>  
                    </div>     
                </div>				
				<!---- Start Widget digList---->
				<div class="widget">
					<div class="wid-vid">
						<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
						<!-- 页面右侧上部336x280 20-06-18 -->
						<ins class="adsbygoogle"
							 style="display:block"
							 data-ad-client="ca-pub-7573657117119544"
							 data-ad-slot="6751585519"
							 data-ad-format="auto"
							 data-full-width-responsive="true"></ins>
						<script>
                            (adsbygoogle = window.adsbygoogle || []).push({});
						</script>
					</div>
				</div>
				<!---- Start Widget digList---->
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


				<!-- Start Widget -->
                <div class="widget">
                  <div class="wid-vid">
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <!-- 右侧中部300x600 20-06-18 -->
          <ins class="adsbygoogle"
               style="display:block"
               data-ad-client="ca-pub-7573657117119544"
               data-ad-slot="3352261515"
               data-ad-format="auto"
               data-full-width-responsive="true"></ins>
          <script>
              (adsbygoogle = window.adsbygoogle || []).push({});
          </script>
                   </div>
                </div>
				
				<!-- Start Widget newList -->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
                <div>
					<jsp:include page="/query/threadNewList.shtml?count=20" flush="true"></jsp:include>
				</div>
							</ul>
							</div>
				</div>
					
				<!-- Start Widget urlList -->
				<div class="widget">
					    <div class="wid-vid">
							<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
							<ins class="adsbygoogle"
								 style="display:block"
								 data-ad-format="autorelaxed"
								 data-ad-client="ca-pub-7573657117119544"
								 data-ad-slot="7669317912"></ins>
							<script>
                                (adsbygoogle = window.adsbygoogle || []).push({});
							</script>
						</div>
				</div>

				
				<!-- Start Widget newList -->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
							    <div id="digNewList"></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
                                    load('/query/threadNewDigList.shtml?count=20', function (xhr) {				
  	                                    document.getElementById("digNewList").innerHTML = xhr.responseText;
			                        });			
							     </script> -->
							  </div>

							</ul>
						</div>
				</div>
					
			
			</div>
		</div>
	</div>
	<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
	<script src="//static.jdon.com/common/login2.js"></script>
	<%-- <%@ include file="../account/loginAJAX.jsp" %> --%>
<%@ include file="./common/IncludeBottomBody.jsp" %> 
<script src="./common/js/jquery.lazyload-any.js"></script>
<script>       
    $('.lazyload').lazyload();
</script>
</body>
</html>
