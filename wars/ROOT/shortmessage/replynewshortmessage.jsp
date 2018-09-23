<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>


	<br>
		<html:form action="/account/protected/replyShortmessageSaveAction.shtml"
			method="post" onsubmit="return checkPost(this);" styleId="smsg_form">
			<html:hidden property="msgId" />
			<html:hidden property="method" value="create"/>
		<%@ include file="messageform.jsp" %>
		
			<table cellpadding="4" cellspacing="0" border="0">
				<tr>
					<td width="20">
						&nbsp;
					</td>
					<td>
						<input type="button" value="发 送"
							onclick="javascript:sendAction('<%=request.getContextPath()%>/account/protected/shortmessageSendAction.shtml?service=shortMessageService&method=sendShortMessage')">						
					</td>
					<td>
					<input type="submit" value="保 存" name="formButton" tabindex="1"
					        onclick="javascript:sendAction('<%=request.getContextPath()%>/account/protected/shortmessageSaveAction.shtml')">
					</td>
					<td>
						<input type="reset" value="重 置"
							name="resetButton">
					</td>
				</tr>
			</table>
		</html:form>
		
		
		<%@ include file="footer.jsp" %>