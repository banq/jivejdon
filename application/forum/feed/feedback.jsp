<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
if (errorBlocker.checkCount(request.getRemoteAddr(), 3)){
	response.sendError(404);
    return;
}
%>


<bean:parameter name="subject" id="subject" value=""/>
<bean:parameter name="body" id="body" value=""/>
<bean:parameter name="email" id="email" value=""/>

<%@ include file="../../common/IncludeTop.jsp" %>
	
<script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
<body>
<script>
<!--
var verified = false;
function Juge(theForm)
{
   var myRegExp = /\w+\@[.\w]+/;
   if (!myRegExp.test(theForm.email.value)) {
		alert("请输入正确的Email");
		theForm.email.focus();
		return false;
	}
	if (!verified && !myRegExp.test(theForm.registerCode.value)) {
		alert("请点击验证");
		theForm.TencentCaptcha.focus();
		return false;
	}
}

window.callback = function(res){
    console.log(res)
    // res（未通过验证）= {ret: 1, ticket: null}
    // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
    if(res.ret === 0){
      
		document.getElementById("registerCode").value=res.ticket;
		document.getElementById("randstr").value=res.randstr;
		//alert(document.getElementById("registerCode").value)   // 票据
		//alert(document.getElementById("randstr").value)   // 票据
		verified = true;
    }
}
-->
</script>
<html:errors/>
<p><h3 style="text-align: center">联系表单提交</h3>
<%-- <div style="text-align: center"><html:form action="/forum/feed/feedbackAction.shtml" method="POST" onsubmit="return Juge(this);">
  <html:hidden property="action" value="send"/>
  <table>
    <tr><td>您的信箱:</td><td><input type="text" name="email" size="20" value="<bean:write name="email"/>">(以便回复您结果)</td></tr>
  
  <tr><td>表单标题:</td><td><input type="text" name="subject" size="50" value="<bean:write name="subject"/>"></td></tr>
    <tr><td>表单内容:</td><td><textarea name="body" cols="50" rows="6"><bean:write name="body"/></textarea></td></tr>

    <tr><td>验证码:</td><td>
    <!--点击此元素会自动激活验证码-->
<!--id : 元素的id(必须)-->
<!--data-appid : AppID(必须)-->
<!--data-cbfn : 回调函数名(必须)-->
<!--data-biz-state : 业务自定义透传参数(可选)-->
<button type="button" id="TencentCaptcha"
        data-appid="2050847547"
        data-cbfn="callback"
>验证</button>
               <input type="hidden" id="registerCode" name="registerCode"  > 
                <input type="hidden" id="randstr" name="randstr"   > 
                </td></tr>
    <tr><td colspan="2" style="text-align: center"><html:submit property="submit" value="提交"/></td></tr>
  
</table>
  </html:form> --%>
  <p>
    <p>邮箱：BanQ <img src="/simgs/email23ghsd486-(%5Ek.gif"> 163.com</p>
                   

</div>
	
<%@ include file="../../common/IncludeBottom.jsp" %>
