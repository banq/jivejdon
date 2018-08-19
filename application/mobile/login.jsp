<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="header.jsp" %>

<div data-role="header" data-theme="e">
		<h1><b>登录</b> </h1>
	</div><!-- /header -->

	<div data-role="content" data-theme="d">
		
<form method="POST" action="<%=request.getContextPath()%>/mobile/new"  onsubmit="return Juge(this);"> 

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

           <table border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td> 用户 </td>
    <td width="10">&nbsp;</td>
    <td><input type="text" name="j_username" id="j_username" size="20" tabindex="1">
    </td>
    <td width="10">&nbsp;</td>
    <td><table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>自动登陆 </td>
        <td align="right"><input type="checkbox" name="rememberMe"  id="rememberMe" checked="checked" >
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>密码 </td>
    <td width="10">&nbsp;</td>
    <td><input type="password" name="j_password" id="j_password" size="20" tabindex="2">
    </td>
    <td width="10">&nbsp;</td>
    <script>
	    	 loggedURL = '<html:rewrite
	    	 page="/account/protected/logged.jsp"/>?Referer=https://'+ location.hostname;
	       var mloginOk= function (){}
	    </script>
    <td><input type="submit" value=" 登陆 " tabindex="3" onclick="Login(mloginOk)" >
    </td>
  </tr>
   <tr>
    <td align="center" colspan="5">
      <a href="<%=request.getContextPath()%>/mobile/account/oauth/sinaCallAction.shtml"  target="_blank" >
                          <img src='/images/sina.png' width="16" height="16" alt="登录" border="0" />新浪微博
                    </a>
    <a href="<%=request.getContextPath()%>/mobile/account/oauth/tecentCallAction.shtml" target="_blank">
                          <img src='/images/qq.gif' width="16" height="16" alt="登录" border="0" />腾讯微博
                    </a>

	</td>	
  </tr>
</table>
</form>


</div><!-- /content -->

<%@include file="footer.jsp"%> 