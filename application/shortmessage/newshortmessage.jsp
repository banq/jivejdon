<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@page import="com.jdon.jivejdon.presentation.listener.UserCounterListener,java.util.List,java.util.Iterator"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>

<script>
function copyname(from){	

  document.getElementById("messageTo").value=from;
	
}
</script>
当前在线:
<%
int count = 1;
List userList = (List)application.getAttribute(UserCounterListener.OnLineUser_KEY);
StringBuffer sb = new StringBuffer();
Iterator iter = userList.iterator();
while(iter.hasNext()){
	String username = (String)iter.next();
	sb.append("<span onclick=copyname('"+username+"') >"+username+"</span>&nbsp;");
	if ((count = count % 6) == 0)
		sb.append("<br>");
	count = count + 1;
}
out.print(sb.toString());
%>
<p>
<html:form action="/account/protected/shortmessageSaveAction.shtml"
	method="post" onsubmit="return checkPost(this);" styleId="smsg_form">
	<html:hidden property="msgId" />
	<html:hidden property="method" value="create" />
	
	<%@ include file="messageform.jsp" %>
	
	<table cellpadding="4" cellspacing="0" border="0">
		<tr>
			<td width="20">&nbsp;</td>
			<td><input type="button" value="发 送"
				onclick="javascript:sendAction('<%=request.getContextPath()%>/account/protected/shortmessageSendAction.shtml?service=shortMessageService&method=sendShortMessage')"></td>
			<td><input type="submit" value="保 存" name="formButton"
				tabindex="1"></td>
			<td><input type="reset" value="重 置" name="resetButton">
			</td>
		</tr>
	</table>
</html:form>

<%@ include file="footer.jsp" %>