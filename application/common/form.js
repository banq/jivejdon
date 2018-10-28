
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
      if (subject.length  !== 0){       
          if (subjectold != subject){
            formSubmitcheck = true;
            theForm.formButton.disabled=true;
          }else 
            alert("发音重复" );
            subjectold = subject;
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
