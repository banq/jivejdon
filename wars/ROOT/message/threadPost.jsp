<%--  called bu ThreadList http://127.0.0.1:8080/jivejdon/forum.jsp --%>
<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript" src="/common/js/prototype.js"></script>
<script type="text/javascript" src="/common/jquery.min.js"></script>
<script type="text/javascript" src="/common/login.js"></script>

<bean:parameter id="forumId" name="forumId" value="" />

<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>

<html:form action="/message/messageSaveAction.sthml" method="post" target="target_new" styleId="messageNew"  onsubmit="return checkPost(this);" >

<logic:notEmpty name="forumId">
  <input type="hidden" name="forum.forumId" value="<bean:write name="forumId"/>" id="forumId_select"/>
</logic:notEmpty>


<logic:empty name="forumId">
<table cellpadding="4" cellspacing="0" border="0"  width="971" align="center">

<tr>
	<td  width="50" align="right">在 </td>
	<td align="left"> 
	
       <select name="forum.forumId"  id="forumId_select" ></select> 中发表新的
     <input type="radio" id="postText" value="1"  checked="checked" onclick="postLinkText(this.value)"/>文本
     <input type="radio" id="postLink" value="2"  onclick="postLinkText(this.value)"/>链接	    
    </td>
</tr>
</table>
</logic:empty>
<html:hidden property="messageId" />
<html:hidden property="action" value="create"/>

<jsp:include page="../message/messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="false"/>   
</jsp:include> 

    
    
<table cellpadding="2" cellspacing="0" border="0" width="971" align="center">
<tr>
 <td width="50">&nbsp;</td>
 <td align="left">
 	  <div id="postText2">每2分钟自动备份发帖内容, 重新打开会自动加载备份。提问题前先查询<html:link page="/query/tagsList.shtml?count=200" target="_blank">标签列表</html:link></div>    
    <br>
    <input type="submit" value=" 确定 Ctrl+Enter " name="formButton" id="formSubmitButton" tabindex="3">     
       如有回复通知我<input type="checkbox" name="replyNotify" checked="checked">
 </td>
</tr>
</table> 


</html:form>

<div id="textassit2" style="display:none"></div>
<script>
function loadPostjs(){
  if (typeof(openInfoDiag) == 'undefined') {
     $LAB
     .script('/common/threadPost.js').wait()

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

function loadCache(objId){
 try{
  	if(window.localStorage){  		
  	   return window.localStorage.getItem(objId);
	  }else if (window.clipboardData) { 	  	
      return(window.clipboardData.getData(objId));   	   
   } 
   }catch(e){}		
}
var formBody = loadCache('formBody');
if (formBody != null)
    document.getElementById('formBody').value = formBody;

var fetctbutton = '<button type="button" onclick="fetchTitle()">输入url自动获得标题</button><span id="title-status"></span>';
function postLinkText(value){	
  if (value == 1) {		
	 document.getElementById("postText").checked=true ;
	 document.getElementById("postText2").style.display  = "inline";
     document.getElementById("postLink").checked=false ;    
     document.getElementById("textassit").innerHTML = document.getElementById("textassit2").innerHTML; 	     document.getElementById("bodyName").innerHTML ="内容";
     $('formBody').setAttribute("rows", "30");
     $('formBody').value ="";	    
	 document.getElementById("formBody").onkeyup =null;
  }else if(value==2){
	 document.getElementById("postText").checked=false ;
	 document.getElementById("postText2").style.display  = "none";
     document.getElementById("postLink").checked=true ;
     document.getElementById("textassit2").innerHTML = document.getElementById("textassit").innerHTML;
 	 document.getElementById("textassit").innerHTML = fetctbutton;	  
  	 document.getElementById("bodyName").innerHTML ="网址";
     $('formBody').setAttribute("rows", "2");
	 $('formBody').value ="http://";		  
     document.getElementById("formBody").onkeyup =countChar;
  }
		
}  	
var myRegExp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
var countChar =function(textareaName,spanName)
{  
  document.getElementById("title-status").innerHTML = 140 - document.getElementById("formBody").value.replace(myRegExp,"").length;
}  
function fetchTitle(){

	if (!myRegExp.test($('formBody').value)){
		alert("请输入网址http://开始");
		return;
	}
	document.getElementById("title-status").innerHTML ="<font color=red >载入中...</font>";
	
	var pars = "url=" + $('formBody').value;
	new Ajax.Request('<html:rewrite page="/forum/threadPostLinkfetchtitle.jsp"/>', 
	  	    {method: 'get', parameters: pars, onSuccess: shoForumResponse}); 
	  	     	    	  
	function shoForumResponse(transport){
		if (!transport.responseText.isJSON())
		 	 return;
	     var dataArray = (transport.responseText).evalJSON();	 	 
	     dataArray.each(function(title){   
	    	 $('replySubject').value = title.value;		
	    	 document.getElementById("title-status").innerHTML ="";
	     });
	 }	
}
<%if (request.getParameter("link") != null){
	out.print("postLinkText(2)");
}

%>
</script>

<script>
 
 var $j = jQuery.noConflict();
// $j is now an alias to the jQuery function; creating the new alias is optional.

    
      $j(function() {
         
    // get initial top offset of navigation 
    var floating_navigation_offset_top = $j('#textassit').offset().top;
             
    // define the floating navigation function
    var floating_navigation = function(){
                // current vertical position from the top
        var scroll_top = $j(window).scrollTop(); 
         
        // if scrolled more than the navigation, change its 
                // position to fixed to float to top, otherwise change 
                // it back to relative
        if (scroll_top > floating_navigation_offset_top) { 
            $j('#textassit').css({ 'position': 'fixed', 'top':0});
        } else {
            $j('#textassit').css({ 'position': 'relative' }); 
        }   
    };
     
    // run function on load
    floating_navigation();
     
    // run function every time you scroll
    $j(window).scroll(function() {
         floating_navigation();
    });
 
});

</script>
<script>
var pars = "";
new Ajax.Request('<html:rewrite page="/forum/forumListJSON.shtml"/>', 
  	    {method: 'get', parameters: pars, onSuccess: shoForumResponse}); 
  	     	    	  
function shoForumResponse(transport){
	if (!transport.responseText.isJSON())
	 	 return;
     var dataArray = (transport.responseText).evalJSON();	 	 
     dataArray.each(function(forum){   
    	 var optn = document.createElement('option');
	     optn.text = forum.name;
	     optn.value = forum.forumId;		  
          $('forumId_select').options.add(optn);
     });
 }
</script>    

