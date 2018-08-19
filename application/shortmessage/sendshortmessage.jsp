<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>

<br>
<html:form action="/account/protected/deletemsgAction.shtml"
	method="post" onsubmit="return checkPost(this);" styleId="smsg_form">
	<html:hidden property="msgId" />
	<html:hidden property="method" value="delete" />
		
		<%@ include file="messageformReadonly.jsp" %>
		
	<table cellpadding="4" cellspacing="0" border="0">
		<tr>
			<td width="20">
				&nbsp;
			</td>
			<td>
				<input type="button" value="删 除" onclick="javascript:delAction('<%=request.getContextPath()%>/account/protected/deletemsgAction.shtml?action=delete')">
			</td>
		</tr>
	</table>

</html:form>


<%@ include file="footer.jsp" %>