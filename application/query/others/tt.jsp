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
<div>
<ul>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
 <bean:define id="body" name="forumMessage" property="messageVO.body" />
<logic:equal name="i" value="0">
<li>
	<h3 class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
    <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" class="hover-preload"><bean:write name="forumThread" property="name"/></a>
  </h3>
      
         <div class="smallgray">			 
              <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />.             
            </div>
</li>
</logic:equal>
<logic:greaterThan name="i" value="0">
<li class="info">
	  <h3 class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" title="<bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />"  class="hover-preload"><bean:write name="forumThread" property="name"/></a></h3>
</li>
</logic:greaterThan>
</logic:iterate>
</ul>
</div>
</logic:notEmpty>