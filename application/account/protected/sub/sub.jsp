<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../../../blog/header.jsp" %>

<%--  /account/protected/sub/subAction.shtml?subscribeType=1&subscribeId=XXXX --%>
<%--  /account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=XXXX --%>
<bean:parameter name="subscribeType" id="subscribeType"/>


   <div class="mainarea_right"> 
      <div class="box_mode_2"> 


<html:form action="/account/protected/sub/subSaveAction.shtml" method="post" styleId="subscriptionForm">
<html:hidden name="subscriptionForm" property="subscriptionId"/>
<html:hidden name="subscriptionForm" property="jdonActionType"/>
<html:hidden name="subscriptionForm" property="subscribeType"/>
<html:hidden name="subscriptionForm" property="subscribeId"/>
<%
com.jdon.jivejdon.model.Account account = (com.jdon.jivejdon.model.Account)request.getAttribute("loginAccount");
String userId = account.getUserId();
String email = account.getEmail();
String role = account.getRoleName();
pageContext.setAttribute("role", role);
%>
<input type="hidden" name="userId" value="<%=userId%>" />
	   
<logic:equal name="subscribeType" value="1">
         <div class="title"> 
		    <div class="title_left">加入下面为您关注的主题？</div> 
		    <div class="title_right"></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content><a href="<%=request.getContextPath()%>/<bean:write name="subscriptionForm" property="subscribeId"/>" 
              target="_blank"><bean:write name="subscriptionForm" property="subscribed.name"/></a>
                </div>
           </div>
          </div>	 
</logic:equal>

<logic:equal name="subscribeType" value="2">
         <div class="title"> 
		    <div class="title_left">加入该标签为您关注的？</div> 
		    <div class="title_right"></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content><a href="<%=request.getContextPath()%>/tags/<bean:write name="subscriptionForm" property="subscribeId"/>" 
              target="_blank"><bean:write name="subscriptionForm" property="subscribed.name"/></a>
                </div>
           </div>
          </div>	 
</logic:equal>
	

<div class="content"> 	
<br>
 <html:checkbox name="subscriptionForm" property="sendmsg"/>关注有新内容时，以站内信息通知？
<br>
<html:checkbox name="subscriptionForm" property="sendemail"/>关注有新内容时，以发送邮件通知<%=email %>？(注册时请用QQ或163信箱 国外Gmail等退信多)。                      
<br>

<br><br>
<ul>关注有新内容时，以微博通知：
<logic:equal name="role" value="SinaUser">
<li>
<html:radio name="subscriptionForm" property="notifymode" value="sinaweibo"  />
新浪微博
</li>
</logic:equal>
<logic:equal name="role" value="User">
<li>
<html:radio name="subscriptionForm" property="notifymode" value="sinaweibo"  />
新浪微博(只提供OAuth2.0，)
</li>
</logic:equal>

<li>
<html:radio name="subscriptionForm" property="notifymode" value=""  />
不选
</li>

</ul>
<input type="button"  value="确定" onclick="submitAction()" />


<br>
</div>

</div> 
</div> 

</html:form>
	  
<script>
function submitAction(){
	   var notifymode ;
	   if (document.subscriptionForm.notifymode.checked){
       notifymode = document.subscriptionForm.notifymode.value;
 }else{
   for (i=0;i<document.subscriptionForm.notifymode.length;i++){
      if (document.subscriptionForm.notifymode[i].checked){
        notifymode = document.subscriptionForm.notifymode[i].value;
        break;
       }
   }
 }
if (notifymode == "sinaweibo"){
   document.subscriptionForm.action="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml";
   document.subscriptionForm.submit();
   
}else if (notifymode == "tecentweibo"){
	document.subscriptionForm.action="<%=request.getContextPath()%>/account/oauth/tecentCallAction.shtml";
       document.subscriptionForm.submit();
          
 }else
   document.subscriptionForm.submit();
}
</script>

<%@ include file="../../../blog/footer.jsp" %>