<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="../common/security.jsp" %>

<logic:notEmpty name="Notification">
          <div style="text-align: center">
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
