<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.jdon.security.web.CookieUtil"%>

<bean:define id="forum" name="messageReplyForm" property="forum"  />

<bean:define id="parentMessage" name="messageReplyForm" property="parentMessage"  />
<bean:define id="forumThread" name="parentMessage" property="forumThread" />

<bean:define id="title" name="forumThread" property="name"/>
<%@ include file="header.jsp" %>

<div data-role="header" data-theme="e">
		<h1>发布评论</h1>
	</div><!-- /header -->

	<div data-role="content" data-theme="d">	

<!-- create another name "messageForm", so in messageFormBody.jsp it can be used -->
<bean:define id="messageForm" name="messageReplyForm" />

<bean:parameter id="reply" name="reply"  value="true"/>

<logic:notEmpty name="messageForm">     
<logic:notEmpty name="messageForm" property="messageId">     
     <logic:equal  name="messageForm" property="root" value="true">
         <bean:define id="reply" value="false"></bean:define>
     </logic:equal>
     <logic:equal  name="messageForm" property="root" value="false">
         <bean:define id="reply" value="true"></bean:define>
     </logic:equal>
</logic:notEmpty>
</logic:notEmpty>

<logic:notEmpty name="messageReplyForm">
   <bean:define id="messageForm" name="messageReplyForm" scope="request" />
   <bean:define id="reply" value="true"></bean:define>
</logic:notEmpty>

<form action="" id="loginForm">
<%if (request.getUserPrincipal() == null){
String username = CookieUtil.getUsername(request);
String password = CookieUtil.getPassword(request);
if (username == null){
	username = "";
	password = "";
}
%>
用户:<input type="text" name="j_username" id="j_username" size="20" tabindex="1" value="<%=username%>" >
密码:<input type="password" name="j_password" id="j_password" size="20" tabindex="2" value="<%=password%>">
<div data-role="fieldcontain">
			<fieldset data-role="controlgroup">
				<legend>自动登录</legend>
       <input type="checkbox" name="rememberMe" checked="checked" id="rememberMe">
				
			</fieldset>
</div>			

<%} %>
</form>

<html:form action="/mobile/message/messageReplySaveAction.sthml" method="post" styleId="messageReply"  >
<html:hidden property="action" />
<html:hidden property="parentMessage.messageId" />
<html:hidden property="messageId" />
<bean:parameter name="newPageNo" id="newPageNo" value="0"/>
	<%
	String token = org.apache.struts.util.TokenProcessor.getInstance().generateToken(request);
    request.setAttribute("org.apache.struts.action.TOKEN", token);
    %>
<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=token%>" />
<input type="hidden" name=newPageNo value="<bean:write name="newPageNo"/>" />

标题:<html:text  property="subject" styleId="replySubject" size="20" maxlength="35" tabindex="3" />

 <br>
内容:<br>
<html:textarea property="body" cols="26" rows="10" styleId="formBody" tabindex="4"></html:textarea>	
 <br><input type="submit" value="发 表" name="formButton" id="formSubmitButton" onclick="return Login();">
</html:form>



<script type="text/javascript">
var loggedURL =
    '<html:rewrite page="/account/protected/logged.jsp"/>?Referer=https://'+ location.hostname;
   
</script>

</div>		    

 <%@include file="js.jsp"%>  
