

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

function lastPost(threadId, messageId){
   var pars = 'messageId=' +messageId ;
   new Ajax.Updater(threadId, contextpath + '/forum/lastPost.shtml', { method: 'get', parameters: pars });
   
}

function addStickyList(appendPos, forumId){
   var pars = 'forumId=' + forumId;
   new Ajax.Request(contextpath + '/forum/stickyList.shtml', {
            method: 'get', parameters: pars,evalScripts:false,
            onComplete: function(response) {
                var stickList = response.responseText;
               $(appendPos).insert({after: stickList});
               //alert(stickList);
              // $('ejiaA1_title_tr').appendChild(stickList);
                //document.getElementById('ejiaA1_title_tr').appendChild(stickList);
           }
           });
}
////jQuery.get("stickyList.shtml",{ forumId: "<bean:write name="forum" property="forumId"/>"},function(data){
//    	jQuery("#ejiaA1 tr:first").after(data);
 		
	//}); 
	
	//jQuery.getScript("<%=request.getContextPath()%>/forum/stickyList.shtml?getType=script");
	   


//ejiaA1("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");

function ejiaA1(o,a,b,c,d,lastVisitDate){
  var t=document.getElementById(o).getElementsByTagName("tr");
  for(var i=1;i<t.length;i++){
	if (lastVisitDate<t[i].id){ 
	  		 t[i].style.backgroundColor="#EFE0E0";
	  		  continue;
	 }
    t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
    t[i].onclick=function(){
           if(this.x!="1"){
                 this.x="1";//本来打算直接用背景色判断，FF获取到的背景是RGB值，不好判断
                 this.style.backgroundColor=d;
           }else{
                 this.x="0";
                 this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
           }
    }
    t[i].onmouseover=function(){
          if(this.x!="1")this.style.backgroundColor=c;
    }
    t[i].onmouseout=function(){
          if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
    }
  }
}

function outMultiPages(e, path,  messageNumber,  threadId,  count){
      var result = generateLinkString(path,  messageNumber,  threadId,  count);
      $(e).innerHTML = result;
}

//pageShow
THRESHOLD = 3;
 function generateLinkString( path,  messageNumber,  threadId,  count){		
		 url = "<a href=\"" + path + "/" + threadId;
		 var pageNumber = parseInt(messageNumber / count);
		  
		 if (pageNumber > 0) {
			var link = new Array();
			link[0] = url + "\">1</a>&nbsp;";
			if (pageNumber >= 1) {
				for (var i = 1; i <= pageNumber ;i++ ){
					var s = "\">" + (i + 1) + "</a>&nbsp;";
					link[i] = url + "/" + count * (i) + s;
				}
			}
		}	
		if (link.length > (2 * THRESHOLD + 1))
				link = splitLink(link);
		var text="<br />[&nbsp;" ;
			
		for (var i = 0; i <link.length;i++ ){
				text = text + link[i];
		}		
		text = text + "]" ;			
		return text;
	}
function  splitLink(link) {	
		var length = link.length;		
		var newlength = 2 * THRESHOLD + 1;
		var newlink = new Array(newlength);
		
		for (var i = 0; i < THRESHOLD;i++ ){
			newlink[i] = link[i];
			newlink[newlength - i - 1] = link[length - i - 1];
		}
		newlink[THRESHOLD] = "...&nbsp;";
		return newlink;
}
//pageShow end


var initTooltipWL = function (e){
  TooltipManager.init("tooltip", {url: "", options: {method: 'get'}}, {showEffect: Element.show, hideEffect: Element.hide,className: "mac_os_x", width: 250, height: 100});
  TooltipManager.showNow(e);   
}
  

var initLastPost = function(e){
 TooltipManager.init('ThreadLastPost', 
  {url: contextpath+"/query/threadLastPostViewAction.shtml", 
   options: {method: 'get'}},
   {className:"mac_os_x", width:150});
    TooltipManager.showNow(e);   
}


var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
 TooltipManager.showNow(e);   
}

