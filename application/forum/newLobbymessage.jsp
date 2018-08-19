<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(60 * 60, request, response);
%>
<%@ include file="../common/security.jsp" %>

<logic:notEmpty name="Notification">
          <div align="center">
           <br>
			<bean:write name="Notification" property="content" filter="false" />
			<br>
			<br>
			注:进入如无新帖，请按浏览器"刷新"
			<br>
			<br>
			<a href="javascript:void(0);" onclick='window.top.disablePopUPWithID(<bean:write name="Notification" property="sourceId" />,<bean:write name="Notification" property="scopeSeconds" />)'>不再需要该贴更新提示</a>
		  </div>
<script>								
	window.top.$LAB
     .script("/common/js/window_def.js").wait()
     .script("<%=request.getContextPath()%>/forum/js/newMessage.js").wait()
     .wait(function(){     					
        popUpNewMessage(); 
     })    
  </script>    
</logic:notEmpty>

<logic:empty name="Notification">
	       
	
</logic:empty>
