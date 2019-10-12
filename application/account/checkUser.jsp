<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.service.account.AccountService"%>

<%
String sessionId = request.getParameter("sessionId");
if (sessionId == null || !sessionId.equals(request.getSession().getId())){
	return;
}
String randstr = (String)request.getSession().getAttribute("randstr");
if(randstr == null){
	return;
}
String username = request.getParameter("username");
if (username != null && username.length() != 0){
	AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
	if (accountService.checkUser(username))
		out.println("<font color='red'>这个用户名已经被占用</font>");	
	else
		out.println("<font color='green'>这个用户名可以使用</font>");
}
String email = request.getParameter("email");
if (email != null && email.length() != 0){
	AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
	if (accountService.checkUser(email))
		out.println("<font color='red'>这个邮件已经被占用</font>");	
	else
		out.println("<font color='green'>这个邮件可以使用</font>");
}
%>