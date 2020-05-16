<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="java.util.*"%>
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF"%>
<%
    ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
    if (errorBlocker.checkCount(request.getRemoteAddr(), 3)){
        response.sendError(404);
        return;
    }
    Integer limit = (Integer)request.getSession().getServletContext().getAttribute("limit");
    if (limit != null){
        if (limit <= 0){
            out.println("<p><br> 为防止垃圾广告，新用户注册额度用完，下周再来。<br>可用<a href='/account/oauth/sinaCallAction.shtml'>微博账号登入</a>。");
            System.err.println("new user over limited...");
            return;
        }
    }

    Calendar startingCalendar = Calendar.getInstance();
    startingCalendar.setTime(new Date());
    if (startingCalendar.get(Calendar.HOUR_OF_DAY) < 10 || startingCalendar.get(Calendar.HOUR_OF_DAY) > 16) {
        out.println("<p><br> 为防止垃圾广告，本段时间关闭注册，工作时间内正常开放。<br>期间可用<a href='/account/oauth/sinaCallAction.shtml'>微博账号登入</a>。");
        return;
    }

%>

<bean:parameter name="subject" id="subject" value=""/>
<bean:parameter name="body" id="body" value=""/>
<bean:parameter name="email" id="email" value=""/>

<%@ include file="../common/IncludeTop.jsp" %>

<script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
<script>


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

</script>
<body>

<html:errors/>
<p><h3 align="center">新用户注册准备阶段，请点击验证按钮验证</h3>
<div align="center"><form action="/account/NewAccountForm.jsp" method="POST">
    <input type="hidden" name="action" value="create"/>
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                为防止垃圾广告，新用户注册实现有条件限制。<br>
                如果注册过程无法正常进行，请使用<a href='/account/oauth/sinaCallAction.shtml'>微博账号登入</a>。<br>
                请严格按照下面两步骤顺序点击按钮,点完第一步按钮再点第二部按钮：
            </div>
            <div class="box">
                <div>第一步：
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
                </div>

            </div>
            <div class="box">

                <div>第二步：
                    <input type="submit" value="确认"/>
                </div>
            </div>
        </div>

    </div>

</form>


</div>


<%@ include file="../common/IncludeBottom.jsp" %>
