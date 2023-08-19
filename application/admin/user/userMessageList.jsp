<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 用户发贴管理" />
<%@ include file="../header.jsp" %>


<p><b>用户发帖概览</b>

<hr size="0">

<bean:parameter  name="username" id="username" value=""/>
<html:form action="/admin/user/userMessageList.shtml" method="post" >
查询用户名为<input type="text" name="username" value="<bean:write name="username"/>"/>发布的所有帖子
 <html:submit value=" 查询 " property="btnsearch" />
</html:form>




<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<form action="<%=request.getContextPath()%>/admin/user/deleteUserMessages.shtml" method="post" onsubmit="return delConfirm()">
 <input name="method" type="hidden" value="deleteUserMessages"/>
 <input name="username" type="hidden" value="<bean:write name="username"/>"/>
 删除该用户资料:<input type="checkbox" name="deluserprofile"  value="/admin/user/removeAccountForm.shtml?action=delete&username=<bean:write name="username"/>">
 <input type="submit" value=" 删除该用户所有帖子 "/>
</form>



<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td>
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="messageListForm" page="/admin/user/userMessageList.shtml"  paramId="username" paramName="username">
<MultiPages:prev><html:img  page="/images/prev.gif" width="10" height="10" hspace="2" border="0" alt="上页"/></MultiPages:prev>
<MultiPages:index />
<MultiPages:next><html:img  page="/images/next.gif" width="10" height="10" hspace="2" border="0" alt="下页"/></MultiPages:next>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>


<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td> 
<table bgcolor="#999999" cellpadding="3" cellspacing="1" border="0" width="100%">
<tr bgcolor="#FFFFCC">
    <td style="text-align: center" nowrap="nowrap"><b>帖子ID</b></td>
    <td style="text-align: center" nowrap="nowrap"><b>主题</b></td>
        <td style="text-align: center" nowrap="nowrap"><b>IP</b></td>
    <td style="text-align: center" nowrap="nowrap"><b>修改时间</b></td>
    <td style="text-align: center" nowrap="nowrap"><b>编辑</b></td>
    <td style="text-align: center" nowrap="nowrap"><b>屏蔽</b></td>
    <td style="text-align: center" nowrap="nowrap"><b>删除</b></td>
</tr>

<logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" >
 
<bean:define id="forumThread" name="forumMessage" property="forumThread"/>

<tr bgcolor="#ffffff">
    <td style="text-align: center" width="2%"><bean:write name="forumMessage" property="messageId"/></td>
    <td width="30%">
      <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />?message=<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'       
        title="<bean:write name="forumMessage" property="messageVO.body"/>"
        target="_blank" >
        <bean:write name="forumMessage" property="messageVO.subject"/></a>
    </td>   
     <td style="text-align: center"><bean:write name="forumMessage" property="postip" />
        <form action="<%=request.getContextPath()%>/admin/user/banIPAction.shtml" method="post" target="_blank">
          <input name="ip" type="hidden" value="<bean:write name="forumMessage" property="postip" />"/>
          <input type="submit" value="屏蔽IP"/>
        </form>    
    </td>   
     <td style="text-align: center" width="4%"
        ><bean:write name="forumMessage" property="modifiedDate3" /></td>
    <td style="text-align: center"  width="4%"
        > <html:link page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId"
            ><img src="../images/button_edit.gif" width="17" height="17" alt="编辑该贴..." border="0"
        ></html:link></td>
        
    <logic:equal name="forumMessage" property="masked" value="true">
      <td style="text-align: center" width="4%"
        > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=false" paramId="messageId" paramName="forumMessage" paramProperty="messageId" >
        <img src="../images/button_approve.gif" width="17" height="17" alt="取消屏蔽" border="0"
        ></html:link></td>   
     </logic:equal>           
        
    <logic:notEqual name="forumMessage" property="masked" value="true">
      <td style="text-align: center" width="4%"
        > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=true" paramId="messageId" paramName="forumMessage" paramProperty="messageId" >
        <img src="../images/button_mask.gif" width="17" height="17" alt="屏蔽该贴..." border="0"
        ></html:link></td>    
     </logic:notEqual>                
       
    <td style="text-align: center" width="4%"
        > <html:link page="/message/messageDeleAction.shtml?" paramId="messageId" paramName="forumMessage" paramProperty="messageId" onclick="return delConfirm()">
        <img src="../images/button_delete.gif" width="17" height="17" alt="删除该贴..." border="0"
        ></html:link></td>
</tr>

</logic:iterate>
</table>

</td></tr>
</table>

<table  cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td>
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="messageListForm" page="/admin/user/userMessageList.shtml"  paramId="username" paramName="username">
<MultiPages:prev><html:img  page="/images/prev.gif" width="10" height="10" hspace="2" border="0" alt="上页"/></MultiPages:prev>
<MultiPages:index />
<MultiPages:next><html:img  page="/images/next.gif" width="10" height="10" hspace="2" border="0" alt="下页"/></MultiPages:next>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>


<script type="text/JavaScript">
function delConfirm(){
  if (confirm( '删除这些帖子 ! \n\n 不能再恢复 确定吗?  '))
  {
    document.forms[0].method.value="delete";
    document.forms[0].submit();
    return true;
  }else{
    return false;
  }
}
</script>

<%@include file="../footer.jsp"%>


