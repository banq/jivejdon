<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>

  
<%
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(30 * 24 * 60 * 60, request, response);	
%>

<logic:iterate id="key" name="HOTKEYS" property="props">
	<a href="<bean:write name="key" property="value"/>"><bean:write
		name="key" property="name" /></a>
</logic:iterate>

