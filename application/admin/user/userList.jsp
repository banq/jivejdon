<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 用户管理" />
<%@ include file="../header.jsp" %>
 
<p><b>用户概览</b>

<hr size="0">

<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr>
    <td>
<table bgcolor="#ffffff"
 cellpadding="3" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr><td>
<bean:size id="userCount" name="userListForm" property="list"/>
共有 <bean:write name="userCount"/> 个用户 [
<MultiPages:pager actionFormName="userListForm" page="/admin/user/userListAction.shtml">
<MultiPages:prev name="&laquo;" />
<MultiPages:index />
<MultiPages:next name="&raquo;" />
</MultiPages:pager>
     ]
    <td><td >

    </td>
</tr>
</table>
    </td>
</tr>
</table>
<bean:parameter  name="username" id="username" value=""/>
<html:form action="/admin/user/userListAction.shtml" method="post" >
查询用户名为<input type="text" name="username" value="<bean:write name="username"/>"/>
 <html:submit value=" 查询 " property="btnsearch" />
</html:form>

<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td> 
<table bgcolor="#999999" cellpadding="3" cellspacing="1" border="0" width="100%">
<tr bgcolor="#FFFFCC">
    <td style="text-align: center" nowrap><b>用户ID</b></td>
    <td style="text-align: center" nowrap><b>用户名</b>></td>
    <td style="text-align: center" nowrap><b>EMAIL</b></td>
    <td style="text-align: center" nowrap><b>编辑</b></td>
    <td style="text-align: center" nowrap><b>删除</b></td>
</tr>

<logic:iterate id="user" name="userListForm" property="list">
 

<tr bgcolor="#ffffff">
    <td style="text-align: center" width="2%"><bean:write name="user" property="userId"/></td>
    <td width="30%"><html:link page="/admin/user/editAccountForm.shtml?action=edit" paramId="username" paramName="user" paramProperty="username"
    ><bean:write name="user" property="username"/></html:link></td>
    <td width="30%"><bean:write name="user" property="email"/></td>
    <td style="text-align: center" width="4%"
        ><html:link page="/admin/user/editAccountForm.shtml?action=edit" paramId="username" paramName="user" paramProperty="username">
        <img src="../images/button_edit.gif" width="17" height="17" alt="编辑用户属性..." border="0"
        ></html:link></td>
    <td style="text-align: center" width="4%"
        ><html:link page="/admin/user/removeAccountForm.shtml?action=delete" paramId="username" paramName="user" paramProperty="username" onclick="return delConfirm()">
        <img src="../images/button_delete.gif" width="17" height="17" alt="删除用户..." border="0"
        ></html:link></td>
</tr>

</logic:iterate>
</table>

<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr>
    <td>
<table bgcolor="#ffffff"
 cellpadding="3" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr><td>
共有 <bean:write name="userCount"/> 个用户 [
<MultiPages:pager actionFormName="userListForm" page="/admin/user/userListAction.shtml">
<MultiPages:prev name="&laquo;" />
<MultiPages:index />
<MultiPages:next name="&raquo;" />
</MultiPages:pager>
     ]
    <td><td >

    </td>
</tr>
</table>
    </td>
</tr>
</table>


<script type="text/JavaScript">
function delConfirm(){
  if (confirm( '删除该论坛 ! \n\n 你肯定吗?  '))
  {
    document.forms[0].method.value="delete";
    document.forms[0].submit();
    return true;
  }else{
    return false;
  }
}
</script>


<%@include file="../footer.jsp"%>
