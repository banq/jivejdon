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
			   <li><a href="/design.htm">架构设计</a></li>	
			   <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">领域驱动 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
				                <li><a href="/ddd.html">DDD介绍</a></li>
                                <li><a href="/tag-272/">DDD专辑</a></li>
								<li><a href="/tag-10740/">战略建模</a></li>   		
								<li><a href="/tag-20044/">领域语言UL</a></li>	
                                <li><a href="/event.html">领域事件</a></li>
								<li><a href="/colorUML.html">商业分析</a></li>
							    <li><a href="/workflow-bpm.html">工作流BPM</a></li>
								<li><a href="/tag-26738/">规则引擎</a></li>						
								<li><a href="/tag-619/">架构师观点</a></li>			
								<li><a href="/tag-49580/">数据工程</a></li>
								<li><a href="/tag-38587/">产品经理</a></li>
                                <li><a href="/tag-58254/">认知谬论</a></li>
                                <li><a href="/tag-38726/">系统思维</a></li>	
							</ul>
						</div>
					</div>
				</li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">微服务 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
				            	<li><a href="/microservice.html">微服务介绍</a></li>  
                                <li><a href="/tag-25407/">微服务专辑</a></li>
                                <li><a href="/tag-9899/">模块化设计</a></li>
                                <li><a href="/soa.html">SOA</a></li>
                                <li><a href="/tag-33426/">API设计</a></li>
								<li><a href="/tag-40250/">clean架构</a></li>
                                <li><a href="/springboot.html">SpringBoot</a></li>
								<li><a href="/tag-20358/">Saga事务</a></li>	
								<li><a href="/tag-17268/">事件溯源</a></li>
								<li><a href="/tag-34196/">Kubernetes</a></li>	
                                <li><a href="/tag-29134/">DevOps</a></li>
							</ul>
						</div>
					</div>
				</li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">编程设计 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">								
                                <li><a href="/designpatterns/">GoF设计模式</a></li>	
                                <li><a href="/tag-324/">模式专辑</a></li>	
                                <li><a href="/oo.html">面向对象</a></li>
								<li><a href="/functional.html">函数式编程</a></li>
								<li><a href="/tag-222/">编程语言比较</a></li>
								<li><a href="/tag-38692/">编程工具比较</a></li>
						        <li><a href="/tag-16355/">形式逻辑</a></li>	
                                <li><a href="/tag-31545/">前端编程</a></li>	
								<li><a href="/reactive.html">Reactive编程</a></li>
								<li><a href="/jdonframework/">Jdon框架</a></li>
                                <li><a href="/idea/rust.html">Rust语言</a></li>	
								<li><a href="/tag-30661/">人工智能</a></li>		
                                <li><a href="/tag-58439/">Web3</a></li>															
							</ul>
						</div> 
					</div>
				</li>
				<li><a href="/tags/">#(.*)</a></li>                					
			</ul>
			<ul class="list-inline navbar-right top-social">
				<li><a href="javascript:shareto('sina')"><i class="fa fa-weibo"></i></a></li>
				<li><a href="javascript:shareto('weixin')"><i class="fa fa-weixin"></i></a></li>
				<li><a href="javascript:shareto('qzone')"><i class="fa fa-qq"></i></a></li>
			</ul>
		</div>
	</nav>
<!-- Header -->	