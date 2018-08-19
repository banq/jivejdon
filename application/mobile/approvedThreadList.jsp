<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ include file="header.jsp" %>

	<div data-role="header">
		<h1><b>推荐</b> </h1>
	</div><!-- /header -->
	
	<div data-role="navbar">
	<ul>
		<li><a href="<%=request.getContextPath() %>/mobile/new" >主题</a></li>		
		<li><a href="<%=request.getContextPath() %>/mobile/messages">评论</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/approval" class="ui-btn-active ui-state-persist">推荐</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/tags">标签</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/authors">用户</a></li>
	</ul>
     </div><!-- /navbar -->
 
	<div data-role="content">	
		
		<ul data-role="listview" data-inset="true" data-filter="true">

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >

<li><a href="<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId"/>" data-ajax="false" >
<h3><bean:write name="forumThread" property="name" /></h3> 
 <p>
<bean:write name="forumThread" property="viewCount" />阅
&nbsp;&nbsp;
<logic:greaterThan name="forumThread" property="state.messageCount" value="0">
    <logic:notEmpty name="forumThread" property="state.lastPost">                        
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>                        
            最近更新：
            <bean:write name="lastPost" property="account.username" />
            &nbsp;
            <bean:write name="lastPost" property="modifiedDate3" />          
    </logic:notEmpty>
</logic:greaterThan>        

 </p>
 
 </a>
 
 
 
 	<span class="ui-li-count">
             <%
             com.jdon.jivejdon.model.ForumThread thread = (com.jdon.jivejdon.model.ForumThread)pageContext.getAttribute("forumThread");
             int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
             java.text.DecimalFormat df  = new java.text.DecimalFormat("#.000");
             
             %>
             <%=bodylength %>
    </span>
  </li>

</logic:iterate>
</ul>


<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="allCount" name="threadListForm" property="allCount" />
<%
String preurl = request.getContextPath() + "/mobile/approval";
%>
<%@ include file="pagination.jsp" %>        
        
</div><!-- /content -->
 <%@include file="js.jsp"%>  
<%@include file="footer.jsp"%> 

