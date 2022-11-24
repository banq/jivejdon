<%@ page session="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>


<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">

<bean:define id="title"  value=" 活跃用户列表 " />
<%@ include file="../common/IncludeTop.jsp" %>

<center>
<h3> 活跃用户列表</h3>
</center>

</logic:notEqual>


<bean:parameter id="tablewidth" name="tablewidth" value="600"/>
<table width="<bean:write name="tablewidth"/>" align="center"><tr><td>
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate id="account" name="accountListForm" property="list" length='<%=coutlength%>'>
<div class="linkblock">  
<table width="100%"><tr><td width="50%" align="center">
      
           <logic:notEmpty name="account" property="uploadFile">
            <logic:equal name="account" property="roleName" value="User"> 
               <img  src="/img/account/<bean:write name="account" property="userId"/>"  border='0' class="post_author_pic" />
            </logic:equal>
            <logic:equal name="account" property="roleName" value="SinaUser">                 
               <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' class="post_author_pic" />
            </logic:equal>
           </logic:notEmpty>
           <logic:empty name="account" property="uploadFile">
           	    <img  src="/images/emavatar.png"  border='0' width="75" height="75" />
           </logic:empty>                     

<logic:equal name="noheader" value="on">
<table width="100%"><tr><td >
   <bean:write name="account" property="username" />
  <br>        
      帖数：<bean:write name="account" property="messageCount"/>
   <br>
     注册：<bean:write name="account" property="creationDate"/>      	
   <br>
   <logic:greaterThan name="account" property="subscribedCount" value="0">
    <span class="smallgray"><bean:write name="account" property="subscribedCount"/>人关注  </span>    	
   </logic:greaterThan>
   <br> 	
   </td></tr></table>
</logic:equal>
	
</td>
<logic:notEqual name="noheader" value="on">
<td>
  <span onmouseover="loadWLJSWithP(this, initUsersW)"  class='Users ajax_userId=<bean:write name="account" property="userId"/>' id="users" >                      
              <bean:write name="account" property="username" />
  <br>        
      文章：<bean:write name="account" property="messageCount"/>
   <br>
     注册：<bean:write name="account" property="creationDate"/>      	
   <br>
关注  
    <logic:greaterThan name="account" property="subscriptionCount" value="0">
      <a href="<%=request.getContextPath()%>/social/following.shtml?subscribeType=3&userId=<bean:write name="account" property="userId"/>"  target="_blank"  rel="nofollow">
           <span class="smallgray"><bean:write name="account" property="subscriptionCount"/></span>
      </a>
   </logic:greaterThan>             
&nbsp;| 粉丝 
    <logic:greaterThan name="account" property="subscribedCount" value="0">
      <a href="<%=request.getContextPath()%>/social/follower.shtml?subscribeType=3&userId=<bean:write name="account" property="userId"/>"  target="_blank"  rel="nofollow">
            <span class="smallgray"><bean:write name="account" property="subscribedCount"/></span>
      </a>
    </logic:greaterThan>
<%--              
&nbsp; | 微博              
    <logic:greaterThan name="account" property="messageCount" value="0">
       <a href="<%=request.getContextPath()%>/blog/messages/<bean:write name="account" property="username"/>"  target="_blank"  rel="nofollow">
           <span class="smallgray"><bean:write name="account" property="messageCount"/></span>
                 </a>
    </logic:greaterThan>
   
   
   <br> 	
   <a href="<%=request.getContextPath()%>/blog/<bean:write name="account" property="username"/>" >
       博客：<span rel="nofollow"><bean:write name="account" property="username"/>.jdon.com</span>
   </a> --%>
      <br>
   <%--<a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="account" property="userId"/>"--%>
                  <%--target="_blank" title="加关注"  rel="nofollow">            --%>
                 <%--<img src="/images/user_add.gif" width="15" height="15" alt="加关注" border="0" align="absmiddle"/><span class="blackgray">+关注</span>--%>
     <%--</a>--%>
   </td>
</logic:notEqual>     
   </tr></table>       

</div>        	
<p></p> 
</logic:iterate>

</td></tr></table>


<logic:notEqual name="noheader" value="on">
<br><br><br><br><br><br><br><br>


<%@include file="../common/IncludeBottom.jsp"%>
</logic:notEqual>