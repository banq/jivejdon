
function loadPrototypeJS(myfunc){
  if (typeof(AJAX) == 'undefined') {
     $LAB
     .script('//cdn.jdon.com/common/js/prototype.js')
     .wait(function(){
          myfunc();          
     })     
  }else
     myfunc()
}


//initTooltipWL in login.js
var initTooltipW = function (){
  TooltipManager.init("tooltip", {url: "", options: {method: 'get'}}, {showEffect: Element.show, hideEffect: Element.hide,className: "mac_os_x", width: 250, height: 100});   
}

var initUsersW = function (e){
 TooltipManager.init('Users', 
  {url: getContextPath() +'/account/accountProfile.shtml?winwidth=250', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:260});     
 TooltipManager.showNow(e);
}


var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
 TooltipManager.showNow(e);   
}

 //loadWLJS(qtCode) 
var qtCode = function(threadId){
	new Window({url: getContextPath()+"/common/barcode.jsp?threadId=" + threadId, className: "mac_os_x", width:300, height:300,title: " 微信扫描阅读本贴 "  }).showCenter();
}
////loadWLJS(mark)
var mark = function(){  
	if( readCookie("popw") == "disable")
        return;
 if (typeof(TooltipManager) == 'undefined') 
       loadWLJS(nof);
    new Window({url: getContextPath() +"/common/bookmark.jsp", className: "mac_os_x", width:300, height:120,title: "  将本主题收藏如下站点  " }).showCenter();
}

////loadWLJSWithP(url, openShortmessageWindow)
var openShortmessageWindow = function(url){
     if (!isLogin){//login defined in .common/security.jsp        
        Dialog.alert("请先登陆", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
        return false;
    }
   openPopUpWindow("发送消息", url);
}


function addfavorite( title) {
 var url = location.href;
 if (document.all) {
   window.external.addFavorite(url,title); 
 }else if (window.sidebar) {
   window.sidebar.addPanel(title, url, ""); 
 }else{
   alert('Press ctrl+D to bookmark ');
 }
}






function goToAnotherPageREST(contextPath, count)
{
	var page = document.getElementById("pageToGo").value * 1;
	var path;

	if (!isNaN(page) && page > 0) {	 
	    var ttt = (page-1) * count;
	    if (ttt != 0)	    	
		   path = contextPath + "/" + ttt;
	    else
	       path = contextPath ;
		document.location = path;
	}
	
} 

function goToAnotherPage(contextPath, count)
{
	var page = document.getElementById("pageToGo").value * 1;

	if (!isNaN(page) && page > 0) {
	    var mychar = "?";
	    if (contextPath.toLowerCase().indexOf("?") > -1)
	      mychar = "&";
		var path = contextPath + mychar + "count=" + count + "&start=" + ((page-1) * count) ;
		document.location = path;
	}
	
} 

function viewcount(threadId, sId)
{            
	var pars = 'thread=' + threadId + "&sId=" + sId;   
    new Ajax.Updater({success: 'viewcount'}, getContextPath() +'/query/viewThread.shtml', { method: 'get', parameters: pars });
}


 function digMessage(id)
    {            
    	var pars = 'messageId='+id;   
        new Ajax.Updater('digNumber_'+id, getContextPath() +'/query/updateDigCount.shtml', { method: 'get', parameters: pars });
        $('textArea_'+id).update("已赞");
        
    }
    
function stickyThread(threadId, ui_state,action,forumId)
	{
		var pars = 'threadId='+threadId+'&ui_state='+ui_state+'&action='+action; 
		new Ajax.Request(  
            getContextPath() +'/admin/stickyThread.shtml',  
            {  
                method:'post',  
                parameters:pars,  
                asynchronous:true  
            }  
        );  
		alert("操作成功");
		window.location = getContextPath() +'/forum/' + forumId;
	}
	
function checkUserIfOnline(username,messageId)
	{
		var pars = 'username='+username; 
		new Ajax.Updater('messageOwnerOnline_'+messageId, getContextPath() +'/onlineCheck.jsp',  
            {  
               method:'get',  
               parameters:pars
            }
        );
	}


var popupW;
function openPopUpWindow(wtitlename, url){
 if (typeof(TooltipManager) == 'undefined') 
       loadWLJS(nof);
       
    if (popupW == null) {       
       popupW = new Window({className: "mac_os_x", width:600, height:380, title: wtitlename}); 
       popupW.setURL(url);
       popupW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myW) {    	  
          if (myW == popupW){        	        	
            popupW = null;   
            Windows.removeObserver(this);
          }
        }
       }
     Windows.addObserver(myObserver);
     } 
 }     
	


function hotkeys(){
   if (typeof(Ajax) != "undefined")
        new Ajax.Updater('hotkeys', getContextPath() +'/query/hotKeys.shtml?method=hotkeys', { method: 'get' });
}

function approveList(){
   if (typeof(Ajax) != "undefined")
        new Ajax.Updater('approved', getContextPath() +'/query/threadApprovedTagsList.shtml?start=0&count=6', { method: 'get' });
}                
 
function hotList(){
   if (typeof(Ajax) != "undefined"){
        var pars = "";
        new Ajax.Updater('newPosts', getContextPath() +'/query/hotlist.shtml?dateRange=180&tablewidth=400&length=10&count=10', { method: 'get', parameters: pars });
   }
}

function newpostList(){
	   var pars = "count=12&length=12&tablewidth=400";
	   new Ajax.Updater('newPosts', getContextPath() +'/query/popularlist.shtml', { method: 'get', parameters: pars });   
}

function stickyList(){
	   var pars = "count=10&length=10&tablewidth=400";
	   new Ajax.Updater('newPosts', getContextPath() +'/query/stickyList.shtml', { method: 'get', parameters: pars });   
}


var loadWPostjs =function(mId){  
  if (typeof(openReplyWindow) == 'undefined') {
   $LAB
   .script(getContextPath() + '/forum/js/postreply.js').wait()
   .wait(function(){
	   loadPostReply(mId);
	     openReplyWindow(mId);
   })   
  }else{
	  loadPostReply(mId);
	     openReplyWindow(mId);
  }
}

var loadPostReplyOk = false;
function loadPostReply(mId, quote){	   	  
	   if (loadPostReplyOk) return;
	   var replySubject = document.getElementById("replySubject").value;
	   var pars = "parentMessage.messageId=" + mId;
	   new Ajax.Updater('reply', getContextPath() +'/forum/messageReplyAction.shtml', {
		   method: 'get',
		   asynchronous: false, 
		   evalJS: true, evalScripts:true,
		   parameters: pars })	   	   
	   document.messageReplyForm.subject.value=replySubject;
	   loadPostReplyOk = true;
}

var loadQPostjs =function(mId){  
  if (typeof(openReplyWindow) == 'undefined') {
   $LAB
   .script(getContextPath() + '/forum/js/postreply.js').wait()
   .wait(function(){
	   loadPostReply(mId);      
	   openQuoteWindow(mId);
   })   
  }else{
	  loadPostReply(mId);
	  openQuoteWindow(mId);
  }
}


var hasDisplayNeedLoad = false;
function isDisplayNeedLoad(divid){ //need prototype.js
	  if (hasDisplayNeedLoad) return false;	

      var WindowSize = Class.create({
        	    width: window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth),
	            height: window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight)
      });		
	  var ws = new WindowSize();
	  var winheight = ws.height;     
	  var arr = $(divid).cumulativeOffset();
	  var oTop = arr[1];
	    
	  var scrolltop = document.documentElement.scrollTop ? 
              document.documentElement.scrollTop : 
              document.body.scrollTop;	  
	  if ((oTop-scrolltop)<winheight ){
	  	  hasDisplayNeedLoad = true;
	      return true;
	  }
	  return false;
}


function loadAcJS(thisId){
  if (typeof(ac) == 'undefined') {
     $LAB
     .script('/common/js/autocomplete.js')
     .wait(function(){
          ac(thisId, getContextPath());
     })     
  }else
      ac(thisId, getContextPath());
}

