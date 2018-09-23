<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>

	<center><h3>收件箱</h3></center>
<form action="">
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
		<logic:iterate id="message" name="receiveListForm" property="list"
			indexId="i">
			
				<bean:define id="styleclass" value="contactlight"></bean:define>
	            <logic:match name="message" property="shortMessageState.hasRead" value="true">
	                    <bean:define id="styleclass" value="contact"></bean:define>	
	            </logic:match>
	            
			<tr>
			
				<td class="<bean:write name='styleclass'/>" align="center" >
				  <logic:match name="message" property="shortMessageState.hasRead" value="false">
	                    <html:img page="/images/new.gif" width="23" height="9" />
	            </logic:match>
				
					<html:link
						page="/account/protected/shortmsgInReceiveBoxAction.shtml"
						paramId="msgId" paramName="message" paramProperty="msgId">
						<bean:write name="message" property="messageTitle" />
					</html:link>
				</td>
				<td class="<bean:write name='styleclass'/>" align="center" >
					<bean:write name="message" property="messageFrom" />
				</td>
				<td class="<bean:write name='styleclass'/>" align="center" >
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
				<MultiPages:pager actionFormName="receiveListForm"
					page="/account/protected/receiveListAction.shtml">
					<MultiPages:prev name="[Prev ]" />
					<MultiPages:index />
					<MultiPages:next name="[Next ]" />
				</MultiPages:pager>
			</td>
		</tr>
	</table>
</form>
<html:link page="/account/protected/delAll.shtml?service=shortMessageService&method=deleteUserRecAllShortMessage"
>全部清空</html:link>(超过3页请清空 否则无法接受新信息)


<%@ include file="footer.jsp" %>