 <%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="messageListForm">
  <bean:size id="messageCount" name="messageListForm" property="list" />
  <logic:equal name="messageCount" value="0">
    <% response.sendError(404); %>
  </logic:equal>

  <logic:greaterThan name="messageCount" value="0">
    <logic:empty name="messageListForm" property="oneModel" >
    <% 
      response.sendError(404);
     %>
    </logic:empty>
   <%@include file="./messagListCore.jsp"%>
  </logic:greaterThan>
</logic:notEmpty>
