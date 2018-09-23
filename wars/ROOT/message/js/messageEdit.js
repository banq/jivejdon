
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


function callbackSubmit(){
    openInfoDiag("已经提交...如计时后无响应，请备份数据后刷新页面后再提交");    

}

function forwardNewPage(fmainurl, fmainPars, anchor){
      infoDiagClose();         
      $('formBody').value = ""; //clear input                         
      clearCache('formBody');
      var url = fmainurl + "/nocache" +fmainPars + "#" + anchor;
      window.location.href =  url;
      
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
