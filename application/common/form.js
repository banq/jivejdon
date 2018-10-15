
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

var uploadW;
  function openUploadWindow(url){
   if (typeof(TooltipManager) == 'undefined') 
       loadWLJS(nof);
       
    if (uploadW == null) {
       uploadW = new Window({className: "mac_os_x", width:450, height:300, title: " Upload ", closable: false}); 
       uploadW.setURL(url);
       uploadW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myuploadW) {    	  
          if (myuploadW == uploadW){        	
            appendUploadUrl();
            appendUploadAttach();
            uploadW = null;
            Windows.removeObserver(this);
          }
         }
        }
        Windows.addObserver(myObserver);
    } else
	  uploadW.showCenter();
   }     
   
   var saveS;
   var attcount;
   function killUploadWindow(surl, attcount){
      this.saveS = surl;
      this.attcount = attcount;      
      if (uploadW != null){  
           uploadW.close();  //this will  enable  appendUploadUrl() or   appendUploadAttach()                    
     }
   }
   
  function appendUploadUrl(){
      if (saveS == null) return;
      saveS = document.getElementById("formBody").value + "\n" + saveS;      	 
      Form.Element.setValue("formBody", saveS);
   }
   
   function appendUploadAttach(){
      if (attcount == null) return;
      var insimag = "";
      for (i = 0; i < attcount; i++)
      {
        var ind = i + 1;
      	var inst = '[img index=' + ind + ']';      	
        insimag = insimag + '<a href="javascript:void(0);" onClick=\'insertString(document.getElementById("formBody"),"'+inst+'")\' title="插入第'+ ind +'个图片" > ' +
             document.getElementById("insertImage").innerHTML + '</a> ';
      }                   
      document.getElementById("attachsize").innerHTML = "有"+ attcount +"个附件:" + insimag;
   }
   
   
  function myalert(errorM){
        if (errorM == null) return;
        alert(errorM);
        //loadWLJSWithP(errorM, myalertD);
  }
  
var myalertD=function(errorM){
    Dialog.closeInfo();    
    Dialog.alert(errorM,
               {width:260, height:150, okLabel: " 确定 "});
}  
  
  
    

  function tag(theTag) {
    var e = document.getElementById("formBody");        
    if (theTag == 'b') {
      insertString(e,"[b][/b]");        
    } else if (theTag == 'i') {
        insertString(e,"[i][/i]");
    } else if (theTag == 'u') {
        insertString(e,"[u][/u]");        
    } else if (theTag == 'code') {
        insertString(e,"\n[code]\n// [/code]");
    } else if (theTag == 'image') {
        var url = prompt("请输入一个图片的URL","http://");
        if (url != null) {
            insertString(e,"[img]" + url + "[/img]");
        }
    } else if (theTag == 'url') {
        var url = prompt("请输入链接的URL","http://");
        var text = prompt("请输入链接文本");
        if (url != null) {
            if (text != null) {
                insertString(e, "[url=" + url + "]" + text + "[/url]");
            } else {
                insertString(e, "[url]" + url + "[/url]");
            }
        }
    }
   }

  var formSubmitcheck = false;      
  function checkPost(theForm) {
           
      saveCache('formBody');
      closeCopy();
      
      if (document.getElementById('forumId_select') != null
        && document.getElementById('forumId_select').value == ""){
    	  alert("页面forum错误，请拷贝备份你的发言后，重新刷新本页");
    	    formSubmitcheck = false;
            return formSubmitcheck;
     }
      
      var body = theForm.body.value.replace(/(^\s*)|(\s*$)/g, ""); 
      var subject = theForm.subject.value.replace(/(^\s*)|(\s*$)/g, ""); 
      if (subject.length  !== 0){          
           formSubmitcheck = true;
      }else{
    	  alert("请输入发言标题" );
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
   
   var timedCopy;
   function startCopy(timeout){
       if (timedCopy == null)
           timedCopy = setInterval(copyBody, timeout);
   }
   
   function closeCopy(){
      clearInterval(timedCopy);
      timedCopy = null;
   }
   

function insertAtCursor(myField, myValue) {
  //IE support
  if (document.selection) {
    myField.focus();
    sel = document.selection.createRange();
    sel.text = myValue;
  }

  //MOZILLA/NETSCAPE support
  else if (myField.selectionStart || myField.selectionStart == '0') {
    var startPos = myField.selectionStart;
    var endPos = myField.selectionEnd;
    myField.value = myField.value.substring(0, startPos)
                  + myValue
                  + myField.value.substring(endPos, myField.value.length);
  } else {
    myField.value += myValue;
  }
}

function insertString(e,stringToInsert) {
	insertAtCursor(e,stringToInsert);
}
//end

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
