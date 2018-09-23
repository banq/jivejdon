<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" Email验证" />
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="stylesheet" href="/common/portlet.css" type="text/css">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

<div align="center">

<logic:messagesPresent>
 验证错误。
</logic:messagesPresent>
    
<logic:present name="errors">
  验证错误。
</logic:present>

<logic:notPresent name="errors">
<logic:messagesNotPresent>
<br><br><br>
    验证成功。  
<br><br><br>    
</logic:messagesNotPresent>
</logic:notPresent>
</div>
  </td></tr></table>

<%@include file="../common/IncludeBottomBody.jsp"%>

