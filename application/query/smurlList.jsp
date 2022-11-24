<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<a href="<%=request.getContextPath()%>/course.html"><b>教程</b></a>
<div class="important" >
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>
<logic:iterate indexId="i" id="url" name="listForm" property="list" length='<%=coutlength%>'>     
    <div class="info"><a href="<bean:write name="url" property="ioc"/>"
    target="_blank" class="smallgray"><bean:write name="url" property="name" /></a>
    </div>
</logic:iterate>
</div>


