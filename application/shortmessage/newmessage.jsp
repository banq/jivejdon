<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-store"); 
response.setDateHeader("Expires", 0); 
%>
<%@ include file="../common/security.jsp" %>

<logic:present name="NEWMESSAGES">
	<logic:notEmpty name="NEWMESSAGES">
		<logic:greaterThan name="NEWMESSAGES" value="0">
		   <br><br>
		   <div align="center">
			<html:link page="/shortmessage.jsp" target="_blank">
				<html:img page="/images/message.gif" width="16" height="12"
					styleId="myblank" alt="新消息" title="新消息" border="0" />
				您有<bean:write name="NEWMESSAGES" />条新消息
			</html:link>
			</div>
			<script>								
				window.top.$LAB
     .script("/common/js/window_def.js").wait()
     .script("<%=request.getContextPath()%>/forum/js/newMessage.js").wait()
     .wait(function(){     					
        popUpNewMessage(); 
     })    
				</script>
			
		</logic:greaterThan>
	</logic:notEmpty>
</logic:present>

