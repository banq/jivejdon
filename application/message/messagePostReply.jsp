<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="parentMessage" name="messageReplyForm" property="parentMessage"  />
<bean:define id="forumThread" name="parentMessage" property="forumThread" />
<bean:define id="forum" name="parentMessage" property="forum" />

<iframe id='target_reply' name='target_reply' src='' style='display: none'></iframe>
<div class="box">
<div id="replyDiv">
<div class="tooltip_content">
<html:form action="/message/messageReplySaveAction.sthml" target="target_reply" styleId="messageReply"  method="post"  onsubmit="return checkPost(this);" >
<html:hidden property="jdonActionType" value="create"/>
<input type="hidden" name="forum.forumId" value="<bean:write name="forum" property="forumId" />" />
<input type="hidden" name="parentMessage.messageId" id="parentMessageId" value="<bean:write name="parentMessage" property="messageId"  />"/>
<input type="hidden" name="" id="parentMessageSubject" value="<bean:write name="parentMessage" property="messageVO.subject"  />"/>
<html:hidden property="messageId" />
    
<!-- create another name "messageForm", so in messageFormBody.jsp it can be used -->
<bean:define id="messageForm" name="messageReplyForm" scope="request" />

 <jsp:include page="messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="true"/>   
 </jsp:include>  


   <input type="hidden" name="reblog" id="reblog" value="" />
   <input type="hidden" name="onlyreblog" id="onlyreblog" value="" />
   <input type="submit" value=" 评论 " name="formButton" id="formSubmitButton" tabindex="3" onclick="return chksubject();"> 
   <input type="submit" value=" 转发 "  tabindex="4" name="onlyreblog" onclick="return chksubject('onlyreblog');" > 

<script>
function loadPostjs(){
  if (typeof(openReplyWindow) == 'undefined') {
     $LAB
     .script('/common/postreply.js').wait()
     .wait(function(){
          setObserve();
     })     
  }else
     setObserve();
}

function setObserve(){
 if(typeof(Ajax) != "undefined"){
      Event.observe('messageReply', 'submit', callbackSubmit);
  }   
}
//reblog in postreply.js


</script>

</html:form>
     </div>
</div>
</div>

