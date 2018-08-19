<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title" value=" 帖子保存结果"/>
<%@ include file="messageHeader.jsp" %>


<table cellpadding="0" cellspacing="0" border="0" align="center">
    <tr>
        <td valign="top">

            <p>
                <html:errors/>

                <logic:messagesNotPresent>
                <logic:empty name="errors">
                <br><br>
                操作成功！


                <a href='<%=request.getContextPath()%>/<bean:write name="threadForm"
        property="threadId" />?<%=System.currentTimeMillis() %> '>按这里返回，因为浏览器缓存需要重新刷新</a>
                </logic:empty>
                </logic:messagesNotPresent>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <br><br><br>
            <p>
                <html:link page="/">按这里返回首页</html:link>
        </td>
    </tr>
</table>


<%@include file="../common/IncludeBottom.jsp" %> 


