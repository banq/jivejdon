<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">
<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>
<style>
body,table,td,select,input,div,form{font-size: 12px; }
A{text-decoration:none;color:#036;}a:visited{text-decoration:none;color: #666;font-weight:lighter;}A:hover{text-decoration:underline;color:#eab30d;}
.important {
	width: 285px;
	padding: 8px;
	margin: 0px;
	background-color: #F7F7F7;
	border: 1px solid #ddd;
	border-radius: 3px;
	box-shadow: 0 15px 10px -15px rgba(0,0,0,0.4);
}

</style>

</logic:notEqual>
 <font color="#3e6d8e" ><b>开发教程</b></font>
<div class="important">
<a href="http://www.jdon.com/performance/cache-control-immutable.html" target="_blank">Cache-Control: immutable</a><br>
<a href="http://www.jdon.com/idea/target-blank.html" target="_blank">Target=”_blank”有史以来最低估的漏洞</a><br>
<a href="http://www.jdon.com/project/fundamental-laws-of-software-development.html" target="_blank">15条软件开发的基本规律</a><br>  
<a href="http://www.jdon.com/dl/best/event-sourcing-microservices-spring-cloud.html" target="_blank">使用Spring Cloud和Reactor在微服务中实现Event Sourcing</a><br>
<a href="http://www.jdon.com/idea/angular2.html" target="_blank">使用Angular2建立一个可扩展单页应用</a><br> 
<a href="http://www.jdon.com/idea/js/rel-prev-net.html" target="_blank">SEO:分页使用rel=“next” 和 rel=“prev”</a><br>
<a href="http://www.jdon.com/idea/redux.html" target="_blank">什么是Redux?</a><br>
<a href="http://www.jdon.com/artichect/logical-clock.html" target="_blank">分布式系统的逻辑时钟</a><br>
 
</div>
