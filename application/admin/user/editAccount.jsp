<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 用户管理" />
<%@ include file="../header.jsp" %>

<h3 align="center">用户注册资料修改</h3>
<center>*密码只能重新设定</center>
<div align="center">

<html:form method="post" action="/admin/user/editSaveAccount.shtml" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="edit" />
<html:hidden name="accountForm" property="userId" />
<input type="hidden" name="actionType" value="editSave"/>
<script>
<!--

function Juge(theForm)
{
   var myRegExp = /\w+\@[.\w]+/;
   if (!myRegExp.test(theForm.email.value)) {
		alert("请输入正确的Email");
		theForm.email.focus();
		return false;
  }
   myRegExp = /^[a-z0-9.]*$/gi;
   if (!myRegExp.test(theForm.username.value)) {
		alert("用户名必须为英文字符");
		theForm.username.focus();
		return false;
  }

 if (theForm.username.value == "")
  {
     alert("请输入用户名！");
     theForm.username.focus();
     return (false);
  }
     
  if (theForm.password.value != theForm.password2.value)
  {
     alert("两次密码不一致！");
     theForm.password.focus();
     return (false);
  }

}

function checkUsername(){	
	var pars = "username=" +  document.getElementById("username").value + "&sessionId=<%=request.getSession().getId()%>";
    new Ajax.Updater('usernameCheck', '<%=request.getContextPath()%>/account/checkUser.jsp', { method: 'get', parameters: pars  });
}

-->
</script>    


<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>注册资料</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 <table border="0" cellpadding="3" cellspacing="0"  >
                 <tr>
                     <th align="right">用户名:</th>
                     <td align="left"><html:text property="username" maxlength="15" styleId="username"  onblur="checkUsername()" />
                      <span class="small2" id="usernameCheck">(英文字符或数字)</span>
                     </td>
  
                </tr>
                 <tr>
                     <th align="right">密码:</th>
                     <td align="left"><input type="password" name="password" value="" maxlength="30"/></td>
                 </tr>
                 <tr>
                     <th align="right">确认密码:</th>
                     <td align="left"><input type="password" name="password2" value="" maxlength="30"/></td>
                 </tr>
  
                <tr>
                    <th align="right">Email:
                    </th>
                    <td align="left"><html:text property="email" maxlength="30"/>
                    <html:checkbox property="emailVisible"  ><span class="small2">公开</span></html:checkbox>
                    <br>* 忘记密码通过该信箱获得
                    </td>
                 </tr>
                  <tr>
                     <th align="right">验证码:</th>
                     <td align="left">
                       <input type="text" name="registerCode" size="10" maxlength="50" >
                       <html:img page="/account/protected/registerCodeAction" border="0" />
                    </td>
                 </tr>
  
                <tr>
                    <td align="center" colspan="2">
                      <html:submit property="submit" value="确定"/>
                      <html:reset value ="Reset"/>
                  </td>
                 </tr>
   </table>


 </td>
</tr></table>
</td></tr>
</table>





</html:form>

<%@include file="../footer.jsp"%>


