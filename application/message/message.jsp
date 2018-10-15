<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="messageForm">
<bean:define id="forum" name="messageForm" property="forum"  />
<logic:notEmpty name="forum">
<bean:define id="title" name="forum" property="name" />
<%@ include file="../common/IncludeTop.jsp" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
response.setStatus(HttpServletResponse.SC_OK);
%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">   

<div class="box">
<div class="comment">
<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>

<html:form action="/message/messageSaveAction.sthml" method="post" target="target_new" styleId="messageNew" onsubmit="return checkPost(this);" >

<html:hidden property="action" />
<html:hidden property="messageId" />
<html:hidden property="forumThread.threadId" />

<div class="row">
	<div class="col-md-6">       
        <div class="form-group">    
     <html:select name="messageForm" styleClass="form-control" property="forum.forumId" styleId="forumId_select">
       <html:option value="">请选择</html:option>
       <html:optionsCollection name="forums" value="forumId" label="name"/>       
     </html:select>
  
        </div>
    </div>
    <div class="col-md-6">
    </div>
</div>
<logic:equal name="messageForm" property="authenticated"  value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="messageForm" property="authenticated" value="true">
  
 <jsp:include page="messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="false"/>   
 </jsp:include> 
 

<div class="row">
	<div class="col-md-12">       
        <div class="form-group"> 
              <button type="submit" class="btn btn-4 btn-block" name="formButton" id="formSubmitButton">发布</button>
        </div>
      </div>
</div>

<script>
function loadPostjs(){
  if (typeof(openInfoDiag) == 'undefined') {
    $LAB
     .script('/common/js/prototype.js').wait()
     .script('/common/messageEdit.js')
     .wait(function(){
         setObserve();
     
     })      
  }else
     setObserve();
       
}

function setObserve(){
 if(typeof(Ajax) != "undefined"){
      $('messageNew').observe("submit", callbackSubmit);
  }   
}


function hackAction(myform){
	//hack <input type="hidden" name="action" value="edit"> for IE not work
	var a=[];
	for (var i = 0, p=myform.elements; i < p.length; i++)
         if(p[i].name&&p[i].name.toLowerCase()=='action'){
             a.push([p[i], p[i].name]);
             p[i].removeAttribute('name', 0);
          }
	return a;
}

function recoverAction(a){
	//recover hack
	for (var i = 0; i < a.length; i++)
         a[i][0].setAttribute('name', a[i][1], 0);	 
}

function changeAction(myform, newAction){
	var a = hackAction(myform);
	var oldformAction = myform.action;
	myform.action = newAction;
	recoverAction(a);
	return oldformAction;
}

function notify(){	
	var oldformAction = changeAction(document.messageForm, "<%=request.getContextPath()%>/message/messageSaveAction2.shtml");
	document.messageForm.submit();
		
	changeAction(document.messageForm, oldformAction);
}
notify();
loadPostjs();
</script>



<table cellpadding="4" cellspacing="0" border="0" width="400">
<tr>
	<td width="20">&nbsp;</td>
  <logic:equal name="messageForm" property="action" value="edit">
	<td>
       <html:link page="/message/messageDeleAction.shtml" paramId="messageId" paramName="messageForm" paramProperty="messageId">
       删除本贴(只有无跟贴才可删除)
       </html:link>	
	</td>
	 <logic:equal name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=false" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
        取消屏蔽
        </html:link></td>   
     </logic:equal>           
        
    <logic:notEqual name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=true" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
       屏蔽该贴
        </html:link></td>    
     </logic:notEqual>        
     
   </logic:equal>  
</tr>
</table>

</logic:equal>   

</html:form>
</div>
</div>

</logic:notEmpty>
</logic:notEmpty>


<%@include file="../common/IncludeBottom.jsp"%> 

