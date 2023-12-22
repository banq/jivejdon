<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<logic:iterate indexId="i" id="forumThread" name="threadListForm" property="list" length='1' >
    <bean:define id="forumMessage" name="forumThread" property="rootMessage" />	
    <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">
        <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border="0" class="thumbnail center" loading="lazy"/>
    </logic:notEmpty>
	<logic:empty name="forumMessage" property="messageUrlVO.thumbnailUrl">
        <img src="<%=request.getContextPath() %>//cdn.jdon.com/simgs/thumb/<%=1 + i%>.jpg" border="0" class="thumbnail center" loading="lazy">  
	</logic:empty>	
</logic:iterate>
