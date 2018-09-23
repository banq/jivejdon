var timeout = 1;
var startDiaglog = false;  
var scontent;

function openInfoDiag(content) {  
      scontent = content;       
      Dialog.info(scontent + "  计时：" +  timeout + " 秒   ",
               {width:260, height:150, showProgress: true});
      setTimeout(infoTimeOut, 1000);
      startDiaglog = true;   
}
   
function infoTimeOut() {  
      if (startDiaglog){
         if (timeout > 4)
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


var replyW;
  function openReplyWindow(pmessageId){
	  killreplyW();
      initReplyForm(pmessageId);
      openWindowForReply(pmessageId);
   }   
   
    function openQuoteWindow(pmessageId){
       if (initQuoteForm(pmessageId)){
    	   killreplyW();     
           openWindowForReply(pmessageId);
       }
   }       

    function openWindowForReply(pmessageId){              
    	var formBody = loadCache('formBody');
      if (formBody != null)
          document.getElementById('formBody').value = formBody;	
    	
        if (replyW == null) {
           replyW = new Window({className: "mac_os_x", width:630, height:480, title: " Reply "}); 
           replyW.setContent("replyDiv",false, false);
           replyW.showCenter();
    	
    	    
    	   var myObserver = {
            onClose: function(eventName, myreplyW) {    	  
            	if (myreplyW == replyW){
            	   killreplyW();
                   Windows.removeObserver(this);
            	}
            }
           }
           var myObserver2 = {
            onMaximize: function(eventName, myreplyW) {    	  
              if (myreplyW == replyW){        	        	
                 $('formBody').setAttribute("cols", "80");
       	         $('formBody').setAttribute("rows", "23");
              }
            }
           }
           
           
         Windows.addObserver(myObserver);
         Windows.addObserver(myObserver2);
         } 
       }   
   
function initReplyForm(pmessageId){
   	 $('parentMessageId').value =pmessageId;
   	 $('formBody').setAttribute("cols", "63");
   	 $('formBody').setAttribute("rows", "14");   	 
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
  
  
   function initQuoteForm(pmessageId){
     var quotetexts = getSelText() + "";
   	 if (quotetexts == ""){
    	 Dialog.alert("请首先用鼠标选择本帖中需要回复的文字", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});   	    
      	return false;
   	 }   	 
   	 quotetexts = quotetexts.substring(0, 120) + " ... ";
   	 $('parentMessageId').value =pmessageId;
   	 if ($("subject_" + pmessageId) != null)
   	     $('replySubject').value =  $("subject_" + pmessageId).innerHTML; 
   	 $('formBody').setAttribute("cols", "63");
   	 $('formBody').setAttribute("rows", "14");
   	 
     $('formBody').value="[quote author=@"+$("author_" + pmessageId).innerHTML+" date="+$("creationDate_" + pmessageId).innerHTML+"]"+ quotetexts +"[/quote]";
   	 var regExNewLine = /<br>/g;
   	 $('formBody').value = $('formBody').value.replace(regExNewLine, "\n");
   	 var regEx = /<[^>]*>/g; 
   	 $('formBody').value = $('formBody').value.replace(regEx, "");
   	 return true; 
   	 
  }
   
   function killreplyW(){
      if (replyW != null){          
           replyW.close();    
           replyW = null;
     }
   }
   
   function callLogin(){    
     infoDiagClose();  
     killreplyW();           
     loginW('messageReply');
   }
   
   function goAfterLogged(fromFormName){
     $(fromFormName).submit();
     if (formSubmitcheck)
        openInfoDiag("登录成功，继续提交...");   
   }
   
   var anchor ;
   function forwardNewPage(fmainurl, fmainPars, anchor){	
	  $('formBody').value = ""; //clear input
	  clearCache('formBody');     
      fmainurl = fmainurl + "/" + "noheaderfooter"
      var pars = fmainPars; 
      this.anchor = anchor;      
      new Ajax.Updater("messageListBody", fmainurl, 
  	    {method: 'get', parameters: pars, evalScripts: true, onComplete: showReplyResponse});  
  	   
   }
   
   function showReplyResponse(transport){    
      infoDiagClose();         
      window.location.href= '#'+ anchor;          
   }
   
   function callbackSubmit(){
       killreplyW();  
       openInfoDiag("正在提交...如计时后无响应，注意是否登录或网络是否正常连接? ");                
   }
  
   
   var loadformjs = function(){
	   if (typeof(checkPost) == 'undefined') {
	    $LAB
	    .script(getContextPath() + '/forum/js/form.js').wait()
	    .wait(function(){
	       loadPostjs();
	    })     
	   }else
	     loadPostjs();
	   
	 }

   function loadPostjs(){
	   if (typeof(openInfoDiag) == 'undefined') {
	     $LAB
	      .script(getContextPath() +'/message/js/messageEdit.js').wait()
	      .wait(function(){
	          setObserve();
	      
	      })      
	   }else
	      setObserve();
	        
	 }

	 function setObserve(){
	  if(typeof(Ajax) != "undefined"){
	       $('messageReply').observe("submit", callbackSubmit);
	   }   
	 }

	 function openUploadWindowStart(url){
	     if (isLogin){       
	       loadWLJSWithP(url, openUploadWindow);       
	     }else{
		        myalert("只有登录后才能打开上传页面");
		        return;	    	 
	     }     	     
	  }
	 
		
	 function loadAcJS(thisId){
	   if (typeof(ac) == 'undefined') {
	      $LAB
	      .script( '/common/js/autocomplete.js')
	      .wait(function(){
	           ac(thisId,getContextPath());
	      })     
	   }else
	       ac(thisId,getContextPath());
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

