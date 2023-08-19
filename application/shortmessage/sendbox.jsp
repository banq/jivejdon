<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>

<center><h3>发件箱</h3></center>
<form action="">
<input type="hidden" name="method" value="delete"/>
	<table class="contacts" cellspacing="0" width="550">
		<tr>
			<td class="contactDept" style="text-align: center">
				标题
			</td>
			<td class="contactDept" style="text-align: center">
				发往
			</td>
				<td class="contactDept" style="text-align: center">
				状态
			</td>
			<td class="contactDept" style="text-align: center">
				时间
			</td>
			
			<td class="contactDept" style="text-align: center">
				操作
			</td>
			
		</tr>
		<logic:iterate id="message" name="sendListForm" property="list"
			indexId="i">
			    <bean:define id="styleclass" value="contactlight"></bean:define>
	            <logic:match name="message" property="shortMessageState.hasRead" value="true">
	                    <bean:define id="styleclass" value="contact"></bean:define>	
	            </logic:match>
			<tr>
				<td class="<bean:write name='styleclass'/>" style="text-align: center">
					<html:link
						page="/account/protected/shortmsgInSendBoxAction.shtml"
						paramId="msgId" paramName="message" paramProperty="msgId">
						<bean:write name="message" property="messageTitle" />
					</html:link>
				</td>
				<td class="<bean:write name='styleclass'/>" style="text-align: center" >
					<bean:write name="message" property="messageTo" />
				</td>
				<td class="<bean:write name='styleclass'/>" style="text-align: center" >
				  <logic:match name="message" property="shortMessageState.hasRead"  value="true">
				    已读
				  </logic:match>
  				  <logic:match name="message" property="shortMessageState.hasRead"  value="false">
				    <b>未读</b>
				  </logic:match>				  
				</td>
				<td class="<bean:write name='styleclass'/>" style="text-align: center" >
					<bean:write name="message" property="shortMessageState.sendTime" />
				</td>
				
				<td class="<bean:write name='styleclass'/>" style="text-align: center" >
				    <html:link page="/account/protected/deletemsgAction.shtml?method=delete" 
				    paramId="msgId" paramName="message" paramProperty="msgId">删 除</html:link>
				</td>
				
			</tr>
		</logic:iterate>
	</table>
	<table cellpadding="4" cellspacing="0" border="0" width="580px">
		<tr>
			<td width="562px">
				<MultiPages:pager actionFormName="sendListForm"
					page="/account/protected/sendListAction.shtml">
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