<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>


 <logic:iterate id="forumThread" name="threadListForm"  property="list" length="8">
     <%@ include file="threadListCore.jsp" %>   
 </logic:iterate>






