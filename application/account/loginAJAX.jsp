<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<script>
    var isLogin = false;
</script>
<%
  if (request.getUserPrincipal() != null) {
    request.setAttribute("principal", request.getUserPrincipal());
    request.getSession().setAttribute("online",request.getUserPrincipal().toString());
%>
<script>   isLogin = true;</script>
<%
}else{
  request.removeAttribute("principal");
  if (request.getSession(false) != null)
    request.getSession().removeAttribute("online");
%>
<script>   isLogin = false;</script>
<%
  }
%>


<%@page import="com.jdon.controller.WebAppUtil,
              com.jdon.jivejdon.domain.model.account.Account,
              com.jdon.jivejdon.api.account.AccountService" %>
<%
  //need at first include IncludeTop.jsp
//this jsp can save logined account all datas into request, if you inlude this jsp, you can get them. 
  if (request.getAttribute("principal") != null) {
    AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
    Account account = accountService.getloginAccount();
    if (account != null) {
      request.setAttribute("loginAccount", account);
    }
  }
%>
<script>
    var loggedURL = '<%=request.getContextPath()%>/account/protected/logged.jsp';
</script>

<div id="login" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <button class="close" data-dismiss="modal">
          <span>&times;</span>
        </button>
      </div>
      <div class="modal-title">
        <h1 class="text-center">登录</h1>
      </div>
      <div class="modal-body">
        <form class="form-group" id="loginform" action="<%=request.getContextPath()%>/account/protected/loginhome.jsp">
          <div class="form-group">
            <label for="">用户名</label>
            <input class="form-control" type="text" placeholder="" name="j_username" id="j_username">
          </div>
          <div class="form-group">
            <label for="">密码</label>
            <input class="form-control" type="password" placeholder="" name="j_password" id="j_password">
          </div>

          <div class="text-right">
            <button class="btn btn-primary" type="submit" id="subbtn">登录</button>
            <button class="btn btn-danger" data-dismiss="modal">取消</button>
          </div>
          <input type="checkbox" name="rememberMe" id="rememberMe" checked="checked">自动登陆
          <%-- <a href="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml" target="_blank">新浪微博登录 </a>
          <a href="<%=request.getContextPath()%>/account/newAccountForm.shtml" target="_blank"> 新用户注册 </a> <a href="<%=request.getContextPath()%>/account/forgetPasswd.jsp" target="_blank"> 忘记密码? </a> --%>

        </form>
      </div>
    </div>
  </div>
</div>


