<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<script>
var loggedURL = '<%=request.getContextPath()%>/account/protected/logged.jsp';
</script>
<script src="/common/login.js"></script>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<div id="loginAJAX" style="display:none" align="center">
  <div class="tooltip_content"> <div id='login_error_msg' class="login_error" style="display:none">&nbsp;</div>
    <table border="0" cellpadding="0" cellspacing="2">
      <tr>
        <td> 用户 </td>
        <td width="8">&nbsp;</td>
        <td><input type="text" name="j_username" size="22" tabindex="1" id="j_username" value=""></td>
        <td width="8">&nbsp;</td>
        <td><table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>自动登陆 </td>
              <td align="right"><input type="checkbox" name="rememberMe"  id="rememberMe" checked="checked"></td>
            </tr>
          </table></td>
      </tr>
      <tr>
        <td>密码 </td>
        <td width="10">&nbsp;</td>
        <td><input type="password" name="j_password" size="22" tabindex="2" id="j_password" value=""></td>
        <td width="10">&nbsp;</td>
        <td></td>
      </tr>
      <tr>
        <td align="center" colspan="5"><a href="<%=request.getContextPath()%>/account/newAccountForm.shtml"  target="_blank" > 新用户注册 </a> <a href="<%=request.getContextPath()%>/account/forgetPasswd.jsp" target="_blank"> 忘记密码? </a></td>
      </tr>
      <tr>
        <td align="center" colspan="5"><a href="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml"  target="_blank" >新浪微博 </a> <a href="<%=request.getContextPath()%>/account/oauth/tecentCallAction.shtml" target="_blank">腾讯微博 </a></td>
      </tr>
    </table>
  </div>
</div>

