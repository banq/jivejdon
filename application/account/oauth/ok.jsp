<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>

<bean:define id="title" value=" 登录成功 " />
<%@ include file="../../common/IncludeTop.jsp"%>

<%@ page import="java.util.Map" %>
<center>
     <br><br>
<ul>     
   登录成功，您以后可以继续用第三方帐号登录;
   <br>也可以记住下面本道场分配的用户名和密码;
   <br>如果修改密码就只能用本站用户名和新密码登入：
</ul>   
   <p>
   <br>
<ul>   
   <%
   String username = "";
   String password = "";

   java.util.Map<String, String> subParams = null;
   HttpSession session = request.getSession(false);
   if (session != null) 
       subParams = (java.util.Map<String, String>) request.getSession().getAttribute("subscriptionParameters");
                  
   if(subParams != null){
          username = subParams.get("j_username");
          password = subParams.get("j_password");
   }
   %>   
   用户：<%=username %>
   <br>
   密码：<%=password %>
 <p> <br>
 <%--
  <a href="<%=request.getContextPath()%>/account/protected/editAccountForm.shtml?action=edit&username=<%=username %> " >继续完善本站个人资料</a>
 --%>  
 <p> <br>如果首次登陆本站，为防止垃圾广告，前一小时
  <br>内只能每五分钟内发一次贴，一小时后一切正常。

</ul>
  

  <logic:notEmpty name="errors">
     <bean:write name="errors"/>
  </logic:notEmpty> 
		   

<br>
<br>
<br>
<p>返回<html:link page="/"> 首页</html:link>
</center>
<%@include file="../../common/IncludeBottom.jsp"%>

