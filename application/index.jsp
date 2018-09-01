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
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
   <title>解道jdon - 企业级软件架构解决之道</title>
   <meta name="Description" content="有关企业软件的业务建模、软件架构设计、编程技术等方面的探索、分享和交流"/>
   <meta name="Keywords" content="编程,设计,架构,开发,分析,教程,领域驱动设计,web框架,设计模式,数据库,大数据,java性能,面向对象,函数编程,java培训,EDA,SOA,JEE,j2ee,web,android,Java,JavaEE,DDD,CQRS,EventSourcing,javascript,分布式,微服务,Reactive,扩展性,scalable,Spring,node.js" />
	 <meta name="domain_verify" content="pmrgi33nmfuw4ir2ejvgi33ofzrw63jcfqrgo5ljmqrduirtmu2wczlcgvrtomzyha2dcylfhfqtmntemmydezbvme4gmmrugarcyitunfwwku3bozsseorrgq2dmmjrgu4teobrgqzh2">
	<link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap" />
    <link rel="alternate" type="application/rss+xml" title="解道订阅" href="/rss" />
	<link rel="canonical" href="https://www.jdon.com/" />
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="./js/bootstrap.min.css"  type="text/css">
	<!-- Custom Fonts -->
    <link rel="stylesheet" href="./js/font-awesome-4.4.0/css/font-awesome.min.css"  type="text/css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="./js/style.css">
		
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="/js/html5shiv.min.js"></script>
        <script src="/js/respond.min.js"></script>
    <![endif]-->
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({
          google_ad_client: "ca-pub-7573657117119544",
          enable_page_level_ads: true
     });
</script>
</head>
<body>
<%@ include file="./common/body_header.jsp" %>

	<!-- /////////////////////////////////////////Content -->
	
	<div id="page-content" class="single-page container">
		<div class="row">
			<div id="main-content" class="col-md-8">
				<div class="box">
					<jsp:include page="/query/threadApprovedNewList.shtml?count=15" flush="true"></jsp:include>
              </div>
				 <div class="box">
					<div class="box-header header-natural">
					</div>
					<div class="box-content">
						<div class="row">
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
						</div>
					</div>
				</div>
			</div>
		   	<div id="sidebar" class="col-md-4">
				<!---- Start Widget ---->
				<div class="widget wid-follow">
					<div class="heading"></div>
					<div class="content">
						<ul class="list-inline">
							<form role="form" class="form-horizontal" method="post" action="/query/searchAction.shtml">
								<input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
							</form>
						</ul>						
					</div>
				</div>
				<!---- Start Widget ---->
				<div class="widget wid-post">
					<div class="heading"></div>
					<div class="content">
					    <div class="post wrap-vid">
							<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 页上左336 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:336px;height:280px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="6751585519"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
						</div>
					</div>
				</div>
				<!---- Start Widget digList---->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
							  <div id="digList"></div>   
							</ul>
							</div>
				</div>
				
				<!-- Start Widget newList -->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
  <jsp:include page="/query/threadNewList.shtml" flush="true">   
	<jsp:param name="count" value="20"/>
  </jsp:include>   
							</ul>
							</div>
				</div>
							
				<!-- Start Widget urlList -->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
							  
  <jsp:include page="/query/urlListAction.shtml" flush="true">   
	<jsp:param name="count" value="20"/>
  </jsp:include>
							</ul>
							</div>
				</div>
			
				<!-- Start Widget poplist -->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
							  <div id="poplist"></div>   
							</ul>
							</div>
				</div>
				
					<!-- Start Widget urlList -->
				<div class="widget">
					    <div class="wid-vid">
							<ul>
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 推荐贴右侧300x600 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:600px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3352261515"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
							</ul>
							</div>
				</div>
			
			</div>
		</div>
	</div>
	
<%@ include file="./common/IncludeBottomBody.jsp" %> 
<%@ include file="../account/loginAJAX.jsp" %>
<script>   
$LAB
.script("https://cdn.jdon.com/common/js/prototype.js").wait()
.wait(function(){
	new Ajax.Updater('digList', '/query/threadDigList.shtml?count=20', { method: 'get' });
     new Ajax.Updater('poplist', '/query/popularlist.shtml?count=11&dateRange=180', { method: 'get' });
});  
</script> 	

</body>
</html>
