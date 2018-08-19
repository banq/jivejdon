<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page session="false"  %>
<%@ include file="header.jsp" %>

	<div data-role="header">
		<h1><b>最新评论</b> </h1>
	</div><!-- /header -->
	
	<div data-role="navbar">
	<ul>
		<li><a href="<%=request.getContextPath() %>/mobile/new" >主题</a></li>		
		<li><a href="<%=request.getContextPath() %>/mobile/messages" class="ui-btn-active ui-state-persist">评论</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/approval">推荐</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/tags">标签</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/authors">用户</a></li>
	</ul>
     </div><!-- /navbar -->
 
	<div data-role="content">	
		
		<ul data-role="listview" data-inset="true" data-filter="true">
<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">
		

  <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
  <bean:define id="forum" name="forumMessage" property="forum" />
<bean:define id="account" name="forumMessage" property="account" />
<li>       
       <logic:equal name="forumMessage" property="root" value="true">
              <a href='<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId" />' target="_blank" data-ajax="false">                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a href='<%=request.getContextPath()%>/mobile/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />' target="_blank" data-ajax="false" rel="nofollow"> 
       </logic:equal>
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
           	    <img  src="/images/emavatar.png"  border='0' width="75" height="75" />
           </logic:empty>          
           
       <h3><bean:write name="forumMessage" property="account.username" /></h3> 
      <p>             
           <br><bean:write name="forumMessage" property="messageVO.shortBody[100]" filter="false"/> 
     </p>
            	        
      </a>
      
</li> 	

</logic:iterate>		
</ul>		
<bean:define id="pagecount" name="messageListForm" property="count" />
<bean:define id="allCount" name="messageListForm" property="allCount" />
<%
String preurl = request.getContextPath() + "/mobile/messages";
%>
<%@ include file="pagination.jsp" %>        
        
</div><!-- /content -->
<%@include file="js.jsp"%>  
<%@include file="footer.jsp"%> 


		