<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@page contentType="text/html; charset=UTF-8"%>
<bean:define id="title"  value=" Sitemap url ok" />
<%@ include file="../header.jsp" %>
<logic:messagesPresent>
  <html:errors/>
</logic:messagesPresent>
<logic:messagesNotPresent>
  Operate Successfully!
  <br>
  <a href="https://www.jdon.com/admin/sitemap/urlListAction.shtml">返回</a>

</logic:messagesNotPresent>

<%@include file="../footer.jsp"%>
