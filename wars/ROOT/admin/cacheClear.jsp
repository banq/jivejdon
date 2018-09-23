<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="com.jdon.jivejdon.util.ContainerUtil"%>
<%@page import="com.jdon.controller.WebAppUtil"%>
<bean:define id="title"  value=" 关键词设置" />
<%@ include file="header.jsp" %>

<bean:parameter name="action" id="action" value=""/>

<logic:notEmpty name="action">
<%
ContainerUtil containerUtil = (ContainerUtil)WebAppUtil.getComponentInstance("containerUtil", request);
containerUtil.clearAllModelCache();

%>
<h3>缓存清除复位成功</h3>

</logic:notEmpty>

<logic:empty name="action">


<form action="cacheClear.jsp">
<input type="hidden" name="action" value="1">
<input type="submit" value="缓存清除复位"/>
</form>
</logic:empty>


<%@include file="footer.jsp"%>