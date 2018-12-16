<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%
    String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
    domainUrl = domainUrl + request.getContextPath();
domainUrl = domainUrl + "/forum/?";
response.sendRedirect(domainUrl);
return;
%>