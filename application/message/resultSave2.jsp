<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>


<bean:define id="messageId" name="messageReplyForm" property="messageId"/>
<bean:define id="pMessageId" name="messageReplyForm" property="parentMessage.messageId"/>
<bean:define id="action" name="messageReplyForm" property="action"/>


<logic:messagesNotPresent>
    <logic:empty name="errors">
        <logic:notEqual name="action" value="delete">
          帖子保存成功
            <script>
                window.top.location.href = '<%=request.getContextPath()%>/forum/messageNavList2.shtml?pMessage=<bean:write name="pMessageId"/>&message=<bean:write name="messageId"/>#<bean:write name="messageId"/>';
            </script>
        </logic:notEqual>
        <logic:equal name="action" value="delete">
            <bean:define id="title"  value=" 帖子删除成功" />
            <%@ include file="../common/IncludeTop.jsp" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-store);
response.setDateHeader("Expires", 0);
response.setStatus(HttpServletResponse.SC_OK);
%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store">
<META HTTP-EQUIV="Expires" CONTENT="0"> 
            <table cellpadding="0" cellspacing="0" border="0"  align="center">
                <tr><td valign="top" >
                    <br><br>帖子删除成功 ， 由于您自己的缓存过10分钟后可能才会生效。
                    <br><br><br><p><html:link page="/">按这里返回首页</html:link>
                </p>
                </td></tr></table>
            <%@include file="../common/IncludeBottom.jsp"%>
        </logic:equal>

    </logic:empty>
</logic:messagesNotPresent>

<logic:messagesPresent>
    <logic:present name="errors">
      <span id="errors">
         	 <html:errors />
         </span>
    </logic:present>
    <script>
        var errInfo = document.getElementById("errors").innerHTML.replace(/<\/?.+?>/g,'');  
    if (window.top.openInfoDiag)
        window.top.openInfoDiag(errInfo);
    else{
        window.top.alert(errInfo);
    }
    window.top.document.getElementById("formSubmitButton").disabled=false;    
    </script>
</logic:messagesPresent>


