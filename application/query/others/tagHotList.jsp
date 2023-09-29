<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        

<div class="widget_tag_cloud">
<div class="tagcloud">
<logic:iterate indexId="i"   id="ThreadTag" name="tagsHotListForm" property="list" length='<%=coutlength%>' >
    <a href="<%=request.getContextPath()%>/tag-<bean:write name="ThreadTag" property="tagID"/>/" class="tag-cloud-link"><bean:write name="ThreadTag" property="title"/></a>
                     &nbsp;&nbsp; 
</logic:iterate>
</div>
</div>

