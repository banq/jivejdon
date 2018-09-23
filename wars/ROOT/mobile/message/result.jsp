<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ include file="../header.jsp" %>

<div data-role="header" data-theme="e">
		<h1>回复结果</h1>
	</div><!-- /header -->

	<div data-role="content" data-theme="d">	
		
<logic:notEmpty name="messageReplyForm">
<bean:define id="messageId" name="messageReplyForm" property="messageId"  />
<bean:define id="forumThread" name="messageReplyForm" property="forumThread"  />
<bean:define id="action" name="messageReplyForm" property="action"  />
</logic:notEmpty>


<logic:notEmpty name="messageForm">
<bean:define id="messageId" name="messageForm" property="messageId"  />
<bean:define id="forumThread" name="messageForm" property="forumThread"  />
<bean:define id="action" name="messageForm" property="action"  />
</logic:notEmpty>

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
  <table cellpadding="0" cellspacing="0" border="0"  align="center"> 
<tr> 
    <td valign="top" > 
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
    
    </td></tr></table>
  </logic:iterate>
</logic:present>

 <logic:messagesNotPresent>
    <logic:empty name="errors">
    	帖子操作成功！需刷新页面看到效果 <br>
    	<bean:parameter name="newPageNo" id="newPageNo" value="0"/>
      <logic:equal name="newPageNo" value="0">
      	<a href="<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId" />">回主题</a>
      </logic:equal>
      <logic:notEqual name="newPageNo" value="0">
      	<script>
    		window.location.href="<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId" />/<bean:write name="newPageNo" />";
    		</script>
      	<a href="<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId" />/<bean:write name="newPageNo" />">回主题</a>
      </logic:notEqual>
      
  </logic:empty>
  </logic:messagesNotPresent>      
</div>
<%@include file="../js.jsp"%>  
<%@include file="../footer.jsp"%> 