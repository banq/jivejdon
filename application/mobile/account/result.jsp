<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 注册资料成功" />
<%@ include file="../header.jsp" %>

<div data-role="header">
		<h1><b>注册结果</b> </h1>
	</div><!-- /header -->

	<div data-role="content">	


<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>注册资料操作</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 
<center>
<br>
<br>
<br>
<logic:messagesNotPresent>
    注册资料操作成功  
    <p>
  <br>如果你是第一次注册新用户，为防止垃圾广告，新用户
  <br>前一小时内只能每五分钟内发一次贴，一小时后无限制。    
</logic:messagesNotPresent>
<p>
<html:link page="/mobile/" target="_top"><b>按这里回首页</b></html:link>
</center>
<br>
<br>
<br>
 </td>
</tr></table>
</td></tr>
</table>

</div>

<%@ include file="../footer.jsp" %>

