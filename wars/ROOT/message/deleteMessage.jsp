<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="messageList" name="messageListForm" property="list" />

<bean:define id="parentMessage" name="messageListForm" property="oneModel" />
<bean:define id="forum" name="parentMessage" property="forum" />
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>


<table cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr><td><html:img page="/images/blank.gif" width="1" height="10" border="0" alt=""/></td></tr>
</table>

<table cellpadding="0" cellspacing="0" border="0"  width="971" align="center">
<tr>
    <td valign="top" width="98%">
    <p>
    在
    <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId">
            <bean:write name="forum" property="name" />
    </html:link>
    论坛中删除下面帖子.
    <br> 注意：如果该贴有子贴，只有管理员才能连同子贴一起删除，发表者自己则不行，除非子贴都是自己发布的。
    
    </td>
    <td valign="top" width="1%" align="center">
    
    </td>
</tr>
</table>


<table  width="971" align="center" >
<tr>
	<td>
<!-- 被删除的帖子 -->	
 
<html:form action="/message/messageSaveAction.shtml" method="post"  >

<html:hidden property="method" value="delete"/>

<html:hidden name="parentMessage" property="messageId" />
<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
	<td>
    <table bgcolor="#cccccc" cellpadding="4" cellspacing="1" border="0" width="100%">
   
    <tr bgcolor="#E3E7F0">
        <td width="1%" rowspan="2" valign="top">
        <table cellpadding="0" cellspacing="0" border="0" width="140">
        <tr><td>
            
               <b><bean:write name="parentMessage" property="account.username"/></b>
            			
            <br><br>
            
            发表文章: <bean:write name="parentMessage" property="account.messageCount"/>
            
            <br>            
            注册时间: <bean:write name="parentMessage" property="account.creationDate"/>
            <br><br>

            </td>
        </tr>
        </table>
        </td>
        <td >
       
        <b><bean:write name="parentMessage" property="messageVO.subject"/></b>
    
		</td>
        <td width="1%" nowrap>
        发表: <bean:write name="parentMessage" property="creationDate"/>
        </td>

        <td width="1%" nowrap align="center">
         </td>        
    </tr>
    <tr bgcolor="#E3E7F0">
        <td width="99%" colspan="4" valign="top">
        <span class="tpc_content">
	<bean:write name="parentMessage" property="messageVO.body" filter="false"/>    
    	</span>
		<p>
        <div id=vgad2 ></div>
        </td>
    </tr>
    </table>

</td></tr>
</table>

    </td></tr>
<tr>	
	<td align='center'>
	<logic:notEqual name="messageListForm" property="allCount" value="0" >
       <font color="red"><b>注意：删除本贴将连同以下子贴一起删除，请慎重</b></font>
    </logic:notEqual>
	
	</td>
</tr>
<tr>
	<td  align='center'>
    <input type="submit" value="确定删除"  >	

	</td>
</tr>    
    
    <tr><td>
    <br>
    <center>子贴列表</center>
	</td></tr><tr><td>
	
<table bgcolor="#868602"
 cellpadding="3" cellspacing="0" border="0"  width="971" align="center">
<tr><td width="400">
<bean:parameter id="messageId" name="messageId" />
    这个主题有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页 [ 
<MultiPages:pager actionFormName="messageListForm" page="/message/messageDeleAction" paramId="messageId" paramName="messageId">
<MultiPages:prev name="&laquo;" />
<MultiPages:index />
<MultiPages:next name="&raquo;" />
</MultiPages:pager>
     ] 
    </td><td >
    </td>
</tr>
</table>	

<%
   int row = 1;
%>
<logic:iterate id="forumMessage" name="messageListForm" property="list"  >
 <%  
 String bgcolor = "#E3E7F0";
 if (row++%2 != 1) {
   bgcolor = "#EAE9EA";
 } 
 %>

<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
	<td>
    <table bgcolor="#cccccc" cellpadding="4" cellspacing="1" border="0" width="100%">
   
    <tr bgcolor="<%=bgcolor%>">
        <td width="1%" rowspan="2" valign="top">
        <table cellpadding="0" cellspacing="0" border="0" width="140">
        <tr><td>
               <b><bean:write name="forumMessage" property="account.username"/></b>
			
            <br><br>
            发表文章: <bean:write name="forumMessage" property="account.messageCount"/></a>
            <br>            
            注册时间: <bean:write name="forumMessage" property="account.creationDate"/>
            <br><br>

            </td>
        </tr>
        </table>
        </td>
        <td >
       
        <b><bean:write name="forumMessage" property="messageVO.subject"/></b>
    
		</td>
        <td width="1%" nowrap>
        发表: <bean:write name="forumMessage" property="creationDate"/>
        </td>

        <td width="1%" nowrap align="center">
        删除
         </td>        
    </tr>
    <tr bgcolor="<%=bgcolor%>">
        <td width="99%" colspan="4" valign="top">
        <span class="tpc_content">
	<bean:write name="forumMessage" property="messageVO.body" />    
    	</span>
		<p>
        <div id=vgad2 ></div>
        </td>
    </tr>
    </table>

</td></tr>
</table>

</logic:iterate>
</td></tr>


</table>


</html:form>

<%@include file="../common/IncludeBottom.jsp"%>


