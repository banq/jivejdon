<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<header>
	<!--Top-->
	<nav id="top">
		<div class="container">
			<div class="row">
                <div class="col-lg-12 ">
                    <a href="/"><img src="/simgs/jdon100.png" width="100" height="40" loading="lazy" alt="极道"/></a>
                    <span class="list-inline top-link link pull-right">
				        <a href="/forum/"><i class="fa fa-home"></i>Dojo</a>
                        <a href="/tagAll/"><i class="fa fa-arrow-circle-o-down"></i>话题</a>
						<a href="/approval/"><i class="fa fa-star-half-full"></i>新佳</a>
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
						  <div class="dropdown-inner" id="renzhi">
                       
                          </div>
					</div>
				</li>    
               <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">领域驱动 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						  <div class="dropdown-inner"  id="ddd">
 
                           </div>
					</div>
				</li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">微服务 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
	                      <div class="dropdown-inner" id="micros">
                          
                          </div>
					</div>
				</li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">编程设计 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
	                   <div class="dropdown-inner" id="prog">
                        
                       </div>
					</div>
				</li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">模因梗 <i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
	                   <div class="dropdown-inner" id="meme">
                       
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
