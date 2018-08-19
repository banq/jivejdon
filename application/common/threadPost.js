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
    	  //发完贴计时5秒刷新。
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



 
 
function callbackSubmit(){
	if (formSubmitcheck)
         openInfoDiag("正在提交...如没有反应，请先保存要发表内容，登录后再发表"); 	

}

function callLogin(){    
      infoDiagClose();  
      loginW('messageNew');
}
   
function goAfterLogged(fromFormName){
     $(fromFormName).submit();
     openInfoDiag("登陆成功，继续提交...");   
}
   
function forwardNewPage(fmainurl, fmainPars, anchor){  
     // infoDiagClose();       
      var url = fmainurl + fmainPars + "#" + anchor;   
     // window.alert("url=" + url);    
      clearCache('formBody');
      window.location.href =  url;
      
}

function clearCache(objId){
  try{
  	if(window.localStorage){
  	   window.localStorage.removeItem(objId);
    }else if (window.clipboardData) { 
       window.clipboardData.clearData();  
	  }
  }catch(e){}			
}

