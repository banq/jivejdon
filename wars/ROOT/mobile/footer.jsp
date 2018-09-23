<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div data-role="footer" class="footer-docs" data-theme="c">
  <center>   
    <br>
    <a href="<%=request.getContextPath() %>/mobile/account/newAccountForm.shtml" rel="nofollow" data-role="button"  data-theme="e">注册</a> 
    <script>
	    	 loggedURL = '<html:rewrite
	    	 page="/account/protected/logged.jsp"/>?Referer=https://'+ location.hostname;
	       var loginOk= function (){
	       	 alert("登录成功");
	      }
	    </script> 
    <a href="<%=request.getContextPath() %>/mobile/login.jsp" rel="nofollow" data-role="button" data-rel="dialog" data-transition="pop" data-theme="e">登录</a> <a href="<%=request.getContextPath()%>/rss" data-role="button" data-theme="e">RSS订阅</a>
    <logic:empty name="messageCount"> <a href="<%=request.getContextPath()%>/threads" data-role="button" data-theme="e">电脑版</a> </logic:empty>
    <logic:notEmpty name="messageCount"> <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" data-role="button" data-theme="e">电脑版</a> </logic:notEmpty>
    <br>
    &copy;jdon.com
  </center>
</div>
</div>
</span> <!-- end fastclick -->
<script language="javascript" src="/common/mb.js" ></script><script>
	<%
	String furl = "/";
	if (request.getParameter("thread") != null){
      furl ="/" + request.getParameter("thread");      
    }
	if (request.getParameter("tagID") != null){
      furl ="/tags/" + request.getParameter("tagID");      
    }
	%>
 detectRes("<%=furl%>");
</script>

</body></html>