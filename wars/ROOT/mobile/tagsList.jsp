<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page session="false" %>

<bean:define id="title"  value=" 标签列表" />
<%@ include file="header.jsp" %>


	<div data-role="header">
		<h1><b>分类标签</b> </h1>
	</div><!-- /header -->

<div data-role="navbar">
	<ul>
		<li><a href="<%=request.getContextPath() %>/mobile/new" >主题</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/messages">评论</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/approval">推荐</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/tags" class="ui-btn-active ui-state-persist">标签</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/authors">用户</a></li>
	</ul>
</div><!-- /navbar -->

<div data-role="content">	

<ul data-role="listview" data-inset="true" data-filter="true">

<logic:iterate id="threadTag" name="tagsListForm" property="list" >
  <li>
    <a href='<%=request.getContextPath() %>/mobile/tags/<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" />      
    </a>
    
    <span class="ui-li-count">
            <bean:write name="threadTag" property="assonum" />
    </span>
    
   </li>
</logic:iterate>
</ul>

</div>

<%@include file="js.jsp"%>  
<%@include file="footer.jsp"%> 

