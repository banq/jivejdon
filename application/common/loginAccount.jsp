<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.jdon.jivejdon.api.account.AccountService,
              com.jdon.jivejdon.domain.model.account.Account,
              com.jdon.controller.WebAppUtil" %>
<%
//need at first include IncludeTop.jsp
//this jsp can save logined account all datas into request, if you inlude this jsp, you can get them. 
if (request.getAttribute("principal") != null){
	AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
	Account account = accountService.getloginAccount();
	if (account != null) {
	   request.setAttribute("loginAccount", account);
	}
}
%>
