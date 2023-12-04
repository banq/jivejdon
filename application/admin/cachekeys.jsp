<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 缓存" />
<%@ include file="header.jsp" %>
<%@page import="com.jdon.jivejdon.util.ContainerUtil"%>
<%@page import="com.jdon.controller.WebAppUtil"%>
<%@page import="java.text.DecimalFormat"%>


<%
ContainerUtil containerUtil = (ContainerUtil)WebAppUtil.getComponentInstance("containerUtil", request);
double allhits = containerUtil.getCacheManager().getCache().getCacheHits()+ containerUtil.getCacheManager().getCache().getCacheMisses(); 
double hit = containerUtil.getCacheManager().getCache().getCacheHits();
double mis = containerUtil.getCacheManager().getCache().getCacheMisses();
DecimalFormat df = new DecimalFormat();
df.setMaximumFractionDigits(2);
df.setMinimumFractionDigits(2);
%>
缓存总请求数:<%=allhits%>
击中数:<%=containerUtil.getCacheManager().getCache().getCacheHits()%>
击中率:<%=df.format(hit/allhits * 100)%>%
错过数:<%=containerUtil.getCacheManager().getCache().getCacheMisses()%>
错过率:<%=df.format(mis/allhits * 100)%>%

<p>
<html:link page="/admin/cacheClear.jsp" >清除缓存</html:link>

<bean:parameter name="key" id="key" value=""/>
<form action="<%=request.getContextPath()%>/admin/cachekeys.shtml">
<input type="text" name="key" value="<bean:write name="key"/>">
<input type="submit" value="搜索主键">
</form>

<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr>
    <td>
<table bgcolor="#ffffff"
 cellpadding="3" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr><td>
共有 <bean:write name="keyListForm" property="allCount"/> 个
<MultiPages:pager actionFormName="keyListForm" page="/admin/cachekeys.shtml" paramId="key" paramName="key">
<MultiPages:prev name="上页" />
<MultiPages:index />
<MultiPages:next name="下页" />
</MultiPages:pager>
    <td><td >

    </td>
</tr>
</table>
    </td>
</tr>
</table>

<form action="<%=request.getContextPath()%>/admin/deleteCacheKey.shtml">
<input name="method" type="hidden" value="deleteCacheKey"/>
<input name="cacheKey" type="hidden" id="cacheKey" value=""/>

<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td> 
<table bgcolor="#999999" cellpadding="3" cellspacing="1" border="0" width="100%">
<tr bgcolor="#FFFFCC">
    <td style="text-align: center" nowrap><b>缓存主键</b></td>
    <td style="text-align: center" nowrap><b>缓存值类型</b></td>
    <td style="text-align: center" nowrap></td>
</tr>

<logic:iterate id="oneOneDTO" name="keyListForm" property="list">
 
<tr bgcolor="#ffffff">
<td><bean:write name="oneOneDTO" property="parent"/> </td>
<td><bean:write name="oneOneDTO" property="child" filter="false"/></td>
<td><input type="submit" value="清除" onclick='document.getElementById("cacheKey").value = "<bean:write name="oneOneDTO" property="parent"/>";'></td>
</tr>

</logic:iterate>
</table>
</form>

<%@include file="footer.jsp"%>

