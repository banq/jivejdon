<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>

	<center><h3>草稿箱</h3></center>
<form action="" id="shortList">
<input type="hidden" name="method" value="delete"/>
	<table class="contacts" cellspacing="0" width="550">
		<tr>
			<td class="contactDept" align="center">
				标题
			</td>
			<td class="contactDept" align="center">
				来自
			</td>
			<td class="contactDept" align="center">
				时间
			</td>
			<td class="contactDept" align="center">
				操作
			</td>
		</tr>
		<logic:iterate id="message" name="draftListForm" property="list"
			indexId="i">
			
			<bean:define id="styleclass" value="contactlight"></bean:define>
			<tr>
				<td class="contact" align="center" >
					<html:link
						page="/account/protected/shortmsgInDraftBoxAction.shtml"
						paramId="msgId" paramName="message" paramProperty="msgId">
						<bean:write name="message" property="messageTitle" />
					</html:link>
				</td>
				<td class="contact" align="center" >
					<bean:write name="message" property="messageTo" />
				</td>
				<td class="contact" align="center" >
					<bean:write name="message" property="shortMessageState.sendTime" />
				</td>
					<td class="<bean:write name='styleclass'/>" align="center" >
				    <html:link page="/account/protected/deletemsgAction.shtml?method=delete" 
				    paramId="msgId" paramName="message" paramProperty="msgId">删 除</html:link>
				</td>
			</tr>
		</logic:iterate>
	</table>
	<table cellpadding="4" cellspacing="0" border="0" width="580px">
		<tr>
			<td width="562px">
				<MultiPages:pager actionFormName="draftListForm"
					page="/account/protected/draftListAction.shtml">
					<MultiPages:prev name="[Prev ]" />
					<MultiPages:index />
					<MultiPages:next name="[Next ]" />
				</MultiPages:pager>
			</td>
			
		</tr>
	</table>

</form>
<html:link page="/account/protected/delAll.shtml?service=shortMessageService&method=deleteUserAllShortMessage"
>全部清空</html:link>


<%@ include file="footer.jsp" %>