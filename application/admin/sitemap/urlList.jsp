<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<bean:define id="title"  value=" Sitemap url" />
<%@ include file="../header.jsp" %>
<html:errors/>
<center>
<h3>Sitemap URL
</h3>

<form action="" method="POST" name="listForm" >

<table width="550" cellpadding=6 cellspacing=0 border=1  align="center">
  <tr  bgcolor="#C3C3C3">


   <tr>

    <td bgcolor="#D9D9D9">Url</td>
    <td bgcolor="#D9D9D9">name</td>
    <td bgcolor="#D9D9D9"></td>
</tr>
<logic:iterate indexId="i"   id="url" name="urlListForm" property="list" >
<tr bgcolor="#ffffff">

    <td><bean:write name="url" property="ioc" /></td>
    <td ><bean:write name="url" property="name" /></td>      
     <td>
         <input type="radio" name="urlId" value="<bean:write name="url" property="urlId" />" >
     </td>
</tr>

</logic:iterate>

<tr><td colspan="2">

<html:link page="/admin/sitemap/urlAction.shtml">Add</html:link>


</td>
  <td >
       <input type="button" name="edit" value="Edit" onclick="editAction('urlId')" >
       <input type="button" name="delete" value="Delete" onclick="delAction('urlId')" >
  </td>
</tr>

</table>



</form>
<center>
<script type="text/JavaScript">
function editAction(radioName){
    var isChecked = false;

   if (eval("document.listForm."+radioName).checked){
          isChecked = true;
    }else{
      for (i=0;i<eval("document.listForm."+radioName).length;i++){
         if (eval("document.listForm."+radioName+ "["+i+"]").checked){
           isChecked = true;
           break;
          }
      }
    }
    if (!isChecked){
      alert("请选择一个条目");
      return;
    }else{
      document.listForm.action="<%=request.getContextPath()%>/admin/sitemap/urlAction.shtml?action=edit";
      document.listForm.submit();
    }
}

function delAction(radioName){
    var isChecked = false;

   if (eval("document.listForm."+radioName).checked){
          isChecked = true;
    }else{
      for (i=0;i<eval("document.listForm."+radioName).length;i++){
         if (eval("document.listForm."+radioName+ "["+i+"]").checked){
           isChecked = true;
           break;
          }
      }
    }
    if (!isChecked){
      alert("请选择一个条目");
      return;
    }else{
       if (confirm( 'Delete this order ! \n\nAre you sure ? '))
        {
              document.listForm.action="<%=request.getContextPath()%>/admin/sitemap/urlSaveAction.shtml?action=delete";
              document.listForm.submit();
              return true;
         }else{
              return false;
         }
    }
}

</script>
<div class="yahoo2">    
<MultiPages:pager actionFormName="urlListForm" page="/admin/sitemap/urlListAction.shtml">

<MultiPages:prev name="[Prev ]" />
<MultiPages:index displayCount="1" />
<MultiPages:next  name="[Next ]" />
</MultiPages:pager>
</div>
</center>

<%@include file="../footer.jsp"%>