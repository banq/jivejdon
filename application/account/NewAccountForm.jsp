<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%@page import="java.util.UUID"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF)
        WebAppUtil.getComponentInstance("errorBlocker",
                this.getServletConfig().getServletContext());
if (errorBlocker.checkRate(request.getRemoteAddr(), 3)){
	return;
}
%>
<bean:define id="title"  value=" 用户注册" />
<%@ include file="../common/IncludeTop.jsp" %>
<script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>

<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>

<div class="box">
<h2 class="text-center">注册</h2>
<div class="text-center">
  
	直接登录：<a href="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml"  target="_blank" >
                          <img src='/images/sina.png' width="13" height="13" alt="登录" border="0" />新浪微博
                    </a>

  <%--<%--%>
  <%--Calendar startingCalendar = Calendar.getInstance();--%>
  <%--startingCalendar.setTime(new Date());--%>
  <%--if (startingCalendar.get(Calendar.HOUR_OF_DAY) < 7 || startingCalendar.get(Calendar.HOUR_OF_DAY) > 24) {--%>
  <%--out.println("<p><br> 为防止夜晚有人溜进来贴小广告，暂时关闭注册，工作时间正常开放，谢谢。<br>可用新浪微博 直接登入。");--%>
  <%--return;--%>
  <%--}--%>
  <%----%>
  <%--%>	--%>
</div>

<div class="box">
     <div class="row">
<html:form styleClass="form-horizontal col-md-offset-4 col-md-4" action="/account/newAccount.shtml" method="post" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="create" />
<input type="hidden" name="actionType" value="createSave"/>
<html:hidden property="userId" />

   <div class="form-group">
        <label class="col-sm-2 control-label">用户</label>
        <div class="col-sm-10">
            <input name="username" maxlength="15" placeholder="只能是英文字符或数字组合" value="" onblur="checkUsername()" id="username" class="form-control" type="text">
            <span id="usernameCheck" class="smallgray"></span>          
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">密码</label>
        <div class="col-sm-10">
            <input class="form-control" type="password" name="password" value="" maxlength="30"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">确认</label>
        <div class="col-sm-10">
            <input class="form-control" type="password" name="password2" value="" maxlength="30"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱</label>
        <div class="col-sm-10">
            <input placeholder="请用QQ或163信箱" class="form-control" name="email" maxlength="30" value="" onblur="checkEmail()" id="email" type="text">
            <span id="emailCheck" class="smallgray"></span> 
        </div>
    </div>               
    <input name="emailVisible" value="" type="hidden">
              
    <div class="form-group">
        <label class="col-sm-2 control-label">手机</label>
        <div class="col-sm-5">
             <input class="form-control" type="text" id="phoneNumber" size="15">
        </div>
         <div class="col-sm-5">
            <input class="form-control" type="button" id="btn" value="发送验证码" onclick="sendSMS(this)" /> 
        </div>
    </div>      

    <div class="form-group">
        <label class="col-sm-2 control-label">验证</label>
        <div class="col-sm-10">
             <input class="form-control" type="text" id="registerCode" name="registerCode" size="8" >
        </div>
    </div>    

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default login ">确认</button>
        </div>
    </div>
           
               
             <%-- 
                     <tr>
                     <th align="right">验证码:</th>
                     <td align="left">
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
                     
                    </td>
                 </tr>
               
<%@page import="com.jdon.jivejdon.presentation.form.SkinUtils"%>

<%
Map<String[], Integer> panswers = new HashMap();
String problem1[] = { "1.工场模式", "2.工厂模式", "3.仓储模式" };
panswers.put(problem1, 2);
String problem2[] = { "1.网关模式", "2.代替模式", "3.代理模式" };
panswers.put(problem2, 3);
String problem3[] = { "1.组合模式", "2.组织模式", "3.结构模式" };
panswers.put(problem3, 1);
String problem4[] = { "1.路模式", "2.桥模式", "3.船模式" };
panswers.put(problem4, 2);
String problem5[] = { "1.状态模式", "2.状况模式", "3.装束模式" };
panswers.put(problem5, 1);
String problem6[] = { "1.提取模式", "2.提问模式", "3.访问模式" };
panswers.put(problem6, 3);

int j = (int) (Math.random() * panswers.size());
int num = 0;
String[] prob = null;
int ans = 0;
for (String[] p : panswers.keySet()) {
	if (num == j) {
		prob = p;
		ans = panswers.get(p);
		break;
	}
	num++;
}
boolean isTrue = SkinUtils.saveProblemAnswer(ans, request);
%>
<tr>
    <td colspan="2">请问下面哪个属于设计模式？</td>
  </tr>
                 <tr>
                     <th align="right">
                     <%
                     if (isTrue){
                      for (int i = 0; i < prob.length; i++) {
                    	    out.print( prob[i] + "</br>");
                       }         
                     }
                     %>
                     
                     </th>
                      <td align="left">
                     <% if (isTrue){%>
                         请输入答案数字：<br><input name="ans" maxlength="5"  />
                     <%}else{%>
                  	     注册问题只能出现一次，欢迎下次再来注册
                          <%}%>
                     </td>
</tr>

                     

                    <% if (isTrue){ %>
                     <tr>
                    <td align="center" colspan="2">
                      <html:submit property="submit" value="确定"/>
                      <html:reset value ="Reset"/>
                     </td>
                   </tr>
                     <%} %>                     

  
               
   </table>


 </td>
</tr></table>
</td></tr>
</table>

 --%>

</html:form>
</div>
  
</div>


<%--
<%@include file="IncludeAccountFields.jsp"%>
--%>


<script>
<!--

function Juge(theForm)
{
   var myRegExp = /\S+\w+\@[.\w]+/;
   if (!myRegExp.test(theForm.email.value)) {
		alert("请输入正确的Email");
		theForm.email.focus();
		return false;
  }
   myRegExp = /\S+[a-z0-9.]*$/gi;
   if (!myRegExp.test(theForm.username.value)) {
		alert("用户名必须为英文与数字组合");
		theForm.username.focus();
		return false;
  }
   myRegExp = /\S+[a-z0-9.]*$/gi;
   if (!myRegExp.test(theForm.password.value)) {
		alert("密码必须为英文与数字组合");
		theForm.password.focus();
		return false;
   }
     
  if (theForm.password.value != theForm.password2.value)
  {
     alert("两次密码不一致！");
     theForm.password.focus();
     return (false);
  }
  
   myRegExp = /\S+[0-9.]*$/gi;
   if (!myRegExp.test(theForm.registerCode.value)) {

		alert("验证码必须填入数字");
		theForm.registerCode.focus();
		return false;
   }
   
     myRegExp = /\S+[0-9.]*$/gi;
   if (!myRegExp.test(theForm.ans.value)) {
		alert("答案数字必须填入数字");
		theForm.ans.focus();
		return false;
   }
   
   

}

function checkUsername(){	
	   myRegExp = /\S+[a-z0-9.]*$/gi;
   if (!myRegExp.test(document.getElementById("username").value)) {
		  alert("用户名必须为英文与数字组合");		  
		  return;
		}

	  var pars = "username=" +  document.getElementById("username").value + "&sessionId=<%=request.getSession().getId()%>";
    load('<%=request.getContextPath()%>/account/checkUser.jsp?'+ pars, function(xhr) {
  	       document.getElementById('usernameCheck').innerHTML = xhr.responseText;
         });
	
}

function checkEmail(){	
	  var pars = "email=" +  document.getElementById("email").value + "&sessionId=<%=request.getSession().getId()%>";
        load('<%=request.getContextPath()%>/account/checkUser.jsp?'+ pars, function(xhr) {
  	       document.getElementById('emailCheck').innerHTML = xhr.responseText;
         });
}

<%
HttpSession session1 = request.getSession();
String SMSCODE = UUID.randomUUID().toString().substring(0,6);
session1.setAttribute("SMSCODE",SMSCODE);
%>
var countdown=60;
function sendSMS(val) {
    var pn = document.getElementById("phoneNumber").value ;
	 myRegExp = /\S+[0-9.]*$/gi;
    if(!myRegExp.test(pn) ){
        alert("请输入手机号码！");
        return;
    }
    var pars = "phoneNumber=" + pn;
    load('<%=request.getContextPath()%>/account/smsQQAction.shtml?' + pars, function (xhr) {
  	       document.getElementById('smsStatus').innerHTML = xhr.responseText;
         });
    
    settime(val)
}

function settime(val) {
    if (countdown == 0) {
        val.removeAttribute("disabled");
        val.value="获取验证码";
        countdown = 60;
        return;
    } else {
        val.setAttribute("disabled", true);
        val.value="等待(" + countdown + ")";
        countdown--;
    }
    setTimeout(function() {
        settime(val)
    },1000)
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

<%@include file="../common/IncludeBottom.jsp"%>