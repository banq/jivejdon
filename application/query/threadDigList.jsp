<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>
</logic:notEqual>
<bean:parameter id="tablewidth" name="tablewidth" value="300"/>
<b>点赞排行</b>
<div class="important" >
   <bean:parameter id="count" name="count" value="8"/>
      <%
String coutlength = (String)pageContext.getAttribute("count");
%>
      <logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >     
		  <li><a  class="smallgray"  href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>"  target="_blank"><bean:write name="forumThread" property="name" /></a>
		  </li>
      </logic:iterate>
</div>

