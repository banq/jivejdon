<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="count" name="count" value="6"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:notEmpty name="tagsHotListForm" property="list">
  <logic:iterate id="ThreadTag" name="tagsHotListForm" property="list" length='<%=coutlength%>' ><bean:write name="ThreadTag" property="title"/>,</logic:iterate>
</logic:notEmpty>

