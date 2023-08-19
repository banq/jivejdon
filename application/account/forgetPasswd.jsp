<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 用户登录" />
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="stylesheet" href="/common/portlet.css" type="text/css">

<table border="0" cellpadding="0" cellspacing="0" width="450" align='center'>
<tr>
<td>
<div class="portlet-container">
<div class="portlet-header-bar">
<div class="portlet-title">
<div style="position: relative; font-size: smaller; padding-top: 5px;"><b>&nbsp;忘记密码&nbsp;</b></div>
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
<td style="text-align: center">

<div style="text-align: center">
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
}
-->
    </script>
    
<logic:messagesNotPresent>
<bean:parameter name="step" id="step" value="0"/>
<logic:equal name="step" value="0">
    <form action="<%=request.getContextPath() %>/account/forgetPasswd.shtml" method="POST" >
    <input type="hidden" name="step" value="1"/>
    <input type="hidden" name="action" value="forgetPasswd"/>

    <table>
    <p>
    <br>
  <tr><td>
    用户名：</td><td><input type="text" name="username"/><html:submit property="submit" value="确定"/>
  </td></tr>  
<tr><td colspan="2" style="text-align: center">
    
    </td></tr>
      </table>
    </form>
      
</logic:equal>

<logic:equal name="step" value="1">
    <form action="<%=request.getContextPath() %>/account/forgetPasswd.shtml" method="POST" onsubmit="return Juge(this);">
    <input type="hidden" name="action" value="forgetPasswd"/>            
    <input type="hidden" name="userId" value="<bean:write name="passwordassitVO" property="userId"/>"/>
    <input type="hidden" name="step" value="2"/>
    
    <table>       
  <tr><td>
    
    密码问题：</td><td><input type="text" name="passwdtype" value="<bean:write name="passwordassitVO" property="passwdtype"/>"/>(如无留空)
  </td></tr>
  <tr><td>
    密码答案：</td><td><input type="text" name="passwdanswer" value=""/>(如无留空)
  </td></tr>
  
<tr><td>
    
  </td></tr>
  
<tr><td>
    验证码：</td><td>
            <input type="text" name="registerCode" size="10" maxlength="50" >
            <html:img page="/account/protected/registerCodeAction" border="0" />
  </td></tr>
  
<tr><td colspan="2" style="text-align: center">
    <html:submit property="submit" value="确定"/>
    </td></tr>
    
      </table>
    </form>  
</logic:equal>      

<logic:equal name="step" value="2">


<br><br><br>
    新密码已经发往您的信箱<bean:write name="email"/>。请稍候收取。。  
<br><br><br>
</logic:equal>    
</logic:messagesNotPresent>
   
    
  
      
</div>

<br>
<br><br><br><br><br><br><br><br><br><br>
</td>
</tr>
</table>
</div>
<br><br><br><br><br><br><br><br><br><br>
</div>
<br><br><br><br><br><br><br><br><br><br>
</div>
<br><br><br><br><br><br><br><br><br><br>
</div><!-- end portlet-box -->
<div class="portlet-bottom-decoration-2"><div><div></div></div></div>
</div><!-- End portlet-container -->

</td></tr></table>

<%@include file="../common/IncludeBottomBody.jsp"%>

