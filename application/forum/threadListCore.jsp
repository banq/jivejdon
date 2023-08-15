<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="list-group">


 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">

    <%@ include file="threadListCore2.jsp" %>

 </logic:iterate>

</div>
