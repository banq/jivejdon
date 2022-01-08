<%--
<%@taglib uri="struts-bean" prefix="bean"%>
<%@taglib uri="struts-logic" prefix="logic"%>
<%@taglib uri="struts-html" prefix="html"%>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
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
		alert("用户名必须为英文与数字组合");
		theForm.username.focus();
		return false;
  }
   myRegExp = /^[0-9.]*$/gi;
   if (myRegExp.test(theForm.username.value)) {
		alert("用户名必须为英文与数字组合");
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
	  if (document.getElementById("username").value == ""){	  	
	      alert("请输入用户名！");
	      return;
	   }
	   
	   myRegExp = /^[a-z0-9.]*$/gi ;
   if (!myRegExp.test(document.getElementById("username").value)) {
		  alert("用户名必须为英文与数字组合");		  
		  return;
		}

	  var pars = "username=" +  document.getElementById("username").value + "&sessionId=<%=request.getSession().getId()%>";
    new Ajax.Updater('usernameCheck', '<%=request.getContextPath()%>/account/checkUser.jsp', { method: 'get', parameters: pars  });
}

function checkEmail(){	
	  var pars = "email=" +  document.getElementById("email").value + "&sessionId=<%=request.getSession().getId()%>";
     new Ajax.Updater('emailCheck', '<%=request.getContextPath()%>/account/checkUser.jsp', { method: 'get', parameters: pars  });
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
                     <th align="right">密码问题:</th>
                     <td align="left"><input type="text" name="passwdtype" value="<bean:write name="passwordassitVO" property="passwdtype"/>" maxlength="30"/></td>
                 </tr>
                 <tr>
                     <th align="right">密码答案:</th>
                     <td align="left"><input type="text" name="passwdanswer" value="<bean:write name="passwordassitVO" property="passwdanswer"/>" maxlength="30"/></td>
                 </tr>
  
                <tr>
                    <th align="right">Email:
                    </th>
                    <td align="left">
                    <html:text property="email" maxlength="30" onblur="checkEmail()" styleId="email" />
                    <span class="small2" id="emailCheck">请用QQ或163信箱</span>
                    <br>
                    <html:checkbox property="emailVisible"  ><span class="small2">公开</span></html:checkbox>
                    
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



