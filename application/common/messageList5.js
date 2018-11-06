//prototype.js

function openInfoDiag(content) {  
      if (typeof(Dialog) == 'undefined') 
       loadWLJSWithP(content, openInfoDiag2);
    else
       openInfoDiag2(content);
}

function openInfoDiag2(content) {  
      scontent = content;       
      Dialog.info(scontent + "  计时：" +  timeout + " 秒   ",
               {width:260, height:150, showProgress: true});
      setTimeout(infoTimeOut, 1000);
      startDiaglog = true;   
}

   
function infoTimeOut() {  
      if (startDiaglog){
         if (timeout > 2)
            infoDiagClose();
         else
            timeout++;  
            Dialog.setInfoMessage(scontent + "  计时：" +  timeout + " 秒  ");
            setTimeout(infoTimeOut, 1000) ;
      }
}
   
function setDiagInfo(content){
      scontent = content;
}
   
function infoDiagClose(){
     if (startDiaglog){
        Dialog.closeInfo();
        startDiaglog = false;
        timeout = 1;
     }
}


  function getSelText()
  {
    var txt = '';
     if (window.getSelection)
        txt = window.getSelection();
    else if (document.getSelection)
        txt = document.getSelection();
    else if (document.selection)
        txt = document.selection.createRange().text;
    
    return txt;
   }
  
  
   
   function callLogin(){    
        if (typeof(Dialog) == 'undefined') 
          loadWLJS(callLogin2);
    else
         callLogin2();
   }

   function callLogin2(){    
     infoDiagClose();         
     loginW('messageReply');
   }


   
   function goAfterLogged(fromFormName){
     $(fromFormName).submit();
     if (formSubmitcheck){
        openInfoDiag("登录成功，继续提交...");   
        setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
           window.location.reload();//页面刷新
        },2000);
     }
   }
   //prototype.js end
   

function viewcount(threadId, sId)
{            
	var pars = 'thread=' + threadId + "&sId=" + sId;   
    load(getContextPath() +'/query/viewThread.shtml?'+ pars, function(xhr) {
  	      
			});
}

 function digMessage(id)
    {            
    	var pars = 'messageId='+id;     
        load(getContextPath() +'/query/updateDigCount.shtml?'+ pars, function(xhr) {
  	       document.getElementById('digNumber_'+id).innerHTML = xhr.responseText;
           document.getElementById('textArea_'+id).innerHTML ="  ";
			});
     
    }
 


function tagthreads(length,tablewidth,count,tagID){
// window.onload = function() {    
  $.ajax({
        url:getContextPath() +'/query/tagThreads/'+tagID,
        success: function(response) {
         $('#tagthreads_'+tagID).html(response);
        }
  });
// }

//    load(getContextPath() +'/query/tagThreads/'+tagID, function(xhr) {
//  	       document.getElementById('tagthreads_'+tagID).innerHTML = xhr.responseText;
//			});
//    cross domain 
 }                

function approveList(){
     load(getContextPath() +'/query/approved', function(xhr) {
  	       document.getElementById('approved').innerHTML = xhr.responseText;
			});
      
}                

var timeout = 1;
var startDiaglog = false;  
var scontent;


function saveCache(objId){
  try{
  	var o =  document.getElementById(objId).value;
  	if(window.localStorage){  	  
  	   window.localStorage.setItem(objId,o);
    }else if (window.clipboardData) { 
       window.clipboardData.setData(objId,o);   	
	  }
  }catch(e){}	
}

 
 var formSubmitcheck = false;  
var subjectold = "";
 function checkPost(theForm) {
  
      if (document.getElementById('forumId_select') != null
        && document.getElementById('forumId_select').value == ""){
    	  alert("页面forum错误，请拷贝备份你的发言后，重新刷新本页");
    	    formSubmitcheck = false;
            return formSubmitcheck;
     }
      
      var body = theForm.body.value.replace(/(^\s*)|(\s*$)/g, ""); 
      var subject = theForm.subject.value.replace(/(^\s*)|(\s*$)/g, ""); 
      if (subject  != ""){          
          if (subjectold != subject){
            formSubmitcheck = true;
            theForm.formButton.disabled=true;
          }else 
            alert("发言重复" );
          subjectold = subject;
      }else{
    	  alert("请输入发言标题和发言内容！" +subject + " " + body);
    	  formSubmitcheck = false;
      }              
      return formSubmitcheck;      
   }


   var bodyLength = 0;
   function copyBody(){
       if (document.getElementById('formBody').value != ""){
          if (document.getElementById('formBody').value.length != bodyLength){
             bodyLength = document.getElementById('formBody').value.length;
              saveCache('formBody');
          }
       }        
   }   



function chksubject(id){
		 	if (id == null){
		 		document.getElementById("reblog").value = "";
		 		document.getElementById("onlyreblog").value = "";
		 		return;
		 	}
		 	if (document.getElementById("parentMessageSubject").value == document.getElementById("replySubject").value){
		 		alert("转发的标题不能和现在标题一样，请修改");
		 		return false;
		 	}
		 	document.getElementById(id).value ="on";
		 	return true;
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



function clearCache(objId){
  try{
  	if(window.localStorage){
  	   window.localStorage.removeItem(objId);
  	   window.localStorage.clear();
    }else if (window.clipboardData) { 
       window.clipboardData.clearData();  
	  }
  }catch(e){}			
}

//postreply2.js
   
   var timedCopy;
   function startCopy(timeout){
       if (timedCopy == null)
           timedCopy = setInterval(copyBody, timeout);
   }
   
   function closeCopy(){
      clearInterval(timedCopy);
      timedCopy = null;
   }
   
function releaseKeyboard(){
   
   document.onkeydown=forumSubmit;
   //auto backup body content
   startCopy(120000);

}

function forumSubmit(event)
{
   var page;
   event = event ? event : (window.event ? window.event : null);

   if (event.keyCode==13 && event.ctrlKey){      
      document.getElementById("formSubmitButton").click();                     
   }else return;
   
 }


var lazyloaded = false;    
function loadCkeditJS(){
   if(lazyloaded) return;
   $LAB
   .script("/common/ckeditor/ckeditor.js").wait()
   .script("/common/ckeditor/sample.js")
   .wait(function(){
	   initSample();      
       lazyloaded = true;
    })  
}    


