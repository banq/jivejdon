<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>

<br>
<html:form
	action="/account/protected/drafSendAction.shtml?service=shortMessageService&method=sendInDraftMessage"
	method="post" onsubmit="return checkPost(this);" styleId="smsg_form">
	<html:hidden property="msgId" />
	<html:hidden property="method" value="create" />
	
	<%@ include file="messageform.jsp" %>
	
	<table cellpadding="4" cellspacing="0" border="0">
		<tr>
			<td width="20">
				&nbsp;
			</td>
			<td>
				<input type="submit" value="发 送" name="formButton" tabindex="1">
			</td>
			<td>
				<input type="button" value="保 存" onclick="javascript:sendAction('<%=request.getContextPath()%>/account/protected/drafSendAction.shtml?service=shortMessageService&method=saveInDraftMessage')">
			</td>
			<td>
				<input type="button" value="删 除" onclick="javascript:delAction('<%=request.getContextPath()%>/account/protected/drafSendAction.shtml?service=shortMessageService&method=deleInDraftMessage')">
			</td>
		</tr>
	</table>
</html:form>


<%@ include file="footer.jsp" %>
