function GetXmlHttpObject()
{
    var A=null; 
    try { 
        A=new ActiveXObject("Msxml2.XMLHTTP"); 
    } 
    catch(e) { 
        try { 
            A=new ActiveXObject("Microsoft.XMLHTTP"); 
        } 
        catch(oc) { 
            A=null; 
        } 
    }  
    if ( !A && typeof XMLHttpRequest != "undefined" ) { 
        A=new XMLHttpRequest(); 
    } 
     return A; 
}


	function load(url, callback) {
		var xhr =GetXmlHttpObject();
		xhr.onreadystatechange = ensureReadiness;
		function ensureReadiness() {
			if(xhr.readyState < 4) {
				return;
			}
			
			if(xhr.status !== 200) {
				return;
			}

			// all is well	
			if(xhr.readyState === 4) {
				callback(xhr);
			}			
		}
		
		xhr.open('GET', url, true);
		xhr.setRequestHeader('Referer', window.location.href);
		xhr.setRequestHeader('X-Requested-With','XMLHttpRequest');
		xhr.send('');
	
	}



function createCookie(name,value,seconds) {
	var date = new Date();
	if (seconds) 
		date.setTime(date.getTime()+(seconds*1000));
	else
		date.setTime(date.getTime()-10000);

	var expires = "; expires="+date.toGMTString();
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function eraseCookie(name) {
	createCookie(name,"",-1);
}


function decode64(input) {
     var output = "";
     var chr1, chr2, chr3 = "";
     var enc1, enc2, enc3, enc4 = "";
     var i = 0;
     input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
     var keyStr = "ABCDEFGHIJKLMNOP" +
               "QRSTUVWXYZabcdef" +
               "ghijklmnopqrstuv" +
               "wxyz0123456789+/" +
               "=";     
     do {
        enc1 = keyStr.indexOf(input.charAt(i++));
        enc2 = keyStr.indexOf(input.charAt(i++));
        enc3 = keyStr.indexOf(input.charAt(i++));
        enc4 = keyStr.indexOf(input.charAt(i++));
        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;
        output = output + String.fromCharCode(chr1);
        if (enc3 != 64) {
           output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
           output = output + String.fromCharCode(chr3);
        }
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
     } while (i < input.length);
     return unescape(output);
  }


function checkmsg(url){
	   if( readCookie("newMessageW") == "disable")
             return;             
	    load(url, function(xhr) {	   	            	    	     
	    	     var responseT = xhr.responseText;
	    	     if(responseT.indexOf("div")==-1){
	    	     	 clearPopUP();
	    	     }else{
	    	       document.getElementById("msg_content").innerHTML = xhr.responseText; 	           
               Message.init();
             }
				 
			});
}

function disablePopUPWithID(ID,seconds){
    disablePopUP(seconds);    
}

function disablePopUP(seconds){
      createCookie("newMessageW","disable",seconds);
      clearPopUP();
}	
function clearPopUP(){
	 document.getElementById("msg_content").innerHTML = "";	
	 Message.close();
}

var Message={
  set: function() {//最小化与恢复状态切换
  var set=this.minbtn.status == 1?[0,1,'block',this.char[0],'最小化']:[1,0,'none',this.char[1],'恢复'];
  this.minbtn.status=set[0];
  this.win.style.borderBottomWidth=set[1];
  this.content.style.display =set[2];
  this.minbtn.innerHTML =set[3]
  this.minbtn.title = set[4];
  this.win.style.top = this.getY().top;
},
close: function() {//关闭
  document.getElementById("isNewMessage").style.display = 'none';
  window.onscroll = null;
},
setOpacity: function(x) {//设置透明度
  var v = x >= 100 ? '': 'Alpha(opacity=' + x + ')';
  this.win.style.visibility = x<=0?'hidden':'visible';//IE有绝对或相对定位内容不随父透明度变化的bug
  this.win.style.filter = v;
  this.win.style.opacity = x / 100;
},
show: function() {//渐显
  clearInterval(this.timer2);
  var me = this,fx = this.fx(0, 100, 0.1),t = 0;
  this.timer2 = setInterval(function() {
  t = fx();
  me.setOpacity(t[0]);
  if (t[1] == 0) {clearInterval(me.timer2) }
     },6);//10 to 6
  },
   fx: function(a, b, c) {//缓冲计算
   var cMath = Math[(a - b) > 0 ? "floor": "ceil"],c = c || 0.1;
   return function() {return [a += cMath((b - a) * c), a - b]}
  },
  getY: function() {//计算移动坐标
  var d = document,b = document.body, e = document.documentElement;
  var s = Math.max(b.scrollTop, e.scrollTop);
  var h = /BackCompat/i.test(document.compatMode)?b.clientHeight:e.clientHeight;
  var h2 = this.win.offsetHeight;
  return {foot: s + h + h2 + 2+'px',top: s + h - h2 - 2+'px'}
  },
  moveTo: function(y) {//移动动画
     clearInterval(this.timer);
     var me = this,a = parseInt(this.win.style.top)||0;
     var fx = this.fx(a, parseInt(y));
     var t = 0 ;
     this.timer = setInterval(function() {
     t = fx();
     me.win.style.top = t[0]+'px';
     if (t[1] == 0) {
     clearInterval(me.timer);
     me.bind();
  }
   },6);//10 to 6
  },
bind:function (){//绑定窗口滚动条与大小变化事件
  var me=this,st,rt;
   window.onscroll = function() {
   clearTimeout(st);
   clearTimeout(me.timer2);
   me.setOpacity(0);
   st = setTimeout(function() {
   me.win.style.top = me.getY().top;
   me.show();
  },100);//600 mod 100
};

window.onresize = function (){
   clearTimeout(rt);
   rt = setTimeout(function() {me.win.style.top = me.getY().top},100);
  }
  },
init: function() {//创建HTML
   function $(id) {return document.getElementById(id)};
   this.win=$('isNewMessage');
   var set={minbtn: 'msg_min',closebtn: 'msg_close',title: 'msg_title',content: 'msg_content'};
   for (var Id in set) {this[Id] = $(set[Id])};
   var me = this;
   this.minbtn.onclick = function() {me.set();this.blur()};
   this.closebtn.onclick = function() {me.close()};
   this.char=navigator.userAgent.toLowerCase().indexOf('firefox')+1?['_','::','×']:['0','2','r'];//FF不支持webdings字体
   this.minbtn.innerHTML=this.char[0];
   this.closebtn.innerHTML=this.char[2];
   
   setTimeout(function() {//初始化最先位置
      me.win.style.display = 'block';
      me.win.style.top = me.getY().foot;
      me.moveTo(me.getY().top);
      },0);
    return this;
   }
  };

function getContextPath(){
  if (document.getElementById('contextPath') == null){
     return "";
  }
   return document.getElementById('contextPath').value;  
}

var initLastPost = function(e){
 TooltipManager.init('ThreadLastPost', 
  {url: getContextPath()+"/query/threadLastPostViewAction.shtml", 
   options: {method: 'get'}},
   {className:"mac_os_x", width:150});
    TooltipManager.showNow(e);   
}


function loadAcJS(thisId){
  if (typeof(ac) == 'undefined') {
     $LAB
     .script(getContextPath() + 'common/js/autocomplete.js')
     .wait(function(){
          ac(thisId, getContextPath());
     })     
  }else
      ac(thisId, getContextPath());
}

function threadNewList(){
    new Ajax.Updater('frame', '/query/threadNewList.shtml?noheader=on&tablewidth=288&count=10', { method: 'get' });	
}
function messageNewList(){
    new Ajax.Updater('frame', '/query/messageNewList.shtml?noheader=on&tablewidth=288&count=10', { method: 'get' });	
}
function digMessage(id)
 {            
    	var pars = 'messageId='+id;   
        new Ajax.Updater('digNumber_'+id, '/query/updateDigCount.shtml', { method: 'get', parameters: pars });
        $('textArea_'+id).update("已赞");      
}
function viewcount(threadId, sId)
{            	
	 var pars = 'thread=' + threadId + "&sId=" + sId;   
   new Ajax.Updater({success: 'viewcount'}, getContextPath() +'/query/viewThread.shtml', { method: 'get', parameters: pars });
}
function iFrameHeight(framename) { 
var ifm= document.getElementById(framename); 
var subWeb = document.frames ? document.frames[framename].document : ifm.contentDocument; 
if(ifm != null && subWeb != null) { 
ifm.height = subWeb.body.scrollHeight; 
} 
} 
