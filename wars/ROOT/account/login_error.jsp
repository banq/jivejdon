<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", request);
if (errorBlocker.checkCount(request.getRemoteAddr(), 5)){
	response.sendError(404);
    return;
}
%>


<bean:define id="title"  value=" 用户登录错误" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>
<bean:write name="title" />
</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />

<table border="0" cellpadding="0" cellspacing="0" width="500" align='center'>
<tr>
<td>
<div class="portlet-container">
<div class="portlet-header-bar">
<div class="portlet-title">
<div style="position: relative; font-size: smaller; padding-top: 5px;"><b>&nbsp;用户登陆&nbsp;</b></div>
</div>
<div class="portlet-small-icon-bar">
</div>
</div><!-- end portlet-header-bar -->
<div class="portlet-top-decoration"><div><div></div></div></div>
<div class="portlet-box">
<div class="portlet-minimum-height">
<div id="p_p_body_2" >
<div id="p_p_content_2_" style="margin-top: 0; margin-bottom: 0;">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

<div align="center"><br>
  <br>
  <br>
    <span class="small2">输入的用户名或密码错误，请按
    <html:link page="/account/login.jsp"><b>这里</b></html:link>
  重新登陆。</span>
  
</div>

</td>
</tr>
</table>
</div>
</div>
</div>
</div><!-- end portlet-box -->
<div class="portlet-bottom-decoration-2"><div><div></div></div></div>
</div><!-- End portlet-container -->
</td></tr></table>





