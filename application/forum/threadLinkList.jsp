<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>

 <logic:notEmpty name="threadListForm" property="list">          
 <logic:iterate id="forumThread" name="threadListForm"  property="list">
   <logic:notEmpty name="forumThread" property="rootMessage">
    <bean:define id="forumMessage" name="forumThread" property="rootMessage"  />
    <%@ include file="../query/others/threadListCore.jsp" %>  
   </logic:notEmpty>   
 </logic:iterate>
</logic:notEmpty>   
<logic:empty name="threadListForm" property="list">
<% 
  response.sendError(204);  
  %>
</logic:empty>