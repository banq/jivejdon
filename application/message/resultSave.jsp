<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="messageForm">
    <logic:notEmpty name="messageForm" property="messageId">
        <bean:define id="messageId" name="messageForm" property="messageId"  />
        <bean:define id="action" name="messageForm" property="action"  />
    </logic:notEmpty>
</logic:notEmpty>

<logic:notEmpty name="messageReplyForm">
    <logic:notEmpty name="messageReplyForm" property="messageId">
        <bean:define id="messageId" name="messageReplyForm" property="messageId"  />
        <bean:define id="action" name="messageReplyForm" property="action"  />
    </logic:notEmpty>
</logic:notEmpty>


<logic:messagesNotPresent>
    <logic:empty name="errors">
        <logic:notEqual name="action" value="delete">
            帖子保存成功，<a href='<%=request.getContextPath()%>/forum/messageNavList.shtml?message=<bean:write name="messageId" />'
            >按这里返回所发帖子</a>
          <script>
              window.top.location.href = '<%=request.getContextPath()%>/forum/messageNavList.shtml?message=<bean:write name="messageId" />';
          </script>


        </logic:notEqual>
        <logic:equal name="action" value="delete">
            <bean:define id="title"  value=" 帖子删除成功" />
            <%@ include file="messageHeader.jsp" %>
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
    var errInfo = document.getElementById("errors").innerHTML.replace(/<\/?.+?>/g,'').replace(/ /g,"");
    if (window.top.openInfoDiag)
        window.top.openInfoDiag(errInfo);
    else{
        window.top.alert(errInfo);
    }
    window.top.document.getElementById("formSubmitButton").disabled=false;    

    </script>
</logic:messagesPresent>


