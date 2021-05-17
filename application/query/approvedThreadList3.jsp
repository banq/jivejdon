<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(60, request, response);	
%>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store">
    <META HTTP-EQUIV="Expires" CONTENT="0">   
<%
int offset = 0;
if (request.getParameter("offset")!=null){
   offset = Integer.parseInt(request.getParameter("offset"));
}
%>
<logic:iterate indexId="i" id="forumThread" name="threadListForm" property="list" offset="<%=Integer.toString(offset)%>" length="1">
  <%@ include file="threadListCore.jsp" %>
</logic:iterate>
