<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 编辑注册资料" />
<%@ include file="../../common/IncludeTop.jsp" %>

<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>我的消息</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 <table border="0" cellpadding="3" cellspacing="0"  >
                <tr>
                     <td align="middle">

                     </td>
                 </tr>      
    <tr>
      <td align="middle">
        <html:link page="/shortmessage.jsp" paramId="user" paramName="accountForm"
           paramProperty="userId">按这里进入我的消息</html:link>                     
      </td>
     </tr>      
    <tr>
   <td align="middle">
    </td>
     </tr>              
   </table>


 </td>
</tr></table>
</td></tr>
</table>

<br><br>

<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>查看我的发帖</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 <table border="0" cellpadding="3" cellspacing="0"  >
                <tr>
                     <td align="middle">

                     </td>
                 </tr>      
    <tr>
      <td align="middle">
        <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="accountForm"
           paramProperty="userId">按这里查看我的发帖明细</html:link>                     
      </td>
     </tr>      
    <tr>
   <td align="middle">
    </td>
     </tr>              
   </table>


 </td>
</tr></table>
</td></tr>
</table>

<br><br>

<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>个人资料设置</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 <table border="0" cellpadding="3" cellspacing="0"  >
                <tr>
                     <td align="middle">

                     </td>
                 </tr>      
    <tr>
      <td align="middle">
        <html:link page="/account/protected/accountProfileForm.shtml?action=edit" paramId="userId" paramName="accountForm"
           paramProperty="userId">按这里进入个人详细资料设置</html:link>                     
      </td>
     </tr>      
    <tr>
   <td align="middle">
    </td>
     </tr>              
   </table>


 </td>
</tr></table>
</td></tr>
</table>


<br><br>

<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>注册资料修改</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 <table border="0" cellpadding="3" cellspacing="0"  >
                <tr>
                     <td align="middle">

                     </td>
                 </tr>      
    <tr>
      <td align="middle">
        <html:link page="/account/protected/editAccountForm.shtml?action=edit" paramId="username" 
        paramName="principal">按这里进入注册资料修改</html:link>                     
      </td>
     </tr>      
    <tr>
   <td align="middle">
    </td>
     </tr>              
   </table>


 </td>
</tr></table>
</td></tr>
</table>

<p>
<%@include file="../../common/IncludeBottom.jsp"%></p>

