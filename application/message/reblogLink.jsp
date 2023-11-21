<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="threadForm">
<bean:define id="forum" name="threadForm" property="forum"  />
<logic:notEmpty name="forum">
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>


    <bean:define id="ForumMessage" name="threadForm" property="rootMessage"  />

<div class="col-lg-offset-4 col-lg-4">



<logic:equal name="threadForm" property="authenticated"
             value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="threadForm" property="authenticated"
             value="true">
<p>

<html:form action="/message/reblogLink.shtml">
<br>
<html:hidden property="threadId" /> 

<html:hidden property="action" />

<%
int i = 0;
%>

<table cellspacing="0" cellpadding="0" border="0" width="500" >
<tr><td>
<table  border="0" width="100%">
  
<logic:iterate id="prop" name="propertysForm" property="propertys" >
 <logic:notEmpty name="prop" property="value">
 <tr>
    <td>链接</td>
 </tr>
  <tr>
    <td><input type="text" name='<%= "property[" + i + "].value" %>' value='<bean:write name="prop" property="value" filter="false" />'    size="40"/></td>
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
	 var insert = "<table border=0 width=500 ><tr><td><table  border=0 width=100%>  <tr>    <td>链接</td></tr>    <tr >    <td><input type='text' name='property[" 
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
<input type="submit" value="确认"/>
<a href="<%=request.getContextPath()%>/message/messageListOwner.shtml?thread=<bean:write name="threadForm" property="threadId"/>">返回</a>
</html:form>

<p><p><p>

</logic:equal>   


</div>


</logic:notEmpty>
</logic:notEmpty>

<p></p>
<p></p>
<p></p>
<p></p>
<p></p>
<p></p>
<p></p>
<p></p>


<%@include file="../common/IncludeBottom.jsp"%>


