<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 关键词设置" />
<%@ include file="../header.jsp" %>



<p>人工设置热点关键词，用于在论坛搜索位置显示热点关键词。
如想在帖子中涉及这些热点关键词能粗体链接显示。
</p>

<html:form action="/admin/search/HotKeysAction.shtml">
<html:hidden name="propertysForm" property="action"/>

<%
int i = 0;
%>

<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" >
<tr><td>
<table  border="0" width="100%">
  
<logic:iterate id="prop" name="propertysForm" property="propertys" >
 <logic:notEmpty name="prop" property="name">
 <tr  bgcolor="#FFFFCC">
    <td rowspan="2">关键词</td>
    
    <td>名称：<input type="text" name='<%= "property[" + i + "].name" %>' value='<bean:write name="prop" property="name"/>' /></td>
  </tr>
    <tr>
    <td>链接：<input type="text" name='<%= "property[" + i + "].value" %>' value='<bean:write name="prop" property="value" filter="false" />'    size="40"/></td>
  </tr>
  <% i++; %>
  </logic:notEmpty>
</logic:iterate>
 
  
</table>
  </td></tr></table>
<div id="div1">


</div>

<script type='text/javascript'>
var w = <%=i %>;

function setValuesss(){
	 var insert = "<table bgcolor=#cccccc border=0 width=500 ><tr><td><table  border=0 width=100%>  <tr  bgcolor=#FFFFCC>    <td rowspan=2>关键词</td>    <td>名称：<input type='text' name='property[" 
	+ w + 
	"].name' value='' /></td>  </tr>    <tr >    <td>链接：<input type='text' name='property[" 
		+ w + 	"].value' value='' size='40' /></td>  </tr></table></td></tr></table>";
		  var content = document.getElementById("div1").innerHTML;
		content+= insert;
        document.getElementById("div1").innerHTML=content;     
		w++;
}
</script>


<%if (i == 0){%>
	
	<script type='text/javascript'  >
	  setValuesss();
	  setValuesss();
	</script>
<%}%>

<input type="button" value="增加新输入项" onclick="setValuesss()"/>



<br><br>
<input type="submit" value="确认以上所有关键词"/>

</html:form>


<%@include file="../footer.jsp"%>