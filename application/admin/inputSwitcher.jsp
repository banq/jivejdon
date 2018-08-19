<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<bean:define id="title"  value=" 标签编辑" />
<%@ include file="./header.jsp" %>

<form action="<%=request.getContextPath() %>/admin/inputSwitcher.shtml" method="post">
<input name="method" value="save" type="hidden"/>
是否开启暂停用户发言等增删改提交动作?
<p>
<logic:notEmpty name="des">
  <input name="inputSwitcher" type="radio" value="on" checked="checked"/>是<br>
  <input name="inputSwitcher" type="radio"  value="off"/>否<br>
  暂停原因：<input name="des" type="text" value="<bean:write name="des"/>"  size="50"/>
</logic:notEmpty>

<logic:empty name="des">
  <input name="inputSwitcher" type="radio" value="on"/>是<br>
  <input name="inputSwitcher" type="radio" value="off" checked="checked"/>否<br>
    暂停原因：<input name="des" type="text" value="" size="50"/>
</logic:empty>
<br>

<input type="submit" value="确定"/>
</form>
<%@include file="./footer.jsp"%>