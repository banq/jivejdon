<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>
<b>教程</b>
<div class="important" >
   <bean:parameter id="count" name="count" value="8"/>
      <%
String coutlength = (String)pageContext.getAttribute("count");
%>
<logic:iterate indexId="i" id="url" name="listForm" property="list" length='<%=coutlength%>' >     
    <li><a class="smallgray" href="<bean:write name="url" property="ioc"/>"
    target="_blank"><bean:write name="url" property="name" /></a>
    </li>
</logic:iterate>
</div>


