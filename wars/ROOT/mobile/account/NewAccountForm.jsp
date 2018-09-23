<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<bean:define id="title"  value=" 用户注册" />
<%@ include file="../header.jsp" %>

<div data-role="header">
		<h1><b>注册</b> </h1>
	</div><!-- /header -->

	<div data-role="content">	


<h3 align="center">用户注册</h3>
<div align="center">
  
	直接登录：<a href="<%=request.getContextPath()%>/mobile/account/oauth/sinaCallAction.shtml"  target="_blank" >
                          <img src='/images/sina.png' width="13" height="13" alt="登录" border="0" />新浪微博
                    </a>
    <a href="<%=request.getContextPath()%>/mobile/account/oauth/tecentCallAction.shtml" target="_blank">
                          <img src='/images/qq.gif' width="13" height="13" alt="登录" border="0" />腾讯微博
                    </a>
                    
  
<html:form action="/mobile/account/newAccount.shtml" method="post" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="create" />
<input type="hidden" name="actionType" value="createSave"/>
<html:hidden property="userId" />

<%@include file="../../account/IncludeAccountFields.jsp"%>

</html:form>
</div>

</div>


<%@ include file="../footer.jsp" %>