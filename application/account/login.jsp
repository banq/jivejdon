<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<bean:define id="title" value=" 用户登录" />
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="content-language" content="zh-CN" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="robots" content="noindex">
<title>
<bean:write name="title" />
</title>
<link rel="stylesheet" href="https://cdn.jdon.com/common/jivejdon5.css" type="text/css" />
<link rel="stylesheet" href="https://cdn.jdon.com/common/portlet.css" type="text/css">
</head>
<script>
try{
    if (window.parent.callLogin){
        window.parent.callLogin();
    }
}catch(e){
    try{
        if (window.top.callLogin){
            window.top.callLogin();
        }
    }catch(e){}
}
</script>
<table border="0" cellpadding="0" cellspacing="0" width="450" align='center'>
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

  <form method="POST" action="<%=request.getContextPath()%>/jasslogin" onsubmit="return Juge(this);">

    <table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

  <table border="0" cellpadding="0" cellspacing="2">
    <tr>
      <td> 用户 </td>
      <td width="10">&nbsp;</td>
      <td><input type="text" name="j_username" size="20" tabindex="1">
      </td>
      <td width="10">&nbsp;</td>
      <td><table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>自动登陆 </td>
          <td align="right"><input type="checkbox" name="rememberMe"  checked="checked">
          </td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td>密码 </td>
      <td width="10">&nbsp;</td>
      <td><input type="password" name="j_password" size="20" tabindex="2">
      </td>
      <td width="10">&nbsp;</td>
      <td><input type="submit" value=" 登陆 " tabindex="3" >
      </td>
    </tr>
    <tr>
      <td align="center" colspan="5">
        <%--<a href="<%=request.getContextPath()%>/account/newAccountForm.shtml" target="_blank" >--%>
          <%--新用户注册--%>
        <%--</a>--%>
        <%--<a href="<%=request.getContextPath()%>/account/forgetPasswd.jsp" target="_blank">--%>
          <%--忘记密码?--%>
        <%--</a>--%>
      </td>
    </tr>
    <tr>
      <td align="center" colspan="5">
        <p>
          <a href="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml" target="_blank" >
            <img src='/images/sina.png' width="16" height="16" alt="登录" border="0" />新浪微博
          </a>

      </td>
    </tr>

  </table>
</form>
<p></p>
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

<script>
function Juge(theForm)
{
    if (theForm.j_username.value == "") {
        alert("请输入用户名！");
        theForm.j_username.focus();
        return (false);
    }
    if (theForm.j_password.value == "") {
        alert("请输入密码！");
        theForm.j_password.focus();
        return (false);
    }

}
</script>    
</body>
</html>