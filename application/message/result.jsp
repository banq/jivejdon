<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 帖子保存结果" />
<%@ include file="messageHeader.jsp" %>

<logic:notEmpty name="messageForm">
<bean:define id="messageId" name="messageForm" property="messageId"  />
<bean:define id="forumThread" name="messageForm" property="forumThread"  />
<bean:define id="action" name="messageForm" property="action"  />
</logic:notEmpty>

<logic:notEmpty name="messageReplyForm">
<bean:define id="messageId" name="messageReplyForm" property="messageId"  />
<bean:define id="forumThread" name="messageReplyForm" property="forumThread"  />
<bean:define id="action" name="messageReplyForm" property="action"  />
</logic:notEmpty>

<table cellpadding="0" cellspacing="0" border="0"  align="center">
<tr>
    <td valign="top" > 
    
    <p>
    <html:errors />

   <logic:messagesNotPresent>
    <logic:empty name="errors">
     <br><br>
      帖子操作成功！
      
       <logic:notEmpty name="forumThread">
       <logic:notEmpty name="forumThread" property="forum" >
       <logic:notEqual name="action" value="delete">
          
        <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" 
        property="threadId" />?message=<bean:write name="messageId" />#<bean:write name="messageId" />'        
        >按这里返回所发帖子</a>
        ....3秒后自动返回
       <script>      
         function reloadHref(){
              window.location.href = "<a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />?message=<bean:write name="messageId" />#<bean:write name="messageId" />'>";
          }
          //setTimeout(reloadHref, 3000);   
      </script>

        </logic:notEqual>
        </logic:notEmpty>    
        </logic:notEmpty>     
    </logic:empty>
  </logic:messagesNotPresent>
    </td>
</tr>
<tr>
    <td valign="top" > 
    <br><br><br><p><html:link page="/">按这里返回首页</html:link>
    </td>
</tr>
</table>


<%@include file="../common/IncludeBottom.jsp"%> 


