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
                    <a href="<%=domainUrl%>/threads/"><img src="<%=domainUrl%>/simgs/jdon100.png" width="100" height="40" alt="极道Jdon"/></a>
                    <span class="list-inline top-link link pull-right">
				        <a href="<%=domainUrl%>/forum/"><i class="fa fa-home"></i>Dojo</a>
                        <a href="<%=domainUrl%>/tag/"><i class="fa fa-arrow-circle-o-down"></i>话题</a>
						<a href="<%=domainUrl%>/approval/"><i class="fa fa-star-half-full"></i>新佳</a>
                        <a href="<%=domainUrl%>/followus.html"><i class="fa fa-feed"></i>订阅</a>
                    </span>
				</div>
			</div>
		</div>
	</nav>

	<!--Navigation-->
    <nav id="menu" class="navbar container">
        <div class="navbar-header">
		    <button type="button" class="btn btn-navbar navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse"><i class="fa fa-bars"></i></button>
            <a class="navbar-brand" href="<%=domainUrl%>/">极道</a>
        </div>
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav navbar-inverse">
			    <li class="dropdown"><a href="https://www.jdon.com/metacognition.htm" class="dropdown-toggle" data-toggle="dropdown">元认知<i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						  <div class="dropdown-inner" id="renzhi">
                       
                          </div>
					</div>
				</li>    
               <li class="dropdown"><a href="https://www.jdon.com/metalogic.htm" class="dropdown-toggle" data-toggle="dropdown">元逻辑<i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
						  <div class="dropdown-inner"  id="ddd">
 
                           </div>
					</div>
				</li>
                <li class="dropdown"><a href="https://www.jdon.com/metadesign.htm" class="dropdown-toggle" data-toggle="dropdown">元设计<i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
	                      <div class="dropdown-inner" id="micros">
                          
                          </div>
					</div>
				</li>
				<li class="dropdown"><a href="https://www.jdon.com/metaprogramming.htm" class="dropdown-toggle" data-toggle="dropdown">元编程<i class="fa fa-arrow-circle-o-down"></i></a>
					<div class="dropdown-menu">
	                   <div class="dropdown-inner" id="prog">
                        
                       </div>
					</div>
				</li>
                <li class="dropdown"><a href="https://www.jdon.com/metalanguage.htm" class="dropdown-toggle" data-toggle="dropdown">元语言<i class="fa fa-arrow-circle-o-down"></i></a>
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
