<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<bean:define id="title"  value=" 论坛概览" />
<%@ include file="../../admin/header.jsp" %>


<table cellpadding="0" cellspacing="0" border="0" width="100%">
<td>
<b><h3>论坛概览</h3></b>
</td>
<td align="right">
   
</td>
</table>
<hr size="0">

<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td>
<table bgcolor="#999999" cellpadding="3" cellspacing="1" border="0" width="100%">
<tr bgcolor="#FFFFCC">
    <td style="text-align: center"><b>论坛标题及描述</b></td>
    <td style="text-align: center"><b>主题数</b></td>
    <td style="text-align: center"><b>消息数</b></td>
    <td style="text-align: center"><b>编辑</b></td>
    <td style="text-align: center"><b>删除</b></td>
</tr>
<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
<tr bgcolor="#ffffff">
    <td>
         <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId">
                       <bean:write name="forum" property="name" />
         </html:link>

     <br><bean:write name="forum" property="description" /></td>
    <td style="text-align: center"><bean:write name="forum" property="forumState.threadCount" /></td>
    <td style="text-align: center"><bean:write name="forum" property="forumState.messageCount" /></td>
    <td style="text-align: center"><html:link page="/admin/forumAction.shtml?action=edit" paramId="forumId" paramName="forum" paramProperty="forumId">
     <html:img  page="/admin/images/button_edit.gif" width="17" height="17" alt="Click to edit all forum properties" border="0"/>
    </html:link></td>
    <td style="text-align: center">
    <html:link page="/admin/forumSaveAction.shtml?action=delete" paramId="forumId" paramName="forum" paramProperty="forumId" onclick="return delConfirm()">
   <html:img  page="/admin/images/button_delete.gif" width="17" height="17" alt="Confirm that you want to delete this forum..." border="0" />
    </html:link></td>
</tr>
</logic:iterate>

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


</table>
</td></tr>
</table>



<%@include file="../../admin/footer.jsp"%> 