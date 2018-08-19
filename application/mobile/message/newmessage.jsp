<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>


<logic:present name="NEWMESSAGES">
	<logic:notEmpty name="NEWMESSAGES">
		<logic:greaterThan name="NEWMESSAGES" value="0">
		   <div data-role="header" data-theme="e" align="center">		
		   
			<a href="/mobile/shortmessage.jsp" target="_blank"  data-role="button" >
				<html:img page="/images/message.gif" width="16" height="12"
					styleId="myblank" alt="新消息" title="新消息" border="0" />
				您有<bean:write name="NEWMESSAGES" />条新消息					
			</a>
			
			</div>
			
		</logic:greaterThan>
	</logic:notEmpty>
</logic:present>

