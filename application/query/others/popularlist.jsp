<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(6 * 60 * 60, request, response);
%>
<b>热文</b>
<div class="important" >
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
	<div class="info"><i class="fa fa-clone"></i><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
             title="<bean:write name="forumThread" property="name" />" target="_blank" class="smallgray">
             <bean:write name="forumThread" property="name" /></a>
      </div>
</logic:iterate>
</div>
