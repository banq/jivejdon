<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="important" >
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
	<div class="info">
         <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"  class="smallgray"><bean:write name="forumThread" property="name" /></a>
      </div>
</logic:iterate>
</div>
