<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 编辑论坛" />
<%@ include file="../../admin/header.jsp" %>


<table cellpadding="0" cellspacing="0" border="0" width="100%">
<td>
<b>编辑论坛</b>
</td>
<td align="right">
   
</td>
</table>
<hr size="0">

<html:form action="/forum/admin/forumSaveAction.shtml" method="POST">
<html:hidden property="method"/>
<html:hidden property="forumId"/>
  <table cellpadding="2" cellspacing="0" border="0">
    <tr>
    	<td><font size="-1">标题:</font></td>
    	<td><html:text property="name"  size="40" maxlength="100" /> </td>
    </tr>
    <tr>
    	<td valign="top"><font size="-1">描述:</font></td>
    	<td><html:textarea property="description" cols="40" rows="5">
            </html:textarea>
         </td>
    </tr>
    <tr>
    	<td>&nbsp;</td>
    	<td><html:submit property="submit" value="保存修改"/><br> 
    	    <html:submit property="button" value="删除该论坛" onclick="delConfirm()"/><br>
        </td>

    </tr>
    </table>

</html:form>
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


<%@include file="../../admin/footer.jsp"%> 


