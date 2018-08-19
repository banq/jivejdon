<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ include file="header.jsp" %>

	<div data-role="header">
		<h1><b>活跃用户</b> </h1>
	</div><!-- /header -->
	
	<div data-role="navbar">
	<ul>
		<li><a href="<%=request.getContextPath() %>/mobile/new" >主题</a></li>		
		<li><a href="<%=request.getContextPath() %>/mobile/messages">评论</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/approval">推荐</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/tags">标签</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/authors" class="ui-btn-active ui-state-persist">用户</a></li>
	</ul>
     </div><!-- /navbar -->
 
	<div data-role="content">	
		
		<ul data-role="listview" data-inset="true" data-filter="true">


<logic:iterate id="account" name="accountListForm" property="list" >
<li>
         <a href='<%=request.getContextPath()%>/mobile/blog/<bean:write name="account" property="username" />'>          
           <logic:notEmpty name="account" property="uploadFile">
            <logic:equal name="account" property="roleName" value="User"> 
               <img  src="/img/account/<bean:write name="account" property="userId"/>"  border='0' class="post_author_pic" />
            </logic:equal>
            <logic:equal name="account" property="roleName" value="SinaUser">                 
               <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' class="post_author_pic" />
            </logic:equal>
            <logic:equal name="account" property="roleName" value="TecentUser">                 
               <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' class="post_author_pic"/>
            </logic:equal>                                    
           </logic:notEmpty>
           <logic:empty name="account" property="uploadFile">
           	    <img  src="/emavatar.png"  border='0' width="75" height="75" />
           </logic:empty>          
              <h3><bean:write name="account" property="username" /></h3>
   <p>
      文章:<bean:write name="account" property="messageCount"/>&nbsp;&nbsp;
      注册:<bean:write name="account" property="creationDate"/>&nbsp;&nbsp;
   <logic:greaterThan name="account" property="subscribedCount" value="0">
    <bean:write name="account" property="subscribedCount"/>人关注  &nbsp;&nbsp;
   </logic:greaterThan>
   </p>
   </a>
</li>
</logic:iterate>
</ul>

</div><!-- /content -->
 <%@include file="js.jsp"%>  
<%@include file="footer.jsp"%> 
