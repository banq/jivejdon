<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<%@ include file="header.jsp" %>
<div id="reply"></div>


  
<br>
<html:form action="/account/protected/replyShortmessageAction.shtml"
	method="post" styleId="smsg_form" >
	<html:hidden property="msgId"  styleId="msgId"/>
	<input type="hidden" name="messageTo"  id="messageTo" value="">
    <script language="JavaScript" type="text/javascript">
	<!-- 
		function delAction(url)
		{
			 if (confirm( 'Delete this message ! \n\nAre you sure ? '))
        		{
              		$("smsg_form").action=url;
              		$("smsg_form").submit();
             		return true;
         	}else{
              return false;
        	}
		}
	//-->
	</script>
	
    	
	<table cellpadding="2" cellspacing="0" border="0">	    
		<tr >
			<td align="right">
				来自
				<br>
			</td>
			<td>
				<html:text property="messageFrom" size="40" maxlength="75"
					tabindex="0" readonly="true" styleId="messageFrom" ></html:text>
				<br>
			</td>
		</tr>
		<tr>
			<td align="right">
				标题
				<br>
			</td>
			<td>
				<html:text property="messageTitle" size="40" maxlength="75"
					tabindex="1" readonly="true" styleId="messageTitle" ></html:text>
				<br>
			</td>
		</tr>
		<tr>
			<td valign="top" align="right">
				内容
				<br>
			</td>
			<td>
			   <bean:write  name="toShortMessageForm" property="filterMessageBody" filter="false"></bean:write>
				<br>
			</td>
		</tr>
	</table>

	<table cellpadding="4" cellspacing="0" border="0">
		<tr>
			<td width="20">
				&nbsp;
			</td>
			<td>
				<input type="submit" value="回 复" name="formButton" tabindex="1"   >
			</td>
			<td>
				<input type="button" value="删 除" onclick="javascript:delAction('<%=request.getContextPath()%>/account/protected/deletemsgAction.shtml?method=delete')">
			</td>
		</tr>
	</table>
</html:form>

<%@ include file="footer.jsp" %>