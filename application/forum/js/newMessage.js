
  var newMessageW;
  function popUpNewMessageWithID(ID){     
     if( readCookie(ID) == "disable")
        return;
     popUpNewMessage();
        
  }
  
  function popUpNewMessage(){
   if( readCookie("popw") == "disable")
	        return;
   if (newMessageW == null) {
       newMessageW = new Window({className: "mac_os_x", width:250, height:150, title: " 3秒后自动关闭 ", closable: false}); 
       newMessageW.setContent("isNewMessage",false, false);                  
       newMessageW.showCenter();	
       
   
       var myObserver = {
        onClose: function(eventName, mywinMessage) {    	  
          if (mywinMessage == newMessageW){        	
            newMessageW = null;
            Windows.removeObserver(this);
          }
        }
      }
      Windows.addObserver(myObserver);  	 
	} else
	  newMessageW.showCenter();
	setTimeout(clearPopUP, 3*1000);	
   }     
      
   
   function clearPopUPWithID(ID){
      createCookie(ID,"",-1);   
      clearPopUP();
   }
  
   function clearPopUP(){
     if (newMessageW != null){   
           newMessageW.close();    
           newMessageW = null;                  
     }     
     $('isNewMessage').innerHTML = "";
  }
  
   function disablePopUPWithID(ID, seconds){       
       createCookie("popw","disable",seconds);  
       clearPopUP();     
       document.title ="";
    }
    
      
var scrl = " 论坛有新帖啦... ";
function scrlsts() {
 scrl = scrl.substring(1, scrl.length) + scrl.substring(0, 1);
 document.title = scrl;
 setTimeout("scrlsts()", 600);
}            