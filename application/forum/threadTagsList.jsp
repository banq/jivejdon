<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<logic:notEmpty name="threadListForm" property="list">   
<ul style="list-style-type:none;padding:0">
 <logic:iterate id="forumThread" name="threadListForm"  property="list" length="5">
     <%@ include file="../query/others/threadListCore.jsp" %>   
 </logic:iterate>
</ul>
</logic:notEmpty>   

<logic:empty name="threadListForm" property="list">
<% 
  response.sendError(204);  
  %>
</logic:empty>



