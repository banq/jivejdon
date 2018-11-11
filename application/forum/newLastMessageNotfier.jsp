<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:empty name="NEWLASMESSAGE" >
</logic:empty>


 
<logic:notEmpty name="NEWLASMESSAGE" >
<div id="NEWLASMESSAGE"  style="display:none">
<center>
自从您上次访问以来有了更新
<br>
  <a href='<%=request.getContextPath()%>/<bean:write name="NEWLASMESSAGE" property="forumThread.threadId" />'>按这里</a>

<br><br>本窗口1分钟后消失        
        
</center>  
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
