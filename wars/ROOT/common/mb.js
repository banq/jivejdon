
    var logged = false;
    
    function setLogged(){
  	    logged = true;
  	    if (typeof(isLogin) != "undefined")
  	       isLogin = true;
  	 }

	function Login(myfunc){
		   if (typeof(loggedURL) == "undefined"){
		   	    alert("loggedURL not define");
		   	    return false;
  		  }

		   var pars  = "j_username=" + document.getElementById('j_username').value + "&j_password=" + document.getElementById('j_password').value
		               + "&rememberMe=" + document.getElementById('rememberMe').value;
		   var url;
		    
		   if (loggedURL.indexOf("?")   >= 0)
			   url = loggedURL + "&" + pars;
		   else
			   url = loggedURL + "?" + pars;
		   
		  var res = httpGet(url);		   
			if (res.indexOf("if(setLogged)setLogged()")   >=0){
					  setLogged();
					  return true;
			}else{
				    delloginCookies();
					  alert("用户名或密码错误");					
					  return false;
			}
	}
	
	function delloginCookies(){  	    
    	eraseCookie("rememberMe");
  	  	eraseCookie("username");
   		eraseCookie("password");   		
  	}	
	
	function httpGet(theUrl)
    {
    var xmlHttp = null;

    xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false );
    xmlHttp.send( null );
    return xmlHttp.responseText;
    }
	
	
	function checkmsg(url){
	   if( readCookie("newMessageW") == "disable")
             return;             
	    load(url, function(xhr) {	   
	             
				 document.getElementById("isNewMessage").innerHTML = xhr.responseText ;
			});
	}
	
    
	function load(url, callback) {

		var xhr;
		
		if(typeof XMLHttpRequest !== 'undefined') xhr = new XMLHttpRequest();
		else {
			var versions = ["Microsoft.XmlHttp", 
			 				"MSXML2.XmlHttp",
			 			    "MSXML2.XmlHttp.3.0", 
			 			    "MSXML2.XmlHttp.4.0",
			 			    "MSXML2.XmlHttp.5.0"];
			 
			 for(var i = 0, len = versions.length; i < len; i++) {
			 	try {
			 		xhr = new ActiveXObject(versions[i]);
			 		break;
			 	}
			 	catch(e){}
			 } // end for
		}
		
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


function disablePopUPWithID(ID,seconds){
    disablePopUP(seconds);
}

function disablePopUP(seconds){
      createCookie("newMessageW","disable",seconds);
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


function getContextPath(){
	  if (document.getElementById('contextPath') == null){
          return "";
	  }
	   return document.getElementById('contextPath').value;  
	}


function viewcount(threadId, sId)
{            
	var pars = 'thread=' + threadId + "&sId=" + sId;   
	load(getContextPath() +'/query/viewThread.shtml?' + pars, function(xhr) {	            
		 //document.getElementById("viewcount").innerHTML = xhr.responseText ;
	});       
}

function detectRes(url){
	
	if (screen.width>=1366)
	{
		if(readCookie("inMobile") == null){
    		if (confirm("您屏幕可显示1366x768 是否转到大屏幕？")){
				eraseCookie("inMobile");
     		    window.location.href=url;
			}else
			    createCookie("inMobile","on",3600*24);
		}
	}
	
	
}

 function digMessage(id)
    {            
    	var pars = 'messageId='+id;   
        new Ajax.Updater('digNumber_'+id, getContextPath() +'/query/updateDigCount.shtml', { method: 'get', parameters: pars });        
    }
