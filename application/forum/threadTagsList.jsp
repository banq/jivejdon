<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>


<logic:notEmpty name="tagID0">    
 <logic:iterate id="forumThread" name="tagID0" length="4">
      <logic:notEmpty name="forumThread" >
      <%@ include file="threadListCore.jsp" %>   
      </logic:notEmpty>        
 </logic:iterate>
</logic:notEmpty>



<logic:notEmpty name="tagID1">    
  <logic:iterate id="forumThread" name="tagID1" length="4">
     <logic:notEmpty name="forumThread" >
       <%@ include file="threadListCore.jsp" %>
      </logic:notEmpty>             
 </logic:iterate>
</logic:notEmpty>





