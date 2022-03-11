<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(6 * 60 * 60, request, response);
%> 
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="ThreadTag" name="tagsHotListForm" property="list" length='<%=coutlength%>' >
  <li><a href='<%=request.getContextPath() %>/tags/<bean:write name="ThreadTag" property="tagID"/>'><bean:write name="ThreadTag" property="title"/></a></li>
</logic:iterate>                