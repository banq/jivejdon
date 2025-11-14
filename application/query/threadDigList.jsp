<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%
    response.setHeader("X-Robots-Tag", "noindex, nofollow");
%>
<bean:parameter id="count" name="count" value="15"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<ul style="list-style-type:none;padding:0">
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
 <%@ include file="./others/threadListCore.jsp" %>
</logic:iterate>
</ul>

