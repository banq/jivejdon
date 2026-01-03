<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 论坛资料保存成功" />
<%@ include file="../header.jsp" %>


<logic:notPresent name="errors">
<h3>论坛资料保存成功！</h3>
</logic:notPresent>


<%@include file="../footer.jsp"%> 
