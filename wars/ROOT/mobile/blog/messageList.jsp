<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@ page session="false" %>

<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<bean:define id="title" name="accountProfileForm" property="account.username"/>
<%@ include file="../header.jsp" %>

<div data-role="header">
<logic:notEmpty  name="TITLE">
  <h3><bean:write  name="TITLE"  />的博客
  </h3>
</logic:notEmpty>
</div><!-- /header -->

<div data-role="navbar">
	<ul>
		<li><a href="<%=request.getContextPath() %>/mobile/blog/<bean:write name="accountProfileForm" property="account.username"/>" >主题</a></li>
		<li><a href="<%=request.getContextPath() %>/mobile/blog/messages/<bean:write name="accountProfileForm" property="account.username"/>" class="ui-btn-active ui-state-persist">评论</a></li>
	</ul>
</div>

<div data-role="content">	
		
<ul data-role="listview" data-inset="true" data-filter="true">


<logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" >


  <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
  <bean:define id="forum" name="forumMessage" property="forum" />

<li> 
       <logic:equal name="forumMessage" property="root" value="true">
              <a href='<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId" />' target="_blank" data-ajax="false">                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a href='<%=request.getContextPath()%>/mobile/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />' target="_blank" data-ajax="false" rel="nofollow"> 
       </logic:equal>       
         <h3><bean:write name="forumMessage" property="messageVO.subject"/></h3>
       
        
        <p><bean:write name="forumMessage" property="modifiedDate3" />        
           <bean:write name="forumMessage" property="messageVO.shortBody[100]" filter="false"/>
           </p>
       </a>
</li> 	

</logic:iterate>


  
</ul>


<bean:define id="pagecount" name="messageListForm" property="count" />
<bean:define id="allCount" name="messageListForm" property="allCount" />
<%

String titleS = (String)pageContext.getAttribute("title");
String preurl = request.getContextPath() + "/mobile/blog/messages/" + titleS;
%>
<%@ include file="../pagination.jsp" %>        
        
        
</div><!-- /content -->
<%@include file="../js.jsp"%>  
<%@include file="../footer.jsp"%> 

</logic:greaterThan>
</logic:present>