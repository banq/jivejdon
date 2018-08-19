<%          
int expire = 1 * 60 * 60;

long modelLastModifiedDate = com.jdon.jivejdon.presentation.action.util.ForumUtil.getForumsLastModifiedDate(this.getServletContext()).getModifiedDate2();
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
	return ;
}

%>
<%@ page session="false"  %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty>
<logic:empty  name="title">
  解道jdon移动版
</logic:empty>
</title>
<link rel="alternate" type="application/rss+xml" title="jdon.com" href="/rss" />
<%@ include file="../common/security.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/jquery.mobile-1.1.0.min.css" />
</head> 
<body> 
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<%-- <%@ include file="adsense.jsp" %> --%>
<span id="fastclick">
<div data-role="page" id="homeP">

