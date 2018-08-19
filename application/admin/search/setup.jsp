<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 搜索设置" />
<%@ include file="../header.jsp" %>

<bean:parameter name="service" id="service" value=""/>
<logic:notEqual name="service" value="">
   
    正在后台重新建立索引，如果数据量大，需要耗费一段时间。

</logic:notEqual>

<logic:equal name="service" value="">
<form action="<%=request.getContextPath()%>/admin/doRebuildIndex.shtml"  method="post">
<input type="hidden"  name="service" value="forumService" />
<input type="hidden"  name="method" value="doRebuildIndex" />
<html:submit value=" 进行截止当日的重新索引 "/>
</form>
注：如果数据量大，需要耗费一段时间。
</logic:equal>
<%@include file="../footer.jsp"%>
