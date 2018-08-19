<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<bean:define id="title" value=" 帖子保存结果"/>
<%@ include file="../messageHeader.jsp" %>
<div align="center">
<html:errors/>

<logic:messagesNotPresent>
    <logic:empty name="errors">
        <br><br>
        操作成功！


        <a href='<%=request.getContextPath()%>/<bean:write name="threadForm"
        property="threadId" />?<%=System.currentTimeMillis() %> '>按这里返回，因为浏览器缓存需要重新刷新</a>
    </logic:empty>
</logic:messagesNotPresent>
	</div>


<%@include file="../../common/IncludeBottom.jsp" %> 
