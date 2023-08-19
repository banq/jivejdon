<%@taglib uri="struts-bean" prefix="bean"%>
<%@taglib uri="struts-logic" prefix="logic"%>
<%@taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<bean:parameter name="userId" id="userId"/>

<html:form method="post" action="/account/protected/accountProfileSave.shtml">
<html:hidden  property="action" value="editSave" />
<html:hidden  property="userId"  />
<input type="hidden" name="account.userId" value='<bean:write name="userId"/>'/>

<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" style="text-align: center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>个人信息设置</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td style="text-align: center">
 以下输入项目可以自由定义你自己资料项目,
 <br>
 
 <%
int i = 0;
%>
 
<logic:iterate id="property" name="accountProfileForm" property="propertys" >
<logic:notEmpty name="property" property="name">
<table >
  <tr>
  <td align="right"> 
  属性名：<input type="text" name='<%= "property[" + i + "].name" %>' value='<bean:write name="property" property="name"/>' size='5' />
   </td>
  <td>
  属性值：<input type="text" name='<%= "property[" + i + "].value" %>' value='<bean:write name="property" property="value"/>'  />
  </td>
</tr>
</table>
  <% i++; %>
</logic:notEmpty>

</logic:iterate>  
<div id="div1">


</div>


 </td>
</tr></table>
</td></tr>
</table>
                    
<script type='text/javascript'>
var w = <%=i %>;
function setValue(){
        var insert = "<table ><tr><td>属性名：<input type='text' name='property[" 
	+ w + 
	"].name' value='' size='5'/></td>   <td>属性值：<input type='text' name='property[" 
		+ w + 	"].value' value='' /></td></tr></table>";
        var content = document.getElementById("div1").innerHTML;
		content+= insert;
        document.getElementById("div1").innerHTML=content;     
		w++;
}

</script>


<%if (i == 0){%>
	
	<script type='text/javascript'  >
	  setValue();
	  setValue();
	</script>
<%}%>
<center>
<input type="button" value="增加新输入项" onclick="setValue()"/>
×清空输入项即删除
<br>       
<br>                
                      <html:submit property="submit" value="确认保存以上设置"/>
                      <html:reset value ="Reset"/>
                 

</center>
</html:form>
