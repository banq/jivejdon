<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="list-group">

 <logic:iterate indexId="i" id="forumThread" name="threadListForm"
                   property="list">
     <logic:equal name="i" value="2">
     <span  class="list-group-item">
     <h3 class="list-group-item-heading"></h3>
     </span>
     </logic:equal>
  
    <%@ include file="threadListCore2.jsp" %>
    
 </logic:iterate>

</div>
	