<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 用户管理" />
<%@ include file="../header.jsp" %>

<html:form action="/admin/user/editSaveAccount.shtml" method="post">

<html:hidden property="action" value="delete" />
<input type="hidden" name="actionType" value="editSave"/>
<html:hidden property="userId" />
<html:hidden property="username" />

<font size="-1"><b>确认用户删除操作</b></font><p>
<ul>
	警告: 这将永久的删除用户<bean:write name="accountForm" property="username"/>。
	<br/>你确认你真的想这样做？? (这<b>不会</b>删除该用户发布的消息。在用户被删除后该用户发布的消息将被标记为"anonymous"。)
	<br>
	<input type="submit" value="删除用户">
	&nbsp;
	<input type="button" name="cancelButton" value="取消">
</ul>
</html:form>

<script language="JavaScript" type="text/javascript">
<!--
// activate the "cancel" button -- if the user accidentally hits enter or
// space on this page, the default action would be to cancel, not delete
// the user ;)
document.deleteForm.cancelButton.focus();
//-->
</script>

<%@include file="../footer.jsp"%>
