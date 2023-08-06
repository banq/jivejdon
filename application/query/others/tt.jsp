<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%-- 这里是因为被tagsList.jsp的bean:include调用，只能是gbk，不能为正常utf-8，否则乱码 --%>    
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<logic:empty name="threadTag">
  <%response.setStatus(HttpServletResponse.SC_NOT_FOUND);%>
</logic:empty>
<logic:notEmpty name="threadTag">

<bean:parameter id="count" name="count" value="4"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>
<div class="widget">
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
<div class="info">
	  <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" target="_blank" ><bean:write name="forumThread" property="name" /></a>
</div>
</logic:iterate>
</div>
</logic:notEmpty>