<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page trimDirectiveWhitespaces="true" %>
<header>
	<!--Top-->
	<nav id="top">
		<div class="container">
			<div class="row">
                <div class="col-lg-12 ">
                    <a href="/"><img src="/simgs/jdon100.png" width="100" height="40" fetchpriority="high" alt="极道"/></a>
                    <span class="list-inline top-link link pull-right">
				        <a href="/forum"><i class="fa fa-home"></i>Dojo</a>
                        <a href="/tags/"><i class="fa fa-arrow-circle-o-down"></i>话题</a>
						<a href="/threads"><i class="fa fa-list-ul"></i>最新</a>
                        <a href="/approval"><i class="fa fa-arrow-circle-up"></i>新佳</a>
						<a href="/forum/threadDigSortedList"><i class="fa fa-star-half-full"></i>最佳</a>
                        <a href="/followus.html"><i class="fa fa-feed"></i>订阅</a>
                    </span>
				</div>
			</div>
		</div>
	</nav>

	<!--Navigation-->
    <nav id="menu" class="navbar container">
        <div class="navbar-header">
		    <button type="button" class="btn btn-navbar navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse"><i class="fa fa-bars"></i></button>
            <a class="navbar-brand" href="/">极道</a>
        </div>
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav navbar-inverse">
			   <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">认知哲学 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
				                <li><a href="/design.htm">原创思考</a></li>
                                <li><a href="/tag-16355/">形式逻辑</a></li>	
                                <li><a href="/tag-58254/">认知偏差</a></li>
                                <li><a href="/tag-38982/">语言游戏</a></li>
                                <li><a href="/tag-5676/">道德经</a></li>
                                <li><a href="/tag-49140/">大语言模型</a></li>
                                <li><a href="/tag-1159/">算法</a></li>
                                <li><a href="/functional.html">函数语言</a></li>
                                <li><a href="/tag-77518/">大科技</a></li>
                                <li><a href="/tag-49555/">数字化转型</a></li>
                                <li><a href="/tag-49075/">数据科学</a></li>
                                <li><a href="/tag-38726/">系统思维</a></li>	
							</ul>
						</div>
					</div>
				</li>    
               <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">领域驱动 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">
				                <li><a href="/ddd.html">DDD介绍</a></li>
                                <li><a href="/tag-272/">DDD专辑</a></li>
								<li><a href="/tag-10740/">战略建模</a></li>   		
								<li><a href="/tag-20044/">领域语言UL</a></li>
								<li><a href="/tag-15977/">限界上下文</a></li>
                                <li><a href="/event.html">领域事件</a></li>
								<li><a href="/tag-19145/">DDD案例</a></li>
								<li><a href="/colorUML.html">商业分析</a></li>
							    <li><a href="/workflow-bpm.html">工作流BPM</a></li>
								<li><a href="/tag-26738/">规则引擎</a></li>		
								<li><a href="/tag-240/">权限设计</a></li>					
								<li><a href="/tag-619/">架构师观点</a></li>	
								<li><a href="/tag-333/">架构比较</a></li>
								<li><a href="/tag-231/">UML建模</a></li>
								<li><a href="/tag-49580/">数据中台</a></li>
								<li><a href="/tag-38587/">产品经理</a></li>                               
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
								<li><a href="/tag-40250/">Clean架构</a></li>
                                <li><a href="/soa.html">SOA</a></li>
                                <li><a href="/tag-33426/">API设计</a></li>
								<li><a href="/tag-1109/">RESTful</a></li>
                                <li><a href="/springboot.html">SpringBoot</a></li>
								<li><a href="/tag-1093/">分布式事务</a></li>	
                                <li><a href="/tag-212/">分布式架构</a></li>	
								<li><a href="/tag-2513/">伸缩性</a></li>	
								<li><a href="/tag-35487/">弹性工程</a></li>
								<li><a href="/tag-11205/">CAP定理</a></li>	
								<li><a href="/tag-17268/">事件溯源</a></li>
								<li><a href="/cqrs.html">CQRS</a></li>
                                <li><a href="/tag-20358/">Saga模式</a></li>
								<li><a href="/tag-30114/">Redis</a></li>
                                <li><a href="/tag-34196/">Kubernetes</a></li>	
								<li><a href="/tag-28110/">docker</a></li>
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
								<li><a href="/tag-222/">编程语言比较</a></li>
								<li><a href="/tag-38692/">编程工具比较</a></li>
								<li><a href="/tag-31545/">前端编程</a></li>	
								<li><a href="/tag-210/">关系数据库</a></li>
								<li><a href="/tag-313/">缓存设计</a></li>
	                            <li><a href="/tag-19231/">大数据架构</a></li>
								<li><a href="/tag-249/">技术架构</a></li>
								<li><a href="/tag-320/">软件工程</a></li>
								<li><a href="/tag-17472/">敏捷</a></li>
								<li><a href="/tag-300/">性能调优</a></li>
						        <li><a href="/tag-544/">异步编程</a></li>
                                <li><a href="/tag-4973/">并发编程</a></li>
								<li><a href="/reactive.html">Reactive编程</a></li>
								<li><a href="/tag-354/">事务架构</a></li>
								<li><a href="/tag-29768/">流处理</a></li>
								<li><a href="/tag-216/">多线程</a></li>
                                <li><a href="/idea/rust.html">Rust语言</a></li>	
								<li><a href="/tag-48335/">ChatGPT</a></li>															
							</ul>
						</div> 
					</div>
				</li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">模因梗 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						<div class="dropdown-inner">
							<ul class="list-unstyled">								
                                <li><a href="/tag-37546/">幽默梗</a></li>                                                     
                                <li><a href="/tag-297/">程序员吐槽</a></li>                               
                                <li><a href="/tag-20618/">面试技巧</a></li>                               
                                <li><a href="/tag-395/">Java入门</a></li>                                
								<li><a href="/tag-445/">GitHub</a></li>
								<li><a href="/tag-36094/">最佳实践</a></li>
								<li><a href="/tag-42486/">系统设计</a></li>
                                <li><a href="/idea.html">Java教程</a></li>
                                <li><a href="/dl/best/spring.htm">Spring教程</a></li>
                                <li><a href="/course.html">编程教程</a></li>
                                <li><a href="/android.html">安卓编程</a></li>
                                <li><a href="/mybook/index.htm">Java实用书籍</a></li>
                                <li><a href="/concurrency.html">并发教程</a></li>
                                <li><a href="/DistributedSystems.html">分布式教程</a></li>
                                <li><a href="/bigdata.html">大数据教程</a></li>
                                <li><a href="/tags/">更多话题</a></li>	
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
</header>
