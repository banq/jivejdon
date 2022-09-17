<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page trimDirectiveWhitespaces="true" %>

	<!--Top-->
	<nav id="top">
		<div class="container">
			<div class="row">
                <div class="col-md-12 ">
                    <a href="/"><img src="//static.jdon.com/simgs/jdon100.png" width="100" height="40" loading="lazy"/></a>
                    <span class="list-inline top-link link" style="float:right;">
				        <a href="<%=request.getContextPath() %>/forum"><i class="fa fa-home"></i>Dojo</a>
						<a href="<%=request.getContextPath() %>/threads"><i class="fa fa-list-ul"></i>最新</a>
						<a href="<%=request.getContextPath() %>/forum/threadDigSortedList"><i class="fa fa-star-half-full"></i>最佳</a>
						<a href="<%=request.getContextPath() %>/query/threadViewQuery.shtml"><i class="fa fa-search"></i>搜索</a>
						<a href="<%=request.getContextPath() %>/followus.html"><i class="fa fa-feed"></i>订阅</a>
                         <%--<%if (request.getSession(false) == null){%> --%>
							 <%--<a data-toggle="modal" data-target="#login" href=""><i class="fa fa-sign-in"></i>登陆 </a>--%>
						<%--<%}%>  --%>
						<%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
							<a href="<%=request.getContextPath()%>/message/post.jsp"><i class=" fa fa-share-square"></i>发布</a>						
							<a href="<%=request.getContextPath()%>/blog/<%=request.getUserPrincipal()%>"><i class="fa fa-newspaper-o"></i>博客</a>
						    <html:link page="/jasslogin?logout"><i class="fa fa-sign-out"></i>退出 </html:link>
                        <%}%>                       
                    </span>
				</div>
			</div>
		</div>
	</nav>

	<!--Navigation-->
    <nav id="menu" class="navbar container">
        <div class="navbar-header">
            <button type="button" class="btn btn-navbar navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse"><i class="fa fa-bars"></i></button>
            <a class="navbar-brand" href="/">
                <div class="logo"><span>解道Jdon</span></div>
            </a>
        </div>
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav navbar-inverse">
				<li><a href="/ddd.html">领域驱动设计</a></li>
				<li><a href="/microservice.html">微服务</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">企业架构 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
				                <li><a href="/design.htm">架构设计</a></li>   
								<li><a href="/tags/10740">战略建模</a></li>   								  	
								<li><a href="/colorUML.html">商业分析</a></li>														
							    <li><a href="/workflow-bpm.html">工作流BPM</a></li>
								<li><a href="/tags/26738">规则引擎</a></li>	
								<li><a href="/tags/16355">形式逻辑</a></li>								
								<li><a href="/tags/619">架构师观点</a></li>
								<li><a href="/tags/40250">clean架构</a></li>
								<li><a href="/soa.html">SOA</a></li>							
								<li><a href="/tags/49580">数据工程</a></li>
								<li><a href="/tags/57874">团队拓扑</a></li>
								<li><a href="/tags/38587">产品经理</a></li>								
							</ul>
						</div>
					</div>
				</li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">编程 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
								<li><a href="/tags/395">菜鸟入门</a></li>
								<li><a href="/tags/222">编程语言比较</a></li>
								<li><a href="/tags/38692">编程工具比较</a></li>								
								<li><a href="/oo.html">面向对象</a></li>
				                <li><a href="/designpatterns/">设计模式</a></li>									
								<li><a href="/functional.html">函数式编程</a></li>
								<li><a href="/concurrency.html">并发编程</a></li>
								<li><a href="/asynchronous.html">异步编程</a></li>
								<li><a href="/reactive.html">响应编程</a></li>
								<li><a href="/idea/rust.html">Rust语言</a></li>											
                                <li><a href="/tags/31545">前端编程</a></li>																								
								<li><a href="/springboot.html">SpringBoot</a></li>
								<li><a href="/jdonframework/">Jdon框架</a></li>
								<li><a href="/tags/29134">DevOps</a></li>
								<li><a href="/tags/320">软件工程</a></li>	
								<li><a href="/tags/30661">人工智能</a></li>																	
							</ul>
						</div> 
					</div>
				</li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">系统设计 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
								<li><a href="/tags/38726">复杂性系统</a></li>
								<li><a href="/tags/42486">系统设计</a></li>			
								<li><a href="/tags/249">系统架构</a></li>													
								<li><a href="/DistributedSystems.html">分布式设计</a></li>
								<li><a href="/tags/11205">CAP定理</a></li>
								<li><a href="/transaction.html">分布式事务</a></li>									
								<li><a href="/event.html">事件溯源</a></li>
								<li><a href="/cqrs.html">CQRS</a></li>
								<li><a href="/nosql.html">NoSQL</a></li>
								<li><a href="/blockchain.html">区块链</a></li>								
								<li><a href="/bigdata.html">大数据</a></li>
								<li><a href="/soa/serverless.html">无服务器</a></li>								
								<li><a href="/tags/17472">敏捷方法</a></li>								
								<li><a href="/tags/58254">认知谬论</a></li>
							</ul>
						</div> 
					</div>
				</li>
    			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">热点话题<i class="fa fa-arrow-circle-o-down"></i></a>
					    <div class="dropdown-menu">
						    <div class="dropdown-inner">
							<ul class="list-unstyled">
								<li><a href="/tags/37546">幽默模因</a></li>		
								<li><a href="/tags">更多话题</a></li>	
								<jsp:include page="/query/tagHotList_menu.shtml" flush="true"/>								
			   					   								
							</ul>
						    </div> 
					    </div>
			    </li>					
                					
			</ul>
			<ul class="list-inline navbar-right top-social">
				<li><a href="javascript:shareto('sina')"><i class="fa fa-weibo"></i></a></li>
				<li><a href="javascript:shareto('weixin')"><i class="fa fa-weixin"></i></a></li>
				<li><a href="javascript:shareto('qzone')"><i class="fa fa-qq"></i></a></li>
			</ul>
		</div>
	</nav>
<!-- Header -->	