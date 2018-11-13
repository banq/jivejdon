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



<div class="col-md-offset-2 col-md-8">

    <p>
    在
    <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId">
            <bean:write name="forum" property="name" />
    </html:link>
    论坛中删除下面帖子.
    <br> 注意：如果该贴有子贴，只有管理员才能连同子贴一起删除，发表者自己则不行，除非子贴都是自己发布的。
   
<!-- 被删除的帖子 -->	
 
<html:form action="/message/messageSaveAction.shtml" method="post"  >

<html:hidden property="method" value="delete"/>

<html:hidden name="parentMessage" property="messageId" />


        <b><bean:write name="parentMessage" property="messageVO.subject"/></b>
    
         <p><bean:write name="parentMessage" property="account.username"/></p>
            			
        <p>发表: <bean:write name="parentMessage" property="creationDate"/></p>
      <p>
	<logic:notEqual name="messageListForm" property="allCount" value="0" >
       <font color="red"><b>注意：删除本贴将连同以下子贴一起删除，请慎重</b></font>
    </logic:notEqual>

	
<bean:parameter id="messageId" name="messageId" />
    这个主题有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页 [ 
<MultiPages:pager actionFormName="messageListForm" page="/message/messageDeleAction" paramId="messageId" paramName="messageId">
<MultiPages:prev name="&laquo;" />
<MultiPages:index />
<MultiPages:next name="&raquo;" />
</MultiPages:pager>
    


    <p><input type="submit" value="确定删除"  >	

  

</html:form>

</div>
<%@include file="../common/IncludeBottom.jsp"%>


